package client;

import java.awt.*;
import javax.swing.*;

public class WaitingRoom extends JFrame {
	public WaitingRoom() {
		SelectRival a = new SelectRival();
		// add(a);
		/*
		 * setVisible(true); setSize(415,420);
		 */

		MakeRoom mr = new MakeRoom();
		add(mr);

	}

	public static void main(String[] args) {
		new WaitingRoom();

	}
}