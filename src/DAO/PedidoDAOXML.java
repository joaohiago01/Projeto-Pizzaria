package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import DTO.PedidoDTO;
import View.PedidoNaoEncontradoException;

public class PedidoDAOXML {

	private XStream xstream = new XStream(new DomDriver("ISO-8859-1"));
	private File arquivo;
	private ArrayList<ArrayList<String>> tablePedido = new ArrayList<ArrayList<String>>();
	private PedidoDTO pedidoDTO = new PedidoDTO();

	public void finishConnection(ArrayList<ArrayList<String>> table) {
		arquivo = new File("pedidos.xml");
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
		arquivo = new File("pedidos.xml");
		try {
			if(arquivo.exists()) {
				FileInputStream fis = new FileInputStream(arquivo);
				return (ArrayList<ArrayList<String>>) xstream.fromXML(fis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return tablePedido;
	}

	public void createPedido(PedidoDTO pedidoDTO) {
		
		String IDPedido = Integer.toString(pedidoDTO.getIDPedido());
		String CPF = pedidoDTO.getCPFDoCliente();
		String Total = Double.toString(pedidoDTO.getPrecoTotal());
		String Status = pedidoDTO.getStatus();
		String IDsPizza = "";
		for (String registryPizza: pedidoDTO.getIDsPizzas()) {
			IDsPizza += registryPizza + "@";
		}
		ArrayList<String> registry = new ArrayList<String>();
		registry.add(CPF);
		registry.add(IDsPizza);
		registry.add(Total);
		registry.add(Status);
		registry.add(IDPedido);

		ArrayList<ArrayList<String>> table = checkConnection();
		table.add(registry);
		finishConnection(table);
	}

	public void deletePedido(PedidoDTO pedidoDTO) {
		String CPF = pedidoDTO.getCPFDoCliente();
		String ID = Integer.toString(pedidoDTO.getIDPedido());
		ArrayList<ArrayList<String>> tablePedido = checkConnection();
		for (ArrayList<String> pedido: tablePedido) {
			if (pedido.get(0).toString().equals(CPF) && pedido.get(4).toString().equals(ID)) {
				tablePedido.remove(pedido);
				break;
			}
		}
		finishConnection(tablePedido);
	}

	public void updatePedido(PedidoDTO pedidoDTO) {
		String CPF = pedidoDTO.getCPFDoCliente();
		String ID = Integer.toString(pedidoDTO.getIDPedido());
		String Status = pedidoDTO.getStatus();
		ArrayList<ArrayList<String>> table = checkConnection();
		for (ArrayList<String> registry: table) {
			if (registry.get(0).toString().equals(CPF) && registry.get(4).toString().equals(ID)) {
				registry.set(3, Status);
				finishConnection(table);
			}
		}
	} 

	public PedidoDTO readPedido(PedidoDTO pedidoDTO) throws PedidoNaoEncontradoException {
		String CPF = pedidoDTO.getCPFDoCliente();
		String ID = Integer.toString(pedidoDTO.getIDPedido());
		ArrayList<ArrayList<String>> tablePedido = checkConnection();
		for (ArrayList<String> registry: tablePedido) {
			if (registry.get(0).toString().equals(CPF) && registry.get(4).toString().equals(ID)) {
				this.pedidoDTO.setCPFDoCliente(registry.get(0));
				String IDsPizza[] = registry.get(1).split("@");
				this.pedidoDTO.setIDsPizzas(IDsPizza);
				this.pedidoDTO.setStatus(registry.get(3));
				this.pedidoDTO.setPrecoTotal(Double.parseDouble(registry.get(2)));
				this.pedidoDTO.setIDPedido(Integer.parseInt(registry.get(4)));
				return this.pedidoDTO;
			}
		}
		throw new PedidoNaoEncontradoException();
	}

	public PedidoDTO tablePedido() {
		ArrayList<ArrayList<String>> tablePedido = checkConnection();
		String pedidos = "";
		for (ArrayList<String> registry: tablePedido) {
			pedidos += registry.get(0) + "/" + registry.get(1) + "/" + registry.get(4) + "#";
		}
		if (tablePedido.size()>0) {
			String table[] = pedidos.split("#");
			this.pedidoDTO.setTablePedido(table);
			return this.pedidoDTO;
		} else {
			return null;
		}
	}
	
	public int IDPedido() {
		ArrayList<ArrayList<String>> table = checkConnection();
		int cont = 1;
		if (table.size()>0) {
			int i = table.size() - 1;
			cont = Integer.parseInt(table.get(i).get(4));
			cont++;
			return cont;
		} else {
			return cont;
		}
	}

}
