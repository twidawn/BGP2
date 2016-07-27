package client;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
/*
 * 간단한 게임 선택 창
 */
public class ChooseGame extends JFrame{
	JButton othello, omok;
		
	public ChooseGame(){
		setLayout(new FlowLayout());
		
		othello = new JButton("오셀로");
		omok = new JButton("오목");
		
		add(omok);
		add(othello);
		
		setVisible(false);
	}
}