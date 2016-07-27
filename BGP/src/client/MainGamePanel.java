package client;

import java.awt.Color;
import java.awt.Font;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import othello.*;

public class MainGamePanel extends JPanel {
	private JPanel megPanel;
	private JPanel gamePanel;
	private JTextField sendMsg;
	private JTextArea logMsg;
	public JButton send;
	GameControl gc;
	Tile tile;
	JLabel turnNotice;

	Font font1 = new Font("Serif", Font.ITALIC, 50);
	protected static JLabel turnImage;

	public MainGamePanel() {
		setLayout(null);
		setBackground(Color.white);
		setBackground(Color.LIGHT_GRAY);

		megPanel = new JPanel();
		megPanel.setBounds(930, 0, 350, 900);
		megPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();

		logMsg = new JTextArea();
		scrollPane.setViewportView(logMsg);
		scrollPane.setBounds(0, 0, 350, 500);
		logMsg.setEnabled(false);

		sendMsg = new JTextField();
		sendMsg.setBounds(0, 510, 250, 35);
		send = new JButton("전 송");
		send.setBounds(260, 510, 85, 35);

		megPanel.add(scrollPane);
		megPanel.add(sendMsg);
		megPanel.add(send);

		add(megPanel);

		setSize(1300, 1000);

		turnImage = new JLabel(new ImageIcon("image\\black.png"));
		turnNotice = new JLabel("Turn");
		turnNotice.setFont(font1);

		turnImage.setBounds(50, 600, 200, 200);
		turnNotice.setBounds(100, 700, 200, 200);

		megPanel.add(turnImage);
		megPanel.add(turnNotice);
	}

	public void setOthello(GameControl gc) {
		tile = new Tile(gc);
		add(tile);
		tile.setBounds(20, 20, 800, 800);
		// this.gc = gc;
		gc.gameStatus(tile);
	}

	public void setOmok() {
		;
	}
}