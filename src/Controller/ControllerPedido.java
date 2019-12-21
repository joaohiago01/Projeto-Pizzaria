package Controller;

import DTO.PedidoDTO;
import DTO.SaborDTO;
import Model.Pedido;
import View.SaborJaExistenteException;
import View.SaborNaoEncontradoException;

public class ControllerPedido {

	private Pedido pedido;
	
	public ControllerPedido() {
		this.pedido = new Pedido();
	}

	public void fazerPedido(PedidoDTO pedidoDTO) throws SaborNaoEncontradoException, SaborJaExistenteException {
		pedido.fazerPedido(pedidoDTO);
	}
	
	public SaborDTO fillSabores() {
		return pedido.fillSabores();
	}

	public void impostoDaRegiao(String regiao) {
		pedido.imposto(regiao);
	}
}
