package Model;

import DAO.FuncionarioAdapterPostgreSQL;
import DAO.FuncionarioAdapterXML;
import DAO.FuncionarioDAO;
import DTO.FuncionarioDTO;
import View.FuncionarioNaoExisteException;

public class Funcionario {

	//XML
	//private FuncionarioDAO funcionarioDAO = new FuncionarioAdapterXML();
	
	//SQL
	private FuncionarioDAO funcionarioDAO = new FuncionarioAdapterPostgreSQL();

	public FuncionarioDTO entrar(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {

		if (funcionarioDTO.getCodigoID().equals("0") && funcionarioDTO.getSenha().equals("admin")) {
			funcionarioDTO.setCodigoID("0");
			funcionarioDTO.setCargo("admin");
			funcionarioDTO.setTelefone("admin");
			funcionarioDTO.setNome("admin");
			funcionarioDTO.setSenha("admin");
			if (funcionarioDAO.readFuncionario(funcionarioDTO).getCodigoID() == null)
				funcionarioDAO.createFuncionario(funcionarioDTO);
			return funcionarioDTO;
		} else {
			String senha = funcionarioDTO.getSenha();
			String ID = funcionarioDTO.getCodigoID();
			FuncionarioDTO DTOfuncionario = new FuncionarioDTO();
			DTOfuncionario.setCodigoID(ID);
			DTOfuncionario = funcionarioDAO.readFuncionario(DTOfuncionario);
			if (DTOfuncionario.getSenha().equals(senha)) {
				return DTOfuncionario;
			} else {
				throw new FuncionarioNaoExisteException();
			}
		}
	}

	public FuncionarioDTO recuperarSenha(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {	
		return funcionarioDAO.readFuncionario(funcionarioDTO);
	}

	public void cadastrarFuncionario(FuncionarioDTO funcionarioDTO) {
		funcionarioDAO.createFuncionario(funcionarioDTO);
	}

	public void demitirFuncionario(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException {
		funcionarioDAO.deleteFuncionario(funcionarioDTO);
	}

	public int contIDFuncionario() {
		return funcionarioDAO.IDFuncionario() - 1;
	}

	public void closeConnection() {
		funcionarioDAO.closeConnection();
	}
}
