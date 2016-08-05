package client;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import omok.*;
import othello.*;

public class Client extends JFrame implements ActionListener, Runnable {
	private boolean running = false;
	private BufferedReader reader;
	private PrintWriter writer;
	private Socket socket;
	private String userName = null;
	private int selectGame = 0;
	private boolean twitter = false;
	public int playerColor = 0;
	public int game = 0;
	private int win = 0;
	private int lose = 0;
	private int draw = 0;
	private int msgcnt = 0;
	private String myName = null;
	private String rivalName = null;

	GameControl gc;

	CardLayout card = new CardLayout();
	TwitterConnect twitCnn = new TwitterConnect();

	LoginPanel lp;
	Matching ma;
	SelectRival sr;
	ChooseGame cg;
	MainGamePanel mgp;
	DiceGame dg;
	MakeRoom mr;
	ReGame rg;

	MapSize ms = new MapSize();
	Map omokMap;

	public Client() {
		setLayout(card);

		lp = new LoginPanel();
		ma = new Matching();
		sr = new SelectRival();
		cg = new ChooseGame();
		dg = new DiceGame();
		mr = new MakeRoom();
		gc = new GameControl();
		mgp = new MainGamePanel();
		mgp.setOthello(gc);
		rg = new ReGame();

		// 오목세팅
		omokMap = new Map(ms);
		mgp.setOmok(ms, omokMap);

		add("LOGIN", lp);
		add("MATCHI", ma);
		add("SERIV", sr);
		add("DICE", dg);
		add("OTG", mgp);

		lp.twitter.addActionListener(this);
		lp.guest.addActionListener(this);
		ma.start.addActionListener(this);
		cg.othello.addActionListener(this);
		cg.omok.addActionListener(this);
		// @@
		sr.createRoom.addActionListener(this);
		sr.enterRoom.addActionListener(this);
		dg.start.addActionListener(this);
		dg.gamesel.addActionListener(this);
		mr.makeRoom.addActionListener(this);

		mgp.send.addActionListener(this);

		rg.regame.addActionListener(this);
		rg.out.addActionListener(this);

		setTitle("League of Board Game");
		sizeChange(600, 800);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String msg;
		try {
			mgp.setWirter(writer);
			while ((msg = reader.readLine()) != null) {
				// System.out.println(msg);
				if (msg.startsWith("[MKROOM]")) { // 방생성
					String temp = msg.substring(8);
					String roomName = temp.substring(0, temp.indexOf(" "));
					String numOfPlayer = temp.substring(temp.indexOf(" ") + 1);
					String[] a = { roomName, numOfPlayer };
					sr.model.addRow(a);
				} else if (msg.startsWith("[RMROOM]")) { // 방제거
					String temp = msg.substring(8);
					System.out.println(temp);
					for (int i = 0; i < sr.model.getRowCount(); i++) {
						if (sr.model.getValueAt(i, 0).equals(temp))
							sr.model.removeRow(i);
					}
					
				} else if (msg.startsWith("[JOINR]")) { // 방참가
					// String temp = msg.substring(7);
					writer.println("[CHECKF]" + sr.table.getSelectedRow());
					// card.show(getContentPane(), "DICE");
					// sizeChange(1000, 850);
					setDice();
				} else if (msg.equals("[FULLR]")) {
					JOptionPane.showMessageDialog(this, "방이 다 찼습니다.");
				} else if (msg.startsWith("[CHECKF]")) {
					sr.model.setValueAt("2", Integer.parseInt(msg.substring(8)), 1);
				} else if (msg.equals("[LEAVE]")) { // 누가 방을 나갔을 경우
					rivalName = "";
					writer.println("[NEWSET]");
					JOptionPane.showMessageDialog(this, "상대방이 떠났습니다.");
					// reset();
					setEnabled(true);
					setDice();
				} else if (msg.equals("[GODICE]")) {
					this.setEnabled(true);
					dg.start.setVisible(true);
				} else if (msg.startsWith("[SETDC]")) { // 상대방의 주사위 세팅
					dg.movedice2.setIcon(dg.icon[Integer.parseInt(msg.substring(7)) - 1]);
				} else if (msg.startsWith("[UDICE]")) { // 내 주사위 세팅
					dg.movedice1.setIcon(dg.icon[Integer.parseInt(msg.substring(7)) - 1]);
				} else if (msg.startsWith("[DICEW]")) {
					playerColor = 1;
					omokMap.checkBNW = true;
					mgp.tile.turn = 1;
					mgp.tile.setenable(true);
					mgp.omokDb.setEnabled(true);
					dg.gamesel.setVisible(true);
				} else if (msg.startsWith("[DICEL]")) {
					playerColor = 2;
					omokMap.checkBNW = false;
					mgp.tile.turn = 2;
					mgp.tile.setenable(false);
					mgp.omokDb.setEnabled(false);
				} else if (msg.startsWith("[GOTHE]")) {
					String name = msg.substring(7);
					game = 1;
					mgp.turnNotice.setText(name);
					mgp.turnImage.setIcon(new ImageIcon("image\\black.png"));
					gc.gameStatus(mgp.tile);
					card.show(getContentPane(), "OTG");
					mgp.visibleOthello();
					mgp.tile.setPw(writer);
					mgp.logMsg.setText("");
					mgp.sendMsg.setText("");
					sizeChange(1300, 900);
				} else if (msg.startsWith("[GOOMOK]")) { ////////////////// 오목부분
					String name = msg.substring(8);
					mgp.turnNotice.setText(name);
					mgp.turnImage.setIcon(new ImageIcon("image\\black.png"));
					omokMap.init();
					game = 2;
					mgp.logMsg.setText("");
					mgp.sendMsg.setText("");
					omokMap.setPW(writer);
					card.show(getContentPane(), "OTG");
					mgp.visibleOmok();
					sizeChange(1300, 900);
				} else if (msg.startsWith("[STONE]")) {
					String temp = msg.substring(7);
					int x = Integer.parseInt(temp.substring(0, temp.indexOf(" ")));
					int y = Integer.parseInt(temp.substring(temp.indexOf(" ") + 1));
					if (game == 1) {
						if (playerColor == 1)
							mgp.tile.changeStone(x, y, 2);
						else
							mgp.tile.changeStone(x, y, 1);
					} else {
							omokMap.setMap(x, y, 2);
							mgp.omokDb.repaint();
							
					}
				} else if (msg.startsWith("[YOURT]")) {
					// mgp.tile.setenable(true);
					// mgp.tile.gameWLD();
					if(game == 2){
						mgp.omokDb.ena=true;
					}
					mgp.turnNotice.setText(myName);
					if (playerColor == 1)
						writer.println("[BLACK]");
					else
						writer.println("[WHITE]");
					mgp.tile.setenable(true);
				} else if (msg.startsWith("[BLACK]")) {
					mgp.turnImage.setIcon(new ImageIcon("image\\black.png"));
				} else if (msg.startsWith("[WHITE]")) {
					mgp.turnImage.setIcon(new ImageIcon("image\\white.png"));
				} else if (msg.equals("[MYWIN]")) {
					win++;
					setEnabled(false);
					setReGame("재경기?");
				} else if (msg.equals("[MYLOS]")) {
					lose++;
					setEnabled(false);
					setReGame("패배!");
					System.out.println("패배횟수 : " + lose);
					if (twitter == true) {
						if (!twitCnn.sendMsg(lose + "패배를 적립하셨습니다!")) {
							JOptionPane.showMessageDialog(this, "메세지 전송 실패");
						}
					}
				} else if (msg.equals("[GDRAW]")) {
					draw++;
					setEnabled(false);
					setReGame("비김");
				} else if (msg.equals("[CGNUM]")) {
					sr.model.setNumRows(0);
				} else if (msg.startsWith("[SETMG]")) {
					String mg = msg.substring(7);
					mgp.logMsg.append(mg + "\n");
				} else if (msg.startsWith("[SETNM]")) {
					myName = msg.substring(7);
				} else if (msg.startsWith("[RVNAME]")) {
					rivalName = msg.substring(8);
					dg.player2.setText(rivalName);
				} else if (msg.equals("[SETTN]")) {
					mgp.turnNotice.setText(rivalName);
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("실행부에서 에러" + e.getMessage());
		}

	}

	public void setReGame(String text) {
		this.setEnabled(false);
		rg.setTitle("Player" + playerColor);
		rg.lose.setText(text);
		rg.sizeChange(300, 300);
		rg.setVisible(true);
	}

	public void start() {

	}

	private void connect() {
		try {
			System.out.println("연결시도");
			socket = new Socket("127.0.0.1", 9999);
			System.out.println("연결성공");
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			new Thread(this).start();
		} catch (Exception e) {
			System.out.println("클라 컨넥부분 익셉션" + e.getMessage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (socket == null) {
			System.out.println("서버연결에 실패하였습니다.");
			return;
		}

		if (e.getSource() == lp.twitter) {
			twitter = true;
			String result = null;
			twitCnn.createTWittter();
			twitCnn.usingBrowser();
			result = JOptionPane.showInputDialog("주소를 복사해주세요.");

			if (result == null || !result.startsWith("https://apps.twitter.com/?oauth_token=")) {
				JOptionPane.showMessageDialog(this, "정확히 입력해주세요.");
			} else if (result.startsWith("https://apps.twitter.com/?oauth_token=")) {
				if (twitCnn.connTwitter(result)) {
					myName = twitCnn.getName() + "(T)";
					JOptionPane.showMessageDialog(this, "정상 접속 되었습니다.");
					writer.println("[TWITT]" + twitCnn.getName());
					writer.println("[ROOMINDEX]");
					card.show(getContentPane(), "SERIV");
					sizeChange(415, 420);
				} else {
					JOptionPane.showMessageDialog(this, "접속에 실패했습니다.");
				}
			}

		} else if (e.getSource() == lp.guest) {
			System.out.println("게스트 버튼 눌림");
			if (sr.table.getRowCount() == 0)
				writer.println("[ROOMINDEX]");
			// @@대기실들어가기@@
			card.show(getContentPane(), "SERIV");
			sizeChange(415, 420);
			// setSize(415, 420);

			writer.println("[GUEST]");
			// card.show(getContentPane(), "MATCHI");
			// sizeChange(400, 400);
		} else if (e.getSource() == sr.createRoom) {
			mr.sizeChange(250, 150);
			mr.roonname.setText("");
			mr.setVisible(true);
		} else if (e.getSource() == mr.makeRoom) {
			String temp = "";
			String rname = mr.roonname.getText().trim();
			if (rname.length() < 1) {
				JOptionPane.showMessageDialog(this, "방이름을 입력하세요");
				mr.roonname.requestFocus();
				return;
			}
			// 중복방 체크
			for (int i = 0; i < sr.model.getRowCount(); i++) {
				temp = sr.model.getValueAt(i, 0).toString();
				if (rname.equals(temp)) {
					JOptionPane.showMessageDialog(this, "중복된 방이름입니다");
					mr.roonname.setText("");
					return;
				}
			} // 여기서 for문 끝 and 방만드는데 성공했을시
				// 만든 방 등록
			String numOfPlayer = "1";
			writer.println("[MKROOM]" + rname + " " + numOfPlayer);

			// String[] a = { rname, numOfPlayer};

			// sr.model.addRow(a);
			// 다이스화면으로넘기기
			/*
			 * card.show(getContentPane(), "DICE"); dg.start.setVisible(false);
			 * sizeChange(1000, 850);
			 * 
			 */
			setDice();
			mr.setVisible(false);

		} else if (e.getSource() == sr.enterRoom) {// 방참가
			if (sr.table.getSelectedRow() != -1)
				writer.println("[JOINR]" + sr.model.getValueAt(sr.table.getSelectedRow(), 0));
		}
		// 07.26
		// }else if(e.getSource()==ma.start){
		else if (e.getSource() == dg.gamesel) {
			cg.setVisible(true);
			cg.setResizable(false);
			cg.setBounds(300, 300, 170, 70);
			System.out.println("시작버튼 눌림");
		} else if (e.getSource() == cg.othello) {
			System.out.println("내가 버튼을 눌렀지");
			// mgp.tile.setenable(true);
			writer.println("[GOTHE]");

			cg.setVisible(false);
		} else if (e.getSource() == cg.omok) {
			writer.println("[GOOMOK]");
			cg.setVisible(false);
			System.out.println("오목버튼 눌림");
			mgp.omokDb.ena = true;
		} else if (e.getSource() == dg.start) {
			dg.start.setVisible(false);
			writer.println("[CASTD]");
		} else if (e.getSource() == mgp.send) {
			System.out.println("메시지 전송 버튼");
			String temp;
			if (!mgp.sendMsg.getText().equals("")) {
				temp = mgp.sendMsg.getText();
				writer.println("[MSGSD]" + temp);
				mgp.sendMsg.setText("");
			}

		} else if (e.getSource() == rg.regame) {
			setEnabled(true);
			System.out.println("시렁");
			// 다시 주사위화면으로 07.27
			setDice();

			rg.setVisible(false);
			writer.println("[REGAME]");

		} else if (e.getSource() == rg.out) {
			setEnabled(true);
			System.out.println("고러켄 안돼지");
			// System.exit(1); // 강제종료

			card.show(getContentPane(), "SERIV");
			sizeChange(415, 420);
			rg.setVisible(false);
			writer.println("[LEAVE]");
		}
	}

	public void sizeChange(int x, int y) {
		setSize(x, y);
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	public void setDice() {
		dg.player1.setText(myName);
		dg.player2.setText(rivalName);
		dg.movedice1.setIcon(new ImageIcon("image\\mdice01.gif"));
		dg.movedice2.setIcon(new ImageIcon("image\\mdice01.gif"));
		sizeChange(1000, 850);
		dg.gamesel.setVisible(false);
		dg.start.setVisible(false);
		card.show(getContentPane(), "DICE");
	}

	public static void main(String[] args) {
		Client cl = new Client();
		cl.connect();
		cl.start();
	}

	public void reset() {
		win = 0;
		draw = 0;
		lose = 0;
	}

}