package DAO;

import java.util.ArrayList;

import DTO.SaborDTO;
import View.SaborJaExistenteException;
import View.SaborNaoEncontradoException;

public class SaborAdapterPostgreSQL extends SaborDAOPostgreSQL implements SaborDAO {

	@Override
	public void finishConnection(ArrayList<ArrayList<String>> table) {
		super.finishConnection(table);
	}

	@Override
	public ArrayList<ArrayList<String>> checkConnection() {
		return super.checkConnection();
	}

	@Override
	public void createSabor(SaborDTO saborDTO) throws SaborJaExistenteException {
		super.createSabor(saborDTO);
	}

	@Override
	public void deleteSabor(SaborDTO saborDTO) {
		super.deleteSabor(saborDTO);
	}

	@Override
	public SaborDTO readSabor(SaborDTO saborDTO) throws SaborNaoEncontradoException {
		return super.readSabor(saborDTO);
	}

	@Override
	public SaborDTO updateSabor(SaborDTO saborDTO) {
		return super.updateSabor(saborDTO);
	}

	@Override
	public SaborDTO tableSabor() {
		return super.tableSabor();
	}

	
}
