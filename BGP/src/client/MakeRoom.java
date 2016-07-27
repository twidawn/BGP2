package client;

import javax.swing.*;
import java.awt.*;

public class MakeRoom extends JFrame{
	
	JButton makeRoom;
	JLabel roomlabel;
	JTextField roonname;
	//String[] a = {roonnameame, state, Integer.toString(inwon)};
	//wr.model1.addRow(a);
	public MakeRoom(){
		makeRoom=new JButton("방만들기");
		roomlabel=new JLabel("방이름");
		roonname=new JTextField("");
		
		setLayout(null);
		
		makeRoom.setBounds(70, 75, 100, 30);
		roomlabel.setBounds(10, 15, 50, 30);
		roonname.setBounds(65, 15, 150, 30);
		
		add(makeRoom);
		add(roomlabel);
		add(roonname);
		setTitle("방만들기");
		setVisible(false);
		
	}
	
	public void sizeChange(int x, int y){
		setSize(x, y);
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
}
