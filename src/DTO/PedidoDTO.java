package DTO;

import java.util.ArrayList;
import java.util.Iterator;

import Model.Sabor;

public class PedidoDTO {

	private int IDPedido;
	private String CPFDoCliente;
	private Sabor sabores;
	private String tamanhoPizza;
	private double precoTotal;
	private String status;
	private String regiao;
	private double imposto;
	private ArrayList<PizzaDTO> pizzas;
	private String[] IDsPizzas;
	private String[] tablePedido;
	//private Iterator<Object> tablePedido;
	
	public String getCPFDoCliente() {
		return CPFDoCliente;
	}
	public void setCPFDoCliente(String cPFDoCliente) {
		CPFDoCliente = cPFDoCliente;
	}
	public Sabor getSabores() {
		return sabores;
	}
	public void setSabores(Sabor sabores) {
		this.sabores = sabores;
	}
	public String getTamanhoPizza() {
		return tamanhoPizza;
	}
	public void setTamanhoPizza(String tamanhoPizza) {
		this.tamanhoPizza = tamanhoPizza;
	}
	public double getPrecoTotal() {
		return precoTotal;
	}
	public void setPrecoTotal(double precoTotal) {
		this.precoTotal = precoTotal;
	}
	public ArrayList<PizzaDTO> getPizzas() {
		return pizzas;
	}
	public void setPizzas(ArrayList<PizzaDTO> pizzas) {
		this.pizzas = pizzas;
	}
	public String[] getIDsPizzas() {
		return IDsPizzas;
	}
	public void setIDsPizzas(String[] iDsPizzas) {
		IDsPizzas = iDsPizzas;
	}
	public String[] getTablePedido() {
		return tablePedido;
	}
	public void setTablePedido(String[] table) {
		this.tablePedido = table;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getIDPedido() {
		return IDPedido;
	}
	public void setIDPedido(int iDPedido) {
		IDPedido = iDPedido;
	}
	/*public Iterator<Object> getTablePedido() {
		return tablePedido;
	}
	public void setTablePedido(Iterator<Object> tablePedido) {
		this.tablePedido = tablePedido;
	}*/
	public String getRegiao() {
		return regiao;
	}
	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}
	public double getImposto() {
		return imposto;
	}
	public void setImposto(double imposto) {
		this.imposto = imposto;
	}
	
}
