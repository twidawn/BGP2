package client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ReGame extends JFrame {
	// 승패and 재경기or 나가기
	JLabel lose;
	JButton regame, out;
	Font font1, font2;

	public ReGame() {
		font1 = new Font("Serif", Font.ITALIC, 70);
		font2 = new Font("Serif", Font.ITALIC, 100);
		lose = new JLabel("LOSE");
		regame = new JButton("재경기");
		out = new JButton("나가기");
		
		add(out);
		add(regame);
		add(lose);
		
		setVisible(false);
	}

	public void sizeChange(int x, int y) {
		setSize(x, y);
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
}
