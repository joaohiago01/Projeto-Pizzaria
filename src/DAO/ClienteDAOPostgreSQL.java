package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ClienteDTO;
import View.ClienteDuplicadoException;
import View.ClienteNaoExisteException;

public class ClienteDAOPostgreSQL {

	private ArrayList<ArrayList<String>> tableCliente = new ArrayList<ArrayList<String>>();
	private ClienteDTO clienteDTO = new ClienteDTO();
	private ConnectionSingleton connectionSingleton;
	private ClienteDAO clienteDAO = new ClienteAdapterXML();//BACKUP LOCAL PARA CASO A CONEXÃO CAIR

	public ClienteDAOPostgreSQL () {
		
		checkConnection();
		
		//recoverData();//REAJUSTA OS DADOS QUE PODEM TER SIDO PERDIDOS POR ALGUMA QUEDA NA CONEXÃO
		
	}

	public ArrayList<ArrayList<String>> checkConnection() {
		connectionSingleton = ConnectionSingleton.getInstance();
		return tableCliente;
	}

	public void finishConnection(ArrayList<ArrayList<String>> table) {
		connectionSingleton.closeConnection();
	}

	public void createCliente(ClienteDTO clienteDTO) throws ClienteDuplicadoException {

		String CPF = "'"+clienteDTO.getCPF()+"'";
		String nome = "'"+clienteDTO.getNome()+"'";
		String endereco = "'"+clienteDTO.getEndereco()+"'";
		String telefone = "'"+clienteDTO.getTelefone()+"'";
		String idpedido = "'"+""+"'";
		String query = "(" + CPF + ", " + nome + ", " + endereco + ", " + telefone + ", " + idpedido +  ")";

		try {

			clienteDAO.createCliente(clienteDTO);//BACKUP

			if (connectionSingleton.isConnectionValid()) {
				connectionSingleton.getConnection().executeUpdate("insert into cliente (cpf, nome, endereco, telefone, idpedido) values " + query);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCliente(ClienteDTO clienteDTO) throws ClienteNaoExisteException {

		String CPF = clienteDTO.getCPF();

		try {

			clienteDAO.deleteCliente(clienteDTO);//BACKUP

			if (connectionSingleton.isConnectionValid())
				connectionSingleton.getConnection().executeUpdate("delete from cliente where cpf = " + "'"+CPF+"'");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ClienteDTO readCliente(ClienteDTO clienteDTO) throws ClienteNaoExisteException {

		String CPF = clienteDTO.getCPF();

		try {

			if (!connectionSingleton.isConnectionValid()) {
				return clienteDAO.readCliente(clienteDTO);//RECUPERA DO BACKUP
			} else {
				ResultSet set;
				set = connectionSingleton.getConnection().executeQuery("select * from cliente where cpf = " + "'"+CPF+"'");
				if (set.next()) {
					clienteDTO.setCPF(set.getString(1));
					clienteDTO.setNome(set.getString(2));
					clienteDTO.setEndereco(set.getString(3));
					clienteDTO.setTelefone(set.getString(4));
					clienteDTO.setIDPedido(set.getString(5));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return clienteDTO;
	}

	public void updateCliente(ClienteDTO clienteDTO) {

		String CPF = clienteDTO.getCPF();

		try {
			
			clienteDAO.updateCliente(clienteDTO);//BACKUP

			if (connectionSingleton.isConnectionValid()) {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from cliente where cpf = " + "'"+CPF+"'");
				if (set.next()) {
					String IDPedido = "" + set.getString(5) + clienteDTO.getIDPedido() + "#";
					connectionSingleton.getConnection().executeUpdate("update cliente set idpedido = " +"'"+IDPedido+"'" + " where cpf = " + "'"+CPF+"'");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ClienteDTO tableCliente() {

		String registryCliente = "";
		try {

			if (!connectionSingleton.isConnectionValid()) {
				return clienteDAO.tableCliente();//RECUPERA DO BACKUP
			} else {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from cliente order by nome");
				while (set.next()) {
					registryCliente += set.getString(2) +" / "+ set.getString(1) + "#";
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		String table[] = registryCliente.split("#");
		this.clienteDTO.setTableCliente(table);
		return this.clienteDTO;
	} 

	public void recoverData () {
		
		if (connectionSingleton.isConnectionValid()) {
			try {
				ArrayList<ArrayList<String>> tableCliente = clienteDAO.checkConnection();
				//INSERE OS DADOS QUE NÃO FORAM INSERIDOS NO BD RELACIONAL MAS FICARAM GUARDADOS NO BACKUP
				int last = 0;
				ResultSet setInsert = connectionSingleton.getConnection().executeQuery("select * from cliente order by nome");
				while (setInsert.next()) {
					if (setInsert.isLast()) {
						last = setInsert.getRow();
						String query = "";
						for (int i = last; i < tableCliente.size(); i++) {
							query = "(" + "'"+tableCliente.get(i).get(0)+"'" + ", " + "'"+tableCliente.get(i).get(1)+"'" + ", " + "'"+tableCliente.get(i).get(2)+"'" + ", " + "'"+tableCliente.get(i).get(3)+"'" + ", " + "'"+""+"'" +  ")";
							connectionSingleton.getConnection().executeUpdate("insert into cliente (cpf, nome, endereco, telefone, idpedido) values " + query);
						}
					}
				}
				//DELETA OS DADOS QUE NÃO FORAM DELETADOS NO BD RELACIONAL MAS FORAM APAGADOS DO BACKUP
				String CPFs = "";
				ResultSet setDelete = connectionSingleton.getConnection().executeQuery("select * from cliente order by nome");
				while (setDelete.next()) {
					for (int i = 0; i < tableCliente.size(); i++) {
						CPFs += "'"+tableCliente.get(i).get(0)+"'" + ", ";
					}
					connectionSingleton.getConnection().executeUpdate("delete from cliente where cpf not in (" + CPFs + ")");
				}
				//ATUALIZA OS DADOS QUE NÃO FORAM ATUALIZADOS NO BD RELACIONAL MAS FORAM ATUALIZADOS NO BACKUP
				String IDsPedido = "";
				ResultSet setUpdate = connectionSingleton.getConnection().executeQuery("select * from cliente order by nome");
				while (setUpdate.next()) {
					for (int i = 0; i < tableCliente.size(); i++) {
						if (tableCliente.get(i).size()>4) {
							IDsPedido = "'"+tableCliente.get(i).get(4)+"'";
							connectionSingleton.getConnection().executeQuery("update cliente set idpedido = " + IDsPedido + " where cpf = " + "'"+tableCliente.get(i).get(0)+"'");
						}
					}

				}
			} catch (SQLException e) {
				//e.printStackTrace();
			}
		}
	}
}
