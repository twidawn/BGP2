package server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

public class ResourceManager extends Vector {

	public ResourceManager() {
		;
	}

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

	synchronized int diceResult(ControlServer cs, int diceNum) {
		int[] draw = new int[size()];
		for (int i = 0; i < size(); i++) {
			if (getRoomName(i) != null && getCs(i) != cs && cs.getRoomName().equals(getCs(i).getRoomName())) {
				draw[i]++;
				if (diceNum > getCs(i).getDiceNum())
					return 1;
				else if (diceNum < getCs(i).getDiceNum())
					return 2;
				break;
			}
		}

		for (int i = 0; i < size(); i++) {
			getCs(i).setDice(false);
		} // 주사위 굴린 상태를 초기로 바꿈

		return 3;
	}

	synchronized boolean roomCheck(String roomName) {
		if (roomName.equals(""))
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
			if (getRoomName(i) != null && getRoomName(i).equals(cs.getRoomName()) && getCs(i) != cs)
				sendMsg(i, msg);
		}
	}

	synchronized boolean roomCheck(ControlServer cs) {
		int count = 0;
		for (int i = 0; i < size(); i++) {
			if (getRoomName(i) != null && getRoomName(i).equals(cs.getRoomName()) && getCs(i) != cs)
				count++;
		}
		if (count == 0)
			return true;
		return false;
	}

	void roomNotice(ControlServer cs, String msg) {
		for (int i = 0; i < size(); i++) {
			if (getRoomName(i) != null && getRoomName(i).equals(cs.getRoomName()))
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
			if (getRoomName(i) != null && roomName.equals(getRoomName(i)))
				count++;
		}
		if (count == 2)
			return true;
		return false;
	}

	synchronized boolean isReady(String roomName) {
		int count = 0;
		for (int i = 0; i < size(); i++) {
			if (getRoomName(i) != null && roomName.equals(getRoomName(i)) && getCs(i).getReady())
				count++;
		}
		if (count == 2)
			return true;
		return false;
	}

	synchronized boolean checkDice(String roomName) {
		int count = 0;
		for (int i = 0; i < size(); i++) {
			if (getRoomName(i) != null && roomName.equals(getRoomName(i)) && getCs(i).getDice() == true)
				count++;
		}
		if (count == 2)
			return true;
		return false;
	}
	
	synchronized boolean checkNewPlayer(String roomName, ControlServer cs) {
		for (int i = 0; i < size(); i++) {
			if (getRoomName(i) != null && roomName.equals(getRoomName(i)) && getCs(i) != cs && getCs(i).getDice() == false)
				return true;
		}
		return false;
	}
	
	String setRivalName(String roomName, ControlServer cs) {
		for (int i = 0; i < size(); i++) {
			if (getRoomName(i) != null && roomName.equals(getRoomName(i)) && getCs(i) != cs)
				return getCs(i).getUserName();
		}
		return null;
	}
	
	public void roomUpdate(RoomList rl){
		
		for(int i = 0; i < size();i ++){
			try {
				PrintWriter pw = new PrintWriter(getSocket(i).getOutputStream(), true);
				rl.writeRoom(pw);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		
		
	}
}