package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import com.sun.glass.ui.Accessible.EventHandler;

/**
 * 
 */

/**
 * <pre>
 * 
 *   │_ MainController
 *
 * 1. 개요 :
 * 2. 작성일 : 2015. 11. 26.
 * </pre>
 * 
 * @author		: 이상빈
 * @version		: 1.0
 */
public class MainController implements Initializable{
	@FXML private TextField txtMessage;
	@FXML private ListView<String> listview;
	@FXML private TextArea chatArea;
	@FXML private Button sendMssBtn;
	
	private Socket soc;
	private BufferedReader br= null;
	private BufferedReader br2 = null;
    private BufferedWriter bw= null;
    
    public void handleSendMessage(ActionEvent event) {

    	
    	
    }

    private <TestField> boolean isEmpty(TestField txt) {
    	if(txt == null || "".equals(((String) txt).trim())) return true;			
    	return false;
    }
    
    public void handleCloseAction(ActionEvent event) {
		Platform.exit();
	}
	
    public void handleEnterPressed(KeyEvent evt) {
    	sendMssBtn.setOnAction((event) -> {
			PrintWriter pw;
			try {
				pw = new PrintWriter(new OutputStreamWriter(soc.getOutputStream(), "utf-8"));
				pw.println("CHAT\n" + txtMessage.getText());
				if(isEmpty(txtMessage))
				pw.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			txtMessage.clear();
		});
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			soc = new Socket(InetAddress.getLocalHost(), 12345);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		sendMssBtn.setOnAction((event) -> {
			PrintWriter pw;
			try {
				pw = new PrintWriter(new OutputStreamWriter(soc.getOutputStream(), "utf-8"));
				pw.println("CHAT\n" + txtMessage.getText());
				if(isEmpty(txtMessage))
				pw.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			txtMessage.clear();
		});

		Thread thread = new Thread(new Runnable()	{

			@Override
			public void run() {
				int red = -1;
				byte[] buffer = new byte[5*1024]; // a read buffer of 5KiB
				byte[] redData;	
				String redDataText = "";
				try {
					while ((red = soc.getInputStream().read(buffer)) > -1) {
					    redData = new byte[red];
					    System.arraycopy(buffer, 0, redData, 0, red);
					    redDataText = new String(redData,"UTF-8"); // assumption that client sends data UTF-8 encoded
					    // redDataText
					    chatArea.appendText(redDataText);
				}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				

			}
			
		});
		
		thread.start();


		// Thread 두개 만듬
		// 1. input - 
		
		// 2. output
	}
}
