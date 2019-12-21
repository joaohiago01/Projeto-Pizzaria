package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import DTO.ClienteDTO;
import View.ClienteDuplicadoException;
import View.ClienteNaoExisteException;

public class ClienteDAOXML {

	private XStream xstream = new XStream(new DomDriver("ISO-8859-1"));
	private File arquivo;
	private ArrayList<ArrayList<String>> tableCliente;
	private ClienteDTO clienteDTO = new ClienteDTO();

	public void finishConnection(ArrayList<ArrayList<String>> table) {
		arquivo = new File("clientes.xml");
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
		arquivo = new File("clientes.xml");
		try {
			if(arquivo.exists()) {
				FileInputStream fis = new FileInputStream(arquivo);
				return (ArrayList<ArrayList<String>>) xstream.fromXML(fis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return tableCliente = new ArrayList<ArrayList<String>>();
	}

	public void createCliente(ClienteDTO clienteDTO) throws ClienteDuplicadoException {

		String CPF = clienteDTO.getCPF();
		String nome = clienteDTO.getNome();
		String endereco = clienteDTO.getEndereco();
		String telefone = clienteDTO.getTelefone();

		ArrayList<ArrayList<String>> table = checkConnection();
		for (ArrayList<String> cliente: table) {
			if (cliente.get(0).toString().equals(CPF)) {
				throw new ClienteDuplicadoException();
			}
		}
		ArrayList<String> registry = new ArrayList<String>();

		registry.add(CPF);
		registry.add(nome);
		registry.add(endereco);
		registry.add(telefone);

		table.add(registry);
		finishConnection(table);
	} 

	public void deleteCliente(ClienteDTO clienteDTO) throws ClienteNaoExisteException{

		String CPF = clienteDTO.getCPF();

		ArrayList<ArrayList<String>> table = checkConnection();
		for (ArrayList<String> cliente: table) {
			if (cliente.get(0).toString().equals(CPF)) {
				table.remove(cliente);
				finishConnection(table);
			}
		}
		throw new ClienteNaoExisteException();
	}

	public void updateCliente(ClienteDTO clienteDTO) {
		ArrayList<ArrayList<String>> table = checkConnection();
		String IDPedido = (clienteDTO.getIDPedido());

		for (ArrayList<String> registry: table) {

			if (registry.get(0).equals(clienteDTO.getCPF())) {
				if (registry.size()<=4) {
					registry.add(4, IDPedido + "#");
				} else {
					registry.set(4, registry.get(4) + IDPedido + "#");
				}
				finishConnection(table);
				break;
			}
		}
	}

	public ClienteDTO readCliente(ClienteDTO clienteDTO) throws ClienteNaoExisteException {
		ArrayList<ArrayList<String>> table = checkConnection();
		String CPF = clienteDTO.getCPF();
		for (ArrayList<String> registry: table) {
			if (registry.get(0).equals(CPF)) {
				this.clienteDTO.setCPF(registry.get(0));
				this.clienteDTO.setNome(registry.get(1));
				this.clienteDTO.setEndereco(registry.get(2));
				this.clienteDTO.setTelefone(registry.get(3));
				if (registry.size()>4)
					this.clienteDTO.setIDPedido((registry.get(4)));
				break;
			}
		}
		return this.clienteDTO;
	}

	public ClienteDTO tableCliente() {
		ArrayList<ArrayList<String>> table = checkConnection();
		String[] tableCliente = new String[table.size()];
		String registryCliente = "";
		for (ArrayList<String> registry: table) {
			registryCliente += registry.get(1) +" / "+ registry.get(0) +"#";
		}
		tableCliente = registryCliente.split("#");
		this.clienteDTO.setTableCliente(tableCliente);
		return this.clienteDTO;
	}

}
