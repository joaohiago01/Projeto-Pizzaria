package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.IngredienteDTO;
import View.IngredienteDuplicadoException;
import View.IngredienteNaoEncontradoException;

public class IngredienteDAOPostgreSQL {

	private ArrayList<ArrayList<String>> tableIngrediente = new ArrayList<ArrayList<String>>();
	private IngredienteDTO ingredienteDTO = new IngredienteDTO();
	private ConnectionSingleton connectionSingleton;
	private IngredienteDAO ingredienteDAO = new IngredienteAdapterXML();//BACKUP LOCAL PARA CASO A CONEXÃO CAIR

	public IngredienteDAOPostgreSQL() {
		checkConnection();
		
		//recoverData();//REAJUSTA OS DADOS QUE PODEM TER SIDO PERDIDOS POR ALGUMA QUEDA NA CONEXÃO
	}

	public void finishConnection(ArrayList<ArrayList<String>> table) {
		connectionSingleton.closeConnection();
	}

	public ArrayList<ArrayList<String>> checkConnection() {
		connectionSingleton = ConnectionSingleton.getInstance();
		return tableIngrediente;
	}

	public void createIngrediente(IngredienteDTO ingredienteDTO) throws IngredienteDuplicadoException {

		String descricao = "'"+ingredienteDTO.getNome()+"'";
		String preco = "'"+Double.toString(ingredienteDTO.getPreco())+"'";
		String query = "(" + descricao + ", " + preco + ")";

		try {

			ingredienteDAO.createIngrediente(ingredienteDTO);//BACKUP

			if (connectionSingleton.isConnectionValid())
				connectionSingleton.getConnection().executeUpdate("insert into ingrediente (descricao, preco) values " + query);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteIngrediente(IngredienteDTO ingredienteDTO) throws IngredienteNaoEncontradoException {

		String descricao = ingredienteDTO.getNome();

		try {

			ingredienteDAO.deleteIngrediente(ingredienteDTO);//BACKUP

			if (connectionSingleton.isConnectionValid())
				connectionSingleton.getConnection().executeUpdate("delete from ingrediente where descricao = " + "'"+descricao+"'");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public IngredienteDTO readIngrediente(IngredienteDTO ingredienteDTO) throws IngredienteNaoEncontradoException {

		String descricao = ingredienteDTO.getNome();

		try {

			if (!connectionSingleton.isConnectionValid()) {
				return ingredienteDAO.readIngrediente(ingredienteDTO);//RECUPERA DO BACKUP
			} else {
				ResultSet set;
				set = connectionSingleton.getConnection().executeQuery("select * from ingrediente where descricao = " + "'"+descricao+"'");
				if (set.next()) {
					this.ingredienteDTO.setNome((set.getString(1)));
					this.ingredienteDTO.setPreco((set.getDouble(2)));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.ingredienteDTO;
	}

	public void updateIngrediente(IngredienteDTO ingredienteDTO) {}

	public IngredienteDTO tableIngrediente() {

		String registryIngrediente = "";
		try {

			if (!connectionSingleton.isConnectionValid()) {
				return ingredienteDAO.tableIngrediente();//RECUPERA DO BACKUP
			} else {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from ingrediente order by descricao");
				while (set.next()) {
					registryIngrediente += set.getString(1) +"/"+ set.getDouble(2) + "#";
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		String[] tableIngrediente = registryIngrediente.split("#");
		this.ingredienteDTO.setTableIngrediente(tableIngrediente);
		return this.ingredienteDTO;
	}

	public void recoverData () {

		if (connectionSingleton.isConnectionValid()) {
			try {
				ArrayList<ArrayList<String>> table = ingredienteDAO.checkConnection();
				//INSERE OS DADOS QUE NÃO FORAM INSERIDOS NO BD RELACIONAL MAS FICARAM GUARDADOS NO BACKUP
				int last = 0;
				ResultSet setInsert = connectionSingleton.getConnection().executeQuery("select * from ingrediente order by descricao");
				while (setInsert.next()) {
					if (setInsert.isLast()) {
						last = setInsert.getRow();
						String query = "";
						for (int i = last; i < table.size(); i++) {
							query = "(" + "'"+table.get(i).get(0)+"'" + ", " + "'"+table.get(i).get(1)+"'" + ")";
							connectionSingleton.getConnection().executeUpdate("insert into ingrediente (descricao, preco) values " + query);
						}
					}
				}
			} catch (SQLException e) {
				//e.printStackTrace();
			}
		}
	}

}
