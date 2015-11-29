package server;

public class TimerThread extends Thread {

	static final int increaseTimeRate = 30;
	static final int decreaseTimeRate = 20; // unit : second
	static final String increaseTimeMsg = "EXT";
	static final String decreaseTimeMsg = "RDT";
	
	private int second;
	private String deathRattle;
	private int i = 0;
	
	/**
	 * 
	 * @param time
	 * @param deathRattle may must be TIME_M, TIME_E, TIME_V
	 */
	public TimerThread(int second, String deathRattle) {
		this.second = second;
		this.deathRattle = deathRattle;
	}
	
	@Override
	public void run() {
		System.out.println("TimerThread start");
		try { 
			for(; i<second; i++) {
				Thread.sleep(1000);
			}
		} catch(InterruptedException ie) { ie.printStackTrace(); }
		MafiaServer.broadcastMessage(deathRattle);
		System.out.println("TimerThread end");
	}
	
	public void increaseTime() {
		i -= 30;
		MafiaServer.broadcastMessage(increaseTimeMsg);
	}
	
	public void decreaseTime() {
		i += 20;
		MafiaServer.broadcastMessage(decreaseTimeMsg);
	}

	
}
