package DAO;

import java.util.ArrayList;

import DTO.FuncionarioDTO;
import View.FuncionarioNaoExisteException;

public interface FuncionarioDAO {

	public ArrayList<ArrayList<String>> checkConnection();
	
	public void finishConnection(ArrayList<ArrayList<String>> tableFuncionario);
	
	public void createFuncionario(FuncionarioDTO funcionarioDTO);
	
	public void deleteFuncionario(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException;
	
	public FuncionarioDTO readFuncionario(FuncionarioDTO funcionarioDTO) throws FuncionarioNaoExisteException;
	
	public void updateFuncionario(FuncionarioDTO funcionarioDTO);

	public int IDFuncionario();
	
	public void closeConnection();

}
