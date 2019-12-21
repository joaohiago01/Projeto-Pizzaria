package Controller;

import DTO.IngredienteDTO;
import DTO.SaborDTO;
import Model.Pizza;
import View.IngredienteDuplicadoException;
import View.SaborJaExistenteException;

public class ControllerPizza {

	private Pizza pizza;
	
	public ControllerPizza() {
		this.pizza = new Pizza();
	}
	
	public SaborDTO fillSabores() {
		return pizza.fillSabores();
	}
	
	public IngredienteDTO fillIngredientes() {
		return pizza.fillIngredientes();
	}

	public void addPizza(SaborDTO saborDTO) throws SaborJaExistenteException {
		pizza.addPizza(saborDTO);
	}

	public void addIngrediente(IngredienteDTO ingredienteDTO) throws IngredienteDuplicadoException {
		pizza.addIngrediente(ingredienteDTO);
	}
}
