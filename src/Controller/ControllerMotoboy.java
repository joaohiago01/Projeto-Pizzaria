package Controller;

import DTO.ClienteDTO;
import DTO.FuncionarioDTO;
import DTO.PizzaDTO;
import Model.Motoboy;
import View.ClienteNaoExisteException;
import View.FuncionarioNaoExisteException;
import View.PedidoEmProducaoException;
import View.PedidoNaoEncontradoException;
import View.PizzaNaoEncontradaException;

public class ControllerMotoboy {

	private Motoboy motoboy;
	
	public ControllerMotoboy() {
		this.motoboy = new Motoboy();
	}
	
	public void fazerEntrega(PizzaDTO pizzaDTO) throws PedidoNaoEncontradoException, PedidoEmProducaoException, ClienteNaoExisteException {
		motoboy.fazerEntrega(pizzaDTO);
	}
	
	public PizzaDTO fillPizzas() {
		return motoboy.fillPizzas();
	}
	
	public ClienteDTO buscarCliente(PizzaDTO pizzaDTO) throws ClienteNaoExisteException, PizzaNaoEncontradaException {
		return motoboy.buscarCliente(pizzaDTO);
	}
	
	public FuncionarioDTO buscarMotoboy(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {
		return motoboy.buscarMotoboy(funcionarioDTO);
	}

	
}
