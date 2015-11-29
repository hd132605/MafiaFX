package client;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 */

/**
 * <pre>
 * 
 *   │_ Main
 *
 * 1. 개요 :
 * 2. 작성일 : 2015. 11. 26.
 * </pre>
 * 
 * @author		: 이상빈
 * @version		: 1.0
 */
public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		
		stage.setScene(new Scene(root));
		stage.setTitle("아이돌 그룹");
		stage.centerOnScreen();
		stage.setResizable(true);
		
		stage.show();	
	}

}
