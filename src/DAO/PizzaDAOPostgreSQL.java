package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.PizzaDTO;
import View.PizzaNaoEncontradaException;

public class PizzaDAOPostgreSQL {

	private ArrayList<ArrayList<String>> tablePizza = new ArrayList<ArrayList<String>>();
	private PizzaDTO pizzaDTO = new PizzaDTO();
	private ConnectionSingleton connectionSingleton;
	private PizzaDAO pizzaDAO = new PizzaAdapterXML();//BACKUP LOCAL PARA CASO A CONEXÃO CAIR

	public PizzaDAOPostgreSQL() {
		checkConnection();
		
		//recoverData();//REAJUSTA OS DADOS QUE PODEM TER SIDO PERDIDOS POR ALGUMA QUEDA NA CONEXÃO
	}

	public void finishConnection(ArrayList<ArrayList<String>> table) {
		connectionSingleton.closeConnection();
	}

	public ArrayList<ArrayList<String>> checkConnection() {
		connectionSingleton = ConnectionSingleton.getInstance();
		return tablePizza;
	}

	public void createPizza(PizzaDTO pizzaDTO) {

		String id = "'"+Integer.toString(pizzaDTO.getCodigoID())+"'";
		String sabor = "'"+pizzaDTO.getSabor()+"'";
		String tamanho = "'"+pizzaDTO.getTamanho()+"'";
		String preco = "'"+Double.toString(pizzaDTO.getPrecoCompleta())+"'";
		String CPFCliente = "'"+pizzaDTO.getCPFCliente()+"'";
		String status = "'"+pizzaDTO.getStatus()+"'";
		String query = "(" + id + ", " + sabor + ", " + tamanho + ", " + preco + ", " + CPFCliente + ", " + status + ")";

		try {

			pizzaDAO.createPizza(pizzaDTO);//BAKUP

			if (connectionSingleton.isConnectionValid())
				connectionSingleton.getConnection().executeUpdate("insert into pizza (id, sabor, tamanho, preco, cpfcliente, status) values " + query);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deletePizza(PizzaDTO pizzaDTO) {

		String id = "'"+Integer.toString(pizzaDTO.getCodigoID())+"'";

		try {

			pizzaDAO.deletePizza(pizzaDTO);//BACKUP

			if (connectionSingleton.isConnectionValid())
				connectionSingleton.getConnection().executeUpdate("delete from pizza where id = " + id);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public PizzaDTO readPizza(PizzaDTO pizzaDTO) throws PizzaNaoEncontradaException {

		String id = "'"+Integer.toString(pizzaDTO.getCodigoID())+"'";

		try {

			if (!connectionSingleton.isConnectionValid()) {
				return pizzaDAO.readPizza(pizzaDTO);//RECUPERA DO BACKUP
			} else {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from pizza where id = " + id);
				if (set.next()) {
					this.pizzaDTO.setCodigoID(set.getInt(1));
					this.pizzaDTO.setSabor(set.getString(2));
					this.pizzaDTO.setTamanho(set.getString(3));
					this.pizzaDTO.setPrecoCompleta(set.getDouble(4));
					this.pizzaDTO.setCPFCliente(set.getString(5));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.pizzaDTO;
	}

	public void updatePizza(PizzaDTO pizzaDTO) {

		String id = "'"+Integer.toString(pizzaDTO.getCodigoID())+"'";
		String status = "'"+pizzaDTO.getStatus()+"'";

		try {

			pizzaDAO.updatePizza(pizzaDTO);//BACKUP

			if (connectionSingleton.isConnectionValid()) {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from pizza where id = " + id);
				if (set.next()) {
					connectionSingleton.getConnection().executeUpdate("update pizza set status = " + status + " where id = " + id);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int IDPizza() {

		int id = 0;
		try {

			if (!connectionSingleton.isConnectionValid()) {
				return pizzaDAO.IDPizza();//RECUPERA DO BACKUP
			} else {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from pizza");
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

	public PizzaDTO tablePizza() {

		String registryPizza = "";

		try {

			if (!connectionSingleton.isConnectionValid()) {
				return pizzaDAO.tablePizza();//RECUPERA DO BACKUP
			} else {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from pizza order by id");
				while (set.next()) {
					registryPizza += set.getString(2) +"/"+ set.getDouble(4) + "/" + set.getString(6) + "/" + set.getInt(1) + "#";
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (registryPizza.equals("")==false) {
			String[] table = registryPizza.split("#");
			this.pizzaDTO.setTablePizza(table);
			return this.pizzaDTO; 
		} else {
			return null;
		}
	}

	public PizzaDTO tableEntrega() {

		ArrayList<PizzaDTO> pizzas = new ArrayList<PizzaDTO>();
		try {

			if (!connectionSingleton.isConnectionValid()) {
				return pizzaDAO.tableEntrega();//RECUPERA DO BACKUP
			} else {
				ResultSet set = connectionSingleton.getConnection().executeQuery("select * from pizza order by id");
				while (set.next()) {
					PizzaDTO pizza = new PizzaDTO();
					pizza.setCPFCliente(set.getString(5));
					pizza.setCodigoID((set.getInt(1)));
					pizzas.add(pizza);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.pizzaDTO.setPizzas(pizzas);
		return this.pizzaDTO;
	}

	public void recoverData () {

		if (connectionSingleton.isConnectionValid()) {
			try {
				ArrayList<ArrayList<String>> table = pizzaDAO.checkConnection();
				//INSERE OS DADOS QUE NÃO FORAM INSERIDOS NO BD RELACIONAL MAS FICARAM GUARDADOS NO BACKUP
				int last = 0;
				ResultSet setInsert = connectionSingleton.getConnection().executeQuery("select * from pizza order by id");
				while (setInsert.next()) {
					if (setInsert.isLast()) {
						last = setInsert.getRow();
						String query = "";
						for (int i = last; i < table.size(); i++) {
							query = "(" + "'"+table.get(i).get(0)+"'" + ", " + "'"+table.get(i).get(1)+"'" + ", " + "'"+table.get(i).get(2)+"'" + ", " + "'"+table.get(i).get(3)+"'" + ", " + "'"+table.get(i).get(4)+"'" + ", " +"'"+table.get(i).get(5)+"'" +  ")";
							connectionSingleton.getConnection().executeUpdate("insert into pizza (id, sabor, tamanho, preco, cpfcliente, status) values " + query);
						}
					}
				}
				//DELETA OS DADOS QUE NÃO FORAM DELETADOS NO BD RELACIONAL MAS FORAM APAGADOS DO BACKUP
				String IDs = "";
				ResultSet setDelete = connectionSingleton.getConnection().executeQuery("select * from pizza order by id");
				while (setDelete.next()) {
					for (int i = 0; i < table.size(); i++) {
						IDs += "'"+table.get(i).get(0)+"'" + ", ";
					}
					connectionSingleton.getConnection().executeUpdate("delete from pizza where id not in (" + IDs + ")");
				}
				//ATUALIZA OS DADOS QUE NÃO FORAM ATUALIZADOS NO BD RELACIONAL MAS FORAM ATUALIZADOS NO BACKUP
				ResultSet setUpdate = connectionSingleton.getConnection().executeQuery("select * from pizza order by id");
				while (setUpdate.next()) {
					PizzaDTO pizzaDTO = new PizzaDTO();
					for (int i = 0; i < table.size(); i++) {
						pizzaDTO.setStatus(table.get(i).get(5));
						pizzaDTO.setCodigoID(Integer.parseInt(table.get(i).get(0)));
						updatePizza(pizzaDTO);
					}

				}
			} catch (SQLException e) {
				//e.printStackTrace();
			}
		}
	}
}
