package othello;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.*;

import client.MainGamePanel;

public class Tile extends JPanel implements ActionListener {
	private boolean enable = false;
	public GameControl g;
	static ImageIcon[] images = new ImageIcon[3];
	public static final int NONE = 0, BLACK = 1, WHITE = 2;
	public int image = NONE;
	public JButton[][] btn;
	PrintWriter pw;
	public int turn;
	

	public Tile(GameControl gc) {
		super();
		g = gc;
		images[NONE] = new ImageIcon(Toolkit.getDefaultToolkit().getImage(".\\image\\empty.png"));
		images[BLACK] = new ImageIcon(Toolkit.getDefaultToolkit().getImage(".\\image\\black.png"));
		images[WHITE] = new ImageIcon(Toolkit.getDefaultToolkit().getImage(".\\image\\white.png"));

		setLayout(new GridLayout(0, 8));
		createButton(g);

		// gc.printMap();
	}
	
	public void setPw(PrintWriter pw){this.pw = pw;}

	public void setenable(boolean enable) {
		System.out.println(enable);
		this.enable = enable;
	}

	private void createButton(GameControl gc) {
		btn = new JButton[8][8];
		for (int i = 0; i < GameControl.N; i++) {
			for (int j = 0; j < GameControl.N; j++) {
				btn[i][j] = new JButton();
				if ((i + j) % 2 == 0)
					btn[i][j].setBackground(Color.gray);
				else
					btn[i][j].setBackground(Color.white);
				btn[i][j].addActionListener(this);
				btn[i][j].setIcon(images[NONE]);
				changeStone(i, j, 0);
				add(btn[i][j]);
			}
		}

	}

	synchronized public void reverseStone() {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if ((GameControl.x + i) >= 0 && (GameControl.x + i) <= 7 && (GameControl.y + j) >= 0
						&& (GameControl.y + j) <= 7) {
					if (g.getgameMap(GameControl.x + i, GameControl.y + j) != 0
							&& g.getgameMap(GameControl.x + i, GameControl.y + j) != turn) {
						Point a = new Point(GameControl.x + i, GameControl.y + j);
						if (g.takeStoneCheck(turn, a, false, i, j)) {
							for (int k = i, z = j; g.getgameMap(GameControl.x + k,
									GameControl.y + z) != turn; k += i, z += j) {
								changeStone(GameControl.x + k, GameControl.y + z, turn);
								pw.println("[STONE]" + (GameControl.x + k) + " " + (GameControl.y + z));
							}
						}
					}
				}
			}
		}
	}

	synchronized public void changeStone(int i, int j, int trun) {
		g.setgameMap(i, j, trun);
		btn[i][j].setIcon(images[trun]);
	}

	public void gameWLD(){
		if (g.checkEnd() == 1) {
			if (turn == 1)
				pw.println("[MYWIN]");
			else
				pw.println("[MYLOS]");
		} else if (g.checkEnd() == 2) {
			if (turn == 1)
				pw.println("[MYLOS]");
			else
				pw.println("[MYWIN]");
		} else if (g.checkEnd() ==3)
			pw.println("GDRAW");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		if (!enable){
			System.out.println("플레이어 "+ turn + enable);
			return;
		}
		
		gameWLD();		
		
		System.out.println("난 플레이어 "+ turn + "입니다.");

		if (!g.allPlaceCheck(turn))
			pw.println("[ENDTN]");
		
		for (int i = 0; i < GameControl.N; i++) {
			for (int j = 0; j < GameControl.N; j++) {
				if (e.getSource() == btn[i][j] && g.getgameMap(i, j) == 0 && g.placeCheck(turn, i, j)) {
					changeStone(i, j, turn);
					pw.println("[STONE]" + i + " " + j);
					GameControl.x = i;
					GameControl.y = j;
					//g.printMap();
					System.out.println(GameControl.x + " " + GameControl.y);
				} else if(e.getSource() == btn[i][j] && g.getgameMap(i, j) == 0 && !g.placeCheck(turn, i, j)){
					return;
				} else if (e.getSource() == btn[i][j] && g.getgameMap(i, j) != 0){
					return;
				}
					//System.out.println("선택오류");
			}
		}
		
		reverseStone();
		System.out.println("Player"+ turn + "턴 끝");		
		//흑돌Turn 이미지 바꾸기  여기넣을예정		
		enable=false;
		pw.println("[ENDTN]");
		
	}

}