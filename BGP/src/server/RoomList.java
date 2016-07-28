package server;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Vector;

public class RoomList extends Vector{
	
	public RoomList(){;}
	
	public void add(String[] roomList){
		super.add(roomList);
	}
	
	public void remove(String[] roomList) {
		super.remove(roomList);
	}
	
	public String[] getRl(int i) {
		return (String[]) elementAt(i);
	}
	
	public void changeNumber(String roomName, String number){
		for(int i = 0; i < size(); i++){
			if(roomName.equals(getRl(i)[0]))
				getRl(i)[1] = number;
		}
	}
	
	public void delectRoom(String roomName){
		for(int i = 0; i < size(); i++){
			if(getRl(i)[0].equals(roomName))
				remove(getRl(i));
		}
	}
	
	synchronized public void writeRoom(PrintWriter pw){
		for(int i = 0; i<size() ; i++){
			pw.println("[MKROOM]"+getRl(i)[0] + " " + getRl(i)[1]);
		}
		
	}
	
	public void printList(){
		for(int i = 0; i < size(); i++){
			System.out.println(getRl(i)[0] + ", "+ getRl(i)[1]);
		}
	}
	
}
