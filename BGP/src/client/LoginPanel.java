package client;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LoginPanel extends JPanel {
	JButton twitter, guest;
	Image back;

	public LoginPanel() {
		back = Toolkit.getDefaultToolkit().getImage(".\\image\\Background.png");
		setLayout(null);

		twitter = new JButton(new ImageIcon(".\\image\\TwitterLogin.png"));
		guest = new JButton(new ImageIcon(".\\image\\GuestLogin.png"));

		twitter.setContentAreaFilled(false);
		twitter.setOpaque(false);
		twitter.setBorderPainted(false);

		guest.setContentAreaFilled(false);
		guest.setOpaque(false);
		guest.setBorderPainted(false);

		twitter.setBounds(30, 170, 200, 200);
		guest.setBounds(350, 170, 200, 200);

		add(twitter);
		add(guest);

	}

	public void paintComponent(Graphics g) {
		g.drawImage(back, 0, 0, getWidth(), getHeight(), this);
	}
}