package server;
import java.util.List;

public class Vote {
	
	private static final String PLAYERDEATH = "DIE";
	
	private static int[] arr = new int[9]; // ex) arr[1] : player1's container
	
	public static void initialize() {
		for(int i = 1; i<9; i++) {
			arr[i] = 0;
		}
	}
	
	public static int biggestPlayer() {
		int player = 0;
		int value = 0;
		int cnt = 0;
		for(int i = 1; i<9; i++) {
			if(value < arr[i]) {
				value = arr[i];
				player = i;
			}
		}
		for(int i = 1; i<9; i++) {
			if(value == arr[i]) cnt++;
		}
		if(cnt >= 2) player = 0; // if player who have same value is more than 1, set player to 0.(nothing happens flag)
		return player;
	}
	
	public static synchronized void increasePlayer(int player) {
		arr[player]++;
	}
	
	public static void check(List<Uinfo> list) {
		int biggestPlayer = Vote.biggestPlayer();
		if(biggestPlayer != 0) {
			MafiaServer.broadcastMessage(PLAYERDEATH + "\n" + biggestPlayer); // if no one votes, biggestPlayer() equals 0.			
			list.get(biggestPlayer-1).setLive(false);
		}
	}
}
