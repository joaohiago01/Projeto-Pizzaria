package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import DTO.ContabilidadeDTO;
import Model.NovaContabilidadeException;

public class ContabilidadeDAOXML {

	private XStream xstream = new XStream(new DomDriver("ISO-8859-1"));
	private File arquivo;
	private ArrayList<ArrayList<String>> tableContabilidade = new ArrayList<ArrayList<String>>();
	private ContabilidadeDTO contabilidadeDTO = new ContabilidadeDTO();

	public void finishConnection(ArrayList<ArrayList<String>> table) {
		arquivo = new File("contabilidade.xml");
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
		arquivo = new File("contabilidade.xml");
		try {
			if(arquivo.exists()) {
				FileInputStream fis = new FileInputStream(arquivo);
				return (ArrayList<ArrayList<String>>) xstream.fromXML(fis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tableContabilidade;
	}

	public void createContabilidade(ContabilidadeDTO contabilidadeDTO) {
		ArrayList<ArrayList<String>> table = checkConnection();

		String saborVendido = contabilidadeDTO.getSaborMaisVendido();
		String precoTotal = Double.toString(contabilidadeDTO.getLucroDoMes());
		String quantPizzas = Integer.toString(contabilidadeDTO.getQuantDePizzasVendidasNoMes());
		String data = contabilidadeDTO.getMesAtual();

		ArrayList<String> registry = new ArrayList<String>();

		registry.add(data);
		registry.add(saborVendido);
		registry.add(precoTotal);
		registry.add(quantPizzas);

		table.add(registry);
		finishConnection(table);
	}

	public ContabilidadeDTO readContabilidade(ContabilidadeDTO contabilidadeDTO) {
		ArrayList<ArrayList<String>> table = checkConnection();
		for (ArrayList<String> registry: table) {
			if (registry.get(0).equals(contabilidadeDTO.getMesAtual())) {
				this.contabilidadeDTO.setMesAtual(registry.get(0));
				this.contabilidadeDTO.setSaborMaisVendido(registry.get(1));
				this.contabilidadeDTO.setQuantDePizzasVendidasNoMes(Integer.parseInt(registry.get(2)));
				this.contabilidadeDTO.setLucroDoMes(Float.parseFloat(registry.get(3)));
				return this.contabilidadeDTO;
			} 
		}
		return null;
	}

	public void deleteContabilidade() {}

	public ContabilidadeDTO updateContabilidade(ContabilidadeDTO contabilidadeDTO) throws NovaContabilidadeException {
		ArrayList<ArrayList<String>> table = checkConnection();
		boolean flag = contabilidadeDTO.isPrimeiraVenda();
		if (table.size()>0) {
			for (ArrayList<String> registry: table) {
				if (registry.get(0).equals(contabilidadeDTO.getMesAtual())) {
					registry.set(1, contabilidadeDTO.getSaborMaisVendido());
					double lucroMensal = 0.0;
					int quantPizzas = 0;
					if (flag == true) {
						lucroMensal = Double.parseDouble(registry.get(2));
						registry.set(2, Double.toString(lucroMensal));
						quantPizzas = Integer.parseInt(registry.get(3));
						registry.set(3, Integer.toString(quantPizzas));
					} else {
						lucroMensal = Double.parseDouble(registry.get(2));
						lucroMensal += contabilidadeDTO.getLucroDoMes();
						registry.set(2, Double.toString(lucroMensal));
						quantPizzas = Integer.parseInt(registry.get(3));
						quantPizzas += contabilidadeDTO.getQuantDePizzasVendidasNoMes();
						registry.set(3, Integer.toString(quantPizzas));
					}
					finishConnection(table);
					this.contabilidadeDTO.setMesAtual(registry.get(0));
					this.contabilidadeDTO.setSaborMaisVendido(registry.get(1));
					this.contabilidadeDTO.setQuantDePizzasVendidasNoMes(Integer.parseInt(registry.get(3)));
					this.contabilidadeDTO.setLucroDoMes(Float.parseFloat(registry.get(2)));
					return this.contabilidadeDTO;
				}
			}

			throw new NovaContabilidadeException();

		} else {
			flag = true;
			createContabilidade(contabilidadeDTO);
			contabilidadeDTO.setPrimeiraVenda(flag);
			return updateContabilidade(contabilidadeDTO);
		}
	}

	public ContabilidadeDTO tableContabilidade() {
		ArrayList<ArrayList<String>> table = checkConnection();
		String[] tableContabilidade = new String[table.size()];
		String contabilidade = "";
		if (table.size()>0) {
			for (ArrayList<String> registry: table) {
				String mesAtual = registry.get(0);
				String saborMaisVendido = registry.get(1);
				String quantPizzas = registry.get(3);
				String lucroMensal = registry.get(2);
				contabilidade += saborMaisVendido + "/" + quantPizzas + "/" + lucroMensal + "/" + mesAtual + "#";	
			}
			tableContabilidade = contabilidade.split("#");
			this.contabilidadeDTO.setTableContabilidade(tableContabilidade);
			return this.contabilidadeDTO;
		} else 
			return null;
	}

}
