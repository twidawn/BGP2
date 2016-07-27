package server;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ControlServer extends Thread {
	
	private String userName = null;
	//private int roomNumber=-1;
	private String roomName = null;
	private Socket socket;
	private ServerSocket sc;
	private ResourceManager rm;
	private RoomList rl;
	private boolean ready = false;
	private boolean dice = false;
	private int diceNum=0;

	private BufferedReader reader;
	private PrintWriter writer;

	public ControlServer(Socket socket, ServerSocket sc, ResourceManager rm, RoomList rl) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.rm = rm;
		this.sc = sc;
		this.rl = rl;
	}
	
	Socket getSocket(){
		return socket;
	}
	
	public boolean getDice(){return dice;}
	public void setDice(boolean dice){this.dice = dice;}
	public int getDiceNum(){return diceNum;}
	
	public boolean getReady(){return ready;}
	
	public String getRoomName(){
		return roomName;
	}
	
	/*
	public int getRoomNumber(){
		return roomNumber;
	}
*/
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);

			String msg;

			while ((msg = reader.readLine()) != null) {
				
				if(msg.equals("[GUEST]")){//게스트 로그인
					userName = "GUEST" + Integer.toString(++Server.playerCount);
				} else if(msg.equals("[TWITT]")){//트위터 로그인 버튼 누름
					System.out.println("트위터 버튼 눌림");
				} else if(msg.equals("[ROOMINDEX]")){ //07.27 방정보를 불러옴
					rl.writeRoom(writer);
				} else if(msg.startsWith("[MKROOM]")){//방만듬
					String temp = msg.substring(8);
					roomName = temp.substring(0, temp.indexOf(" "));
					String[] roomList = {roomName, "1"};
					rl.add(roomList);
					rm.allNotice(msg);
				} else if(msg.startsWith("[JOINR]")){
					String temp = msg.substring(7);
					roomName =temp;
					if(rm.roomCheck(temp)){
						rl.changeNumber(roomName, "2");
						writer.println("[JOINR]" + temp);
						rm.roomNotice(this, "[GODICE]");
					}else{
						roomName= null;
						writer.println("[FULLR]");
					}
				}else if(msg.startsWith("[CHECKF]")){
					rm.allNotice(msg);
				}else if(msg.startsWith("[CASTD]")){ //주사위 던짐
					dice = true;
					diceNum = (int)(Math.random()*6)+1;
					writer.println("[UDICE]" + diceNum);
					rm.roomMsg(this, "[SETDC]"+diceNum);// 상대방에게 주사위를 세팅할것을 명함
					if(rm.checkDice(roomName)){
						if(rm.diceResult(this, diceNum)==1){
							writer.println("[DICEW]");
							rm.roomMsg(this, "[DICEL]");
						}else if(rm.diceResult(this, diceNum)==2){
							rm.roomMsg(this, "[DICEW]");
							writer.println("[DICEL]");
						}else if(rm.diceResult(this, diceNum)==3){
							rm.roomNotice(this, "[GODICE]"); //다시 주사위 던짐
						}
					}
				}else if(msg.startsWith("[GOTHE]")){ //오셀로를 선택함
					rm.roomNotice(this, "[GOTHE]");
				}else if(msg.startsWith("[STONE]")){
					rm.roomMsg(this, msg);
				}else if(msg.startsWith("[ENDTN]")){
					rm.roomMsg(this, "[YOURT]");
				}else if (msg.equals("[WHITE]")){
					rm.roomNotice(this, "[WHITE]");
				}else if( msg.equals("[BLACK]")){
					rm.roomNotice(this, "[BLACK]");
				}else if (msg.equals("[LEAVE]")){
					dice = false;
					rl.delectRoom(roomName);
					rm.allNotice("[RMROOM]" + roomName);
					roomName=null;
				}else if (msg.equals("[MYWIN]")){ //내 승리일경우 나에겐 승을 적에겐 패를 알린다.
					writer.println("[MYWIN]");
					rm.roomMsg(this, "[MYLOS]");
				}else if (msg.equals("[MYLOS]")){//내 패배일경우 나에겐 패를 적에겐 승을 알린다.
					writer.println("[MYLOS]");
					rm.roomMsg(this, "[MYWIN]");
				}else if (msg.equals("[GDRAW]")){ //비기면 둘다 비긴것이다.
					rm.roomNotice(this, "[GDRAW]");
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("여기닷");
		} finally {
			try {
				//System.out.println("방제목" + roomName);
				//System.out.println(userName+ "퇴장");
				if(roomName != null && rm.roomCheck(this)){ //방이 비었을 경우
					rl.delectRoom(roomName);
					rm.allNotice("[RMROOM]" + roomName);
				}else if(roomName != null && !rm.roomCheck(this)){//방에 사람이 남아있을 경우
					rm.roomMsg(this, "[LEAVE]");
				}
				rm.remove(this);
				if (reader != null)
					reader.close();
				if (writer != null)
					writer.close();
				if (socket != null)
					socket.close();
				reader = null;
				writer = null;
				socket = null;
				System.out.println("접속자수 : " + rm.size());
			} catch (Exception e) {
			}
		}

	}

}