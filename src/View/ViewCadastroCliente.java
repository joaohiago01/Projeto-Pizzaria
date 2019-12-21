package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import Controller.ControllerCliente;
import DTO.ClienteDTO;

public class ViewCadastroCliente extends ViewPadrao{

	private JTextField txtNome, txtEndereco;
	private JFormattedTextField txtCPF, txtTelefone;
	private JButton btnVoltar, btnFazerPedido;
	private String IDAtendente;
	private String regiao;
	private ViewAtendente viewAtendente;
	private ControllerCliente controllerCliente = new ControllerCliente();
	private ClienteDTO clienteDTO = new ClienteDTO();

	public ViewCadastroCliente(String ID, ViewAtendente viewAtendente, String regiao) {
		super();
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		
		this.IDAtendente = ID;
		this.viewAtendente = viewAtendente;
		this.regiao = regiao;

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNome.setBounds(49, 49, 45, 15);
		getContentPane().add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(89, 49, 200, 25);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblCpf.setBounds(49, 89, 45, 15);
		getContentPane().add(lblCpf);

		try {
			MaskFormatter maskCPF = new MaskFormatter("###.###.###-##");
			txtCPF = new JFormattedTextField(maskCPF);
			txtCPF.setBounds(89, 89, 200, 25);
			getContentPane().add(txtCPF);
			txtCPF.setColumns(10);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblEndereo.setBounds(20, 129, 60, 15);
		getContentPane().add(lblEndereo);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(89, 126, 200, 25);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblTelefone.setBounds(20, 169, 60, 15);
		getContentPane().add(lblTelefone);

		try {
			MaskFormatter maskTel = new MaskFormatter("#####.####");
			txtTelefone = new JFormattedTextField(maskTel);
			txtTelefone.setBounds(89, 166, 200, 25);
			getContentPane().add(txtTelefone);
			txtTelefone.setColumns(10);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JLabel lblCadastro = new JLabel("Cadastro do Cliente");
		lblCadastro.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
		lblCadastro.setBounds(200, 11, 134, 15);
		getContentPane().add(lblCadastro);

		btnFazerPedido = new JButton("Cadastrar");
		btnFazerPedido.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnFazerPedido.setBounds(335, 83, 124, 25);
		getContentPane().add(btnFazerPedido);

		btnVoltar = new JButton("Voltar");
		btnVoltar.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnVoltar.setBounds(335, 123, 124, 25);
		getContentPane().add(btnVoltar);

		addListener(this);

		setVisible(true);
	}

	public void addListener(ViewCadastroCliente viewCadastroCliente) {
		OuvinteCadastroCliente ouvinteCadastroCliente = new OuvinteCadastroCliente(viewCadastroCliente);
		viewCadastroCliente.btnFazerPedido.addActionListener(ouvinteCadastroCliente);
		viewCadastroCliente.btnVoltar.addActionListener(ouvinteCadastroCliente);
	}

	public String IDAtendente() {
		return IDAtendente;
	}
	
	public String nomeCliente() {
		return txtNome.getText();
	}

	public String CPFCliente() {
		return txtCPF.getText();
	}

	public String enderecoCliente() {
		return txtEndereco.getText();
	}

	public String telefoneCliente() {
		return txtTelefone.getText();
	}

	public class OuvinteCadastroCliente implements ActionListener{

		private ViewCadastroCliente viewCadastroCliente;

		public OuvinteCadastroCliente(ViewCadastroCliente viewCadastroCliente) {
			this.viewCadastroCliente = viewCadastroCliente;
		}

		public void actionPerformed(ActionEvent e) {

			String button = e.getActionCommand();
			
			switch(button) {
			case "Voltar":
				viewCadastroCliente.dispose();
				break;
			case "Cadastrar":
				clienteDTO.setCPF(CPFCliente());
				clienteDTO.setNome(nomeCliente());
				clienteDTO.setEndereco(enderecoCliente());
				clienteDTO.setTelefone(telefoneCliente());
				if (controllerCliente.preenchido(clienteDTO)) {
					try {
						controllerCliente.cadastrarCliente(clienteDTO);
						JOptionPane.showMessageDialog(viewCadastroCliente, "Cliente Cadastrado!", "Sucesso", JOptionPane.PLAIN_MESSAGE);
						int opc = JOptionPane.showConfirmDialog(viewCadastroCliente, "Deseja Contiuar Cadastrando Clientes?");
						if (opc == JOptionPane.YES_OPTION) {
							viewCadastroCliente.txtNome.setText("");
							viewCadastroCliente.txtCPF.setText("");
							viewCadastroCliente.txtEndereco.setText("");
							viewCadastroCliente.txtTelefone.setText("");
						} else {
							viewCadastroCliente.dispose();
							viewAtendente.dispose();
							new ViewAtendente(IDAtendente, viewCadastroCliente.regiao);
						}

					} catch (ClienteDuplicadoException e1) {
						JOptionPane.showMessageDialog(viewCadastroCliente, e1.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
						viewCadastroCliente.txtCPF.setBackground(Color.YELLOW);
					}
				} else {
					JOptionPane.showMessageDialog(viewCadastroCliente, "Ação Inválida!\nAlgum Campo Está Vazio", "Atenção", JOptionPane.WARNING_MESSAGE);
				}
			}

		}

	}

}
