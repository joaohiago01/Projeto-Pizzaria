package View;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class ViewDetalhes extends ViewPadrao {

	private JButton btnFechar;
	
	public ViewDetalhes() {
		super();
		this.setSize(800, 450);
		this.setLocationRelativeTo(null);
		getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JLabel lblDetalhesDoPedido = new JLabel("Detalhes Do Preparo");
		lblDetalhesDoPedido.setFont(new Font("Courier New", Font.BOLD, 18));
		lblDetalhesDoPedido.setBounds(10, 11, 269, 24);
		getContentPane().add(lblDetalhesDoPedido);
		
		JLabel lblFormaDePreparo = new JLabel("Forma De Preparo:");
		lblFormaDePreparo.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblFormaDePreparo.setBounds(4, 88, 127, 14);
		getContentPane().add(lblFormaDePreparo);
		
		JTextArea textArea = new JTextArea("1 - Esquente a água, dissolva o fermento nela e junte o óleo, o sal e o açúcar.\n2 - Faça um montinho com a farinha numa vasilha bem grande ou em um tampo de granito bem limpo (com álcool) e abra um buraco no meio. Vá adicionando a mistura de água e óleo e misture com as mãos por uns 15 minutos até a massa começar a desgrudar da mão. Se for necessário, acrescente mais farinha para chegar ao ponto ideal da massa. Divida a massa em umas 5 bolas e cubra com um pano úmido por pelo menos 40 minutos, quanto mais tempo a massa descansar, mais fofa ficará. Depois disso, estique as massas com as mãos ou com um rolo e coloque em formas untadas, adicione o recheio e asse em forno alto por cerca de 20 minutos.");
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		textArea.setLineWrap(true);
		textArea.setBounds(139, 84, 630, 299);
		getContentPane().add(textArea);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnFechar.setBounds(654, 13, 115, 25);
		getContentPane().add(btnFechar);
		
		addListener(this);
		setVisible(true);
	}
	
	public void addListener (ViewDetalhes viewDetalhes) {
		OuvinteDetalhes ouvinteDetalhes = new OuvinteDetalhes(viewDetalhes);
		viewDetalhes.btnFechar.addActionListener(ouvinteDetalhes);
	}
	
	public class OuvinteDetalhes implements ActionListener {

		private ViewDetalhes viewDetalhes;
		
		public OuvinteDetalhes (ViewDetalhes viewDetalhes) {
			this.viewDetalhes = viewDetalhes;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			viewDetalhes.dispose();
		}
		
	}

}
