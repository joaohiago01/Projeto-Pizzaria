package DAO;

import java.util.ArrayList;

import DTO.PedidoDTO;
import View.PedidoNaoEncontradoException;

public interface PedidoDAO {

	public void finishConnection(ArrayList<ArrayList<String>> table);
	
	public ArrayList<ArrayList<String>> checkConnection();
	
	public void createPedido(PedidoDTO pedidoDTO);
	
	public void deletePedido(PedidoDTO pedidoDTO);
	
	public PedidoDTO readPedido(PedidoDTO pedidoDTO) throws PedidoNaoEncontradoException;
	
	public void updatePedido(PedidoDTO pedidoDTO);

	public PedidoDTO tablePedido();
	
	public int IDPedido();
}
