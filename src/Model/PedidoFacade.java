package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import DAO.ClienteAdapterPostgreSQL;
import DAO.ClienteAdapterXML;
import DAO.ClienteDAO;
import DAO.ContabilidadeAdapterPostgreSQL;
import DAO.ContabilidadeAdapterXML;
import DAO.ContabilidadeDAO;
import DAO.PedidoAdapterPostgreSQL;
import DAO.PedidoAdapterXML;
import DAO.PedidoDAO;
import DAO.PizzaAdapterPostgreSQL;
import DAO.PizzaAdapterXML;
import DAO.PizzaDAO;
import DAO.SaborAdapterPostgreSQL;
import DAO.SaborAdapterXML;
import DAO.SaborDAO;
import DTO.ClienteDTO;
import DTO.ContabilidadeDTO;
import DTO.PedidoDTO;
import DTO.PizzaDTO;
import DTO.SaborDTO;
import View.SaborJaExistenteException;
import View.SaborNaoEncontradoException;

public class PedidoFacade {

	private PedidoDAO pedidoDAO;
	private PizzaDAO pizzaDAO;
	private SaborDAO saborDAO;
	private ContabilidadeDAO contabilidadeDAO;
	private ClienteDAO clienteDAO;

	public PedidoFacade() {
		
		//XML
		/*clienteDAO = new ClienteAdapterXML();
		saborDAO = new SaborAdapterXML();
		pizzaDAO = new PizzaAdapterXML();
		pedidoDAO = new PedidoAdapterXML();
		contabilidadeDAO = new ContabilidadeAdapterXML();*/
		
		//SQL
		clienteDAO = new ClienteAdapterPostgreSQL();
		saborDAO = new SaborAdapterPostgreSQL();
		pizzaDAO = new PizzaAdapterPostgreSQL();
		pedidoDAO = new PedidoAdapterPostgreSQL();
		contabilidadeDAO = new ContabilidadeAdapterPostgreSQL();
	}
	
	public PedidoDTO pizzasDoPedido (PedidoDTO pedidoDTO) {

		String IDs = "";
		double totalPedido = 0.0;
		double imposto = 0.0;
		
		//Iterator
		//Iterator<PizzaDTO> pedido = pedidoDTO.getPizzas().iterator();
		//ArrayListIteratorPizzaria<PizzaDTO> pedido = new ArrayListIteratorPizzaria<PizzaDTO>(pedidoDTO.getPizzas());
		
		//Strategy
		ContextStrategy strategyIterator = new ContextStrategy(pedidoDTO.getPizzas());
		IteratorPizzaria pedido = strategyIterator.kindIterator();
		
		PizzariaPorRegiao pizzariaDaRegiao = null;
		switch (pedidoDTO.getRegiao()) {
		case "PB":
			pizzariaDaRegiao = new PizzariaPB();
			break;

		case "PE":
			pizzariaDaRegiao = new PizzariaPE();
			break;
		}
		PizzaDaRegiao pizzaDaRegiao = null;
		
		while (pedido.hasNext()) {
			
			PizzaDTO pizzasPedidas = (PizzaDTO) pedido.next();
			//Contabilidade
			SaborDTO saborDTO = new SaborDTO();
			saborDTO.setDescricao(pizzasPedidas.getSabor());
			saborDAO.updateSabor(saborDTO);
			//
			
			//Pizza
			PizzaDTO pizzaDTO = new PizzaDTO();
			int ID = pizzaDAO.IDPizza();
			pizzaDTO.setCodigoID(ID);
			pizzaDTO.setCPFCliente(pedidoDTO.getCPFDoCliente());
			pizzaDTO.setSabor(pizzasPedidas.getSabor());
			pizzaDTO.setTamanho(pizzasPedidas.getTamanho());
			pizzaDTO.setPrecoCompleta(pizzasPedidas.getPrecoCompleta());
			pizzaDTO.setStatus("Em Produção");
			pizzaDAO.createPizza(pizzaDTO);
			//
			
			//Pedido
			IDs += ID + "#";
			pizzaDaRegiao = pizzariaDaRegiao.impostoDaRegiao(pizzasPedidas.getSabor());
			imposto += (pizzasPedidas.getPrecoCompleta() * pizzaDaRegiao.impostoDaPizza());
			System.out.println(imposto);
			totalPedido += pizzasPedidas.getPrecoCompleta();
			//+
		}
		
		//Pedido
		String[] IDsPizzas = IDs.split("#");
		pedidoDTO.setImposto(imposto);//IMPOSTO DA REGIÃO É DESCONTADO DO LUCRO DO MÊS NA CONTABILIDADE
		pedidoDTO.setPrecoTotal(totalPedido);
		pedidoDTO.setStatus("Em Produção");
		pedidoDTO.setIDsPizzas(IDsPizzas);
		int IDPedido = pedidoDAO.IDPedido();
		pedidoDTO.setIDPedido(IDPedido);
		pedidoDAO.createPedido(pedidoDTO);
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setIDPedido(Integer.toString(IDPedido));
		clienteDTO.setCPF(pedidoDTO.getCPFDoCliente());
		clienteDAO.updateCliente(clienteDTO);
		return pedidoDTO;
	}

