package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Controller.ControllerPizza;
import DTO.IngredienteDTO;
import DTO.SaborDTO;

public class ViewCadastroPizza extends ViewPadrao{

	private JTextField textFieldPizza;
	private JComboBox comboBoxPizzas, comboBoxIngredientes;
	private JButton btnAdicionarPizza, btnVoltar, btnAdicionarIngrediente, btnCadastrarIngrediente, btnIncluirNoCardpio;;
	private String IDGerente;
	private ViewGerente viewGerente;
	private JLabel lblIngredientes;
	private ControllerPizza controllerPizza = new ControllerPizza();
	private SaborDTO saborDTO = new SaborDTO();
	private IngredienteDTO ingredienteDTO = new IngredienteDTO();

	public ViewCadastroPizza(String ID, ViewGerente viewGerente) {
		super();
		this.setName("Cardápio");
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.IDGerente = ID;
		this.viewGerente = viewGerente;
		
		JLabel lblCardpio = new JLabel("Card\u00E1pio");
		lblCardpio.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		lblCardpio.setBounds(225, 11, 85, 22);
		getContentPane().add(lblCardpio);

		JLabel lblPizzasNoCardpio = new JLabel("Pizzas no Card\u00E1pio:");
		lblPizzasNoCardpio.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPizzasNoCardpio.setBounds(24, 70, 140, 14);
		getContentPane().add(lblPizzasNoCardpio);

		saborDTO = controllerPizza.fillSabores();
		comboBoxPizzas = new JComboBox(saborDTO.getSabores());
		comboBoxPizzas.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		comboBoxPizzas.setBounds(146, 67, 323, 25);
		getContentPane().add(comboBoxPizzas);

		btnAdicionarPizza = new JButton("Adicionar Pizza");
		btnAdicionarPizza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnAdicionarPizza.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnAdicionarPizza.setBounds(306, 110, 163, 25);
		getContentPane().add(btnAdicionarPizza);

		JLabel lblNovaPizza = new JLabel("Nova Pizza:");
		lblNovaPizza.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNovaPizza.setBounds(24, 114, 70, 14);
		getContentPane().add(lblNovaPizza);

		textFieldPizza = new JTextField();
		textFieldPizza.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		textFieldPizza.setBounds(104, 111, 192, 25);
		getContentPane().add(textFieldPizza);
		textFieldPizza.setColumns(10);

		btnVoltar = new JButton("Voltar");
		btnVoltar.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnVoltar.setBounds(206, 237, 134, 25);
		getContentPane().add(btnVoltar);

		lblIngredientes = new JLabel("Ingredientes:");
		lblIngredientes.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblIngredientes.setBounds(10, 159, 92, 20);
		getContentPane().add(lblIngredientes);

		ingredienteDTO = controllerPizza.fillIngredientes();
		comboBoxIngredientes = new JComboBox(ingredienteDTO.getTableIngrediente());
		comboBoxIngredientes.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		comboBoxIngredientes.setBounds(104, 156, 169, 25);
		getContentPane().add(comboBoxIngredientes);

		btnAdicionarIngrediente = new JButton("Adicionar Ingrediente");
		btnAdicionarIngrediente.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnAdicionarIngrediente.setBounds(277, 154, 192, 25);
		getContentPane().add(btnAdicionarIngrediente);

		btnCadastrarIngrediente = new JButton("Cadastrar Ingrediente");
		btnCadastrarIngrediente.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnCadastrarIngrediente.setBounds(277, 188, 192, 25);
		getContentPane().add(btnCadastrarIngrediente);
		
		btnIncluirNoCardpio = new JButton("Incluir No Card\u00E1pio");
		btnIncluirNoCardpio.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnIncluirNoCardpio.setBounds(104, 188, 169, 25);
		getContentPane().add(btnIncluirNoCardpio);

		addListener(this);
		
		btnAdicionarIngrediente.setEnabled(false);

		setVisible(true);
	}

	public void addListener(ViewCadastroPizza viewCadastroPizza) {
		OuvinteCadastroPizza ouvinteCadastroPizza = new OuvinteCadastroPizza(viewCadastroPizza);
		viewCadastroPizza.btnAdicionarPizza.addActionListener(ouvinteCadastroPizza);
		viewCadastroPizza.btnVoltar.addActionListener(ouvinteCadastroPizza);
		viewCadastroPizza.btnAdicionarIngrediente.addActionListener(ouvinteCadastroPizza);
		viewCadastroPizza.btnCadastrarIngrediente.addActionListener(ouvinteCadastroPizza);
		viewCadastroPizza.btnIncluirNoCardpio.addActionListener(ouvinteCadastroPizza);
	}

