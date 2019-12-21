package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controller.ControllerFuncionario;
import DTO.FuncionarioDTO;
import javax.swing.JComboBox;

public class ViewInicio extends ViewPadrao {

	private JTextField textFieldID;
	private JPasswordField passwordFieldSenha;
	private JButton btnEntrar, btnSair, btnAjuda;
	private JComboBox comboBoxRegiao;

	public String id() {
		return textFieldID.getText();
	}

	public String senha() {
		return passwordFieldSenha.getText();
	}

	public ViewInicio() {
		super();
		this.setSize(600, 500);
		this.setLocationRelativeTo(null);
		this.setName("Pizzaria Italian");

		ImageIcon logoPizzaria = new ImageIcon("images/3bba8cf18c7b31717c98231e7d6e736e-pizza-logo-by-vexels (1).png");
		this.setIconImage(logoPizzaria.getImage().getScaledInstance(1, 1, 0));
		JLabel logo = new JLabel(logoPizzaria);
		logo.setBounds(175, 29, 250, 200);
		getContentPane().add(logo);

		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblId.setBounds(155, 240, 45, 20);
		getContentPane().add(lblId);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblSenha.setBounds(154, 290, 46, 14);
		getContentPane().add(lblSenha);

		textFieldID = new JTextField();
		textFieldID.setBounds(205, 242, 200, 28);
		getContentPane().add(textFieldID);
		textFieldID.setColumns(10);

		passwordFieldSenha = new JPasswordField();
		passwordFieldSenha.setBounds(205, 289, 200, 28);
		getContentPane().add(passwordFieldSenha);

		btnEntrar = new JButton("Entrar");
		btnEntrar.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnEntrar.setBounds(185, 341, 110, 28);
		getContentPane().add(btnEntrar);

		btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnSair.setBounds(325, 341, 110, 28);
		getContentPane().add(btnSair);
		
		btnAjuda = new JButton("Precisa De Ajuda?");
		btnAjuda.setForeground(Color.BLACK);
		btnAjuda.setBackground(Color.LIGHT_GRAY);
		btnAjuda.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnAjuda.setBounds(248, 375, 137, 28);
		getContentPane().add(btnAjuda);
		
		comboBoxRegiao = new JComboBox();
		comboBoxRegiao.addItem("PB");
		comboBoxRegiao.addItem("PE");
		comboBoxRegiao.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		comboBoxRegiao.setBounds(470, 6, 125, 25);
		getContentPane().add(comboBoxRegiao);
		
		JLabel lblRegio = new JLabel("Regi\u00E3o:");
		lblRegio.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblRegio.setBounds(424, 11, 58, 15);
		getContentPane().add(lblRegio);

		this.addListener();

		setVisible(true);
	}

	public void addListener() {
		OuvinteInicio ouvinteInicio = new OuvinteInicio(this);
		btnEntrar.addActionListener(ouvinteInicio);
		btnSair.addActionListener(ouvinteInicio);
		btnAjuda.addActionListener(ouvinteInicio);
	}

	public class OuvinteInicio implements ActionListener {

		private ViewInicio viewInicio;

		public OuvinteInicio(ViewInicio viewInicio) {
			this.viewInicio = viewInicio;
		}

		public void actionPerformed(ActionEvent e) {

			String button = e.getActionCommand();

			ControllerFuncionario controllerFuncionario = new ControllerFuncionario();
			FuncionarioDTO funcionarioDTO = new FuncionarioDTO();

			switch (button) {
			case "Precisa De Ajuda?":
				String id = JOptionPane.showInputDialog(viewInicio, "Digite Seu ID:", "Esqueceu Sua Senha?", JOptionPane.PLAIN_MESSAGE);
				funcionarioDTO.setCodigoID((id));
				try {
					String senhaRecuperada = controllerFuncionario.recuperarSenha(funcionarioDTO).getSenha();
					JOptionPane.showMessageDialog(viewInicio, "Sua Senha é: " + senhaRecuperada);
				} catch (FuncionarioNaoExisteException e2) {
					JOptionPane.showMessageDialog(viewInicio, e2.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
				}
				break;
				
			case "Entrar":
				funcionarioDTO.setCodigoID(id());
				funcionarioDTO.setSenha(senha());
				try {
					String login = controllerFuncionario.entrar(funcionarioDTO).getCargo();
					if (login.equals("admin") || login.equals("Gerente")) {
						viewInicio.dispose();
						new ViewGerente(viewInicio.id(), viewInicio.comboBoxRegiao.getSelectedItem().toString());
					} else {
						viewInicio.dispose();
						switch (login) {
						case "Atendente":
							new ViewAtendente(viewInicio.id(), viewInicio.comboBoxRegiao.getSelectedItem().toString());
							break;
						case "Pizzaiolo":
							new ViewPizzaiolo(viewInicio.id());
							break;
						case "Motoboy":
							new ViewMotoboy(viewInicio.id());
							break;
						}
					}

				} catch (FuncionarioNaoExisteException e1) {
					JOptionPane.showMessageDialog(viewInicio, e1.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
				}
				
				break;

			case "Sair":
				viewInicio.dispose();
				controllerFuncionario.closeConnection();
				break;
			}
		}
	}

	public static void main(String[] args) {
		new ViewInicio();
	}
}
