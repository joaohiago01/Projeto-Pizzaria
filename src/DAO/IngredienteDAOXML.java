package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import DTO.IngredienteDTO;
import View.IngredienteDuplicadoException;
import View.IngredienteNaoEncontradoException;

public class IngredienteDAOXML {

	private XStream xstream = new XStream(new DomDriver("ISO-8859-1"));
	private File arquivo;
	private ArrayList<ArrayList<String>> tableIngrediente = new ArrayList<ArrayList<String>>();
	private IngredienteDTO ingredienteDTO = new IngredienteDTO();

	public void finishConnection(ArrayList<ArrayList<String>> table) {
		arquivo = new File("ingredientes.xml");
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
		arquivo = new File("ingredientes.xml");
		try {
			if(arquivo.exists()) {
				FileInputStream fis = new FileInputStream(arquivo);
				return (ArrayList<ArrayList<String>>) xstream.fromXML(fis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tableIngrediente;
	}

	public void createIngrediente(IngredienteDTO ingredienteDTO) throws IngredienteDuplicadoException {
		String descricao = ingredienteDTO.getNome();
		String preco = Double.toString(ingredienteDTO.getPreco());
		ArrayList<ArrayList<String>> table = checkConnection();
		for (ArrayList<String> ingrediente: table) {
			if (ingrediente.get(0).equals(descricao)) {
				throw new IngredienteDuplicadoException();
			}
		}
		
		ArrayList<String> registry = new ArrayList<String>();
		registry.add(descricao);
		registry.add(preco);
		
		table.add(registry);
		finishConnection(table);
	}

	public void deleteIngrediente(IngredienteDTO ingredienteDTO) throws IngredienteNaoEncontradoException{
		
		String descricao = ingredienteDTO.getNome();
		ArrayList<ArrayList<String>> table = checkConnection();
		for (ArrayList<String> registry: table) {
			if (registry.get(0).equals(descricao)) {
				table.remove(registry);
				finishConnection(table);
				break;
			}
		}	
	}

	public IngredienteDTO readIngrediente(IngredienteDTO ingredienteDTO) throws IngredienteNaoEncontradoException{
		
		String descricao = ingredienteDTO.getNome();
		ArrayList<ArrayList<String>> table = checkConnection();
		for (ArrayList<String> registry: table) {
			if (registry.get(0).equals(descricao)) {
				ingredienteDTO.setNome(registry.get(0));
				ingredienteDTO.setPreco(Double.parseDouble(registry.get(1)));
				return ingredienteDTO;
			}
		}
		
		throw new IngredienteNaoEncontradoException();
	}
	
	public void updateIngrediente(IngredienteDTO ingredienteDTO) {}

	public IngredienteDTO tableIngrediente() {
		ArrayList<ArrayList<String>> table = checkConnection();
		String ingredientes = "";
		for (ArrayList<String> registry: table) {
			ingredientes += registry.get(0) +"/"+ registry.get(1) + "#";
		}
		String[] tableIngrediente = ingredientes.split("#");
		this.ingredienteDTO.setTableIngrediente(tableIngrediente);
		return this.ingredienteDTO;
	}
}
