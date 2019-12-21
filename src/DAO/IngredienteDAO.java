package DAO;

import java.util.ArrayList;

import DTO.IngredienteDTO;
import View.IngredienteDuplicadoException;
import View.IngredienteNaoEncontradoException;

public interface IngredienteDAO {

	public void finishConnection(ArrayList<ArrayList<String>> table);
	
	public ArrayList<ArrayList<String>> checkConnection();
	
	public void createIngrediente(IngredienteDTO ingredienteDTO) throws IngredienteDuplicadoException;
	
	public void deleteIngrediente(IngredienteDTO ingredienteDTO) throws IngredienteNaoEncontradoException;
	
	public IngredienteDTO readIngrediente(IngredienteDTO ingredienteDTO) throws IngredienteNaoEncontradoException;
	
	public void updateIngrediente(IngredienteDTO ingredienteDTO);

	public IngredienteDTO tableIngrediente();
	
}
