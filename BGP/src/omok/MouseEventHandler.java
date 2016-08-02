package omok;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import client.*;


public class MouseEventHandler extends MouseAdapter {
	Map map;
	int x,y;
	MapSize ms;
	DrawBorad d;
	public MouseEventHandler mh;
	
	
	public MouseEventHandler(Map m,MapSize ms,DrawBorad d) {
		// TODO Auto-generated constructor stub
		map=m;
		this.ms=ms;
		this.d=d;
	//	this.main=main;
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mousePressed(arg0);
		System.out.println("mouse point");
		 int x=(int)Math.round(arg0.getX()/(double)ms.getCell())-1;
	     int y=(int)Math.round(arg0.getY()/(double)ms.getCell())-2; 
	     
	    if(x<0||x>19||y<0||y>19)
	    	return;
	    if(map.getXY(y, x)==map.getBlack()||map.getXY(y, x)==map.getWhite())
	    	return;
	    
	    System.out.println(x+" "+y);
	    map.setMap(y, x);

	    //map.changeCheck();
	    d.repaint();
	    if(map.winCheck(x, y));
	   // 	pw.println("[MYWIN]");
	    	
	    
	  //  pw.println("[ENDTN]");
	}
	
	
}
