package DAO;

import java.util.ArrayList;

import DTO.ContabilidadeDTO;
import Model.NovaContabilidadeException;

public interface ContabilidadeDAO {

	public void finishConnection(ArrayList<ArrayList<String>> table);
	
	public ArrayList<ArrayList<String>> checkConnection();
	
	public void createContabilidade(ContabilidadeDTO contabilidadeDTO);
	
	public void deleteContabilidade();
	
	public ContabilidadeDTO readContabilidade(ContabilidadeDTO contabilidadeDTO);
	
	public ContabilidadeDTO updateContabilidade(ContabilidadeDTO contabilidadeDTO) throws NovaContabilidadeException;
	
	public ContabilidadeDTO tableContabilidade();
	
}
