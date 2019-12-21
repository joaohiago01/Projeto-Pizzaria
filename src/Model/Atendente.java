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

public class Atendente {
	
	//SQL
	private ClienteDAO clienteDAO = new ClienteAdapterPostgreSQL();
	private FuncionarioDAO funcionarioDAO = new FuncionarioAdapterPostgreSQL();
	private PizzaDAO pizzaDAO = new PizzaAdapterPostgreSQL();
	private PedidoDAO pedidoDAO = new PedidoAdapterPostgreSQL();
	
	//XML
	/*private ClienteDAO clienteDAO = new ClienteAdapterXML();
	private FuncionarioDAO funcionarioDAO = new FuncionarioAdapterXML();
	private PizzaDAO pizzaDAO = new PizzaAdapterXML();
	private PedidoDAO pedidoDAO = new PedidoAdapterXML();*/
	
	
	public ClienteDTO fillCliente() {
		return clienteDAO.tableCliente();
	}
	
	public FuncionarioDTO buscarAtendente(FuncionarioDTO atendenteDTO) throws FuncionarioNaoExisteException {
		return funcionarioDAO.readFuncionario(atendenteDTO);
	}

	public ClienteDTO buscarCliente(ClienteDTO clienteDTO) throws ClienteNaoExisteException {		
		return clienteDAO.readCliente(clienteDTO);
	}
	
	public PedidoDTO tablePedidos() {
		return pedidoDAO.tablePedido();
	}

	public PedidoDTO buscarPedido(PedidoDTO pedidoDTO) throws PedidoNaoEncontradoException {
		return pedidoDAO.readPedido(pedidoDTO);
	}

	public PizzaDTO fillPizzas(PedidoDTO pedidoDTO) throws PedidoNaoEncontradoException, PizzaNaoEncontradaException {
		pedidoDTO = pedidoDAO.readPedido(pedidoDTO);
		PizzaDTO pizzaDTO = new PizzaDTO();
		String IDsPizzas = "";
		
		//Iterator
		//Iterator<String> IDs = Arrays.asList(pedidoDTO.getIDsPizzas()).iterator();
		//VetorIteratorPizzaria IDs = new VetorIteratorPizzaria(pedidoDTO.getIDsPizzas());
		
		//Strategy
		ContextStrategy strategyIterator = new ContextStrategy(pedidoDTO.getIDsPizzas());
		IteratorPizzaria IDs = strategyIterator.kindIterator();
		
		while (IDs.hasNext()) {
			pizzaDTO.setCodigoID(Integer.parseInt(IDs.next().toString()));
			IDsPizzas += pizzaDAO.readPizza(pizzaDTO).getSabor() + "#";
		}
		String[] tablePizzasDoPedido = IDsPizzas.split("#");
		pizzaDTO.setTablePizza(tablePizzasDoPedido);
		return pizzaDTO;
	}
	
	public PizzaDTO buscarPizza(PedidoDTO pedidoDTO) throws PizzaNaoEncontradaException, PedidoNaoEncontradoException {
		pedidoDTO = pedidoDAO.readPedido(pedidoDTO);
		String IDs[] = pedidoDTO.getIDsPizzas();
		PizzaDTO pizzaDTO = new PizzaDTO();
		pizzaDTO.setTablePizza(IDs);
		
		//Iterator
		//Iterator<String> IDsPizzas = Arrays.asList(IDs).iterator();
		//VetorIteratorPizzaria IDsPizzas = new VetorIteratorPizzaria(pedidoDTO.getIDsPizzas());
		
		//Strategy
		ContextStrategy strategyIterator = new ContextStrategy(pedidoDTO.getIDsPizzas());
		IteratorPizzaria IDsPizzas = strategyIterator.kindIterator();
		
		ArrayList<PizzaDTO> pizzas = new ArrayList<PizzaDTO>();
		while (IDsPizzas.hasNext()) {
			PizzaDTO DTOpizza = new PizzaDTO();
			DTOpizza.setCodigoID(Integer.parseInt(IDsPizzas.next().toString()));
			pizzas.add(pizzaDAO.readPizza(DTOpizza));
		}
		pizzaDTO.setPizzas(pizzas);
		return pizzaDTO;
	}
}
