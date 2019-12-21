package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controller.ControllerPizzaiolo;
import DTO.ClienteDTO;
import DTO.FuncionarioDTO;
import DTO.PedidoDTO;
import DTO.PizzaDTO;
import Model.ContextStrategy;
import Model.IteratorPizzaria;

public class ViewPizzaiolo extends ViewPadrao {
	
	private JButton btnFazerPizza, btnSair, btnDetalhesDoPedido;
	private PedidoDTO pedidoDTO = new PedidoDTO();
	private String ID;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private DefaultTableModel modelPizzas;
	private JScrollPane scrollPane;
	private ControllerPizzaiolo controllerPizzaiolo = new ControllerPizzaiolo();
	private FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
	
	public ViewPizzaiolo(String ID) {
		super();
		getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 12));
		this.setSize(1200, 700);
		getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);
		this.ID = ID;

		JLabel lblPedidosDePizza = new JLabel("Pedidos");
		lblPedidosDePizza.setFont(new Font("Courier New", Font.BOLD, 17));
		lblPedidosDePizza.setBounds(190, 56, 101, 14);
		getContentPane().add(lblPedidosDePizza);

		pedidoDTO = controllerPizzaiolo.fillPedidos();

		btnFazerPizza = new JButton("Finalizar");
		btnFazerPizza.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnFazerPizza.setBounds(1023, 381, 120, 25);
		getContentPane().add(btnFazerPizza);

		btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnSair.setBounds(947, 637, 143, 23);
		getContentPane().add(btnSair);

		JLabel labelNomePizzaiolo;
		try {
			funcionarioDTO.setCodigoID((ID));
			funcionarioDTO = controllerPizzaiolo.buscarPizzaiolo(funcionarioDTO);
			labelNomePizzaiolo = new JLabel("Pizzaiolo: " + funcionarioDTO.getNome() + " | ID: " + funcionarioDTO.getCodigoID());
			labelNomePizzaiolo.setFont(new Font("Times New Roman", Font.BOLD, 16));
			labelNomePizzaiolo.setBounds(10, 11, 420, 35);
			getContentPane().add(labelNomePizzaiolo);
		} catch (FuncionarioNaoExisteException e) {}

		JLabel lblDadosPedido = new JLabel("Dados Do Pedido");
		lblDadosPedido.setFont(new Font("Courier New", Font.BOLD, 17));
		lblDadosPedido.setBounds(940, 56, 150, 14);
		getContentPane().add(lblDadosPedido);

		JLabel lblPedidosFinalizados = new JLabel("Pedidos Finalizados");
		lblPedidosFinalizados.setFont(new Font("Courier New", Font.BOLD, 17));
		lblPedidosFinalizados.setBounds(545, 56, 190, 14);
		getContentPane().add(lblPedidosFinalizados);

		btnDetalhesDoPedido = new JButton("Ver Detalhes");
		btnDetalhesDoPedido.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnDetalhesDoPedido.setBounds(889, 381, 120, 25);
		getContentPane().add(btnDetalhesDoPedido);

		table_2 = new JTable(modelPizzas);
		table_2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		getContentPane().add(table_2);

		scrollPane = new JScrollPane(table_2);
		scrollPane.setBounds(840, 99, 350, 253);
		getContentPane().add(scrollPane);

		this.pedidoDTO = controllerPizzaiolo.fillPedidos();

		DefaultTableModel modelPedidos = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int columIndex) {
				return false;
			} 
		};

		modelPedidos.addColumn("Cliente");
		modelPedidos.addColumn("CPF");
		modelPedidos.addColumn("Qntd. Pizzas");
		modelPedidos.addColumn("Status");
		modelPedidos.addColumn("ID");

		if (pedidoDTO == null) {
			table = new JTable(modelPedidos);
		} else {
			
			//Iterator
			//Iterator<String> pedidosEmProducao = Arrays.asList(pedidoDTO.getTablePedido()).iterator();
			//VetorIteratorPizzaria pedidosEmProducao = new VetorIteratorPizzaria(pedidoDTO.getTablePedido());
			
			//Strategy
			ContextStrategy strategy = new ContextStrategy(pedidoDTO.getTablePedido());
			IteratorPizzaria pedidosEmProducao = strategy.kindIterator();
			
			while (pedidosEmProducao.hasNext()) {
				String[] pedido = pedidosEmProducao.next().toString().split("/");
				String[] IDsPizzas = pedido[1].split("@");
				ClienteDTO clienteDTO = new ClienteDTO();
				clienteDTO.setCPF(pedido[0]);
				PedidoDTO DTOpedido = new PedidoDTO();
				DTOpedido.setCPFDoCliente(pedido[0]);
				DTOpedido.setIDPedido(Integer.parseInt(pedido[2]));
				try {
					DTOpedido = controllerPizzaiolo.buscarPedido(DTOpedido);
					clienteDTO = controllerPizzaiolo.buscarCliente(clienteDTO);
				} catch (ClienteNaoExisteException | PedidoNaoEncontradoException e) {}
				if (DTOpedido.getStatus().equals("Em Produção")) {
					Object[] row = new Object[] {
							clienteDTO.getNome(),
							clienteDTO.getCPF(),
							IDsPizzas.length,
							DTOpedido.getStatus(),
							pedido[2]
					};
					modelPedidos.addRow(row);
				}
			}

			table = new JTable(modelPedidos);
			getContentPane().add(table);

			JScrollPane scrollPanePedidos = new JScrollPane(table);
			scrollPanePedidos.setBounds(0, 101, 450, 570);
			getContentPane().add(scrollPanePedidos);

			DefaultTableModel modelPedidosFinalizados = new DefaultTableModel() {
				public boolean isCellEditable(int rowIndex, int columIndex) {
					return false;
				} 
			};

			modelPedidosFinalizados.addColumn("Cliente");
			modelPedidosFinalizados.addColumn("CPF");
			modelPedidosFinalizados.addColumn("Status");
			modelPedidosFinalizados.addColumn("ID");

			strategy = new ContextStrategy(pedidoDTO.getTablePedido());
			IteratorPizzaria pedidosFinalizados = strategy.kindIterator();
			while (pedidosFinalizados.hasNext()) {
				String[] pedido = pedidosFinalizados.next().toString().split("/");
				ClienteDTO clienteDTO = new ClienteDTO();
				clienteDTO.setCPF(pedido[0]);
				PedidoDTO DTOpedido = new PedidoDTO();
				DTOpedido.setCPFDoCliente(pedido[0]);
				DTOpedido.setIDPedido(Integer.parseInt(pedido[2]));
				try {
					DTOpedido = controllerPizzaiolo.buscarPedido(DTOpedido);
					clienteDTO = controllerPizzaiolo.buscarCliente(clienteDTO);
				} catch (ClienteNaoExisteException | PedidoNaoEncontradoException e) {}
				
				if (DTOpedido.getStatus().equals("Finalizado")) {
					Object[] row = new Object[] {
							clienteDTO.getNome(),
							clienteDTO.getCPF(),
							DTOpedido.getStatus(),
							pedido[2]
					};
					modelPedidosFinalizados.addRow(row);
				}
			}

			table_1 = new JTable(modelPedidosFinalizados);
			getContentPane().add(table_1);

			JScrollPane scrollPanePedidosFinalizados = new JScrollPane(table_1);
			scrollPanePedidosFinalizados.setBounds(455, 101, 380, 570);
			getContentPane().add(scrollPanePedidosFinalizados);

			addListener(this);

			setVisible(true);

		}
	}

	public void addListener(ViewPizzaiolo viewPizzaiolo) {
		OuvintePizzaiolo ouvintePizzaiolo = new OuvintePizzaiolo(viewPizzaiolo);
		viewPizzaiolo.btnFazerPizza.addActionListener(ouvintePizzaiolo);
		viewPizzaiolo.btnDetalhesDoPedido.addActionListener(ouvintePizzaiolo);
		viewPizzaiolo.btnSair.addActionListener(ouvintePizzaiolo);
		OuvinteTable ouvinteTable = new OuvinteTable(viewPizzaiolo);
		viewPizzaiolo.table.addMouseListener(ouvinteTable);
		viewPizzaiolo.table_2.addMouseListener(ouvinteTable);
	}

	public String pedidoSelecionado() {
		return table.getValueAt(table.getSelectedRow(), 1).toString();
	}

	public String IDPedido () {
		return table.getValueAt(table.getSelectedRow(), 4).toString();
	}
	
	public class OuvintePizzaiolo implements ActionListener {

		private ViewPizzaiolo viewPizzaiolo;

		public OuvintePizzaiolo(ViewPizzaiolo viewPizzaiolo) {
			this.viewPizzaiolo = viewPizzaiolo;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String button = e.getActionCommand();

			switch (button) {
			case "Sair":
				viewPizzaiolo.dispose();
				new ViewInicio();
				break;

			case "Finalizar":
				PedidoDTO pedidoDTO = new PedidoDTO();
				pedidoDTO.setCPFDoCliente(pedidoSelecionado());
				pedidoDTO.setIDPedido(Integer.parseInt(IDPedido()));
				try {
					controllerPizzaiolo.fazerPizza(pedidoDTO);
				} catch (ClienteNaoExisteException | PedidoNaoEncontradoException e1) {}
				JOptionPane.showMessageDialog(viewPizzaiolo, "Pedido Finalizado", "Sucesso", JOptionPane.PLAIN_MESSAGE);
				viewPizzaiolo.dispose();
				new ViewPizzaiolo(ID);
				break;

			case "Ver Detalhes":
				new ViewDetalhes();
				break;
			}

		}

	}

	public class OuvinteTable implements MouseListener {

		private ViewPizzaiolo viewPizzaiolo;

		public OuvinteTable(ViewPizzaiolo viewPizzaiolo) {
			this.viewPizzaiolo = viewPizzaiolo;
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

				modelPizzas.addColumn("Pizza");
				modelPizzas.addColumn("Tamanho");

				try {
					pizzaDTO = controllerPizzaiolo.buscarPizza(pedidoDTO);
					
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
								pizza.getTamanho()
						};
						viewPizzaiolo.modelPizzas.addRow(row);
					}

					table_2.setVisible(false);
					table_2 = new JTable(modelPizzas);
					viewPizzaiolo.getContentPane().add(table_2);

					scrollPane.setVisible(false);
					scrollPane = new JScrollPane(table_2);
					scrollPane.setBounds(840, 99, 350, 253);
					viewPizzaiolo.getContentPane().add(scrollPane);

					viewPizzaiolo.repaint();
				} catch (PizzaNaoEncontradaException | PedidoNaoEncontradoException e1) {}

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
