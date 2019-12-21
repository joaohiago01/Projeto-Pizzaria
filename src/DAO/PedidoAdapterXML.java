package DAO;

import java.util.ArrayList;

import DTO.PedidoDTO;
import View.PedidoNaoEncontradoException;

public class PedidoAdapterXML extends PedidoDAOXML implements PedidoDAO {

	@Override
	public void finishConnection(ArrayList<ArrayList<String>> table) {
		super.finishConnection(table);
	}

	@Override
	public ArrayList<ArrayList<String>> checkConnection() {
		return super.checkConnection();
	}

	@Override
	public void createPedido(PedidoDTO pedidoDTO) {
		super.createPedido(pedidoDTO);
	}

	@Override
	public void deletePedido(PedidoDTO pedidoDTO) {
		super.deletePedido(pedidoDTO);
	}

	@Override
	public void updatePedido(PedidoDTO pedidoDTO) {
		super.updatePedido(pedidoDTO);
	}

	@Override
	public PedidoDTO readPedido(PedidoDTO pedidoDTO) throws PedidoNaoEncontradoException {
		return super.readPedido(pedidoDTO);
	}

	@Override
	public PedidoDTO tablePedido() {
		return super.tablePedido();
	}

	@Override
	public int IDPedido() {
		return super.IDPedido();
	}

}
