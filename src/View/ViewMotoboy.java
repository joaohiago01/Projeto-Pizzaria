package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Controller.ControllerMotoboy;
import DTO.ClienteDTO;
import DTO.FuncionarioDTO;
import DTO.PizzaDTO;
import Model.ContextStrategy;
import Model.IteratorPizzaria;
import Model.VetorIteratorPizzaria;

public class ViewMotoboy extends ViewPadrao	{

	private JButton btnSair, btnFazerEntrega;
	private JLabel lblMotoboy;
	private String id;
	private JTable table;
	private JTable table_1;
	private JScrollPane scrollPane_1;
	private JLabel lblPizzasEntregues;
	private JLabel lblNewLabel;
	private JLabel lblNome;
	private JTextField textFieldNomeCliente;
	private JTextField textFieldEnderecoCliente;
	private JTextField textFieldTelefoneCliente;
	private ControllerMotoboy controllerMotoboy = new ControllerMotoboy();
	private FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
	private PizzaDTO pizzaDTO = new PizzaDTO();
	
	public ViewMotoboy(String id) {
		super();
		getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 13));
		this.setSize(1150, 700);
		this.setLocationRelativeTo(null);
		this.id = id;

		btnFazerEntrega = new JButton("Fazer Entrega");
		btnFazerEntrega.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnFazerEntrega.setBounds(850, 257, 131, 25);
		getContentPane().add(btnFazerEntrega);

		btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnSair.setBounds(991, 257, 119, 25);
		getContentPane().add(btnSair);

		try {
			funcionarioDTO.setCodigoID((id));
			funcionarioDTO = controllerMotoboy.buscarMotoboy(funcionarioDTO);
			lblMotoboy = new JLabel("Motoboy: " + funcionarioDTO.getNome() + " | ID: " + funcionarioDTO.getCodigoID());
			lblMotoboy.setFont(new Font("Times New Roman", Font.BOLD, 16));
			lblMotoboy.setBounds(10, 11, 420, 35);
			getContentPane().add(lblMotoboy);

			JLabel lblPizzas = new JLabel("Pizzas");
			lblPizzas.setFont(new Font("Courier New", Font.BOLD, 17));
			lblPizzas.setBounds(162, 73, 104, 14);
			getContentPane().add(lblPizzas);

			DefaultTableModel modelPizzas = new DefaultTableModel() {
				public boolean isCellEditable(int rowIndex, int columIndex) {
					return false;
				} 
			};

			modelPizzas.addColumn("Cliente");
			modelPizzas.addColumn("ID Pizza");
			modelPizzas.addColumn("Pizza");
			modelPizzas.addColumn("Preço");
			modelPizzas.addColumn("Status");

			pizzaDTO = controllerMotoboy.fillPizzas();

			if (pizzaDTO == null) {
				table = new JTable(modelPizzas);
			} else {
				
				//Iterator
				//Iterator<String> pizzas = Arrays.asList(pizzaDTO.getTablePizza()).iterator();
				//VetorIteratorPizzaria pizzas = new VetorIteratorPizzaria(pizzaDTO.getTablePizza());
				
				//Strategy
				ContextStrategy strategy = new ContextStrategy(pizzaDTO.getTablePizza());
				IteratorPizzaria pizzas = strategy.kindIterator();
				
				while (pizzas.hasNext()) {
					String rowTable[] = pizzas.next().toString().split("/");
					PizzaDTO DTOPizza = new PizzaDTO();
					DTOPizza.setCodigoID(Integer.parseInt(rowTable[3]));
					ClienteDTO clienteDTO = new ClienteDTO();
					clienteDTO = controllerMotoboy.buscarCliente(DTOPizza);
					if (rowTable[2].equals("Pronta")) {
						Object[] row = new Object[] {
								clienteDTO.getNome(),
								rowTable[3],
								rowTable[0],
								rowTable[1],
								rowTable[2]
						};

						modelPizzas.addRow(row);
					}
				}
				table = new JTable(modelPizzas);
				getContentPane().add(table);
			}

			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(0, 114, 400, 557);
			getContentPane().add(scrollPane);

			DefaultTableModel modelPizzasEntregues = new DefaultTableModel() {
				public boolean isCellEditable(int rowIndex, int columIndex) {
					return false;
				} 
			};

			modelPizzasEntregues.addColumn("Cliente");
			modelPizzasEntregues.addColumn("ID Pizza");
			modelPizzasEntregues.addColumn("Pizza");
			modelPizzasEntregues.addColumn("Status");

			if (pizzaDTO == null) {
				table_1 = new JTable(modelPizzasEntregues);
			} else {
				
				//Iterator
				//Iterator<String> pizzasEntregues = Arrays.asList(pizzaDTO.getTablePizza()).iterator();
				//VetorIteratorPizzaria pizzasEntregues = new VetorIteratorPizzaria(pizzaDTO.getTablePizza());
				
				//Strategy
				ContextStrategy strategy = new ContextStrategy(pizzaDTO.getTablePizza());
				IteratorPizzaria pizzasEntregues = strategy.kindIterator();
				
				while (pizzasEntregues.hasNext()) {
					String rowTable[] = pizzasEntregues.next().toString().split("/");
					PizzaDTO DTOpizza = new PizzaDTO();
					DTOpizza.setCodigoID(Integer.parseInt(rowTable[3]));
					ClienteDTO DTOcliente = new ClienteDTO();
					DTOcliente = controllerMotoboy.buscarCliente(DTOpizza);
					if (rowTable[2].equals("Entregue")) {
						Object[] row = new Object[] {
								DTOcliente.getNome(),
								rowTable[3],
								rowTable[0],
								rowTable[2]
						};

						modelPizzasEntregues.addRow(row);
					}
				}
			}

			table_1 = new JTable(modelPizzasEntregues);
			getContentPane().add(table_1);

			scrollPane_1 = new JScrollPane(table_1);
			scrollPane_1.setBounds(415, 117, 400, 557);
			getContentPane().add(scrollPane_1);

			lblPizzasEntregues = new JLabel("Pizzas Entregues");
			lblPizzasEntregues.setFont(new Font("Courier New", Font.BOLD, 17));
			lblPizzasEntregues.setBounds(526, 73, 177, 14);
			getContentPane().add(lblPizzasEntregues);

			lblNewLabel = new JLabel("Dados Do Cliente");
			lblNewLabel.setFont(new Font("Courier New", Font.BOLD, 16));
			lblNewLabel.setBounds(906, 74, 177, 14);
			getContentPane().add(lblNewLabel);

			lblNome = new JLabel("Nome:");
			lblNome.setFont(new Font("Times New Roman", Font.BOLD, 13));
			lblNome.setBounds(825, 127, 46, 15);
			getContentPane().add(lblNome);

			textFieldNomeCliente = new JTextField();
			textFieldNomeCliente.setFont(new Font("Times New Roman", Font.PLAIN, 13));
			textFieldNomeCliente.setBackground(Color.LIGHT_GRAY);
			textFieldNomeCliente.setBounds(867, 124, 273, 25);
			getContentPane().add(textFieldNomeCliente);
			textFieldNomeCliente.setColumns(10);

			JLabel lblEndereo = new JLabel("Endere\u00E7o:");
			lblEndereo.setFont(new Font("Times New Roman", Font.BOLD, 13));
			lblEndereo.setBounds(825, 169, 60, 15);
			getContentPane().add(lblEndereo);

			textFieldEnderecoCliente = new JTextField();
			textFieldEnderecoCliente.setFont(new Font("Times New Roman", Font.PLAIN, 13));
			textFieldEnderecoCliente.setBackground(Color.LIGHT_GRAY);
			textFieldEnderecoCliente.setBounds(886, 166, 255, 25);
			getContentPane().add(textFieldEnderecoCliente);
			textFieldEnderecoCliente.setColumns(10);

			JLabel lblTelefone = new JLabel("Telefone:");
			lblTelefone.setFont(new Font("Times New Roman", Font.BOLD, 13));
			lblTelefone.setBounds(825, 207, 60, 15);
			getContentPane().add(lblTelefone);

			textFieldTelefoneCliente = new JTextField();
			textFieldTelefoneCliente.setFont(new Font("Times New Roman", Font.PLAIN, 13));
			textFieldTelefoneCliente.setBackground(Color.LIGHT_GRAY);
			textFieldTelefoneCliente.setBounds(886, 204, 255, 25);
			getContentPane().add(textFieldTelefoneCliente);
			textFieldTelefoneCliente.setColumns(10);

		} catch (FuncionarioNaoExisteException | ClienteNaoExisteException | PizzaNaoEncontradaException e) {}

		addListener(this);

		setVisible(true);
	}

	public String pizzaSelecionada() {
		return table.getValueAt(table.getSelectedRow(), 1).toString();
	}

	public void addListener(ViewMotoboy viewMotoboy) {
		OuvinteMotoboy ouvinteMotoboy = new OuvinteMotoboy(viewMotoboy);
		viewMotoboy.btnFazerEntrega.addActionListener(ouvinteMotoboy);
		viewMotoboy.btnSair.addActionListener(ouvinteMotoboy);
		OuvinteTable ouvinteTable = new OuvinteTable(viewMotoboy);
		viewMotoboy.table.addMouseListener(ouvinteTable);
	}

	public class OuvinteMotoboy implements ActionListener {

		private ViewMotoboy viewMotoboy;

		public OuvinteMotoboy(ViewMotoboy viewMotoboy) {
			this.viewMotoboy = viewMotoboy;
		}

		public void actionPerformed(ActionEvent e) {

			String button = e.getActionCommand();

			switch (button) {

			case "Sair":
				viewMotoboy.dispose();
				new ViewInicio();
				break;	

			case "Fazer Entrega":
				try {
					ClienteDTO clienteDTO = new ClienteDTO();
					PizzaDTO pizzaDTO = new PizzaDTO();
					pizzaDTO.setCodigoID(Integer.parseInt(pizzaSelecionada()));
					clienteDTO = controllerMotoboy.buscarCliente(pizzaDTO);
					pizzaDTO.setCPFCliente(clienteDTO.getCPF());
					controllerMotoboy.fazerEntrega(pizzaDTO);
					JOptionPane.showMessageDialog(viewMotoboy, "Pizza(s) Entregue Com Sucesso" , "Entrega Concluida", JOptionPane.PLAIN_MESSAGE);
					viewMotoboy.dispose();
					new ViewMotoboy(id);
				} catch (PizzaNaoEncontradaException | ClienteNaoExisteException | PedidoNaoEncontradoException | PedidoEmProducaoException e1) {
					JOptionPane.showMessageDialog(viewMotoboy, e1.getMessage(), "Aguarde", JOptionPane.PLAIN_MESSAGE);
				}
				break;
			}
		}
	}
	
	public class OuvinteTable implements MouseListener {

			private ViewMotoboy viewMotoboy;

			public OuvinteTable(ViewMotoboy viewMotoboy) {
				this.viewMotoboy = viewMotoboy;
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {

					ClienteDTO clienteDTO = new ClienteDTO();
					PizzaDTO pizzaDTO = new PizzaDTO();
					pizzaDTO.setCodigoID(Integer.parseInt(pizzaSelecionada()));
					try {
						clienteDTO = controllerMotoboy.buscarCliente(pizzaDTO);
						viewMotoboy.textFieldNomeCliente.setText(clienteDTO.getNome());
						viewMotoboy.textFieldEnderecoCliente.setText(clienteDTO.getEndereco());
						viewMotoboy.textFieldTelefoneCliente.setText(clienteDTO.getTelefone());
						viewMotoboy.repaint();
					} catch (ClienteNaoExisteException | PizzaNaoEncontradaException e1) {}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}


		}

}
