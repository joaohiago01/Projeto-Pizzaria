package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.ContabilidadeDTO;
import Model.NovaContabilidadeException;

public class ContabilidadeDAOPostgreSQL {

	private ArrayList<ArrayList<String>> tableContabilidade = new ArrayList<ArrayList<String>>();
	private ContabilidadeDTO contabilidadeDTO = new ContabilidadeDTO();
	private ConnectionSingleton connectionSingleton;
	private ContabilidadeDAO contabilidadeDAO = new ContabilidadeAdapterXML();//BD LOCAL PARA CASO A CONEXÃO CAIR

	public ContabilidadeDAOPostgreSQL() {
		checkConnection();
		
		//recoverData();//REAJUSTA OS DADOS QUE PODEM TER SIDO PERDIDOS POR ALGUMA QUEDA NA CONEXÃO
	}

	public void finishConnection(ArrayList<ArrayList<String>> table) {
		connectionSingleton.closeConnection();
	}

	public ArrayList<ArrayList<String>> checkConnection() {
		connectionSingleton = ConnectionSingleton.getInstance();
		return tableContabilidade;
	}

	public void createContabilidade(ContabilidadeDTO contabilidadeDTO) {

		String saborVendido = "'"+contabilidadeDTO.getSaborMaisVendido()+"'";
		String precoTotal = "'"+Float.toString(contabilidadeDTO.getLucroDoMes())+"'";
		String quantPizzas = "'"+Integer.toString(contabilidadeDTO.getQuantDePizzasVendidasNoMes())+"'";
		String data = "'"+contabilidadeDTO.getMesAtual()+"'";
		String query = "(" + data + ", " + saborVendido + ", " + quantPizzas + ", " + precoTotal + ")";

		try {

			contabilidadeDAO.createContabilidade(contabilidadeDTO);//BACKUP

			if (connectionSingleton.isConnectionValid())
				connectionSingleton.getConnection().executeUpdate("insert into contabilidade (mesatual, sabormaisvendido, quantpizzas, lucromensal) values " + query);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteContabilidade() {}

	public ContabilidadeDTO readContabilidade(ContabilidadeDTO contabilidadeDTO) {

		String data = "'"+contabilidadeDTO.getMesAtual()+"'";

		try {

			if (!connectionSingleton.isConnectionValid()) {
				return contabilidadeDAO.readContabilidade(contabilidadeDTO);
			} else {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from contabilidade where mesatual = " + data);
				if (set.next()) {
					this.contabilidadeDTO.setMesAtual(set.getString(1));
					this.contabilidadeDTO.setSaborMaisVendido(set.getString(2));
					this.contabilidadeDTO.setQuantDePizzasVendidasNoMes(set.getInt(3));
					this.contabilidadeDTO.setLucroDoMes(set.getFloat(4));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this.contabilidadeDTO;
	}

	public ContabilidadeDTO updateContabilidade(ContabilidadeDTO contabilidadeDTO) throws NovaContabilidadeException {

		String data = "'"+contabilidadeDTO.getMesAtual()+"'";

		try {

			contabilidadeDAO.updateContabilidade(contabilidadeDTO);//BACKUP

			if (connectionSingleton.isConnectionValid()) {
				boolean flag = true;
				boolean novaContabilidade = false;
				boolean pVenda = contabilidadeDTO.isPrimeiraVenda();

				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from contabilidade order by mesatual");
				if (set.next() == false) {
					createContabilidade(contabilidadeDTO);
					flag = false;
				} else {
					set = connectionSingleton.getConnection().executeQuery("select * from contabilidade order by mesatual");
					while (set.next()) {
						if (set.getString(1).equals(contabilidadeDTO.getMesAtual())==false) {
							novaContabilidade = true;
						} else {
							novaContabilidade = false;
						}
					}
				}
				if (novaContabilidade) {
					throw new NovaContabilidadeException();
				}

				set = connectionSingleton.getConnection().executeQuery("select * from contabilidade where mesatual = " + data);
				if (set.next() && flag == true) {
					String saborVendido = "'"+contabilidadeDTO.getSaborMaisVendido()+"'";
					String precoTotal = "";
					String quantPizzas = "";
					float lucroMensal = 0;
					int qtdPizzas = 0;
					if (pVenda==false) {
						lucroMensal = set.getFloat(4);
						lucroMensal += contabilidadeDTO.getLucroDoMes();
						precoTotal = "'"+Double.toString(lucroMensal)+"'";
						qtdPizzas = set.getInt(3);
						qtdPizzas += contabilidadeDTO.getQuantDePizzasVendidasNoMes();
						quantPizzas = "'"+Integer.toString(qtdPizzas)+"'";
					} else {
						lucroMensal = set.getFloat(4);
						precoTotal = "'"+Double.toString(lucroMensal)+"'";
						qtdPizzas = set.getInt(3);
						quantPizzas = "'"+Integer.toString(qtdPizzas)+"'";
					}
					connectionSingleton.getConnection().executeUpdate("update contabilidade set sabormaisvendido = " + saborVendido + " where mesatual = " + data);
					connectionSingleton.getConnection().executeUpdate("update contabilidade set lucromensal = " + precoTotal + " where mesatual = " + data);
					connectionSingleton.getConnection().executeUpdate("update contabilidade set quantpizzas = " + quantPizzas + " where mesatual = " + data);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.contabilidadeDTO;

	}

	public ContabilidadeDTO tableContabilidade() {

		String contabilidade = "";

		try {

			if (!connectionSingleton.isConnectionValid()) {
				return contabilidadeDAO.tableContabilidade();
			} else {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from contabilidade order by mesatual desc");
				while (set.next()) {
					contabilidade += set.getString(2) + "/" + set.getInt(3) + "/" + set.getFloat(4) + "/" + set.getString(1) + "#";
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (contabilidade.equals("")==false) {
			String[] tableContabilidade = contabilidade.split("#");
			this.contabilidadeDTO.setTableContabilidade(tableContabilidade);
			return this.contabilidadeDTO; 
		} else {
			return null;
		}
	}

	public void recoverData () {

		if (connectionSingleton.isConnectionValid()) {
			try {
				ArrayList<ArrayList<String>> tableContabilidade = contabilidadeDAO.checkConnection();
				//INSERE OS DADOS QUE NÃO FORAM INSERIDOS NO BD RELACIONAL MAS FICARAM GUARDADOS NO BACKUP
				int last = 0;
				ResultSet setInsert = connectionSingleton.getConnection().executeQuery("select * from contabilidade order by mesatual");
				while (setInsert.next()) {
					if (setInsert.isLast()) {
						last = setInsert.getRow();
						String query = "";
						for (int i = last; i < tableContabilidade.size(); i++) {
							query = "(" + "'"+tableContabilidade.get(i).get(0)+"'" + ", " + "'"+tableContabilidade.get(i).get(1)+"'" + ", " + "'"+tableContabilidade.get(i).get(2)+"'" + ", " + "'"+tableContabilidade.get(i).get(3)+"'" + ")";
							connectionSingleton.getConnection().executeUpdate("insert into contabilidade (mesatual, sabormaisvendido, quantpizzas, lucromensal) values " + query);
						}
					}
				}
				//ATUALIZA OS DADOS QUE NÃO FORAM ATUALIZADOS NO BD RELACIONAL MAS FORAM ATUALIZADOS NO BACKUP
				ResultSet setUpdate = connectionSingleton.getConnection().executeQuery("select * from contabilidade order by mesatual");
				while (setUpdate.next()) {
					for (int i = 0; i < tableContabilidade.size(); i++) {
						ContabilidadeDTO contabilidadeDTO = new ContabilidadeDTO();
						contabilidadeDTO.setMesAtual(tableContabilidade.get(i).get(0));
						contabilidadeDTO.setSaborMaisVendido(tableContabilidade.get(i).get(1));
						contabilidadeDTO.setQuantDePizzasVendidasNoMes(Integer.parseInt(tableContabilidade.get(i).get(3)));
						contabilidadeDTO.setLucroDoMes(Float.parseFloat(tableContabilidade.get(i).get(2)));
						updateContabilidade(contabilidadeDTO);
					}

				}
			} catch (SQLException e) {
				//e.printStackTrace();
			} catch (NovaContabilidadeException e) {
				//e.printStackTrace();
			}
		}
	}

}
