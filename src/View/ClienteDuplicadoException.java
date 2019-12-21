package View;

public class ClienteDuplicadoException extends Exception{

	public ClienteDuplicadoException() {
		super("Cliente Já Existente\nTente Novamente");
	}
}
