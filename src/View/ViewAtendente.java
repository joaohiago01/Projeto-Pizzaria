package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import Controller.ControllerAtendente;
import DTO.ClienteDTO;
import DTO.FuncionarioDTO;
import DTO.PedidoDTO;
import DTO.PizzaDTO;
import Model.ArrayListIteratorPizzaria;
import Model.ContextStrategy;
import Model.IteratorPizzaria;
import Model.VetorIteratorPizzaria;

public class ViewAtendente extends ViewPadrao {

	private JComboBox comboBoxClientes;
	private JButton btnSelecionar, btnCadastrarCliente, btnSair, btnBuscar; 
	private JTextField textFieldBuscar, textFieldNomeCliente, textFieldCPFCliente, textFieldEnderecoCliente, textFieldTelefoneCliente;
	private JLabel labelNomeAtendente;
	private String idAtendente;
	private ClienteDTO clienteDTO = new ClienteDTO();
	private FuncionarioDTO atendenteDTO = new FuncionarioDTO();
	private ControllerAtendente controllerAtendente = new ControllerAtendente();
	private JTable tablePedidos;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel modelPizzas;
	private String regiao;

	public ViewAtendente(String ID, String regiao) {
		super();
		this.setName("Atendente");

		this.idAtendente = ID;
		this.regiao = regiao;
		JLabel lblClientes = new JLabel("Clientes:");
		lblClientes.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblClientes.setBounds(10, 61, 70, 18);
		getContentPane().add(lblClientes);

		clienteDTO = controllerAtendente.fillCliente();

		comboBoxClientes = new JComboBox(clienteDTO.getTableCliente());
		comboBoxClientes.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		comboBoxClientes.setBounds(77, 60, 612, 25);
		getContentPane().add(comboBoxClientes);

		btnSelecionar = new JButton("Fazer Pedido");
		btnSelecionar.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnSelecionar.setBounds(777, 575, 140, 25);
		getContentPane().add(btnSelecionar);

		btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnSair.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnSair.setBounds(777, 610, 140, 25);
		getContentPane().add(btnSair);

		btnCadastrarCliente = new JButton("Cadastrar");
		btnCadastrarCliente.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnCadastrarCliente.setBounds(872, 237, 105, 25);
		getContentPane().add(btnCadastrarCliente);

		MaskFormatter maskCPF;
		try {
			maskCPF = new MaskFormatter("###.###.###-##");
			textFieldBuscar = new JFormattedTextField(maskCPF);
			textFieldBuscar.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			textFieldBuscar.setBounds(760, 281, 211, 25);
			getContentPane().add(textFieldBuscar);
			textFieldBuscar.setColumns(10);
		} catch (ParseException e1) {}

		JLabel lblNomeCliente = new JLabel("Nome:");
		lblNomeCliente.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNomeCliente.setBounds(717, 73, 46, 14);
		getContentPane().add(lblNomeCliente);

		textFieldNomeCliente = new JTextField();
		textFieldNomeCliente.setFont(new Font("Times New Roman", Font.BOLD, 14));
		textFieldNomeCliente.setBackground(Color.LIGHT_GRAY);
		textFieldNomeCliente.setBounds(760, 70, 211, 25);
		textFieldNomeCliente.setEditable(false);
		getContentPane().add(textFieldNomeCliente);
		textFieldNomeCliente.setColumns(10);

		JLabel lblCpfCliente = new JLabel("CPF:");
		lblCpfCliente.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblCpfCliente.setBounds(717, 115, 46, 14);
		getContentPane().add(lblCpfCliente);

		textFieldCPFCliente = new JTextField();
		textFieldCPFCliente.setFont(new Font("Times New Roman", Font.BOLD, 14));
		textFieldCPFCliente.setBackground(Color.LIGHT_GRAY);
		textFieldCPFCliente.setBounds(760, 114, 211, 25);
		textFieldCPFCliente.setEditable(false);
		getContentPane().add(textFieldCPFCliente);
		textFieldCPFCliente.setColumns(10);

		JLabel lblEndereoCliente = new JLabel("Endere\u00E7o:");
		lblEndereoCliente.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblEndereoCliente.setBounds(706, 155, 70, 14);
		getContentPane().add(lblEndereoCliente);

		textFieldEnderecoCliente = new JTextField();
		textFieldEnderecoCliente.setFont(new Font("Times New Roman", Font.BOLD, 14));
		textFieldEnderecoCliente.setBackground(Color.LIGHT_GRAY);
		textFieldEnderecoCliente.setBounds(760, 152, 211, 25);
		textFieldEnderecoCliente.setEditable(false);
		getContentPane().add(textFieldEnderecoCliente);
		textFieldEnderecoCliente.setColumns(10);

		JLabel lblTelefoneCliente = new JLabel("Telefone:");
		lblTelefoneCliente.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblTelefoneCliente.setBounds(706, 194, 54, 14);
		getContentPane().add(lblTelefoneCliente);

		textFieldTelefoneCliente = new JTextField();
		textFieldTelefoneCliente.setForeground(Color.BLACK);
		textFieldTelefoneCliente.setFont(new Font("Times New Roman", Font.BOLD, 14));
		textFieldTelefoneCliente.setBackground(Color.LIGHT_GRAY);
		textFieldTelefoneCliente.setBounds(760, 191, 211, 25);
		textFieldTelefoneCliente.setEditable(false);
		getContentPane().add(textFieldTelefoneCliente);
		textFieldTelefoneCliente.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnBuscar.setBounds(760, 236, 105, 25);
		getContentPane().add(btnBuscar);

		try {
			atendenteDTO.setCodigoID((ID));
			atendenteDTO = controllerAtendente.buscarAtendente(atendenteDTO);
			labelNomeAtendente = new JLabel("Atendente: " + atendenteDTO.getNome() + " | ID: " + atendenteDTO.getCodigoID());
		} catch (FuncionarioNaoExisteException e) {}
		labelNomeAtendente.setFont(new Font("Times New Roman", Font.BOLD, 16));
		labelNomeAtendente.setBounds(10, 11, 420, 35);
		getContentPane().add(labelNomeAtendente);

		JLabel lblDadosDoCliente = new JLabel("Dados Do Cliente");
		lblDadosDoCliente.setFont(new Font("Courier New", Font.BOLD, 16));
		lblDadosDoCliente.setBounds(777, 18, 175, 23);
		getContentPane().add(lblDadosDoCliente);

		PedidoDTO pedidoDTO = new PedidoDTO();
		pedidoDTO = controllerAtendente.tablePedidos();

		DefaultTableModel model = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int columIndex) {
				return false;
			} 
		};

		model.addColumn("Cliente");
		model.addColumn("CPF");
		model.addColumn("Qntd. Pizzas");
		model.addColumn("Total");
		model.addColumn("Status");
		model.addColumn("ID");

		if (pedidoDTO == null) {
			tablePedidos = new JTable(model);
		} else {
			
			//Iterator
			//Iterator<String> tableP = Arrays.asList(pedidoDTO.getTablePedido()).iterator();
			//VetorIteratorPizzaria tableP = new VetorIteratorPizzaria(pedidoDTO.getTablePedido());
			
			//Strategy
			ContextStrategy strategy = new ContextStrategy(pedidoDTO.getTablePedido());
			IteratorPizzaria tableP = strategy.kindIterator();
			
			while (tableP.hasNext()) {
				String[] pedido = tableP.next().toString().split("/");
				String[] IDsPizzas = pedido[1].split("@");
				ClienteDTO clienteDTO = new ClienteDTO();
				clienteDTO.setCPF(pedido[0]);
				PedidoDTO DTOpedido = new PedidoDTO();
				DTOpedido.setCPFDoCliente(pedido[0]);
				DTOpedido.setIDPedido(Integer.parseInt(pedido[2]));
				try {
					DTOpedido = controllerAtendente.buscarPedido(DTOpedido);
					clienteDTO = controllerAtendente.buscarCliente(clienteDTO);
				} catch (ClienteNaoExisteException | PedidoNaoEncontradoException e) {}
				Object[] row = new Object[] {
						clienteDTO.getNome(),
						clienteDTO.getCPF(),
						IDsPizzas.length,
						DTOpedido.getPrecoTotal(),
						DTOpedido.getStatus(),
						pedido[2]
				};
				model.addRow(row);
			}
			tablePedidos = new JTable(model);
			tablePedidos.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			getContentPane().add(tablePedidos);
		}

		JScrollPane pedidosTable = new JScrollPane(tablePedidos);
		pedidosTable.setBounds(0, 114, 691, 557);
		getContentPane().add(pedidosTable);

		JLabel lblDadosDoPedido = new JLabel("Dados Do Pedido");
		lblDadosDoPedido.setFont(new Font("Courier New", Font.BOLD, 16));
		lblDadosDoPedido.setBounds(777, 327, 158, 22);
		getContentPane().add(lblDadosDoPedido);

		JLabel lblPedidos = new JLabel("Pedidos");
		lblPedidos.setFont(new Font("Courier New", Font.BOLD, 17));
		lblPedidos.setBounds(306, 87, 124, 28);
		getContentPane().add(lblPedidos);
		
		table = new JTable();
		getContentPane().add(table);

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(706, 368, 273, 185);
		getContentPane().add(scrollPane);
		
		addListener(this);

		setVisible(true);
	}	

	public String IDAtendente() {
		return idAtendente;
	}

	public void addListener(ViewAtendente viewAtendente) {
		OuvinteAtendente ouvinteAtendente = new OuvinteAtendente(viewAtendente);
		viewAtendente.btnSelecionar.addActionListener(ouvinteAtendente);
		viewAtendente.btnCadastrarCliente.addActionListener(ouvinteAtendente);
		viewAtendente.btnBuscar.addActionListener(ouvinteAtendente);
		viewAtendente.btnSair.addActionListener(ouvinteAtendente);
		viewAtendente.comboBoxClientes.addActionListener(ouvinteAtendente);
		OuvinteTable ouvinteTable = new OuvinteTable(viewAtendente);
		viewAtendente.tablePedidos.addMouseListener(ouvinteTable);
		viewAtendente.table.addMouseListener(ouvinteTable);
	}

	public String clienteSelecionado() {
		return comboBoxClientes.getSelectedItem().toString();
	}

	public String CPFDoClienteSelecionado() throws ArrayIndexOutOfBoundsException {
		String[] cpf = clienteSelecionado().split(" / ");
		return cpf[1];
	}

	public String buscar() {
		return textFieldBuscar.getText();
	}

	public String nomeCliente() {
		return textFieldNomeCliente.getText();
	}

	public String CPFCliente() {
		return textFieldCPFCliente.getText();
	}

	public String enderecoCliente() {
		return textFieldEnderecoCliente.getText();
	}

	public String telefoneCliente() {
		return textFieldTelefoneCliente.getText();
	}

	public String pedidoSelecionado() {
		return tablePedidos.getValueAt(tablePedidos.getSelectedRow(), 1).toString();
	}

	public String IDPedido () {
		return tablePedidos.getValueAt(tablePedidos.getSelectedRow(), 5).toString();
	}
	
	public class OuvinteAtendente implements ActionListener {

		private ViewAtendente viewAtendente;

		public OuvinteAtendente (ViewAtendente viewAtendente) {
			this.viewAtendente = viewAtendente;
		}

		public void actionPerformed(ActionEvent e) {

			String button = e.getActionCommand();

			switch (button) {
			case "Fazer Pedido":
				new ViewPedido(viewAtendente.CPFDoClienteSelecionado(), viewAtendente.IDAtendente(), viewAtendente, viewAtendente.regiao);
				break;

			case "Cadastrar":
				new ViewCadastroCliente(viewAtendente.IDAtendente(), viewAtendente, viewAtendente.regiao);
				break;

			case "Buscar":
				try {
					clienteDTO.setCPF(buscar());
					clienteDTO = controllerAtendente.buscarCliente(clienteDTO);
					viewAtendente.comboBoxClientes.setSelectedItem(clienteDTO.getNome() +" / "+ clienteDTO.getCPF());
					textFieldCPFCliente.setText(clienteDTO.getCPF());
					textFieldNomeCliente.setText(clienteDTO.getNome());
					textFieldEnderecoCliente.setText(clienteDTO.getEndereco());
					textFieldTelefoneCliente.setText(clienteDTO.getTelefone());

					viewAtendente.repaint();
				} catch (ClienteNaoExisteException e1) {
					JOptionPane.showMessageDialog(viewAtendente, e1.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
				}		
				break;

			case "Sair":
				viewAtendente.dispose();
				new ViewInicio();
				break;
			}

			try {
				clienteDTO.setCPF(CPFDoClienteSelecionado());
				clienteDTO = controllerAtendente.buscarCliente(clienteDTO);
				viewAtendente.comboBoxClientes.setSelectedItem(clienteDTO.getNome() +" / "+ clienteDTO.getCPF());
				textFieldCPFCliente.setText(clienteDTO.getCPF());
				textFieldNomeCliente.setText(clienteDTO.getNome());
				textFieldEnderecoCliente.setText(clienteDTO.getEndereco());
				textFieldTelefoneCliente.setText(clienteDTO.getTelefone());

				viewAtendente.repaint();

			} catch (ClienteNaoExisteException | ArrayIndexOutOfBoundsException e2) {}
		}
	}

	public class OuvinteTable implements MouseListener {

		private ViewAtendente viewAtendente;

		public OuvinteTable(ViewAtendente viewAtendente) {
			this.viewAtendente = viewAtendente;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			if (e.getClickCount() == 2) {
				PedidoDTO pedidoDTO = new PedidoDTO();
				pedidoDTO.setCPFDoCliente((pedidoSelecionado()));
				pedidoDTO.setIDPedido(Integer.parseInt(IDPedido()));
				PizzaDTO pizzaDTO = new PizzaDTO();

				modelPizzas = new DefaultTableModel() {
					public boolean isCellEditable(int rowIndex, int columIndex) {
						return false;
					} 
				};

				modelPizzas.addColumn("Pizza(s)");

				try {
					pizzaDTO = controllerAtendente.buscarPizza(pedidoDTO);
					
					//Iterator
					//Iterator<PizzaDTO> pizzas = pizzaDTO.getPizzas().iterator();
					//ArrayListIteratorPizzaria<PizzaDTO> pizzas = new ArrayListIteratorPizzaria<PizzaDTO>(pizzaDTO.getPizzas());
					
					//Strategy
					ContextStrategy strategy = new ContextStrategy(pizzaDTO.getPizzas());
					IteratorPizzaria pizzas = strategy.kindIterator();
					
					while (pizzas.hasNext()) {
						PizzaDTO pizza = (PizzaDTO) pizzas.next();
						Object[] row = new Object[] {
								pizza.getSabor(),
						};
						
						viewAtendente.modelPizzas.addRow(row);
					}

					table.setVisible(false);
					table = new JTable(modelPizzas);
					viewAtendente.getContentPane().add(table);

					scrollPane.setVisible(false);
					scrollPane = new JScrollPane(table);
					scrollPane.setBounds(706, 368, 273, 185);
					viewAtendente.getContentPane().add(scrollPane);

					viewAtendente.repaint();
				} catch (PizzaNaoEncontradaException | PedidoNaoEncontradoException e1) {}
				
				ClienteDTO DTOcliente = new ClienteDTO();
				DTOcliente.setCPF(pedidoSelecionado());
				try {
					DTOcliente = controllerAtendente.buscarCliente(DTOcliente);
					viewAtendente.comboBoxClientes.setSelectedItem(DTOcliente.getNome() +" / "+ DTOcliente.getCPF());
					textFieldCPFCliente.setText(DTOcliente.getCPF());
					textFieldNomeCliente.setText(DTOcliente.getNome());
					textFieldEnderecoCliente.setText(DTOcliente.getEndereco());
					textFieldTelefoneCliente.setText(DTOcliente.getTelefone());

					viewAtendente.repaint();

				} catch (ClienteNaoExisteException e2) {}
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
