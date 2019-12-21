package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.FuncionarioDTO;
import View.FuncionarioNaoExisteException;

public class FuncionarioDAOPostgreSQL {

	private ArrayList<ArrayList<String>> tableFuncionario  = new ArrayList<ArrayList<String>>();
	private FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
	private ConnectionSingleton connectionSingleton;
	private FuncionarioDAO funcionarioDAO = new FuncionarioAdapterXML();//BD LOCAL PARA CASO A CONEXÃO CAIR

	public FuncionarioDAOPostgreSQL() {
		checkConnection();
		
		//recoverData();//REAJUSTA OS DADOS QUE PODEM TER SIDO PERDIDOS POR ALGUMA QUEDA NA CONEXÃO
	}

	public ArrayList<ArrayList<String>> checkConnection() {
		connectionSingleton = ConnectionSingleton.getInstance();
		return tableFuncionario;
	}

	public void finishConnection(ArrayList<ArrayList<String>> tableFuncionario) {
		connectionSingleton.closeConnection();
	}

	public void closeConnection () {
		this.finishConnection(tableFuncionario);
	}

	public void createFuncionario(FuncionarioDTO funcionarioDTO) {

		String ID = "'"+IDFuncionario()+"'";
		String nome = "'"+funcionarioDTO.getNome()+"'";
		String senha = "'"+funcionarioDTO.getSenha()+"'";
		String telefone = "'"+funcionarioDTO.getTelefone()+"'";
		String cargo = "'"+funcionarioDTO.getCargo()+"'";
		String query = "(" + ID + ", " + senha + ", " + nome + ", " + cargo + ", " + telefone + ")";

		try {

			funcionarioDAO.createFuncionario(funcionarioDTO);

			if (connectionSingleton.isConnectionValid())
				connectionSingleton.getConnection().executeUpdate("insert into funcionario (id, senha, nome, cargo, telefone) values " + query);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteFuncionario(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {

		String ID = funcionarioDTO.getCodigoID();

		try {

			funcionarioDAO.deleteFuncionario(funcionarioDTO);

			if (connectionSingleton.isConnectionValid())
				connectionSingleton.getConnection().executeUpdate("delete from funcionario where id = " + "'"+ID+"'");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public FuncionarioDTO readFuncionario(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {

		String ID = funcionarioDTO.getCodigoID();

		try {

			if (!connectionSingleton.isConnectionValid()) {
				return funcionarioDAO.readFuncionario(funcionarioDTO);
			} else {
				ResultSet set;
				set = connectionSingleton.getConnection().executeQuery("select * from funcionario where id = " + "'"+ID+"'");
				if (set.next()) {
					this.funcionarioDTO.setCodigoID(set.getString(5));
					this.funcionarioDTO.setSenha(set.getString(3));
					this.funcionarioDTO.setNome(set.getString(2));
					this.funcionarioDTO.setCargo(set.getString(1));
					this.funcionarioDTO.setTelefone(set.getString(4));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this.funcionarioDTO;
	}

	public void updateFuncionario(FuncionarioDTO funcionarioDTO) {}

	public int IDFuncionario() {

		int id = 0;
		try {

			if (!connectionSingleton.isConnectionValid()) {
				return funcionarioDAO.IDFuncionario();
			} else {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from funcionario");
				while (set.next()) {
					if (set.isLast())
						id = set.getRow();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	public void recoverData () {

		if (connectionSingleton.isConnectionValid()) {
			try {
				ArrayList<ArrayList<String>> table = funcionarioDAO.checkConnection();
				//INSERE OS DADOS QUE NÃO FORAM INSERIDOS NO BD RELACIONAL MAS FICARAM GUARDADOS NO BACKUP
				int last = 0;
				ResultSet setInsert = connectionSingleton.getConnection().executeQuery("select * from funcionario order by nome");
				while (setInsert.next()) {
					if (setInsert.isLast()) {
						last = setInsert.getRow();
						String query = "";
						for (int i = last; i < table.size(); i++) {
							query = "(" + "'"+table.get(i).get(0)+"'" + ", " + "'"+table.get(i).get(1)+"'" + ", " + "'"+table.get(i).get(2)+"'" + ", " + "'"+table.get(i).get(3)+"'" + ", " + "'"+table.get(i).get(4)+"'" +  ")";
							connectionSingleton.getConnection().executeUpdate("insert into funcionario (id, senha, nome, cargo, telefone) values " + query);
						}
					}
				}
				//DELETA OS DADOS QUE NÃO FORAM DELETADOS NO BD RELACIONAL MAS FORAM APAGADOS DO BACKUP
				String IDs = "";
				ResultSet setDelete = connectionSingleton.getConnection().executeQuery("select * from funcionario order by nome");
				while (setDelete.next()) {
					for (int i = 0; i < table.size(); i++) {
						IDs += "'"+table.get(i).get(0)+"'" + ", ";
					}
					connectionSingleton.getConnection().executeUpdate("delete from funcionario where id not in (" + IDs + ")");
				}
			} catch (SQLException e) {
				//e.printStackTrace();
			}
		}
	}

}
