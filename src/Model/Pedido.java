package Model;

import DAO.SaborAdapterPostgreSQL;
import DAO.SaborAdapterXML;
import DAO.SaborDAO;
import DTO.PedidoDTO;
import DTO.SaborDTO;
import View.SaborJaExistenteException;
import View.SaborNaoEncontradoException;

public class Pedido {

	//XML
	//private SaborDAO saborDAO = new SaborAdapterXML();
	
	//SQL
	private SaborDAO saborDAO = new SaborAdapterPostgreSQL();
	
	private String regiao;
	
	public SaborDTO fillSabores() {
		return saborDAO.tableSabor();
	}

	public void fazerPedido(PedidoDTO pedidoDTO) throws SaborNaoEncontradoException, SaborJaExistenteException {
		
		PedidoFacade pedidoFacade = new PedidoFacade();
		pedidoDTO.setRegiao(imposto(regiao));
		
		//Pedido
		PedidoDTO DTOpedido = pedidoFacade.pizzasDoPedido(pedidoDTO);
		
		//Contabilidade
		pedidoFacade.contabilidadeDoPedido(DTOpedido);
	}

	public String imposto(String regiao) {
		return this.regiao = regiao;
	}

}
