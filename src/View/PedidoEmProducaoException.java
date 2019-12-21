package View;

public class PedidoEmProducaoException extends Exception {

	public PedidoEmProducaoException () {
		super ("Ainda Existem Pedidos Desse Cliente Em Produção");
	}
}
