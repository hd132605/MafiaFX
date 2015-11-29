package server;
import java.util.List;

public class UinfoControl {
	
	static final String SHOT = "DIE";

	public static void ShotPlayer(List<Uinfo> list, int player) {
		list.get(player-1).setLive(false);
		MafiaServer.broadcastMessage(SHOT + "\n" + player);
	}
	
}
