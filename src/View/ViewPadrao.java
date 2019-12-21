package View;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class ViewPadrao extends JFrame {

	public ViewPadrao() {
		this.setSize(1000, 700);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.NORMAL);
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
		ImageIcon logoPizzaria = new ImageIcon("images/3bba8cf18c7b31717c98231e7d6e736e-pizza-logo-by-vexels (1).png");
		this.setIconImage(logoPizzaria.getImage());
		this.getContentPane().setLayout(null);
		
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {}
	}

}
