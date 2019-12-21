package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import DAO.ClienteAdapterPostgreSQL;
import DAO.ClienteAdapterXML;
import DAO.ClienteDAO;
import DAO.FuncionarioAdapterPostgreSQL;
import DAO.FuncionarioAdapterXML;
import DAO.FuncionarioDAO;
import DAO.PedidoAdapterPostgreSQL;
import DAO.PedidoAdapterXML;
import DAO.PedidoDAO;
import DAO.PizzaAdapterPostgreSQL;
import DAO.PizzaAdapterXML;
import DAO.PizzaDAO;
import DTO.ClienteDTO;
import DTO.FuncionarioDTO;
import DTO.PedidoDTO;
import DTO.PizzaDTO;
import View.ClienteNaoExisteException;
import View.FuncionarioNaoExisteException;
import View.PedidoNaoEncontradoException;
import View.PizzaNaoEncontradaException;

public class Pizzaiolo {

	//XML
	/*private PedidoDAO pedidoDAO = new PedidoAdapterXML();
	private FuncionarioDAO funcionarioDAO = new FuncionarioAdapterXML();
	private ClienteDAO clienteDAO = new ClienteAdapterXML();
	private PizzaDAO pizzaDAO = new PizzaAdapterXML();*/
	
	//SQL
	private ClienteDAO clienteDAO = new ClienteAdapterPostgreSQL();
	private FuncionarioDAO funcionarioDAO = new FuncionarioAdapterPostgreSQL();
	private PizzaDAO pizzaDAO = new PizzaAdapterPostgreSQL();
	private PedidoDAO pedidoDAO = new PedidoAdapterPostgreSQL();

	public void fazerPizza(PedidoDTO pedidoDTO) throws PedidoNaoEncontradoException {	
		pedidoDTO = pedidoDAO.readPedido(pedidoDTO);
		
		//Iterator
		//Iterator<String> IDsPizza = Arrays.asList(pedidoDTO.getIDsPizzas()).iterator();
		//VetorIteratorPizzaria IDsPizza = new VetorIteratorPizzaria(pedidoDTO.getIDsPizzas());
		
		//Strategy
		ContextStrategy strategy = new ContextStrategy(pedidoDTO.getIDsPizzas());
		IteratorPizzaria IDsPizza = strategy.kindIterator();
		
 		while (IDsPizza.hasNext()) {
			PizzaDTO pizzaDTO = new PizzaDTO();
			pizzaDTO.setCodigoID(Integer.parseInt(IDsPizza.next().toString()));
			pizzaDTO.setStatus("Pronta");
			pizzaDAO.updatePizza(pizzaDTO);
		}
		pedidoDTO.setStatus("Finalizado");
		pedidoDAO.updatePedido(pedidoDTO);
	}
	
	public FuncionarioDTO buscarPizzaiolo(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {
		return funcionarioDAO.readFuncionario(funcionarioDTO);
	}
	
	public ClienteDTO buscarCliente(ClienteDTO clienteDTO) throws ClienteNaoExisteException {		
		return clienteDAO.readCliente(clienteDTO);
	}
	
	public PedidoDTO buscarPedido(PedidoDTO pedidoDTO) throws PedidoNaoEncontradoException {
		return pedidoDAO.readPedido(pedidoDTO);
	}
	
	public PedidoDTO fillPedidos() {
		return pedidoDAO.tablePedido();
	}

	public PizzaDTO fillPizzas(PedidoDTO pedidoDTO) throws PedidoNaoEncontradoException {
		pedidoDTO = pedidoDAO.readPedido(pedidoDTO);
		
		//Iterator
		//Iterator<String> IDsDoPedido = Arrays.asList(pedidoDTO.getIDsPizzas()).iterator();
		//VetorIteratorPizzaria IDsDoPedido = new VetorIteratorPizzaria(pedidoDTO.getIDsPizzas());
		
		//Strategy
		ContextStrategy strategy = new ContextStrategy(pedidoDTO.getIDsPizzas());
		IteratorPizzaria IDsDoPedido = strategy.kindIterator();
		
		PizzaDTO pizzaDTO = new PizzaDTO();
		String IDsPizzas = "";
		while (IDsDoPedido.hasNext()) {
			IDsPizzas += "Pizza Nº" +":"+ IDsDoPedido.next().toString() + "#";
		}
		String[] tablePizzasDoPedido = IDsPizzas.split("#");
		pizzaDTO.setTablePizza(tablePizzasDoPedido);
		return pizzaDTO;
	}

	public PizzaDTO buscarPizza(PedidoDTO pedidoDTO) throws PizzaNaoEncontradaException, PedidoNaoEncontradoException {
		pedidoDTO = pedidoDAO.readPedido(pedidoDTO);
		PizzaDTO pizzaDTO = new PizzaDTO();
		pizzaDTO.setTablePizza(pedidoDTO.getIDsPizzas());
		
		//Iterator
		//Iterator<String> IDs = Arrays.asList(pedidoDTO.getIDsPizzas()).iterator();
		//VetorIteratorPizzaria IDs = new VetorIteratorPizzaria(pedidoDTO.getIDsPizzas());
		
		//Strategy
		ContextStrategy strategy = new ContextStrategy(pedidoDTO.getIDsPizzas());
		IteratorPizzaria IDs = strategy.kindIterator();
		
		ArrayList<PizzaDTO> pizzas = new ArrayList<PizzaDTO>();
		while (IDs.hasNext()) {
			PizzaDTO DTOpizza = new PizzaDTO();
			DTOpizza.setCodigoID(Integer.parseInt(IDs.next().toString()));
			pizzas.add(pizzaDAO.readPizza(DTOpizza));
		}
		pizzaDTO.setPizzas(pizzas);
		return pizzaDTO;
	}

}
