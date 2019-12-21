package DAO;

import java.util.ArrayList;

import DTO.PizzaDTO;
import View.PizzaNaoEncontradaException;

public interface PizzaDAO {
	
	public void finishConnection(ArrayList<ArrayList<String>> table);
	
	public ArrayList<ArrayList<String>> checkConnection();
	
	public void createPizza(PizzaDTO pizzaDTO);
	
	public void deletePizza(PizzaDTO pizzaDTO);
	
	public PizzaDTO readPizza(PizzaDTO pizzaDTO) throws PizzaNaoEncontradaException;
	
	public void updatePizza(PizzaDTO pizzaDTO);
	
	public int IDPizza();
	
	public PizzaDTO tablePizza();
	
	public PizzaDTO tableEntrega();
	
}
