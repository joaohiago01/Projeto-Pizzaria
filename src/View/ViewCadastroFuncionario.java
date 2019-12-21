package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import Controller.ControllerFuncionario;
import DTO.FuncionarioDTO;

public class ViewCadastroFuncionario extends ViewPadrao{

	private JTextField textFieldNome;
	private JPasswordField passwordFieldSenha;
	private JComboBox<String> comboBoxCargo = new JComboBox<String>();
	private JButton btnCadastrar, btnVoltar, btnDemitir;
	private JTextField textFieldTelefone;
	/*private String IDGerente;
	private ViewGerente viewGerente;*/
	private ControllerFuncionario controllerFuncionario = new ControllerFuncionario();
	private FuncionarioDTO funcionarioDTO = new FuncionarioDTO();

	public ViewCadastroFuncionario(String ID, ViewGerente viewGerente) {
		super();
		this.setName("Funcionários");
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		/*this.IDGerente = ID;
		this.viewGerente = viewGerente;*/

		JLabel lblCadastroFuncionrios = new JLabel("Funcion\u00E1rios");
		lblCadastroFuncionrios.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		lblCadastroFuncionrios.setBounds(213, 11, 150, 14);
		getContentPane().add(lblCadastroFuncionrios);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNome.setBounds(87, 58, 46, 14);
		getContentPane().add(lblNome);
		
		JLabel lblCargo = new JLabel("Cargo:");
		lblCargo.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblCargo.setBounds(87, 93, 46, 20);
		getContentPane().add(lblCargo);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(143, 56, 220, 25);
		getContentPane().add(textFieldNome);
		textFieldNome.setColumns(10);
		comboBoxCargo.setFont(new Font("Times New Roman", Font.BOLD, 14));
		
		comboBoxCargo.setBounds(143, 91, 220, 25);
		comboBoxCargo.addItem("Gerente");
		comboBoxCargo.addItem("Atendente");
		comboBoxCargo.addItem("Pizzaiolo");
		comboBoxCargo.addItem("Motoboy");
		getContentPane().add(comboBoxCargo);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblSenha.setBounds(87, 131, 46, 14);
		getContentPane().add(lblSenha);
		
		passwordFieldSenha = new JPasswordField();
		passwordFieldSenha.setBounds(143, 129, 220, 25);
		getContentPane().add(passwordFieldSenha);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnCadastrar.setBounds(265, 203, 121, 25);
		getContentPane().add(btnCadastrar);
		
		btnVoltar = new JButton("Voltar");
		btnVoltar.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnVoltar.setBounds(198, 237, 128, 25);
		getContentPane().add(btnVoltar);
		
		btnDemitir = new JButton("Demitir");
		btnDemitir.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnDemitir.setBounds(124, 203, 121, 25);
		getContentPane().add(btnDemitir);
		
		JLabel lblFone = new JLabel("Fone:");
		lblFone.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblFone.setBounds(87, 165, 46, 14);
		getContentPane().add(lblFone);
		
		MaskFormatter maskTel;
		try {
			maskTel = new MaskFormatter("#####.####");
			textFieldTelefone = new JFormattedTextField(maskTel);
			textFieldTelefone.setBounds(143, 160, 220, 25);
			getContentPane().add(textFieldTelefone);
			textFieldTelefone.setColumns(10);
		} catch (ParseException e) {}
		
		addListener(this);
		
		setVisible(true);
	}
	
	public String nome() {
		return textFieldNome.getText();
	}
	
	public String senha() {
		return passwordFieldSenha.getText();
	}
	
	public String cargo() {
		return comboBoxCargo.getSelectedItem().toString();
	}
	
	public String telefone() {
		return textFieldTelefone.getText();
	}
	
	public void addListener(ViewCadastroFuncionario viewCadastroFuncionario) {
		OuvinteCadastroFuncionario ouvinteCadastroFuncionario = new OuvinteCadastroFuncionario(viewCadastroFuncionario);
		viewCadastroFuncionario.btnCadastrar.addActionListener(ouvinteCadastroFuncionario);
		viewCadastroFuncionario.btnDemitir.addActionListener(ouvinteCadastroFuncionario);
		viewCadastroFuncionario.btnVoltar.addActionListener(ouvinteCadastroFuncionario);
	}
	
	public class OuvinteCadastroFuncionario implements ActionListener {
		
		private ViewCadastroFuncionario viewCadastroFuncionario;

		public OuvinteCadastroFuncionario(ViewCadastroFuncionario viewCadastroFuncionario) {
			this.viewCadastroFuncionario = viewCadastroFuncionario;
		}

		public void actionPerformed(ActionEvent arg0) {

			String button = arg0.getActionCommand();

			switch (button) {
			case "Cadastrar":
				funcionarioDTO.setNome(nome());
				funcionarioDTO.setSenha(senha());
				funcionarioDTO.setCargo(cargo());
				funcionarioDTO.setTelefone(telefone());
				controllerFuncionario.cadastrarFuncionario(funcionarioDTO);
				JOptionPane.showMessageDialog(viewCadastroFuncionario, "Funcionário Cadastrado Com Sucesso!\nFuncionário: " + nome() + "\nID: " + controllerFuncionario.IDFuncionario() , "Sucesso", JOptionPane.PLAIN_MESSAGE);
				int opc = JOptionPane.showConfirmDialog(viewCadastroFuncionario, "Deseja Continuar Cadastrando Funcionários?");
				if (opc == JOptionPane.YES_OPTION) {
					viewCadastroFuncionario.textFieldNome.setText("");
					viewCadastroFuncionario.passwordFieldSenha.setText("");
					viewCadastroFuncionario.textFieldTelefone.setText("");
				} else {
					viewCadastroFuncionario.dispose();
				}

				break;
			case "Demitir":
				String ID = JOptionPane.showInputDialog("ID Do Funcionpario: ");
				funcionarioDTO.setCodigoID((ID));
				try {
					controllerFuncionario.demitirFuncionario(funcionarioDTO);
					JOptionPane.showMessageDialog(viewCadastroFuncionario, "Funcionário Foi Demitido.", "Concluido", JOptionPane.PLAIN_MESSAGE);
				} catch (FuncionarioNaoExisteException e) {
					JOptionPane.showMessageDialog(viewCadastroFuncionario, e.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
				}
				break;
			case "Voltar":
				viewCadastroFuncionario.dispose();
				break;
			}

		}
	}

}
