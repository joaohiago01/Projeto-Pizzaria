package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Controller.ControllerPedido;
import DTO.PedidoDTO;
import DTO.PizzaDTO;
import DTO.SaborDTO;

public class ViewPedido extends ViewPadrao{

	private JTextField textFieldCompleta, textFieldFatia;
	private JComboBox comboBoxSabores, comboBoxTamanho;
	private JButton btnFazerPedido, btnVoltar;
	private String CPFCliente;
	private String IDAtendente;
	private String regiao;
	private ViewAtendente viewAtendente;
	private ControllerPedido controllerPedido = new ControllerPedido();
	private SaborDTO saborDTO = new SaborDTO();
	private PedidoDTO pedidoDTO = new PedidoDTO();

	public ViewPedido(String CPFCliente, String IDAtendente, ViewAtendente viewAtendente, String regiao) {
		super();
		this.setName("Pedido");
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);

		this.viewAtendente = viewAtendente;
		this.CPFCliente = CPFCliente;
		this.IDAtendente = IDAtendente;
		this.regiao = regiao;

		JLabel lblTamanho = new JLabel("Tamanho:");
		lblTamanho.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblTamanho.setBounds(24, 50, 79, 14);
		getContentPane().add(lblTamanho);

		String []tamanho = {"Pequena (6 Fatias)", "Média (8 Fatias)", "Grande (10 Fatias)", "Familia (12 Fatias)", "Gigante (16 Fatias)"};
		comboBoxTamanho = new JComboBox(tamanho);
		comboBoxTamanho.setBounds(100, 47, 209, 25);
		getContentPane().add(comboBoxTamanho);

		JLabel lblPreoFatia = new JLabel("Pre\u00E7o Fatia:");
		lblPreoFatia.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPreoFatia.setBounds(24, 98, 79, 14);
		getContentPane().add(lblPreoFatia);

		textFieldFatia = new JTextField("3.00");
		textFieldFatia.setEditable(false);
		textFieldFatia.setBounds(100, 95, 86, 25);
		getContentPane().add(textFieldFatia);
		textFieldFatia.setColumns(10);

		JLabel lblPreoCompleta = new JLabel("Pre\u00E7o Completa:");
		lblPreoCompleta.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblPreoCompleta.setBounds(219, 98, 90, 14);
		getContentPane().add(lblPreoCompleta);

		textFieldCompleta = new JTextField();
		textFieldCompleta.setEditable(false);
		textFieldCompleta.setBounds(323, 95, 85, 25);
		getContentPane().add(textFieldCompleta);
		textFieldCompleta.setColumns(10);

		JLabel lblPedidos = new JLabel("Pedidos");
		lblPedidos.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		lblPedidos.setBounds(219, 11, 100, 14);
		getContentPane().add(lblPedidos);

		JLabel lblSabores = new JLabel("Sabores:");
		lblSabores.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblSabores.setBounds(24, 144, 64, 14);
		getContentPane().add(lblSabores);

		saborDTO = controllerPedido.fillSabores();
		comboBoxSabores = new JComboBox(saborDTO.getSabores());
		comboBoxSabores.setBounds(100, 141, 209, 25);
		getContentPane().add(comboBoxSabores);

		btnFazerPedido = new JButton("Fazer Pedido");
		btnFazerPedido.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnFazerPedido.setBounds(262, 194, 118, 25);
		getContentPane().add(btnFazerPedido);

		btnVoltar = new JButton("Voltar");
		btnVoltar.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnVoltar.setBounds(129, 194, 118, 25);
		getContentPane().add(btnVoltar);

		JLabel lblR = new JLabel("R$");
		lblR.setBounds(418, 98, 46, 14);
		getContentPane().add(lblR);

		JLabel lblR_1 = new JLabel("R$");
		lblR_1.setBounds(196, 98, 46, 14);
		getContentPane().add(lblR_1);

		addListener(this);

		setVisible(true);
	}

	public String tamanhoPizza() {
		return (String) comboBoxTamanho.getSelectedItem();
	}

	public String precoPizza() {
		return textFieldCompleta.getText();
	}

	public String saborPizza() {
		return comboBoxSabores.getSelectedItem().toString();
	}

	public String IDAtendente() {
		return IDAtendente;
	}

	public void addListener(ViewPedido viewPedido) {
		OuvintePedido ouvintePedido = new OuvintePedido(viewPedido);
		viewPedido.textFieldCompleta.addActionListener(ouvintePedido);
		viewPedido.btnFazerPedido.addActionListener(ouvintePedido);
		viewPedido.btnVoltar.addActionListener(ouvintePedido);
		viewPedido.comboBoxTamanho.addActionListener(ouvintePedido);
	}

	private ArrayList<PizzaDTO> pizzas = new ArrayList<PizzaDTO>();

	public class OuvintePedido implements ActionListener{

		private ViewPedido viewPedido;

		public OuvintePedido(ViewPedido viewPedido) {
			this.viewPedido = viewPedido;
		}

		public void actionPerformed(ActionEvent e) {

			String button = e.getActionCommand();

			double pCompleta = 0;

			switch (viewPedido.tamanhoPizza()) {
			case "Pequena (6 Fatias)":
				pCompleta = 18.00;
				break;
			case "Média (8 Fatias)":
				pCompleta = 24.00;
				break;
			case "Grande (10 Fatias)":
				pCompleta = 30.00;
				break;
			case "Familia (12 Fatias)":
				pCompleta = 36.00;
				break;
			case "Gigante (16 Fatias)":
				pCompleta = 48.00;
				break;
			}

			viewPedido.textFieldCompleta.setText(Double.toString(pCompleta));

			switch (button) {
			case "Voltar":
				viewPedido.dispose();
				break;

			case "Fazer Pedido":
				PizzaDTO pizzaDTO = new PizzaDTO();
				pizzaDTO.setCPFCliente(CPFCliente);
				pizzaDTO.setSabor(saborPizza());
				pizzaDTO.setTamanho(tamanhoPizza());
				pizzaDTO.setPrecoCompleta(Double.parseDouble(precoPizza()));
				controllerPedido.impostoDaRegiao(viewPedido.regiao);
				pizzas.add(pizzaDTO);
				JOptionPane.showMessageDialog(viewPedido, "Pedido Concluido!", "Sucesso", JOptionPane.PLAIN_MESSAGE);
				int opc = JOptionPane.showConfirmDialog(viewPedido, "Deseja Pedir Mais Pizzas Para Este Cliente? ");
				if (opc == JOptionPane.YES_OPTION) {
					viewPedido.textFieldCompleta.setText("");
				} else {
					try {
						pedidoDTO.setCPFDoCliente(CPFCliente);
						pedidoDTO.setPizzas(pizzas);
						controllerPedido.fazerPedido(pedidoDTO);
						viewPedido.dispose();
						viewAtendente.dispose();
						new ViewAtendente(viewPedido.IDAtendente(), viewPedido.regiao);
					} catch (SaborNaoEncontradoException | SaborJaExistenteException e1) {
						JOptionPane.showMessageDialog(viewPedido, e1.getMessage());
					}
				}
				break;
			}
		}
	}
}
