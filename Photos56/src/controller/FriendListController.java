/*
 * FriendsListController.java
 * 
 * Ronak Gandhi (rjg184)
 * Andrew Lowe (all157)
 * 
 */

package controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import app.Photos;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AccessibleUsersList;
import model.Album;
import model.ListOfAllUsers;
import model.User;
import javafx.scene.input.MouseEvent;

/**
 * This is the Friends List Controller that handles all functionality in the for the Friends List Page
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 * 
 */
public class FriendListController implements Initializable, Serializable{

	/**
	 * Serial ID to help with differing versions when serializing/deserializing
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * fxid for the listview of the list of friends
	 */
	@FXML
	private ListView<User> friends;

	/**
	 * fxid for the listview for the list of albums for the selected user
	 */
	@FXML
	private ListView<Album> friendAlbums;
	
	/**
	 * the observable list of the list of friend users
	 */
	public static ObservableList<User> friendList = FXCollections.observableArrayList();
	
	/**
	 * the observable list of the list of albums for the user
	 */
	public static ObservableList<Album> friendAlbumList = FXCollections.observableArrayList();
	
	/**
	 * file where we will serialize into
	 */
	public static final String userFile = "listOfUsers.txt";
	
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
	
	/*
	 * User d = user to be removed
	 * ArrayList<User> friends = friendlist
	 */
	/**
	 * Removes a user from the arraylist without deleting the actual user
	 * @param friends the Arraylist of friends
	 * @param d the user we want to omit from the list
	 * @return return the new list without user d
	 */
	public ArrayList<User> removeReferenceFromList(ArrayList<User> friends, User d) {
		ArrayList<User> newlist = new ArrayList<User>();
		for(User u : friends) {
			if(d.equals(u)) {
				//System.out.println("removing user albums: " + d.getAlbumsList());
				continue;
			}
			newlist.add(u);
		}
		return newlist;
	}
	
