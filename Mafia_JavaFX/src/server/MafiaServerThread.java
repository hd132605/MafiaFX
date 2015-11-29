package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class MafiaServerThread extends Thread {
	
	private Socket soc;
	private int count;

	public MafiaServerThread(Socket soc, int count) {
		this.soc = soc;
		this.count = count;
	}

	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(soc.getInputStream(), "utf-8"));
			String msg;
			int player;
			while(true) {
				msg = br.readLine();
				System.out.println("[Received Message] " + msg);
				switch(msg) {
				case "EXT":
					MafiaServer.broadcastMessage("EXT");
					((TimerThread)(MafiaServer.getTimerThread())).increaseTime();
					break;
				case "RDT":
					MafiaServer.broadcastMessage("RDT");
					((TimerThread)(MafiaServer.getTimerThread())).decreaseTime();
					break;
				case "CHAT":
					msg = br.readLine();
					msg = "플레이어" + (count+1) + " : " + msg;
					System.out.println("[Received CHAT] " + msg);
					MafiaServer.broadcastMessage(msg);
					break;
				case "SHOT":
					msg = br.readLine();
					player = Integer.parseInt(msg);
					UinfoControl.ShotPlayer(MafiaServer.getUserList(), player);
					break;
				case "VOTE":
					msg = br.readLine();
					player = Integer.parseInt(msg);
					Vote.increasePlayer(player);
				}
			}
		} catch(SocketException se) {
			System.out.println("소켓 닫힘");
		}
		catch(IOException ie) { ie.printStackTrace(); }
		
		
	}

}
