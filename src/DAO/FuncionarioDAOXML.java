package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import DTO.FuncionarioDTO;
import View.FuncionarioNaoExisteException;

public class FuncionarioDAOXML {

	private XStream xstream = new XStream(new DomDriver("ISO-8859-1"));
	private File arquivo ;
	private ArrayList<ArrayList<String>> tableFuncionario = new ArrayList<ArrayList<String>>();

	public void finishConnection(ArrayList<ArrayList<String>> tableFuncionario) {
		arquivo = new File("funcionarios.xml");
		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n";
		xml += xstream.toXML(tableFuncionario);
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
		arquivo = new File("funcionarios.xml");
		try {
			if(arquivo.exists()) {
				FileInputStream fis = new FileInputStream(arquivo);
				return (ArrayList<ArrayList<String>>) xstream.fromXML(fis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return new ArrayList<ArrayList<String>>();
	}

	public void createFuncionario(FuncionarioDTO funcionarioDTO) {

		String ID = "";
		if (funcionarioDTO.getSenha().equals("admin") && funcionarioDTO.getCodigoID().equals("0")) {
			ID = funcionarioDTO.getCodigoID();
		} else {
			ID = Integer.toString(IDFuncionario());
		}
		String senha = funcionarioDTO.getSenha();
		String nome = funcionarioDTO.getNome();
		String cargo = funcionarioDTO.getCargo();
		String telefone = funcionarioDTO.getTelefone();

		ArrayList<String> registry = new ArrayList<String>();

		registry.add(ID);
		registry.add(senha);
		registry.add(nome);
		registry.add(cargo);
		registry.add(telefone);

		ArrayList<ArrayList<String>> f = checkConnection();
		f.add(registry);
		finishConnection(f);
	} 

	public void deleteFuncionario(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {
		ArrayList<ArrayList<String>> tableFuncionario = checkConnection();
		String ID = (funcionarioDTO.getCodigoID());
		for (ArrayList<String> funcionario: tableFuncionario) {
			if (funcionario.get(0).equals(ID)) {
				tableFuncionario.remove(funcionario);
				finishConnection(tableFuncionario);
				break;
			}
		}
	}

	public void updateFuncionario(FuncionarioDTO funcionarioDTO) {}

	public FuncionarioDTO readFuncionario(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {
		ArrayList<ArrayList<String>> table = checkConnection();
		String ID = (funcionarioDTO.getCodigoID());
		for (ArrayList<String> registry: table) {
			if (registry.get(0).equalsIgnoreCase(ID)) {
				funcionarioDTO.setSenha(registry.get(1));
				funcionarioDTO.setNome(registry.get(2));
				funcionarioDTO.setCargo(registry.get(3));
				funcionarioDTO.setTelefone(registry.get(4));
				return funcionarioDTO;
			}
		}

		throw new FuncionarioNaoExisteException();
	}

	public int IDFuncionario() {
		ArrayList<ArrayList<String>> table = checkConnection();
		int ID = 1;
		if (table.size()>0) {
			int i = table.size()-1;
			ID = Integer.parseInt(table.get(i).get(0));
			ID++;
			return ID;
		} else {
			return ID;
		}
	}


}
