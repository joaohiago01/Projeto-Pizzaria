package Model;

import DAO.ClienteAdapterPostgreSQL;
import DAO.ClienteAdapterXML;
import DAO.ClienteDAO;
import DTO.ClienteDTO;
import View.ClienteDuplicadoException;

public class Cliente {

	//SQL
	private ClienteDAO clienteDAO = new ClienteAdapterPostgreSQL();
	
	//XML
	//private ClienteDAO clienteDAO = new ClienteAdapterXML();
	
	public void cadastrarCliente(ClienteDTO clienteDTO) throws ClienteDuplicadoException{
		clienteDAO.createCliente(clienteDTO);
	}
}
