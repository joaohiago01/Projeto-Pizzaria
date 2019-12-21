package Controller;

import DTO.ClienteDTO;
import DTO.FuncionarioDTO;
import DTO.PedidoDTO;
import DTO.PizzaDTO;
import Model.Atendente;
import View.ClienteNaoExisteException;
import View.FuncionarioNaoExisteException;
import View.PedidoNaoEncontradoException;
import View.PizzaNaoEncontradaException;

public class ControllerAtendente {

	private Atendente atendente;
	
	public ControllerAtendente() {
		this.atendente = new Atendente();
	}
	
	public  ClienteDTO fillCliente() {
		return atendente.fillCliente();
	}
	
	public ClienteDTO buscarCliente(ClienteDTO clienteDTO) throws ClienteNaoExisteException {
		return atendente.buscarCliente(clienteDTO);
	}
	
	public FuncionarioDTO buscarAtendente(FuncionarioDTO atendenteDTO) throws FuncionarioNaoExisteException {
		return atendente.buscarAtendente(atendenteDTO);
	}
	
	public PedidoDTO buscarPedido(PedidoDTO pedidoDTO) throws PedidoNaoEncontradoException {
		return atendente.buscarPedido(pedidoDTO);
	}
	
	public PedidoDTO tablePedidos() {
		return atendente.tablePedidos();
	}

	public PizzaDTO fillPizzas(PedidoDTO pedidoDTO) throws PedidoNaoEncontradoException, PizzaNaoEncontradaException {
		return atendente.fillPizzas(pedidoDTO);
	}
	
	public PizzaDTO buscarPizza(PedidoDTO pedidoDTO) throws PizzaNaoEncontradaException, PedidoNaoEncontradoException {
		return atendente.buscarPizza(pedidoDTO);
	}
	
}