	public String saborPizza() {
		return textFieldPizza.getText();
	}

	public String ingredientePizza() {
		String nomeIngrediente[] = comboBoxIngredientes.getSelectedItem().toString().split("/");
		return nomeIngrediente[0];
	}
	
	public String precoIngredientePizza() {
		String precoIngrediente[] = comboBoxIngredientes.getSelectedItem().toString().split("/");
		return precoIngrediente[1];
	}
	
	private SaborPizza saborPizza = new IngredienteBase();
	
	public class OuvinteCadastroPizza implements ActionListener	{

		private ViewCadastroPizza viewCadastroPizza;
		
		public OuvinteCadastroPizza(ViewCadastroPizza viewCadastroPizza) {
			this.viewCadastroPizza = viewCadastroPizza;
		}

		public void actionPerformed(ActionEvent e) {

			String button = e.getActionCommand();

			ControllerPizza controllerPizza = new ControllerPizza();

			switch (button) {
			case "Voltar":
				viewCadastroPizza.dispose();
				break;

			case "Adicionar Pizza":
				viewCadastroPizza.comboBoxPizzas.setEnabled(false);
				viewCadastroPizza.textFieldPizza.setEditable(false);
				viewCadastroPizza.btnAdicionarPizza.setEnabled(false);
				viewCadastroPizza.comboBoxIngredientes.setEnabled(true);
				viewCadastroPizza.btnAdicionarIngrediente.setEnabled(true);
				break;
			case "Incluir No Cardápio":
				if (viewCadastroPizza.textFieldPizza.getText().equals("")) {
					JOptionPane.showMessageDialog(viewCadastroPizza, "Insira O Nome Da Pizza", "Atenção", JOptionPane.WARNING_MESSAGE);
				} else {
					try {		
						SaborDTO saborDTO = new SaborDTO();
						saborDTO.setDescricao(saborPizza());
						saborDTO.setIngredientes(saborPizza);
						controllerPizza.addPizza(saborDTO);
						JOptionPane.showMessageDialog(viewCadastroPizza, "Pizza Adicionada ao Cardápio", "Sucesso", JOptionPane.PLAIN_MESSAGE);
					} catch (SaborJaExistenteException e1) {
						JOptionPane.showMessageDialog(viewCadastroPizza, e1.getMessage());
					}
				}
				int opc = JOptionPane.showConfirmDialog(viewCadastroPizza, "Deseja Continuar Adicionando Pizzas ao Cardápio?");
				if (opc == JOptionPane.YES_OPTION) {
					viewCadastroPizza.textFieldPizza.setText("");
					viewCadastroPizza.dispose();
					new ViewCadastroPizza(IDGerente, viewGerente);
				} else {
					viewCadastroPizza.dispose();
				}
				break;
				
			case "Adicionar Ingrediente":
				saborPizza = new Ingrediente(saborPizza, ingredientePizza(), Double.parseDouble(precoIngredientePizza()));
				JOptionPane.showMessageDialog(viewCadastroPizza, "Ingrediente Adicionado á Pizza", "Ingredientes", JOptionPane.PLAIN_MESSAGE);
				break;

			case "Cadastrar Ingrediente":
				String nomeIngrediente = JOptionPane.showInputDialog("Nome Do Ingrediente: ");
				String precoIngrediente = JOptionPane.showInputDialog("Preço Do Ingrediente: ");
				double pIngrediente = Double.parseDouble(precoIngrediente);
				IngredienteDTO DTOingrediente = new IngredienteDTO();
				DTOingrediente.setNome(nomeIngrediente);
				DTOingrediente.setPreco(pIngrediente);
				try {
					controllerPizza.addIngrediente(DTOingrediente);
					viewCadastroPizza.dispose();
					new ViewCadastroPizza(IDGerente, viewGerente);
				} catch (IngredienteDuplicadoException e1) {
					JOptionPane.showMessageDialog(viewCadastroPizza, e1.getMessage());
				}
				break;

			}

		}
	}

}
