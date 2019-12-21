package DTO;

import View.SaborPizza;

public class SaborDTO {

	private String descricao;
	private double preco;
	private int quantMaisPedido;
	private String[] tableSabor;
	private String tableIngredientes;
	private SaborPizza ingredientes;
	
	public String[] getSabores() {
		return tableSabor;
	}
	public void setSabores(String[] table) {
		this.tableSabor = table;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getQuantMaisPedido() {
		return quantMaisPedido;
	}
	public void setQuantMaisPedido(int quantMaisPedido) {
		this.quantMaisPedido = quantMaisPedido;
	}
	public String getTableIngredientes() {
		return tableIngredientes;
	}
	public void setTableIngredientes(String tableIngredientes) {
		this.tableIngredientes = tableIngredientes;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public SaborPizza getIngredientes() {
		return ingredientes;
	}
	public void setIngredientes(SaborPizza ingredientes) {
		this.ingredientes = ingredientes;
	}
	
	
}
