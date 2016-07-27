package client;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ReGame extends JFrame {
	// 승패and 재경기or 나가기
	JLabel lose;
	JButton regame, out;
	Font font1, font2;

	public void Lose() {
		font1 = new Font("Serif", Font.ITALIC, 70);
		font2 = new Font("Serif", Font.ITALIC, 100);
		lose = new JLabel("LOSE");
		regame = new JButton("재경기");
		out = new JButton("나가기");
		
		setVisible(false);
	}

}
