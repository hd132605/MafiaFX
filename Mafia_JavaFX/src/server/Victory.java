package server;
import java.util.List;

public class Victory {
	
	static final String CIVVICTORY = "ENDGAME\nC";
	static final String MAFVICTORY = "ENDGAME\nM";
	
	/**
	 * @param list
	 * @return false when game is over
	 */
	public static boolean check(List<Uinfo> list) {
		int liveMafia = 0;
		int liveCivilian = 0;
		
		for(int i = 0; i<8; i++) {
			if(list.get(i).isLive()) {
				if("C".equals(list.get(i).getVocation())) liveCivilian++;
				else if("M".equals(list.get(i).getVocation())) liveMafia++;
				else {
					System.out.println("invalid value : Victory");
					System.exit(-1);
				}
			}
		}
	
		if(liveMafia == 0) {
			CivVictory(); // including server initialize
			return false;
		} else if(liveMafia >= liveCivilian) {
			MafVictory(); // including server initialize
			return false;
		}
		return true;
	}
	
	private static void CivVictory() {
		MafiaServer.broadcastMessage(CIVVICTORY);
	}
	
	private static void MafVictory() {
		MafiaServer.broadcastMessage(MAFVICTORY);
	}
}
	
