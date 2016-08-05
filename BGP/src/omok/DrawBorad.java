package omok;

import java.awt.Color;
import java.awt.Graphics;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class DrawBorad extends JPanel{
	public boolean ena = false;
	MapSize size;
	Map map;
	final int STONE_SIZE=40;
	
	public DrawBorad(MapSize m,Map map) {
		// TODO Auto-generated constructor stub
		
		setBackground(new Color(206,167,61));
		size=m;
		setLayout(null);
		System.out.println("board");
		this.map=map;
		
	}
	/*public DrawBorad(MouseEventHandler mh) {
		MapSize ms = new MapSize();
		Map mp = new Map(ms.SIZE,ms.SIZE);
	
	}*/

	@Override
	public void paint(Graphics arg0) {
		ImageIcon icon = new ImageIcon(".\\image\\dukpan3.jpg");
		arg0.drawImage(icon.getImage(),0,0,800,800,null);
		drawStone(arg0);
	}
	
	public void board(Graphics arg0) {
		for(int i=1;i<=size.getSize();i++){
			arg0.drawLine(size.getCell(), i*size.getCell(), size.getCell()*size.getSize(), i*size.getCell());
			arg0.drawLine(i*size.getCell(), size.getCell(), i*size.getCell() , size.getCell()*size.getSize());
		}
	}
	public void drawStone(Graphics arg0) {
		for(int y=0;y<size.SIZE;y++){
			for(int x=0;x<size.SIZE;x++){
				if(map.getXY(y, x)==map.getBlack())
					drawBlack(arg0,x,y);
				else if(map.getXY(y, x)==map.getWhite())
					drawWhite(arg0, x, y);
			}
		}
	}
	public void drawBlack(Graphics arg0,int x,int y){
		arg0.setColor(Color.BLACK);
		arg0.fillOval((x)*size.getCell(), (y)*size.getCell(), STONE_SIZE, STONE_SIZE);
		
	}
	public void drawWhite(Graphics arg0,int x,int y){
		arg0.setColor(Color.WHITE);
		arg0.fillOval(x*size.getCell(), y*size.getCell(), STONE_SIZE, STONE_SIZE);
	}
}
