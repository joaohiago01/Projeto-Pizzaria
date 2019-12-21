package Model;

import DAO.IngredienteAdapterPostgreSQL;
import DAO.IngredienteAdapterXML;
import DAO.IngredienteDAO;
import DAO.SaborAdapterPostgreSQL;
import DAO.SaborAdapterXML;
import DAO.SaborDAO;
import DTO.IngredienteDTO;
import DTO.SaborDTO;
import View.IngredienteDuplicadoException;
import View.SaborJaExistenteException;

public class Pizza {

	//XML
	/*private IngredienteDAO ingredienteDAO = new IngredienteAdapterXML();
	private SaborDAO saborDAO = new SaborAdapterXML();*/
	
	//SQL
	private IngredienteDAO ingredienteDAO = new IngredienteAdapterPostgreSQL();
	private SaborDAO saborDAO = new SaborAdapterPostgreSQL();
	
	public SaborDTO fillSabores() {
		return saborDAO.tableSabor();
	}

	public IngredienteDTO fillIngredientes() {
		return ingredienteDAO.tableIngrediente();
	}
	
	public void addPizza(SaborDTO saborDTO) throws SaborJaExistenteException {
		String ingredientes = saborDTO.getIngredientes().getNome() +"$"+ saborDTO.getIngredientes().getPreco();
		saborDTO.setTableIngredientes(ingredientes);
		saborDAO.createSabor(saborDTO);
	}

	public void addIngrediente(IngredienteDTO ingredienteDTO) throws IngredienteDuplicadoException {
		ingredienteDAO.createIngrediente(ingredienteDTO);
	}
}
