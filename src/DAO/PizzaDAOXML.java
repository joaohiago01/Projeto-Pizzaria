package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import DTO.PizzaDTO;
import View.PizzaNaoEncontradaException;

public class PizzaDAOXML {

	private XStream xstream = new XStream(new DomDriver("ISO-8859-1"));
	private File arquivo;
	private ArrayList<ArrayList<String>> tablePizza = new ArrayList<ArrayList<String>>();
	private PizzaDTO pizzaDTO = new PizzaDTO();

	public void finishConnection(ArrayList<ArrayList<String>> table) {
		arquivo = new File("pizzas.xml");
		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n";
		xml += xstream.toXML(table);
		try {
			arquivo.createNewFile();
			PrintWriter gravar = new PrintWriter(arquivo);
			gravar.print(xml);
			gravar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ArrayList<String>> checkConnection() {
		arquivo = new File("pizzas.xml");
		try {
			if(arquivo.exists()) {
				FileInputStream fis = new FileInputStream(arquivo);
				return (ArrayList<ArrayList<String>>) xstream.fromXML(fis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tablePizza;
	}

	public void createPizza(PizzaDTO pizzaDTO) {

		String id = Integer.toString(pizzaDTO.getCodigoID());
		String sabor = pizzaDTO.getSabor();
		String tamanho = pizzaDTO.getTamanho();
		String preco = Double.toString(pizzaDTO.getPrecoCompleta());
		String CPFCliente = pizzaDTO.getCPFCliente();
		String status = pizzaDTO.getStatus();

		ArrayList<String> registry = new ArrayList<String>();

		registry.add(id);
		registry.add(sabor);
		registry.add(tamanho);
		registry.add(preco);
		registry.add(CPFCliente);
		registry.add(status);

		ArrayList<ArrayList<String>> tablePizza = checkConnection();
		tablePizza.add(registry);
		finishConnection(tablePizza);
	}

	public void deletePizza(PizzaDTO pizzaDTO) {
		String id = Integer.toString(pizzaDTO.getCodigoID());
		ArrayList<ArrayList<String>> tablePizza = checkConnection();
		for (ArrayList<String> pizza: tablePizza) {
			if (pizza.get(0).equals(id)) {
				tablePizza.remove(pizza);
				finishConnection(tablePizza);
				break;
			}
		}
	}

	public void updatePizza(PizzaDTO pizzaDTO) {
		ArrayList<ArrayList<String>> table = checkConnection();
		String ID = Integer.toString(pizzaDTO.getCodigoID());
		String Status = pizzaDTO.getStatus();
		for (ArrayList<String> registry: table) {
			if (registry.get(0).equals(ID)) {
				registry.set(5, Status);
				finishConnection(table);
			}
		}
	}

	public PizzaDTO readPizza(PizzaDTO pizzaDTO) throws PizzaNaoEncontradaException {
		String id = Integer.toString(pizzaDTO.getCodigoID());
		ArrayList<ArrayList<String>> tablePizza = checkConnection();
		for (ArrayList<String> registry: tablePizza) {
			if (registry.get(0).equals(id)) {
				pizzaDTO.setCodigoID(Integer.parseInt(registry.get(0)));
				pizzaDTO.setSabor(registry.get(1));
				pizzaDTO.setTamanho(registry.get(2));
				pizzaDTO.setPrecoCompleta(Double.parseDouble(registry.get(3)));
				pizzaDTO.setCPFCliente(registry.get(4));
			}	
		}
		return pizzaDTO;
	}

	public int IDPizza() {
		ArrayList<ArrayList<String>> table = checkConnection();
		int cont = 1;
		if (table.size()>0) {
			int i = table.size() - 1;
			cont = Integer.parseInt(table.get(i).get(0));
			cont++;
			return cont;
		} else {
			return cont;
		}
	}

	public PizzaDTO tablePizza() {
		ArrayList<ArrayList<String>> table = checkConnection();
		String pizzas = "";
		for (ArrayList<String> registry: table) {
			pizzas += registry.get(1) +"/"+ registry.get(3) + "/" + registry.get(5) + "/" + registry.get(0) + "#";
		}
		if (table.size()>0) {
			String[] tablePizza = pizzas.split("#");
			this.pizzaDTO.setTablePizza(tablePizza);
			return this.pizzaDTO;
		} else {
			return null;
		}
	}

	public PizzaDTO tableEntrega() {
		ArrayList<ArrayList<String>> table = checkConnection();
		ArrayList<PizzaDTO> pizzas = new ArrayList<PizzaDTO>();
		for (ArrayList<String> registry: table) {
			PizzaDTO pizza = new PizzaDTO();
			pizza.setCPFCliente(registry.get(4));
			pizza.setCodigoID(Integer.parseInt(registry.get(0)));
			pizzas.add(pizza);
		}
		this.pizzaDTO.setPizzas(pizzas);
		return this.pizzaDTO;
	}
}
