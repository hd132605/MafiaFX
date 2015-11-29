package server;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Uinfo {
	private String vocation; // 'M' is mafia, 'C' is civilian.
	private boolean isLive = true;
	private Thread thread;
	private int order;
	private Socket soc;
	private PrintWriter pw;
	
	public Uinfo(Thread thread, Socket soc, int order) {
		this.thread = thread;
		this.order = order;
		this.soc = soc;
		try {
			pw = new PrintWriter(new OutputStreamWriter(soc.getOutputStream(), "utf-8"));
		} catch(IOException ie) {
			ie.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void sendMessage(String msg) {
		pw.println(msg);
		pw.flush();
	}
	
	/***************getters and setters**************************/

	public String getVocation() {
		return vocation;
	}

	public void setVocation(String vocation) {
		if("M".equals(vocation) || "C".equals(vocation)) {
			this.vocation = vocation;
		} else {
			System.out.println("invalid value to setVocation");
			System.exit(-1);
		}
		
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
	
	public Thread getThread() {
		return thread;
	}
	
	public Socket getSocket() {
		return soc;
	}
}
