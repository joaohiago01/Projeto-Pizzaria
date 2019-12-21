package DAO;

import java.util.ArrayList;

import DTO.ClienteDTO;
import View.ClienteDuplicadoException;
import View.ClienteNaoExisteException;

public interface ClienteDAO {
	
	public ArrayList<ArrayList<String>> checkConnection();
	
	public void finishConnection(ArrayList<ArrayList<String>> table);
	
	public void createCliente(ClienteDTO clienteDTO) throws ClienteDuplicadoException;
	
	public void deleteCliente(ClienteDTO clienteDTO) throws ClienteNaoExisteException;
	
	public ClienteDTO readCliente(ClienteDTO clienteDTO) throws ClienteNaoExisteException;
	
	public void updateCliente(ClienteDTO clienteDTO);
	
	public ClienteDTO tableCliente();
	
}
