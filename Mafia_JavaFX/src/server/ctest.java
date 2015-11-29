package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ctest {
	
	private static Socket soc;
	
	public static void main(String[] args) {
		
		try {
			soc = new Socket("127.0.0.1", 12345);
			
			new Thread(() -> {
				try {
					BufferedReader br;
					br = new BufferedReader(new InputStreamReader(soc.getInputStream(), "utf-8"));
					while(true){
						String str = br.readLine();
						if(str == null) System.exit(0);
						System.out.println(str);
					}
				} catch(IOException ie) { ie.printStackTrace(); }
			}).start();
			
			Scanner scanner = new Scanner(System.in);
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(soc.getOutputStream(), "utf-8"));
			while(true) {
				String str = scanner.nextLine();
				pw.println(str);
				pw.flush();
			}
		} catch(IOException ie) { ie.printStackTrace(); }

	}

}
