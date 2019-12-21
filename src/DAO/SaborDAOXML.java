package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import DTO.SaborDTO;
import View.SaborJaExistenteException;
import View.SaborNaoEncontradoException;

public class SaborDAOXML {

	private XStream xstream = new XStream(new DomDriver("ISO-8859-1"));
	private File arquivo ;
	private ArrayList<ArrayList<String>> tableSabor = new ArrayList<ArrayList<String>>();
	private SaborDTO saborDTO = new SaborDTO();

	public void finishConnection(ArrayList<ArrayList<String>> table) {
		arquivo = new File("sabores.xml");
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
		arquivo = new File("sabores.xml");
		try {
			if(arquivo.exists()) {
				FileInputStream fis = new FileInputStream(arquivo);
				return (ArrayList<ArrayList<String>>) xstream.fromXML(fis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tableSabor;
	}

	public void createSabor(SaborDTO saborDTO) throws SaborJaExistenteException {

		String descricao = saborDTO.getDescricao();
		String quantPedida = Integer.toString(saborDTO.getQuantMaisPedido());
		for (ArrayList<String> sabor: tableSabor) {
			if (sabor.get(0).equalsIgnoreCase(descricao)) {
				throw new SaborJaExistenteException();
			}
		}
		String ingredientes = saborDTO.getTableIngredientes();

		ArrayList<String> sabor = new ArrayList<String>();
		sabor.add(descricao);
		sabor.add(ingredientes);
		sabor.add(quantPedida);

		ArrayList<ArrayList<String>> tableSabor = checkConnection();
		tableSabor.add(sabor);
		finishConnection(tableSabor);
	}

	public void deleteSabor(SaborDTO saborDTO) {

		String descricao = saborDTO.getDescricao();
		ArrayList<ArrayList<String>> tableSabor = checkConnection();
		for (ArrayList<String> sabor: tableSabor) {
			if (sabor.get(0).equals(descricao)) {
				tableSabor.remove(sabor);
				finishConnection(tableSabor);
				break;
			}
		}
	}

	public SaborDTO readSabor(SaborDTO saborDTO) throws SaborNaoEncontradoException {

		String descricao = saborDTO.getDescricao();
		ArrayList<ArrayList<String>> tableSabor = checkConnection();
		for (ArrayList<String> registry: tableSabor) {
			if (registry.get(0).equalsIgnoreCase(descricao)) {
				saborDTO.setDescricao(registry.get(0));
				saborDTO.setTableIngredientes(registry.get(1));
				saborDTO.setQuantMaisPedido(Integer.parseInt(registry.get(2)));
				return saborDTO;
			}
		}
		throw new SaborNaoEncontradoException();
	}

	public SaborDTO updateSabor(SaborDTO saborDTO) {
		ArrayList<ArrayList<String>> table = checkConnection();
		for (ArrayList<String> registry: table) {

			if (registry.get(0).equals(saborDTO.getDescricao())) {
				
				int cont = Integer.parseInt(registry.get(2));
				cont++;
				saborDTO.setQuantMaisPedido(cont);
				registry.set(2, Integer.toString(saborDTO.getQuantMaisPedido()));
				break;
			}
		}

		finishConnection(table);
		return saborDTO;
	}
	
	public SaborDTO tableSabor() {
		ArrayList<ArrayList<String>> tableSabor = checkConnection();
		String[] table = new String[tableSabor.size()];
		String registrySabor = "";
		for (ArrayList<String> registry: tableSabor) {
			registrySabor += registry.get(0) + "#";
		}
		table = registrySabor.split("#");
		saborDTO.setSabores(table);
		return saborDTO;

	}
}
