package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ReGame extends JFrame {
	// 승패and 재경기or 나가기
	JLabel lose;
	JButton regame, out;
	
	Font font1 = new Font("Serif", Font.ITALIC, 50) ;
	public ReGame() {
		setLayout(null);
		
		lose = new JLabel("LOSE");
		regame = new JButton("재경기");
		out = new JButton("나가기");
		
		lose.setBounds(90, 20, 200, 100);
		lose.setFont(font1);
		lose.setForeground(new Color(255,0,0));
		
		regame.setBounds(30, 180, 100, 50);
		out.setBounds(170, 180, 100, 50);
		
		add(out);
		add(regame);
		add(lose);
		
		setSize(310, 300);
		setVisible(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void sizeChange(int x, int y) {
		setSize(x, y);
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
}
