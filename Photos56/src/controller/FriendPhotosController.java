/*
 * FriendPhotosController.java
 * 
 * Ronak Gandhi (rjg184)
 * Andrew Lowe (all157)
 * 
 */

package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.AccessibleUsersList;
import model.Album;
import model.Photo;

/**
 * This is the Friend Photos Controller that handles all functionality in the for the Friends Photos Page
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 * 
 */
public class FriendPhotosController implements Initializable, Serializable{

	/**
	 * Serial ID to help with differing versions when serializing/deserializing
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * fxid for the photo list that will display the friend album's photos
	 */
	@FXML
	private ListView<ImageView> photolistDisplay;
	
	/**
	 * observable list for the friend's photos
	 */
	public static ObservableList<ImageView> photolist = FXCollections.observableArrayList();
	
	/*
	 * back button
	 */
	/**
	 * Goes to the Friend List Page Window
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void goToFriendListPage(ActionEvent event) throws IOException { //this is going to close the current window and open a new window
		//this should also display the User Page
		Parent user_page = FXMLLoader.load(getClass().getResource("/view/FriendList_Page.fxml"));
		Scene userpage_scene = new Scene(user_page);
		Stage app_stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		app_stage.setScene(userpage_scene);
		app_stage.setTitle("Friend List");
		app_stage.show();
	}
	
	/*
	 * should grab photos list and set the display
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		photolist.clear();
		photolist = FXCollections.observableArrayList();
		Album currAlbum = AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum;
		ArrayList<Photo> phots = currAlbum.getAlbum();
		
		ArrayList<ImageView> resultImages = new ArrayList<ImageView>();
		for(Photo p : phots) {
			String path = p.getPathToPhoto();
			FileInputStream inputstream = null;
			try {
				inputstream = new FileInputStream(path);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			Image image = new Image(inputstream);
			ImageView pic = new ImageView();
			pic.setFitWidth(380);
			pic.setFitHeight(180);
			pic.setImage(image);
			
			photolist.add(pic);
		}
		
		photolistDisplay.setItems(photolist);
	}

}
