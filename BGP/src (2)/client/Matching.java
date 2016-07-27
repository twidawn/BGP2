package client;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Matching extends JPanel {
	Image background;
	// Image tooltip;
	JButton start;
	ImageIcon[] img = new ImageIcon[2];

	public Matching() {
		setLayout(null);
		img[0] = new ImageIcon(Toolkit.getDefaultToolkit().getImage(".\\image\\waitOther1.png"));
		img[1] = new ImageIcon(Toolkit.getDefaultToolkit().getImage(".\\image\\goGame1.png"));
		start = new JButton();
		start.setOpaque(false);
		start.setBorderPainted(false);
		start.setContentAreaFilled(false);
		start.setEnabled(false);
		setButtonImage(0);
		add(start);
		start.setBounds(150, 20, 100, 100);
		background = Toolkit.getDefaultToolkit().getImage(".\\image\\ya.gif");
		// tooltip = Toolkit.getDefaultToolkit().getImage(".\\image\\wait.png");

	}

	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		// g.drawImage(tooltip, 50, 50, 150,75, this);
	}

	public void setButtonImage(int i) {
		start.setIcon(img[i]);
	}
}
