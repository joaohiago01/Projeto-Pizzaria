package Controller;

import DTO.FuncionarioDTO;
import Model.Funcionario;
import View.FuncionarioNaoExisteException;

public class ControllerFuncionario {
	
	private Funcionario funcionario;
	
	public ControllerFuncionario() {
		this.funcionario = new Funcionario();
	}

	public FuncionarioDTO entrar(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {
		return funcionario.entrar(funcionarioDTO);
	}
	
	public FuncionarioDTO recuperarSenha(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {
		return funcionario.recuperarSenha(funcionarioDTO);
	}

	public void cadastrarFuncionario(FuncionarioDTO funcionarioDTO) {
		funcionario.cadastrarFuncionario(funcionarioDTO);
	}
	
	public void demitirFuncionario(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {
		funcionario.demitirFuncionario(funcionarioDTO);
	}

	public int IDFuncionario() {
		return funcionario.contIDFuncionario();
	}

	public void closeConnection() {
		funcionario.closeConnection();
	}
}
