package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.SaborDTO;
import View.SaborJaExistenteException;
import View.SaborNaoEncontradoException;

public class SaborDAOPostgreSQL {

	private ArrayList<ArrayList<String>> tableSabor = new ArrayList<ArrayList<String>>();
	private SaborDTO saborDTO = new SaborDTO();
	private ConnectionSingleton connectionSingleton;
	private SaborDAO saborDAO = new SaborAdapterXML();//BACKUP LOCAL PARA CASO A CONEXÃO CAIR

	public SaborDAOPostgreSQL() {
		checkConnection();
		
		//recoverData();//REAJUSTA OS DADOS QUE PODEM TER SIDO PERDIDOS POR ALGUMA QUEDA NA CONEXÃO
	}

	public void finishConnection(ArrayList<ArrayList<String>> table) {
		connectionSingleton.closeConnection();
	}

	public ArrayList<ArrayList<String>> checkConnection() {
		connectionSingleton = ConnectionSingleton.getInstance();
		return tableSabor;
	}

	public void createSabor(SaborDTO saborDTO) throws SaborJaExistenteException {

		String descricao = "'"+saborDTO.getDescricao()+"'";
		String quantPedida = "'"+Integer.toString(saborDTO.getQuantMaisPedido())+"'";
		String ingredientes = "'"+saborDTO.getTableIngredientes()+"'";
		String query = "(" + descricao + ", " + ingredientes + ", " + quantPedida + ")";

		try {

			saborDAO.createSabor(saborDTO);//BACKUP

			if (connectionSingleton.isConnectionValid())
				connectionSingleton.getConnection().executeUpdate("insert into sabor (descricao, ingredientes, quantpedida) values " + query);

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}

	public void deleteSabor(SaborDTO saborDTO) {

		String descricao = "'"+saborDTO.getDescricao()+"'";

		try {

			saborDAO.deleteSabor(saborDTO);//BACKUP

			if (connectionSingleton.isConnectionValid())
				connectionSingleton.getConnection().executeUpdate("delete from sabor where descricao = " + descricao);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public SaborDTO readSabor(SaborDTO saborDTO) throws SaborNaoEncontradoException {

		String descricao = "'"+saborDTO.getDescricao()+"'";

		try {

			if (!connectionSingleton.isConnectionValid()) {
				return saborDAO.readSabor(saborDTO);//RECUPERA DO BACKUP
			} else {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from sabor where descricao = " + descricao);
				if (set.next()) {
					this.saborDTO.setDescricao(set.getString(1));
					this.saborDTO.setTableIngredientes(set.getString(2));
					this.saborDTO.setQuantMaisPedido(set.getInt(3));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.saborDTO;
	}

	public SaborDTO updateSabor(SaborDTO saborDTO) {

		String descricao = "'"+saborDTO.getDescricao()+"'";

		try {

			saborDAO.updateSabor(saborDTO);//BACKUP

			if (connectionSingleton.isConnectionValid()) {
				int qtd = 0;
				ResultSet set =	connectionSingleton.getConnection().executeQuery("select * from sabor where descricao = " + descricao);
				if (set.next()) {
					qtd = set.getInt(3);
					qtd++;
				}
				connectionSingleton.getConnection().executeUpdate("update sabor set quantpedida = " + qtd + " where descricao = " + descricao);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return saborDTO;
	}

	public SaborDTO tableSabor() {

		String registrySabor = "";
		try {

			if (!connectionSingleton.isConnectionValid()) {
				return saborDAO.tableSabor();
			} else {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from sabor order by descricao");
				while (set.next()) {
					registrySabor += set.getString(1) + "#";
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		String[] table = registrySabor.split("#");
		saborDTO.setSabores(table);
		return saborDTO;
	}

	public void recoverData () {

		if (connectionSingleton.isConnectionValid()) {
			try {
				ArrayList<ArrayList<String>> table = saborDAO.checkConnection();
				//INSERE OS DADOS QUE NÃO FORAM INSERIDOS NO BD RELACIONAL MAS FICARAM GUARDADOS NO BACKUP
				int last = 0;
				ResultSet setInsert = connectionSingleton.getConnection().executeQuery("select * from sabor order by descricao");
				while (setInsert.next()) {
					if (setInsert.isLast()) {
						last = setInsert.getRow();
						String query = "";
						for (int i = last; i < table.size(); i++) {
							query = "(" + "'"+table.get(i).get(0)+"'" + ", " + "'"+table.get(i).get(1)+"'" + ", " + "'"+table.get(i).get(2)+"'" +  ")";
							connectionSingleton.getConnection().executeUpdate("insert into sabor (descricao, ingredientes, quantpedida) values " + query);
						}
					}
				}
				//DELETA OS DADOS QUE NÃO FORAM DELETADOS NO BD RELACIONAL MAS FORAM APAGADOS DO BACKUP
				String desc = "";
				ResultSet setDelete = connectionSingleton.getConnection().executeQuery("select * from sabor order by descricao");
				while (setDelete.next()) {
					for (int i = 0; i < table.size(); i++) {
						desc += "'"+table.get(i).get(0)+"'" + ", ";
					}
					connectionSingleton.getConnection().executeUpdate("delete from sabor where descricao not in (" + desc + ")");
				}
				//ATUALIZA OS DADOS QUE NÃO FORAM ATUALIZADOS NO BD RELACIONAL MAS FORAM ATUALIZADOS NO BACKUP
				ResultSet setUpdate = connectionSingleton.getConnection().executeQuery("select * from sabor order by descricao");
				while (setUpdate.next()) {
					SaborDTO saborDTO = new SaborDTO();
					for (int i = 0; i < table.size(); i++) {
						saborDTO.setDescricao(table.get(i).get(0));
						updateSabor(saborDTO);
					}

				}
			} catch (SQLException e) {
				//e.printStackTrace();
			}
		}
	}
}
