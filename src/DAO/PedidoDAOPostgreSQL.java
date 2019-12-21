package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.PedidoDTO;
import View.PedidoNaoEncontradoException;

public class PedidoDAOPostgreSQL {

	private ArrayList<ArrayList<String>> tablePedido = new ArrayList<ArrayList<String>>();
	private PedidoDTO pedidoDTO = new PedidoDTO();
	private ConnectionSingleton connectionSingleton;
	private PedidoDAO pedidoDAO = new PedidoAdapterXML();//BACKUP LOCAL PARA CASO A CONEXÃO CAIR

	public PedidoDAOPostgreSQL() {
		checkConnection();
		
		//recoverData();//REAJUSTA OS DADOS QUE PODEM TER SIDO PERDIDOS POR ALGUMA QUEDA NA CONEXÃO
	}

	public void finishConnection(ArrayList<ArrayList<String>> table) {
		connectionSingleton.closeConnection();
	}

	public ArrayList<ArrayList<String>> checkConnection() {
		connectionSingleton = ConnectionSingleton.getInstance();
		return tablePedido;
	}

	public void createPedido(PedidoDTO pedidoDTO) {

		String IDPedido = "'"+Integer.toString(pedidoDTO.getIDPedido())+"'";
		String CPF = "'"+pedidoDTO.getCPFDoCliente()+"'";
		String Total = "'"+Double.toString(pedidoDTO.getPrecoTotal())+"'";
		String Status = "'"+pedidoDTO.getStatus()+"'";
		String IDsPizza = "";
		for (String registryPizza: pedidoDTO.getIDsPizzas()) {
			IDsPizza += registryPizza + "@";
		}
		String query = "(" + IDPedido + ", " + CPF + ", " + Total + ", " + Status + ", " + "'"+IDsPizza +"'" + ")";

		try {

			pedidoDAO.createPedido(pedidoDTO);//BACKUP

			if (connectionSingleton.isConnectionValid())
			connectionSingleton.getConnection().executeUpdate("insert into pedido (idpedido, cpfcliente, total, status, idspizza) values " + query);

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}

	public void deletePedido(PedidoDTO pedidoDTO) {

		String IDPedido = "'"+Integer.toString(pedidoDTO.getIDPedido())+"'";
		String CPF = "'"+pedidoDTO.getCPFDoCliente()+"'";

		try {

			pedidoDAO.deletePedido(pedidoDTO);//BACKUP

			if (connectionSingleton.isConnectionValid()) 
			connectionSingleton.getConnection().executeUpdate("delete from pedido where idpedido = " + IDPedido + " and cpfcliente = " + CPF);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public PedidoDTO readPedido(PedidoDTO pedidoDTO) throws PedidoNaoEncontradoException {

		String IDPedido = "'"+Integer.toString(pedidoDTO.getIDPedido())+"'";
		String CPF = "'"+pedidoDTO.getCPFDoCliente()+"'";

		try {

			if (!connectionSingleton.isConnectionValid()) {
				return pedidoDAO.readPedido(pedidoDTO);//RECUPERA DO BACKUP
			} else {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from pedido where idpedido = " + IDPedido + " and cpfcliente = " + CPF);
				if (set.next()) {
					this.pedidoDTO.setCPFDoCliente(set.getString(2));
					String IDsPizza[] = set.getString(5).split("@");
					this.pedidoDTO.setIDsPizzas(IDsPizza);
					this.pedidoDTO.setStatus(set.getString(4));
					this.pedidoDTO.setPrecoTotal(set.getDouble(3));
					this.pedidoDTO.setIDPedido(set.getInt(1));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this.pedidoDTO;
	}

	public void updatePedido(PedidoDTO pedidoDTO) {

		String IDPedido = "'"+Integer.toString(pedidoDTO.getIDPedido())+"'";
		String CPF = "'"+pedidoDTO.getCPFDoCliente()+"'";
		String Status = "'"+pedidoDTO.getStatus()+"'";

		try {

			pedidoDAO.updatePedido(pedidoDTO);//BACKUP

			if (connectionSingleton.isConnectionValid()) {
			ResultSet set = connectionSingleton.getConnection().executeQuery("select * from pedido where idpedido = " + IDPedido + " and cpfcliente = " + CPF);
			if (set.next()) {
				connectionSingleton.getConnection().executeUpdate("update pedido set status = " + Status + " where idpedido = " + IDPedido + " and cpfcliente = " + CPF);
			}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public PedidoDTO tablePedido() {

		String pedidos = "";

		try {

			if (!connectionSingleton.isConnectionValid()) {
				return pedidoDAO.tablePedido();
			} else {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from pedido order by idpedido");
				while (set.next()) {
					pedidos += set.getString(2) + "/" + set.getString(5) + "/" + set.getInt(1) + "#";
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (pedidos.equals("")==false) {
			String table[] = pedidos.split("#");
			this.pedidoDTO.setTablePedido(table);
			return this.pedidoDTO;
		} else {
			return null;
		}
	}

	public int IDPedido() {

		int id = 0;
		try {

			if (!connectionSingleton.isConnectionValid()) {
				return pedidoDAO.IDPedido();
			} else {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from pedido order by idpedido");
				while (set.next()) {
					if (set.isLast())
						id = set.getRow();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id+1;
	}

	public void recoverData () {

		if (connectionSingleton.isConnectionValid()) {
			try {
				ArrayList<ArrayList<String>> table = pedidoDAO.checkConnection();
				//INSERE OS DADOS QUE NÃO FORAM INSERIDOS NO BD RELACIONAL MAS FICARAM GUARDADOS NO BACKUP
				int last = 0;
				ResultSet setInsert = connectionSingleton.getConnection().executeQuery("select * from pedido order by idpedido");
				while (setInsert.next()) {
					if (setInsert.isLast()) {
						last = setInsert.getRow();
						String query = "";
						for (int i = last; i < table.size(); i++) {
							query = "(" + "'"+table.get(i).get(4)+"'" + ", " + "'"+table.get(i).get(0)+"'" + ", " + "'"+table.get(i).get(2)+"'" + ", " + "'"+table.get(i).get(3)+"'" + ", " + "'"+table.get(i).get(1)+"'" +  ")";
							connectionSingleton.getConnection().executeUpdate("insert into pedido (idpedido, cpfcliente, total, status, idspizza) values " + query);
						}
					}
				}
				//DELETA OS DADOS QUE NÃO FORAM DELETADOS NO BD RELACIONAL MAS FORAM APAGADOS DO BACKUP
				String CPFs = "";
				String IDs = "";
				ResultSet setDelete = connectionSingleton.getConnection().executeQuery("select * from cliente order by nome");
				while (setDelete.next()) {
					for (int i = 0; i < table.size(); i++) {
						CPFs += "'"+table.get(i).get(0)+"'" + ", ";
						IDs += "'"+table.get(i).get(4)+"'" + ", ";
					}
					connectionSingleton.getConnection().executeUpdate("delete from pedido where cpfcliente not in (" + CPFs + ") and idpedido not in (" + IDs + ")");
				}
				//ATUALIZA OS DADOS QUE NÃO FORAM ATUALIZADOS NO BD RELACIONAL MAS FORAM ATUALIZADOS NO BACKUP
				ResultSet setUpdate = connectionSingleton.getConnection().executeQuery("select * from pedido order by idpedido");
				while (setUpdate.next()) {
					for (int i = 0; i < table.size(); i++) {
						PedidoDTO pedidoDTO = new PedidoDTO();
						pedidoDTO.setCPFDoCliente(table.get(i).get(0));
						pedidoDTO.setIDPedido(Integer.parseInt(table.get(i).get(4)));
						pedidoDTO.setStatus(table.get(i).get(3));
						updatePedido(pedidoDTO);
					}

				}
			} catch (SQLException e) {
				//e.printStackTrace();
			}
		}
	}
}
