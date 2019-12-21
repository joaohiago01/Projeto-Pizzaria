package Model;

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
import View.PedidoEmProducaoException;
import View.PedidoNaoEncontradoException;
import View.PizzaNaoEncontradaException;

public class Motoboy {

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

	public PizzaDTO fillPizzas() {
		return pizzaDAO.tablePizza();
	}

	public FuncionarioDTO buscarMotoboy(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {
		return funcionarioDAO.readFuncionario(funcionarioDTO);
	}

	public void fazerEntrega(PizzaDTO pizzaDTO) throws PedidoEmProducaoException, PedidoNaoEncontradoException, ClienteNaoExisteException{

		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setCPF(pizzaDTO.getCPFCliente());
		clienteDTO = clienteDAO.readCliente(clienteDTO);
		
		String IDsPedidoDoCliente = clienteDTO.getIDPedido();
		String IDsPedCli[] = IDsPedidoDoCliente.split("#");
		
		//Iterator
		//VetorIteratorPizzaria IDs = new VetorIteratorPizzaria(IDsPedCli);
		
		//Strategy
		ContextStrategy strategy = new ContextStrategy(IDsPedCli);
		IteratorPizzaria IDs = strategy.kindIterator();
		
		PedidoDTO pedidoDTO = new PedidoDTO();
		pedidoDTO.setCPFDoCliente(pizzaDTO.getCPFCliente());
		while (IDs.hasNext()) {
			pedidoDTO.setIDPedido(Integer.parseInt(IDs.next().toString()));
			pedidoDTO = pedidoDAO.readPedido(pedidoDTO);
			if (pedidoDTO.getStatus().equalsIgnoreCase("Em Produção")) {
				throw new PedidoEmProducaoException();
			}
		}

		PizzaDTO DTOpizza = new PizzaDTO();
		DTOpizza = pizzaDAO.tableEntrega();
		
		//Iterator
		//ArrayListIteratorPizzaria<PizzaDTO> pizzasEntrega = new ArrayListIteratorPizzaria<PizzaDTO>(DTOpizza.getPizzas());
		
		//Strategy
		ContextStrategy strategy2 = new ContextStrategy(DTOpizza.getPizzas());
		IteratorPizzaria pizzasEntrega = strategy2.kindIterator();
		
		String CPF = pizzaDTO.getCPFCliente();
		while (pizzasEntrega.hasNext()) {
			PizzaDTO pizza = (PizzaDTO) pizzasEntrega.next();
			if (pizza.getCPFCliente().equals(CPF)) {
				PizzaDTO pi = new PizzaDTO();
				pi.setCodigoID(pizza.getCodigoID());
				pi.setStatus("Entregue");
				pizzaDAO.updatePizza(pi);
			}
		}
		
		PedidoDTO DTOpedido = new PedidoDTO();
		DTOpedido.setCPFDoCliente(CPF);
		strategy = new ContextStrategy(IDsPedCli);
		IteratorPizzaria IDsFinalizados = strategy.kindIterator();
		while (IDsFinalizados.hasNext()) {
			DTOpedido.setIDPedido(Integer.parseInt(IDsFinalizados.next().toString()));
			DTOpedido.setStatus("Entregue");
			pedidoDAO.updatePedido(DTOpedido);
		}
	}

	public ClienteDTO buscarCliente(PizzaDTO pizzaDTO) throws ClienteNaoExisteException, PizzaNaoEncontradaException {
		pizzaDTO = pizzaDAO.readPizza(pizzaDTO);
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setCPF(pizzaDTO.getCPFCliente());
		clienteDTO = clienteDAO.readCliente(clienteDTO);
		return clienteDTO;
	}

}
