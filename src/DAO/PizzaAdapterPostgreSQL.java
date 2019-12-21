package DAO;

import java.util.ArrayList;

import DTO.PizzaDTO;
import View.PizzaNaoEncontradaException;

public class PizzaAdapterPostgreSQL extends PizzaDAOPostgreSQL implements PizzaDAO {

	@Override
	public void finishConnection(ArrayList<ArrayList<String>> table) {
		super.finishConnection(table);
	}

	@Override
	public ArrayList<ArrayList<String>> checkConnection() {
		return super.checkConnection();
	}

	@Override
	public void createPizza(PizzaDTO pizzaDTO) {
		super.createPizza(pizzaDTO);
	}

	@Override
	public void deletePizza(PizzaDTO pizzaDTO) {
		super.deletePizza(pizzaDTO);
	}

	@Override
	public PizzaDTO readPizza(PizzaDTO pizzaDTO) throws PizzaNaoEncontradaException {
		return super.readPizza(pizzaDTO);
	}

	@Override
	public void updatePizza(PizzaDTO pizzaDTO) {
		super.updatePizza(pizzaDTO);
	}

	@Override
	public int IDPizza() {
		return super.IDPizza();
	}

	@Override
	public PizzaDTO tablePizza() {
		return super.tablePizza();
	}

	@Override
	public PizzaDTO tableEntrega() {
		return super.tableEntrega();
	}

	
}