	/**
	 * Deletes a friend user from the arraylist
	 * @param event handles the button action
	 */
	public void deleteFriend(ActionEvent event) {
		ObservableList<User> users;
		users = friends.getSelectionModel().getSelectedItems();
		
		if(users.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Friend Selected");
			alert.setContentText("No friend selected.  Failed to delete friend.");
			alert.showAndWait();
			
			return;
		}
		
		Alert alert2 = new Alert(AlertType.CONFIRMATION);
		alert2.setTitle("Confirmation Dialog");
		alert2.setHeaderText("Confirm Action");
		alert2.setContentText("Are you sure?");
		Optional<ButtonType> result = alert2.showAndWait();
		
		if(result.get() == ButtonType.OK) {
			User selectedUser = (User) friends.getSelectionModel().getSelectedItem();
			
			ArrayList<User> resultlist = removeReferenceFromList(AccessibleUsersList.masterUserList.userLoggedIn.getFriendList(),selectedUser);
			AccessibleUsersList.masterUserList.userLoggedIn.setFriendList(resultlist);
			friendList = FXCollections.observableList(resultlist);
			friends.setItems(friendList);
		}else {
			return;
		}
		
		//friendAlbumList.clear(); <- ends up deleting the album list too. dont let this happen
		ArrayList<Album> emptylist = new ArrayList<Album>();
		friendAlbumList = FXCollections.observableList(emptylist);
		friendAlbums.setItems(friendAlbumList);
		
		try {
			serializeUsers(AccessibleUsersList.masterUserList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Displays the friend user's albums when the friend user is clicked in the list
	 * @param event handles the mouse click action
	 */
	public void clickOnFriend(MouseEvent event) {
		ObservableList<User> users;
		users = friends.getSelectionModel().getSelectedItems();
		
		if(users.isEmpty()) {
			return;
		}
		
		User selectedUser = (User) friends.getSelectionModel().getSelectedItem();
		
		ArrayList<Album> friendsalbums = selectedUser.getAlbumsList();
		friendAlbumList = FXCollections.observableList(friendsalbums);
		
		friendAlbums.setItems(friendAlbumList);
	}
	
	/**
	 * Goes the the Search Photos Page
	 * @param event handles the button action
	 */
	public void viewAlbum(ActionEvent event) {
		ObservableList<User> albums;
		albums = friends.getSelectionModel().getSelectedItems();
		if(albums.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Album Selected");
			alert.setContentText("No album selected.  Please select a friend and album to view album.");
			alert.showAndWait();
			
			return;
		}
		
		Album selectedAlbum = (Album) friendAlbums.getSelectionModel().getSelectedItems();
		
		
	}
	
	/**
	 * Goes to the User Page
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void goToUserPage(ActionEvent event) throws IOException { //this is going to close the current window and open a new window
		//this should also display the User Page
		Parent user_page = FXMLLoader.load(getClass().getResource("/view/User_Page.fxml"));
		Scene userpage_scene = new Scene(user_page);
		Stage app_stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		app_stage.setScene(userpage_scene);
		app_stage.setTitle("User Page");
		app_stage.show();
	}
	
	/**
	 * Goes to the Friend Photos Page
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void goToFriendPhotosPage(ActionEvent event) throws IOException { //this is going to close the current window and open a new window
		//this should also display the Friend Photo list
		
		//check that there is a selected user and a selected album
		ObservableList<User> users;
		users = friends.getSelectionModel().getSelectedItems();
		if(users.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Friend Selected");
			alert.setContentText("No friend selected.  Please select a friend.");
			alert.showAndWait();
			
			return;
		}
		ObservableList<Album> friendalbs;
		friendalbs = friendAlbums.getSelectionModel().getSelectedItems();
		if(friendalbs.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Album Selected");
			alert.setContentText("No album selected.  Please select an album.");
			alert.showAndWait();
			
			return;
		}
		
		//first check that both the owner of the album you're trying to open ALSO has YOU in HIS friends list
		User selectedUser = (User) friends.getSelectionModel().getSelectedItem();
		User you = AccessibleUsersList.masterUserList.userLoggedIn;
		if(selectedUser.isInFriendsList(you) == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Not Friends With User");
			alert.setContentText("Other User does not have you in their friends list.  Access to view their photos denied.");
			alert.showAndWait();
			
			return;
		}
		
		Album selAlbum = (Album) friendAlbums.getSelectionModel().getSelectedItem();
		AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum = selAlbum;
		
		Parent user_page = FXMLLoader.load(getClass().getResource("/view/FriendPhotos_Page.fxml"));
		Scene userpage_scene = new Scene(user_page);
		Stage app_stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		app_stage.setScene(userpage_scene);
		app_stage.setTitle("Friend Photos");
		app_stage.show();
	}
	
	/**
	 * Open up the Add Friend window
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void goToAddFriendPage(ActionEvent event) throws IOException { //this is going to close the current window and open a new window
		//this should open up the addFriend window
		Stage mainStage = new Stage(); 
       	
	    FXMLLoader loader = new FXMLLoader(); 
	    loader.setController(new AddNewFriendController());
		Parent root = loader.load(Photos.class.getResource("/view/AddNewFriend.fxml"));
	    	
		AddNewFriendController addNewAlbumController = new AddNewFriendController(); 
			
	    Scene scene = new Scene(root); // tells app to load the fxml file
	    mainStage.setScene(scene);
		mainStage.setResizable(false);
	    mainStage.setTitle("New Friend");
		    
	    mainStage.initModality(Modality.APPLICATION_MODAL);
		mainStage.showAndWait();
		
		ArrayList<User> listfriends = AccessibleUsersList.masterUserList.userLoggedIn.getFriendList();
		AccessibleUsersList.masterUserList.userLoggedIn.setFriendList(listfriends);
		friendList = FXCollections.observableList(listfriends);
		friends.setItems(friendList);
		
		try {
			serializeUsers(AccessibleUsersList.masterUserList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * should grab friends list and update the display to show the current friends list
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ArrayList<User> listfriends = AccessibleUsersList.masterUserList.userLoggedIn.getFriendList();
		
		//deletes any users that were deleted from the master user list
		for(User u: listfriends) {
			if(AccessibleUsersList.masterUserList.searchUser(u) == false) { //user not found in the master array list -> means it was deleted
				listfriends.remove(u);
			}
		}
		
		//System.out.println(AccessibleUsersList.masterUserList.userLoggedIn.getFriendList());
		
		friendList = FXCollections.observableList(listfriends);
		friends.setItems(friendList);
	}

}
