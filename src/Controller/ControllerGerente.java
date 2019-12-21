package Controller;

import DTO.ContabilidadeDTO;
import DTO.FuncionarioDTO;
import Model.Gerente;
import View.FuncionarioNaoExisteException;

public class ControllerGerente {

	private Gerente gerente;
	
	public ControllerGerente() {
		this.gerente = new Gerente();	
	}

	public ContabilidadeDTO fillTableContabilidade() {
		return gerente.fillTableContabilidade();
	}

	public FuncionarioDTO buscarGerente(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {
		return gerente.buscarGerente(funcionarioDTO);
	}
}
