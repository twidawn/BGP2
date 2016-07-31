package client; //주사위이미지

import java.awt.*;
import java.awt.event.*;

import javax.lang.model.AnnotatedConstruct;
import javax.swing.*;

public class DiceGame extends JPanel{

	ImageIcon icon[] = { new ImageIcon("image\\dice1.png"), new ImageIcon("image\\dice2.png"),
			new ImageIcon("image\\dice3.png"), new ImageIcon("image\\dice4.png"), new ImageIcon("image\\dice5.png"),
			new ImageIcon("image\\dice6.png") };

	
	JButton start,  gamesel;
	JLabel player1, player2, vs, movedice1, movedice2;
	JLabel winner;
	Font font1 = new Font("Serif", Font.ITALIC, 50);
	Font font2 = new Font("Serif", Font.ITALIC, 70);
	String who;
	// 셀렉버튼s

	DiceGame() {
		setLayout(null);
		movedice1 = new JLabel(new ImageIcon("image\\mdice01.gif"));
		movedice2 = new JLabel(new ImageIcon("image\\mdice01.gif"));
		setBackground(Color.white);

		start = new JButton(new ImageIcon("image\\stop2.png"));
		player1 = new JLabel("1P");
		player2 = new JLabel("2P");
		vs = new JLabel("VS");
		winner = new JLabel("");
		start.setVisible(false);
		
		gamesel = new JButton(new ImageIcon("image\\select.png"));
		gamesel.setFont(font2);
		gamesel.setBounds(50, 600, 300, 200);
		gamesel.setContentAreaFilled(false);
		gamesel.setOpaque(false);
		gamesel.setBorderPainted(false);

		start.setFont(font2);
		player1.setFont(font1);
		player2.setFont(font1);
		vs.setFont(font2);
		vs.setForeground(Color.RED);
		winner.setFont(font1);

		add(start);
		add(player1);
		add(player2);
		add(vs);
		add(movedice1);
		add(movedice2);
		add(winner);
		add(gamesel);

		start.setBounds(350, 400, 200, 200);
		player1.setBounds(50, 30, 220, 70);
		player2.setBounds(650, 30, 220, 70);
		vs.setBounds(400, 150, 150, 150);

		movedice1.setBounds(70, 100, 250, 250);
		movedice2.setBounds(650, 100, 250, 250);
		winner.setBounds(390, 280, 200, 150);

		gamesel.setVisible(false);

	}

}