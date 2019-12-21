package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controller.ControllerGerente;
import DTO.ContabilidadeDTO;
import DTO.FuncionarioDTO;
import Model.ContextStrategy;
import Model.IteratorPizzaria;
import Model.VetorIteratorPizzaria;

public class ViewGerente extends ViewPadrao {
	
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel();
	private String ID;
	private String regiao;
	private JButton btnAtendente, btnPizzaiolo, btnMotoboy, btnSair, btnFuncionrios, btnCardpio; 
	private ControllerGerente controllerGerente = new ControllerGerente();
	private ContabilidadeDTO contabilidadeDTO = new ContabilidadeDTO();
	private FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
	
	public ViewGerente(String ID, String regiao) {
		super();
		this.ID = ID;
		this.regiao = regiao;
		this.setSize(800, 450);
		this.setLocationRelativeTo(null);
		model.addColumn("Mês/Ano");
		model.addColumn("Sabor Do Mês");
		model.addColumn("Quantidade De Pizzas");
		model.addColumn("Lucro");

		contabilidadeDTO = controllerGerente.fillTableContabilidade();
		if (contabilidadeDTO != null) {
			
			//Iterator
			//Iterator<String> tableContabilidade = Arrays.asList(contabilidadeDTO.getTableContabilidade()).iterator();
			//VetorIteratorPizzaria tableContabilidade = new VetorIteratorPizzaria(contabilidadeDTO.getTableContabilidade());
			
			//Strategy
			ContextStrategy strategy = new ContextStrategy(contabilidadeDTO.getTableContabilidade());
			IteratorPizzaria tableContabilidade = strategy.kindIterator();
			
			while (tableContabilidade.hasNext()) {
				String[] contabilidadeMeses = tableContabilidade.next().toString().split("/");
				Object[] row = new Object[] {
						contabilidadeMeses[3] +"/"+ contabilidadeMeses[4],
						contabilidadeMeses[0],
						contabilidadeMeses[1],
						contabilidadeMeses[2]
				};
				model.addRow(row);
			}
		}

		table = new JTable(model);
		table.setBounds(60, 69, 402, 111);
		getContentPane().add(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(174, 111, 610, 250);
		getContentPane().add(scrollPane);

		funcionarioDTO.setCodigoID(ID);
		try {
			funcionarioDTO = controllerGerente.buscarGerente(funcionarioDTO);
		} catch (FuncionarioNaoExisteException e) {}
		JLabel lblGerente = new JLabel("Gerente: " + funcionarioDTO.getNome() + " |" + "ID: " + funcionarioDTO.getCodigoID());
		lblGerente.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblGerente.setBounds(10, 11, 276, 35);
		getContentPane().add(lblGerente);

		btnAtendente = new JButton("Atendente");
		btnAtendente.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnAtendente.setBounds(24, 200, 140, 25);
		getContentPane().add(btnAtendente);

		btnPizzaiolo = new JButton("Pizzaiolo");
		btnPizzaiolo.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnPizzaiolo.setBounds(24, 248, 140, 25);
		getContentPane().add(btnPizzaiolo);

		btnMotoboy = new JButton("Motoboy");
		btnMotoboy.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnMotoboy.setBounds(24, 295, 140, 25);
		getContentPane().add(btnMotoboy);

		btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnSair.setBounds(24, 338, 140, 25);
		getContentPane().add(btnSair);

		btnFuncionrios = new JButton("Funcion\u00E1rios");
		btnFuncionrios.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnFuncionrios.setBounds(24, 154, 140, 25);
		getContentPane().add(btnFuncionrios);

		btnCardpio = new JButton("Card\u00E1pio");
		btnCardpio.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnCardpio.setBounds(24, 107, 140, 25);
		getContentPane().add(btnCardpio);

		JLabel lblContabilidade = new JLabel("Contabilidade");
		lblContabilidade.setFont(new Font("Courier New", Font.BOLD, 17));
		lblContabilidade.setBounds(419, 66, 190, 14);
		getContentPane().add(lblContabilidade);

		addListener(this);

		setVisible(true);
	}

	public void addListener(ViewGerente viewGerente) {
		OuvinteGerente ouvinteGerente = new OuvinteGerente(viewGerente);
		viewGerente.btnAtendente.addActionListener(ouvinteGerente);
		viewGerente.btnCardpio.addActionListener(ouvinteGerente);
		viewGerente.btnFuncionrios.addActionListener(ouvinteGerente);
		viewGerente.btnMotoboy.addActionListener(ouvinteGerente);
		viewGerente.btnPizzaiolo.addActionListener(ouvinteGerente);
		viewGerente.btnSair.addActionListener(ouvinteGerente);
	}

	public class OuvinteGerente implements ActionListener {

		private ViewGerente viewGerente;

		public OuvinteGerente(ViewGerente viewGerente) {
			this.viewGerente = viewGerente;
		}

		public void actionPerformed(ActionEvent e) {

			String button = e.getActionCommand();

			switch (button) {

			case "Cardápio":
				new ViewCadastroPizza(ID, viewGerente);
				break;
			case "Funcionários":
				new ViewCadastroFuncionario(ID, viewGerente);
				break;
			case "Atendente":
				viewGerente.dispose();
				new ViewAtendente(ID, viewGerente.regiao);
				break;
			case "Pizzaiolo":
				viewGerente.dispose();
				new ViewPizzaiolo(ID);
				break;
			case "Motoboy":
				viewGerente.dispose();
				new ViewMotoboy(ID);
				break;
			case "Sair":
				viewGerente.dispose();
				new ViewInicio();
				break;
			}
		}
	}

}
