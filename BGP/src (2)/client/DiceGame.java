package client; //주사위이미지

import java.awt.*;
import java.awt.event.*;

import javax.lang.model.AnnotatedConstruct;
import javax.swing.*;

public class DiceGame extends JPanel implements ActionListener {

	ImageIcon Icon[] = { new ImageIcon("image\\dice1.png"), new ImageIcon("image\\dice2.png"),
			new ImageIcon("image\\dice3.png"), new ImageIcon("image\\dice4.png"), new ImageIcon("image\\dice5.png"),
			new ImageIcon("image\\dice6.png") };

	Image m1, m2;
	JButton b1, b2, b3, gc;
	JLabel la1, la2, la3, la4, la5;
	JLabel what;
	Font font1 = new Font("Serif", Font.ITALIC, 50);
	Font font2 = new Font("Serif", Font.ITALIC, 70);
	int ran = (int) (Math.random() * 6) + 1;
	int ran2 = (int) (Math.random() * 6) + 1;
	String who;

	// 셀렉버튼s

	DiceGame() {
		setLayout(null);
		la4 = new JLabel(new ImageIcon("image\\d1.gif"));
		la5 = new JLabel(new ImageIcon("image\\d2.gif"));
		setBackground(Color.white);

		b1 = new JButton("Start");
		la1 = new JLabel("1P");
		la2 = new JLabel("2P");
		la3 = new JLabel("VS");
		what = new JLabel("");

		gc = new JButton("게임 고르기");
		gc.setFont(font2);
		gc.setBounds(50, 600, 300, 200);

		b1.setFont(font2);
		la1.setFont(font1);
		la2.setFont(font1);
		la3.setFont(font2);
		la3.setForeground(Color.RED);
		what.setFont(font1);

		add(b1);
		add(la1);
		add(la2);
		add(la3);
		add(la4);
		add(la5);
		add(what);
		add(gc);

		b1.addActionListener(this);

		b1.setBounds(350, 400, 200, 200);
		la1.setBounds(150, 30, 70, 70);
		la2.setBounds(750, 30, 70, 70);
		la3.setBounds(400, 150, 150, 150);

		la4.setBounds(70, 100, 250, 250);
		la5.setBounds(650, 100, 250, 250);
		what.setBounds(390, 280, 200, 150);

		gc.setVisible(false);

	}

	static String win(int a, int b) {
		String c = "";

		if (a == b)
			c = "DROW";
		if (a > b)
			c = "1P WIN";
		if (a < b)
			c = "2P WIN";
		return c;
	}

	/*
	public static void main(String[] args) {
		DiceGame dice = new DiceGame();
		JFrame jf = new JFrame();
		jf.setSize(1000, 850);
		jf.add(dice);
		jf.setVisible(true);
	}
*/
	@Override
	public void actionPerformed(ActionEvent e) {

	}

}