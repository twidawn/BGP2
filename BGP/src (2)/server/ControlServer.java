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
	private boolean ready = false;

	private BufferedReader reader;
	private PrintWriter writer;

	public ControlServer(Socket socket, ServerSocket sc, ResourceManager rm) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.rm = rm;
		this.sc = sc;
	}
	
	Socket getSocket(){
		return socket;
	}
	
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
				} else if(msg.startsWith("[MKROOM]")){//방만듬
					String temp = msg.substring(8);
					roomName = temp.substring(0, temp.indexOf(" "));
					rm.allNotice(msg);
				} else if(msg.startsWith("[JOINR]")){
					String temp = msg.substring(7);
					roomName =temp;
					if(rm.roomCheck(temp)){
						writer.println("[JOINR]" + temp);
					}else{
						roomName= null;
						writer.println("[FULLR]");
					}
				}else if(msg.startsWith("[CHECKF]")){
					rm.allNotice(msg);
				}
				
				
/*
				if (msg.startsWith("[GUEST]")) {
					userName = "GUEST" + Integer.toString(++Server.playerCount);
					
					roomNumber=1;
					if(rm.roomCheck(1)){
						writer.println("[ROOM]");
						userName = "GUEST" + Integer.toString(++Server.playerCount);
						System.out.println(userName + "의 입장");
						if(rm.gamerCheck(1)){
							//rm.btnEnabled(this, "[BTNEN]"); 두명 다에게 버튼 활성호
							rm.sendMsg(this, "[BTNEN]"); //체크용
							System.out.println("정상작동");
							////////////////////////////임시 돌 색깔 결정
							rm.sendMsg(this, "[BLACK]");
							writer.println("[WHITE]");														
						}
						else{
							writer.println("[BTNRE]");
							System.out.println("비정상");
						}
						
					}else{
						writer.println("[FULL]");
						System.out.println("더 이상 방에 접속 할 수 없습니다.");
					}
					
				}else if(msg.startsWith("[GOTHE]")){
					rm.btnEnabled(this, "[GMSOS]");
					writer.println("[BTURN]");
					rm.sendMsg(this, "[WTURN]");
				}else if(msg.startsWith("[GOMOK]")){
					
				}else if(msg.startsWith("[STONE]")){
					rm.sendMsg(this, msg);
				}else if(msg.startsWith("[ENDTN]")){
					rm.sendMsg(this, "[YOURT]");
				}
*/
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
					rm.allNotice("[RMROOM]" + roomName);
				}else if(roomName != null && !rm.roomCheck(this)){//방에 사람이 남아있을 경우
					
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