	public String saborMaisPedido () throws SaborNaoEncontradoException {
		
		ArrayList<SaborDTO> contSabores = new ArrayList<SaborDTO>();
		
		ContextStrategy strategyIterator = new ContextStrategy(saborDAO.tableSabor().getSabores());
		IteratorPizzaria sabores = strategyIterator.kindIterator();
		
		while (sabores.hasNext()) {
			SaborDTO saborDTO = new SaborDTO();
			saborDTO.setDescricao(sabores.next().toString());
			saborDTO = saborDAO.readSabor(saborDTO);
			contSabores.add(saborDTO);
		}
		
		int maisPedido = 0;
		for (int i = 0; i<contSabores.size()-1; i++) {
			
			if (contSabores.get(maisPedido).getQuantMaisPedido()<=contSabores.get(i+1).getQuantMaisPedido()) {
				maisPedido = i+1;
			}
		}
		return contSabores.get(maisPedido).getDescricao();
	}
	
	public void contabilidadeDoPedido (PedidoDTO pedidoDTO) throws SaborNaoEncontradoException, SaborJaExistenteException {

		ContabilidadeDTO contabilidadeDTO = new ContabilidadeDTO();
		
		Date date = new Date(); 
		DateFormat df = new SimpleDateFormat("DD/MM/YYYY");
		String dtCompleta[] = df.format(date).split("/");
		String dataPedido = dtCompleta[1] + "/" + dtCompleta[2];
		contabilidadeDTO.setMesAtual(dataPedido);
		contabilidadeDTO.setQuantDePizzasVendidasNoMes(pedidoDTO.getIDsPizzas().length);
		contabilidadeDTO.setLucroDoMes((float) (pedidoDTO.getPrecoTotal() - pedidoDTO.getImposto()));
		contabilidadeDTO.setSaborMaisVendido(saborMaisPedido());
		
		try {
			contabilidadeDAO.updateContabilidade(contabilidadeDTO);
		} catch (NovaContabilidadeException e) {
			
			SaborDTO DTOsabor = new SaborDTO();
			SaborDTO saborDTO = new SaborDTO();
			
			//Iterator
			//Iterator<String> sabores = Arrays.asList(saborDAO.tableSabor().getSabores()).iterator();
			//VetorIteratorPizzaria sabores = new VetorIteratorPizzaria(saborDAO.tableSabor().getSabores());
			
			//Strategy
			ContextStrategy strategyIterator = new ContextStrategy(saborDAO.tableSabor().getSabores());
			IteratorPizzaria sabores = strategyIterator.kindIterator();
			
			while (sabores.hasNext()) {
				
				//Deleto O Sabor, Mas Recupero Seus Dados Para Depois Criar De Novo Mudando Só A Quantidade Pedida
				DTOsabor.setDescricao(sabores.next().toString());
				saborDTO = saborDAO.readSabor(DTOsabor);
				saborDAO.deleteSabor(DTOsabor);
		
				saborDTO.setQuantMaisPedido(0);
				saborDAO.createSabor(saborDTO);
			}
			
			contabilidadeDTO.setSaborMaisVendido(saborMaisPedido());
			contabilidadeDAO.createContabilidade(contabilidadeDTO);
			contabilidadeDTO.setPrimeiraVenda(true);
			try {
				contabilidadeDAO.updateContabilidade(contabilidadeDTO);
			} catch (NovaContabilidadeException e1) {}
		}
		
	}
	
}
