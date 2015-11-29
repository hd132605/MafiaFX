package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MafiaServer {

	private static final String DAYTIME = "TIME_M";
	private static final String VOTETIME = "TIME_V";
	private static final String TWILIGHTTIME = "TIME_E";
	
	private static final int PHASE_TWILIGHT = 0;
	private static final int PHASE_DAY = 1;
	private static final int PHASE_VOTE = 2;
	static final int DAY_DUR = 10;
	static final int VOTE_DUR = 20;
	static final int TWI_DUR = 10;
	static final int PLAYABLEMEMNUM = 8;
	
	private static Thread timer;
	private static List<Uinfo> list = new ArrayList<>();
	private static int CurrentPhase;
	
	private static boolean flag = true;
	
	private static ServerSocket ssoc;
	
	public static void main(String[] args) {
		while(true) {
			Socket soc;
			int count = 0;
			int date = 1;
			
			/*********** accept client ***********/
			try {
				ssoc = new ServerSocket(12345);
				System.out.println("server ready..");
				while((soc = ssoc.accept()) != null) {
					System.out.println("누군가 접속! " + (count+1) + "명");
					Thread newThread = new Thread(new MafiaServerThread(soc, count));
					newThread.start();
					Uinfo newUinfo = new Uinfo(newThread, soc, ++count);
					newUinfo.sendMessage("PLAYERNUM\n" + count);
					list.add(newUinfo);
					if(count == PLAYABLEMEMNUM) {
						gamestart(list);
						break;
					}
				}
			} catch(SocketException se) {
				se.printStackTrace();
				System.exit(-1);
			} catch(IOException ie) {
				ie.printStackTrace();
				System.exit(-1);
			}
			
			/*********** twilight - daytime - vote loop ***********/
			while(flag) {
				if(!flag) break;
				
				CurrentPhase = 0;
				timer = new TimerThread(TWI_DUR, DAYTIME);
				timer.start(); // twilight start
				try { timer.join(); } catch(InterruptedException ie) { }
				if(!Victory.check(list)) {
					System.out.println("first initialize block");
					Vote.initialize();
					initializeMain();
					flag = false;
					continue;
				};
				
				CurrentPhase = 1;
				timer = new TimerThread(DAY_DUR, VOTETIME);
				timer.start(); // daytime start
				try { timer.join(); } catch(InterruptedException ie) { }
				
				CurrentPhase = 2;
				timer = new TimerThread(VOTE_DUR, TWILIGHTTIME);			
				timer.start(); // votetime start
				try { timer.join(); } catch(InterruptedException ie) { }
				Vote.check(list);
				if(!Victory.check(list)) {
					System.out.println("initialize block");
					Vote.initialize();
					initializeMain();
					flag = false;
					continue;
				};
				
			}
			flag = true;
		}
	}

	/**
	 * when connected user number becomes 8, this method executed.
	 * broadcast gamestart message and vocation information
	 * to all user.
	 * @param list Userinfo list
	 */
	private static void gamestart(List<Uinfo> list) {
		Random rand = new Random();
		int num1 = rand.nextInt(8);
		int num2;
		while((num2 = rand.nextInt(8)) == num1);
		for(int i = 0; i<8; i++) {
			String tmp;
			if(i == num1 || i == num2) tmp = "M";
			else tmp = "C";
			list.get(i).setVocation(tmp);
			list.get(i).sendMessage("GAMESTART\n" + tmp);
		}
	}
	
	public synchronized static void broadcastMessage(String msg) {
		for(Uinfo user : list) {
			user.sendMessage(msg);
		}
	}
	
	private static void initializeMain() {
		flag = false;
		for(Uinfo u : list) {
			try { u.getSocket().close(); } catch(IOException ie) { ie.printStackTrace(); }
			System.out.println("close socket");
		}
		list = new ArrayList<>();
		CurrentPhase = 0;
		try { ssoc.close(); } catch(IOException ie) { }
	}
	
	public static int getCurrentPhase() {
		return CurrentPhase;
	}
	
	public static Thread getTimerThread() {
		return timer;
	}
	
	public static List<Uinfo> getUserList() {
		return list;
	}
}
