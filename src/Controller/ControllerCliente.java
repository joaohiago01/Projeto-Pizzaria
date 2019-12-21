package Controller;

import DTO.ClienteDTO;
import Model.Cliente;
import View.ClienteDuplicadoException;

public class ControllerCliente {

	private Cliente cliente;
	
	public ControllerCliente() {
		this.cliente = new Cliente();
	}
	
	public void cadastrarCliente(ClienteDTO clienteDTO) throws ClienteDuplicadoException {
		cliente.cadastrarCliente(clienteDTO);
	}
	
	public boolean preenchido(ClienteDTO clienteDTO) {
		if (clienteDTO.getNome().equals("") || clienteDTO.getCPF().equals("") || clienteDTO.getEndereco().equals("") || clienteDTO.getTelefone().equals("")) {
			return false;
		} else {
			return true;
		}
	}
	
}
