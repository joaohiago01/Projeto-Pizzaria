package DAO;

import java.util.ArrayList;

import DTO.ClienteDTO;
import View.ClienteDuplicadoException;
import View.ClienteNaoExisteException;

public class ClienteAdapterPostgreSQL extends ClienteDAOPostgreSQL implements ClienteDAO {
	
	@Override
	public ArrayList<ArrayList<String>> checkConnection() {
		return super.checkConnection();
	}

	@Override
	public void finishConnection(ArrayList<ArrayList<String>> table) {
		super.finishConnection(table);
	}

	@Override
	public void createCliente(ClienteDTO clienteDTO) throws ClienteDuplicadoException {
		super.createCliente(clienteDTO);
	}

	@Override
	public void deleteCliente(ClienteDTO clienteDTO) throws ClienteNaoExisteException {
		super.deleteCliente(clienteDTO);
	}

	@Override
	public ClienteDTO readCliente(ClienteDTO clienteDTO) throws ClienteNaoExisteException {
		return super.readCliente(clienteDTO);
	}

	@Override
	public void updateCliente(ClienteDTO clienteDTO) {
		super.updateCliente(clienteDTO);
	}

	@Override
	public ClienteDTO tableCliente() {
		return super.tableCliente();
	}
	
}
