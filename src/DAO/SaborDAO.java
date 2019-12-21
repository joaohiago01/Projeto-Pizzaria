package DAO;

import java.util.ArrayList;

import DTO.SaborDTO;
import View.SaborJaExistenteException;
import View.SaborNaoEncontradoException;

public interface SaborDAO {
	
	public void finishConnection(ArrayList<ArrayList<String>> table);
	
	public ArrayList<ArrayList<String>> checkConnection();
	
	public void createSabor(SaborDTO saborDTO) throws SaborJaExistenteException;
	
	public void deleteSabor(SaborDTO saborDTO);
	
	public SaborDTO readSabor(SaborDTO saborDTO) throws SaborNaoEncontradoException;
	
	public SaborDTO updateSabor(SaborDTO saborDTO);
	
	public SaborDTO tableSabor();

}
