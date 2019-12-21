package DTO;

public class ContabilidadeDTO {

	private String mesAtual;
	private int quantDePizzasVendidasNoMes;
	private float lucroDoMes;
	private String saborMaisVendido;
	private String[] tableContabilidade;
	private boolean primeiraVenda = false;
	
	public int getQuantDePizzasVendidasNoMes() {
		return quantDePizzasVendidasNoMes;
	}
	public void setQuantDePizzasVendidasNoMes(int quantDePizzasVendidasNoMes) {
		this.quantDePizzasVendidasNoMes = quantDePizzasVendidasNoMes;
	}
	public float getLucroDoMes() {
		return lucroDoMes;
	}
	public void setLucroDoMes(float precoContabilidade) {
		this.lucroDoMes = precoContabilidade;
	}
	public String getSaborMaisVendido() {
		return saborMaisVendido;
	}
	public void setSaborMaisVendido(String saborMaisVendido) {
		this.saborMaisVendido = saborMaisVendido;
	}
	public String[] getTableContabilidade() {
		return tableContabilidade;
	}
	public void setTableContabilidade(String[] tableContabilidade) {
		this.tableContabilidade = tableContabilidade;
	}
	public String getMesAtual() {
		return mesAtual;
	}
	public void setMesAtual(String mesAtual) {
		this.mesAtual = mesAtual;
	}
	public boolean isPrimeiraVenda() {
		return primeiraVenda;
	}
	public void setPrimeiraVenda(boolean primeiraVenda) {
		this.primeiraVenda = primeiraVenda;
	}
	
}
