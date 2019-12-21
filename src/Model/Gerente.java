package Model;

import DAO.ContabilidadeAdapterPostgreSQL;
import DAO.ContabilidadeAdapterXML;
import DAO.ContabilidadeDAO;
import DAO.FuncionarioAdapterPostgreSQL;
import DAO.FuncionarioAdapterXML;
import DAO.FuncionarioDAO;
import DTO.ContabilidadeDTO;
import DTO.FuncionarioDTO;
import View.FuncionarioNaoExisteException;

public class Gerente {

	//XML
	/*private ContabilidadeDAO contabilidadeDAO = new ContabilidadeAdapterXML();
	private FuncionarioDAO funcionarioDAO = new FuncionarioAdapterXML();*/
	
	//SQL
	private FuncionarioDAO funcionarioDAO = new FuncionarioAdapterPostgreSQL();
	private ContabilidadeDAO contabilidadeDAO = new ContabilidadeAdapterPostgreSQL();
	
	public ContabilidadeDTO fillTableContabilidade() {
		ContabilidadeDTO contabilidadeDTO = new ContabilidadeDTO();
		contabilidadeDTO = contabilidadeDAO.tableContabilidade();
		return contabilidadeDTO;
	}

	public FuncionarioDTO buscarGerente(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {
		return funcionarioDAO.readFuncionario(funcionarioDTO);
	}

}
