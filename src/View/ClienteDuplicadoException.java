package View;

public class ClienteDuplicadoException extends Exception{

	public ClienteDuplicadoException() {
		super("Cliente J� Existente\nTente Novamente");
	}
}
