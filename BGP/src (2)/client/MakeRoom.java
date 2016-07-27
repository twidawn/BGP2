package client;

import javax.swing.*;
import java.awt.*;

public class MakeRoom extends JFrame{
	
	JButton makeRoom;
	JLabel la1;
	JTextField rn;
	//String[] a = {rname, state, Integer.toString(inwon)};
	//wr.model1.addRow(a);
	public MakeRoom(){
		makeRoom=new JButton("방만들기");
		la1=new JLabel("방이름");
		rn=new JTextField("");
		
		setLayout(null);
		
		makeRoom.setBounds(70, 75, 100, 30);
		la1.setBounds(10, 15, 50, 30);
		rn.setBounds(65, 15, 150, 30);
		
		add(makeRoom);
		add(la1);
		add(rn);
		
		setVisible(false);
		
	}
	
	public void sizeChange(int x, int y){
		setSize(x, y);
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
}
