package DAO;

import java.util.ArrayList;

import DTO.IngredienteDTO;
import View.IngredienteDuplicadoException;
import View.IngredienteNaoEncontradoException;

public class IngredienteAdapterXML extends IngredienteDAOXML implements IngredienteDAO {

	@Override
	public void finishConnection(ArrayList<ArrayList<String>> table) {
		super.finishConnection(table);
	}

	@Override
	public ArrayList<ArrayList<String>> checkConnection() {
		return super.checkConnection();
	}

	@Override
	public void createIngrediente(IngredienteDTO ingredienteDTO) throws IngredienteDuplicadoException {
		super.createIngrediente(ingredienteDTO);
	}

	@Override
	public void deleteIngrediente(IngredienteDTO ingredienteDTO) throws IngredienteNaoEncontradoException {
		super.deleteIngrediente(ingredienteDTO);
	}

	@Override
	public IngredienteDTO readIngrediente(IngredienteDTO ingredienteDTO) throws IngredienteNaoEncontradoException {
		return super.readIngrediente(ingredienteDTO);
	}

	@Override
	public void updateIngrediente(IngredienteDTO ingredienteDTO) {
		super.updateIngrediente(ingredienteDTO);
	}

	@Override
	public IngredienteDTO tableIngrediente() {
		return super.tableIngrediente();
	}

	
}
