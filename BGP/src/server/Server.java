package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static int playerCount = 0;
	public static int mat=0;
	private ResourceManager rm;
	private RoomList rl;
	private ControlServer cs;
	private ServerSocket server; // 서버소켓
	private Socket soc; // 연결소켓
	private int Port = 9999;
	
	public Server(){
		
		rm = new ResourceManager();
		rl = new RoomList();
	}
	
	public void startServer(){
		try {
			server = new ServerSocket(Port);
			System.out.println("서버 소켓생성");
			while(true){
				Socket socket = server.accept();
				cs = new ControlServer(socket, server, rm, rl);
				System.out.println("컨트롤 서버 시작");	
				cs.start();
				System.out.println("리소스 매니저 시작");
				rm.add(cs);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			
		}
	}

	public static void main(String[] args) {

		Server server = new Server();
		server.startServer();
	}

}