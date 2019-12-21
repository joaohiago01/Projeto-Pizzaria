package Model;

import DAO.SaborAdapterPostgreSQL;
import DAO.SaborAdapterXML;
import DAO.SaborDAO;
import DTO.SaborDTO;
import View.SaborJaExistenteException;

public class Sabor {

	//XML
	//private SaborDAO saborDAO = new SaborAdapterXML();
	
	//SQL
	private SaborDAO saborDAO = new SaborAdapterPostgreSQL();

	public SaborDTO tableSabores() {
		return saborDAO.tableSabor();
	}
	
	public void addSabor(SaborDTO saborDTO) throws SaborJaExistenteException {
		saborDAO.createSabor(saborDTO);
	}
}
