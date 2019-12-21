package View;

public class ClienteNaoExisteException extends Exception{

	public ClienteNaoExisteException() {
		super("Cliente Não Existe");
	}
}
