package Controller;

import DTO.ClienteDTO;
import DTO.FuncionarioDTO;
import DTO.PedidoDTO;
import DTO.PizzaDTO;
import Model.Pizzaiolo;
import View.ClienteNaoExisteException;
import View.FuncionarioNaoExisteException;
import View.PedidoNaoEncontradoException;
import View.PizzaNaoEncontradaException;

public class ControllerPizzaiolo {

	private Pizzaiolo pizzaiolo;
	
	public ControllerPizzaiolo () {
		this.pizzaiolo = new Pizzaiolo();
	}

	public void fazerPizza(PedidoDTO pedidoDTO) throws ClienteNaoExisteException, PedidoNaoEncontradoException {
		pizzaiolo.fazerPizza(pedidoDTO);
	}
	
	public PedidoDTO fillPedidos() {
		return pizzaiolo.fillPedidos();
	}
	
	public PizzaDTO fillPizzas(PedidoDTO pedidoDTO) throws PedidoNaoEncontradoException {
		return pizzaiolo.fillPizzas(pedidoDTO);
	}

	public FuncionarioDTO buscarPizzaiolo(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {
		return pizzaiolo.buscarPizzaiolo(funcionarioDTO);
	}

	public ClienteDTO buscarCliente(ClienteDTO clienteDTO) throws ClienteNaoExisteException {
		return pizzaiolo.buscarCliente(clienteDTO);
	}
	
	public PedidoDTO buscarPedido(PedidoDTO pedidoDTO) throws PedidoNaoEncontradoException {
		return pizzaiolo.buscarPedido(pedidoDTO);
	}
	
	public PizzaDTO buscarPizza(PedidoDTO pedidoDTO) throws PizzaNaoEncontradaException, PedidoNaoEncontradoException {
		return pizzaiolo.buscarPizza(pedidoDTO);
	}

}
