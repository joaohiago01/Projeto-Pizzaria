package DAO;

import java.util.ArrayList;

import DTO.ContabilidadeDTO;
import Model.NovaContabilidadeException;

public class ContabilidadeAdapterXML extends ContabilidadeDAOXML implements ContabilidadeDAO {

	@Override
	public void finishConnection(ArrayList<ArrayList<String>> table) {
		super.finishConnection(table);
	}

	@Override
	public ArrayList<ArrayList<String>> checkConnection() {
		return super.checkConnection();
	}

	@Override
	public void createContabilidade(ContabilidadeDTO contabilidadeDTO) {
		super.createContabilidade(contabilidadeDTO);
	}

	@Override
	public ContabilidadeDTO readContabilidade(ContabilidadeDTO contabilidadeDTO) {
		return super.readContabilidade(contabilidadeDTO);
	}

	@Override
	public void deleteContabilidade() {
		super.deleteContabilidade();
	}

	@Override
	public ContabilidadeDTO updateContabilidade(ContabilidadeDTO contabilidadeDTO) throws NovaContabilidadeException {
		return super.updateContabilidade(contabilidadeDTO);
	}

	@Override
	public ContabilidadeDTO tableContabilidade() {
		return super.tableContabilidade();
	}

	
}
