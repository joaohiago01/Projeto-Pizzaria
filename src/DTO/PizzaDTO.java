package DTO;

import java.util.ArrayList;
import java.util.Iterator;

public class PizzaDTO {

	private String CPFCliente;
	private String sabor;
	private String tamanho;
	private int codigoID;
	private double precoCompleta;
	private String status;
	private String[] tablePizza;
	private ArrayList<PizzaDTO> pizzas;
	//private Iterator<PizzaDTO> pizzas;
	
	public String getSabor() {
		return sabor;
	}
	public void setSabor(String string) {
		this.sabor = string;
	}
	public String getTamanho() {
		return tamanho;
	}
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
	public int getCodigoID() {
		return codigoID;
	}
	public void setCodigoID(int codigoID) {
		this.codigoID = codigoID;
	}
	public double getPrecoCompleta() {
		return precoCompleta;
	}
	public void setPrecoCompleta(double precoCompleta) {
		this.precoCompleta = precoCompleta;
	}
	public String getCPFCliente() {
		return CPFCliente;
	}
	public void setCPFCliente(String cPFCliente) {
		CPFCliente = cPFCliente;
	}
	public String[] getTablePizza() {
		return tablePizza;
	}
	public void setTablePizza(String[] tablePizza) {
		this.tablePizza = tablePizza;
	}
	public ArrayList<PizzaDTO> getPizzas() {
		return pizzas;
	}
	public void setPizzas(ArrayList<PizzaDTO> pizzas) {
		this.pizzas = pizzas;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/*public Iterator<PizzaDTO> getPizzas() {
		return pizzas;
	}
	public void setPizzas(Iterator<PizzaDTO> pizzas) {
		this.pizzas = pizzas;
	}*/
	
}
