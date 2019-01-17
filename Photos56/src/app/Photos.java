/*
 * Photos.java
 * 
 * Ronak Gandhi (rjg184)
 * Andrew Lowe (all157)
 * 
 */

package app;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


/**
 * This is the Main Photo Class that will run the application.
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 * 
 */
public class Photos extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		/*
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		*/
		
		Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Photos Login Page");
		primaryStage.show();
	}
	
	/**
	 * The Main Method of the Application
	 * @param args list of arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
