package client;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * 방만들기
 * 미구현
 * 접속시 만들어져 있는 방의 목록을 보여주고
 * 참여할 것인가, 자신의 직접 방을 만들 것인가를 나타내주는 패널
 */
public class SelectRival extends JPanel {
	JButton createRoom, enterRoom;
	JTable table;
	DefaultTableModel model;

	public SelectRival() {
		setLayout(null);

		String[][] row = new String[0][2];
		String[] col = { "방이름", "인원" };

		createRoom = new JButton("방만들기");
		enterRoom = new JButton("참여하기");

		model = new DefaultTableModel(row, col);
		table = new JTable(model) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JScrollPane js = new JScrollPane(table);

		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		add(createRoom);
		add(enterRoom);
		add(js);

		js.setBounds(0, 0, 400, 330);
		createRoom.setBounds(50, 340, 100, 35);
		enterRoom.setBounds(250, 340, 100, 35);
	}

}
