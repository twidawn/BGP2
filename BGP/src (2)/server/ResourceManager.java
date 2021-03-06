package server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

public class ResourceManager extends Vector {

	public ResourceManager() {;}

	public void add(ControlServer cs) {
		super.add(cs);
	}

	public void remove(ControlServer cs) {
		super.remove(cs);
	}

	public ControlServer getCs(int i) {
		return (ControlServer) elementAt(i);
	}

	Socket getSocket(int i) {
		return getCs(i).getSocket();
	}

	void sendMsg(int i, String msg) {
		try {
			PrintWriter pw = new PrintWriter(getSocket(i).getOutputStream(), true);
			pw.println(msg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	String getRoomName(int i) {
		return getCs(i).getRoomName();
	}

	synchronized boolean roomCheck(String roomName) {
		if(roomName.equals(""))
			return false;
		int count = 0;
		for (int i = 0; i < size(); i++) {
			System.out.print(getRoomName(i) + " ");
			if (roomName.equals(getRoomName(i)))
				count++;
			if (count > 2)
				return false;
		}
		System.out.println();
		return true;
	}

	void roomMsg(ControlServer cs, String msg) {
		for (int i = 0; i < size(); i++) {
			if (getRoomName(i).equals(cs.getRoomName()) && getCs(i) != cs)
				sendMsg(i, msg);
		}
	}
	
	synchronized boolean roomCheck(ControlServer cs) {
		int count = 0;
		for (int i = 0; i < size(); i++) {
			if (getRoomName(i) != null &&getRoomName(i).equals(cs.getRoomName()) && getCs(i) != cs)
				count++;
		}
		if(count==0)
			return true;
		return false;
	}

	void roomNotice(ControlServer cs, String msg) {
		for (int i = 0; i < size(); i++) {
			if (getRoomName(i).equals(cs.getRoomName()))
				sendMsg(i, msg);
		}
	}

	void allNotice(String msg) {
		for (int i = 0; i < size(); i++) {
			sendMsg(i, msg);
		}
	}

	synchronized boolean gamerCheck(String roomName) {
		int count = 0;
		for (int i = 0; i < size(); i++) {
			if (roomName.equals(getRoomName(i)))
				count++;
		}
		if (count == 2)
			return true;
		return false;
	}

	synchronized boolean isReady(String roomName) {
		int count = 0;
		for (int i = 0; i < size(); i++) {
			if (roomName.equals(getRoomName(i)) && getCs(i).getReady())
				count++;
		}
		if (count == 2)
			return true;
		return false;
	}

	/*
	 * int getRoomNumber(int i){ return getCs(i).getRoomNumber(); }
	 * 
	 * synchronized boolean roomCheck(int roomNum){
	 * 
	 * int count=0; for(int i=0; i < size();i++){ if(roomNum ==
	 * getRoomNumber(i)) count++; if(count>2) return false; }
	 * 
	 * return true; }
	 * 
	 * void sendMsg(ControlServer cs, String msg){ for(int i= 0; i < size();
	 * i++){ if(getRoomNumber(i) == cs.getRoomNumber() && getCs(i)!=cs)
	 * sendMsg(i, msg); } }
	 * 
	 * void btnEnabled(ControlServer cs, String msg){ for(int i= 0; i < size();
	 * i++){ if(getRoomNumber(i) == cs.getRoomNumber()) sendMsg(i, msg); } }
	 * 
	 * 
	 * synchronized boolean gamerCheck(int roomNum){ int count=0; for(int i= 0;
	 * i<size();i++){ if(roomNum ==getRoomNumber(i)) count++; } if(count==2)
	 * return true; return false; }
	 * 
	 * synchronized boolean isReady(int roomNum){ int count=0; for(int i= 0;
	 * i<size();i++){ if(roomNum ==getRoomNumber(i) && getCs(i).getReady())
	 * count++; } if(count==2) return true; return false; }
	 */
}