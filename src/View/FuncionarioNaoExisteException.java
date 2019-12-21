package View;

public class FuncionarioNaoExisteException extends Exception{

	public FuncionarioNaoExisteException() {
		super("Funcionário Não Existe");
	}
}
