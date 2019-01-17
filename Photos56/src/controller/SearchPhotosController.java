/*
 * SearchPhotosController.java
 * 
 * Ronak Gandhi (rjg184)
 * Andrew Lowe (all157)
 * 
 */

package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.AccessibleUsersList;
import model.Album;
import model.ListOfAllUsers;
import model.Photo;
import model.Tag;
import model.User;

/**
 * This is the Search Photo Controller that handles all functionality in the for the Search Photo Page
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 * 
 */
public class SearchPhotosController implements Initializable, Serializable{
	
	/**
	 * Serial ID to help with differing versions when serializing/deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * fxid for the listview of search result photos
	 */
	@FXML
	ListView<ImageView> DisplayedPhotos;
	
	/**
	 * fxid for the the textfield for the new album name
	 */
	@FXML
	private TextField newAlbumName;
	
	/**
	 * file where we will serialize into
	 */
	public static final String userFile = "listOfUsers.txt";
	
	/**
	 * observable list that will have the list of search result photos
	 */
	public static ObservableList<ImageView> foundPhotos = FXCollections.observableArrayList();
	
	/**
	 * Serialize function to store data in a textfile
	 * @param list the list we are serializing
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void serializeUsers(ListOfAllUsers list) throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFile));
		oos.writeObject(list);
		oos.close();
		//System.out.println("Serialized.");
	}
	
	/**
	 * Makes an album from the search result photos
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void makeAlbum(ActionEvent event) throws IOException {
		User currUser = AccessibleUsersList.masterUserList.userLoggedIn;
		ArrayList<Album> usersAlbumList = currUser.getAlbumsList();
		
		String album_name = newAlbumName.getText();
		if(album_name.equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Album Name");
			alert.setContentText("No album name input.  Create Album Failed!");
			alert.showAndWait();
			
			return;
		}
		
		if(currUser.checkAlbumName(album_name) == true) { //then duplicate album name entry
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error!");
			alert.setHeaderText("Duplicate Field");
			alert.setContentText("Album with name already exists!  Name must be unique!");
			alert.showAndWait();
			
			newAlbumName.setText("");
			
			return;
		}
		
		Album album = new Album(album_name);
		
		ArrayList<Photo> photolistToBeAdded = new ArrayList<Photo>();
		for(Photo p : AccessibleUsersList.masterUserList.userLoggedIn.searchListPhotos) {
			String path = p.getPathToPhoto();
			long date = p.getlastModifiedDate();
			ArrayList<Tag> tags = p.getCopyOfTags();
			String cap = p.getCaption();
			Photo copied_photo = new Photo(path, date);
			copied_photo.setCaption(cap);
			copied_photo.setTags(tags);
			copied_photo.setImage(p.getImage());
			
			photolistToBeAdded.add(copied_photo);
		}
		
		album.setAlbum(AccessibleUsersList.masterUserList.userLoggedIn.searchListPhotos);
		
		usersAlbumList.add(album);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Album Created");
		alert.setContentText("New Album "+ album.getName() + " created.");
		alert.showAndWait();
		
		try {
			serializeUsers(AccessibleUsersList.masterUserList);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		goToStockUserPage(event);
	}
	
	
	/**
	 * Goes to the User Page
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void goToStockUserPage(ActionEvent event) throws IOException { //this is going to close the current window and open a new window
		//this should also display the Admin Page
		
		Parent album_page = FXMLLoader.load(getClass().getResource("/view/User_Page.fxml"));
		Scene albumpage_scene = new Scene(album_page);
		Stage app_stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		app_stage.setScene(albumpage_scene);
		app_stage.setTitle("User Page");
		app_stage.show();
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//prints out the searched list of photos
		foundPhotos.clear();
		foundPhotos = FXCollections.observableArrayList();
		ArrayList<Photo> resultPhotos = new ArrayList<Photo>();
		resultPhotos = AccessibleUsersList.masterUserList.userLoggedIn.searchListPhotos;
		//System.out.println("Photos in SearchPhotoController: " + resultPhotos);
		ArrayList<ImageView> resultImages = new ArrayList<ImageView>();
		
		for(Photo p : resultPhotos) {
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
			
			foundPhotos.add(pic);
		}
		
		DisplayedPhotos.setItems(foundPhotos);
		
	}
}
