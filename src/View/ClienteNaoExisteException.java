package View;

public class ClienteNaoExisteException extends Exception{

	public ClienteNaoExisteException() {
		super("Cliente N�o Existe");
	}
}
