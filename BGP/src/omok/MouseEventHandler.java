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
		if(!d.ena)
			return;
		
		
		super.mousePressed(arg0);
		System.out.println("mouse point");
		 int x=(int)Math.round((arg0.getX()-80)/(double)ms.getCell());
	     int y=(int)Math.round((arg0.getY()-50)/(double)ms.getCell()); 
	     
	   //  System.out.println("x y는 " + x + ", " + y );
	   //  System.out.println("좌표는 "+arg0.getX() + ", " + arg0.getY());
	     
	     
	    if(x<0||x>18||y<0||y>18)
	    	return;
	    if(map.getXY(y, x)==map.getBlack()||map.getXY(y, x)==map.getWhite())
	    	return;
	    
	    //System.out.println(x+" "+y);
	    map.setMap(y, x, 1);

	    //map.changeCheck();
	    d.repaint();
	    if(map.winCheck(x, y));
	   // 	pw.println("[MYWIN]");
	    	
	    d.ena=false;
	  //  pw.println("[ENDTN]");
	}
	
	
}