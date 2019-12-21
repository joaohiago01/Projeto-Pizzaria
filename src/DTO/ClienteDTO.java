package DTO;

public class ClienteDTO {

	private String nome = null;
	private String CPF = null;
	private String endereco = null;
	private String telefone = null;
	private String[] tableCliente = null;
	private String IDPedido;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCPF() {
		return CPF;
	}
	public void setCPF(String cPF) {
		CPF = cPF;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String[] getTableCliente() {
		return tableCliente;
	}
	public void setTableCliente(String[] tableCliente2) {
		this.tableCliente = tableCliente2;
	}
	public String getIDPedido() {
		return IDPedido;
	}
	public void setIDPedido(String iDPedido) {
		IDPedido = iDPedido;
	}
		
}
