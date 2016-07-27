package othello;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameControl {
	public static final int N = 8;
	public static final int SIZE = 64;
	public static int x = -1;
	public static int y = -1;
	public GameControl gc;	
	private Tile tile;
	private int[][] gameMap = new int[N][N];
	private int gap = 0;
	private int player1 = 0;
	private int player2 = 0;
	private int gameSet = 0;
	private int end = 0;

	public void gameStatus(Tile tile) {  //////////////////게임창을 초기화 상태로 만듬
		for(int i = 0; i < N ; i++){
			for(int j = 0; j < N ; j++){
				tile.changeStone(i, j, 0);
			}
		}
		
		tile.changeStone(3, 3, 1);
		tile.changeStone(4, 4, 1);
		tile.changeStone(3, 4, 2);
		tile.changeStone(4, 3, 2);

	}

	public int checkEnd() { // 현재 상황이 게임이 끝났는지 안끝났는지 단순 검사한다.
		gap = 0;
		player1 = 0;
		player2 = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (gameMap[i][j] == 0)
					gap++;
				if (gameMap[i][j] == 1)
					player1++;
				if (gameMap[i][j] == 2)
					player2++;
			}
		}
		if (gap == 0) { // 둘곳이 없을때
			if (player1 > player2) // P1이 많으면 P1의 승리
				return 1;
			else if (player1 < player2) // p2가 많으면 P2의 승리
				return 2;
			else
				return 3; // 같으면 비김
		}
		if (gap != 0 && player1 == 0) // 둘곳은 있으나 플레이어 1의 돌이 하나도 없으면 플레이어1의 패배
			return 2;
		else if (gap != 0 && player2 == 0) // 둘곳은 있으나 플레이어 2의 돌이 하나도 없으면 플레이어2의
											// 패배
			return 1;

		return 0; // 모든 경우에 해당이 안될경우 게임 진행
	}

	public boolean placeCheck(int player, int x, int y) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (((x + i >= 0 && x + i <= 7) && (y + j >= 0 && y + j <= 7))) {
					if (gameMap[i + x][j + y] != 0 && gameMap[x][y] == 0) {
						Point a = new Point(x + i, y + j);
						if (takeStoneCheck(player, a, false, i, j)) {
							return true;
						}

					}

				}
			}
		}
		return false;
	}

	// 비어있는 자리중에 플레이어가 둘 수 있는 곳이 있는지 체크
	public boolean allPlaceCheck(int player) {
		boolean check = false;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (gameMap[i][j] == 0 && placeCheck(player, i, j)) {
					check = true;
				}
			}
		}
		return check;
	}

	public boolean takeStoneCheck(int player, Point a, boolean check, int x, int y) {
		//////// 검사할 위치가 맵의 밖이면 검사하지 않음
		if ((a.x) < 0 || (a.x) > 7 || (a.y) < 0 || (a.y) > 7)
			return false;

		// 검사 위치가 빈칸이거나 현재 플레이어의 색과 같으면 검사하지 않음
		if (gameMap[a.x][a.y] == 0)
			return false;
		else if ((gameMap[a.x][a.y] != 0) && (gameMap[a.x][a.y] != player)) {
			a.x += x;
			a.y += y;
			if (!takeStoneCheck(player, a, true, x, y))
				return false;
		} else if (gameMap[a.x][a.y] == player) {
			if (check == false)
				return false;
		}

		return true;
	}

	public GameControl() {
		gameMap = new int[N][N];
		//tile = new Tile(this);
		//add(tile);		
	}

	public int getgameMap(int i, int j) {
		return gameMap[i][j];
	}

	public void setgameMap(int i, int j, int trun) {
		gameMap[i][j] = trun;
	}

	public void printMap() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				System.out.print(gameMap[i][j] + " ");
			System.out.println();
		}
		if (x != -1 && y != -1)
			System.out.println("플레이어" + gameMap[x][y] + "의 턴의 완료");
			
	}
}
