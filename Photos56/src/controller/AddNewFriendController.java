/*
 * AddNewFriendController.java
 * 
 * Ronak Gandhi (rjg184)
 * Andrew Lowe (all157)
 * 
 */

package controller;

import java.io.IOException;
import java.io.Serializable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.AccessibleUsersList;
import model.User;

/**
 * This is the Add New Friend Controller that handles all functionality in the for the Add New Friend Page
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 * 
 */
public class AddNewFriendController implements Serializable{
	/*
	 * should add the new friend into the master list then serialize the list afterwards
	 */
	
	/**
	 * Serial ID to help with differing versions when serializing/deserializing
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * fxid for the textfield of new friend username
	 */
	@FXML
	private TextField newUser;
	
	/**
	 * fxid for the label of the new friend username
	 */
	@FXML
	private Label username;
	
	/**
	 * fxid for the label of the title of the window
	 */
	@FXML
	private Label title;
	
	/**
	 * fxid for the cancel button to cancel adding a new friend
	 */
	@FXML
	private Button cancel;
	
	/**
	 * fxid for the add button to add the new friend
	 */
	@FXML
	private Button add;
	
	//friend already exists
	/**
	 * Checks if there are duplicate friends in the current friend list already
	 * @param username the username we are checking to see if it exists
	 * @return returns true if the username was already found in the list, false otherwise
	 */
	public boolean checkDuplicateFriend(String username) {
		for(User u : AccessibleUsersList.masterUserList.userLoggedIn.getFriendList()) {
			if(u.getUsername().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a friend to the user friend list
	 * @param event handles the button action
	 */
	public void addFriend(ActionEvent event) {
		//check the current list of users for the name, if the name does not exist, close and return error;
		String friendname = newUser.getText();
		if(checkDuplicateFriend(friendname) == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Duplicate User!");
			alert.setContentText("User already exists in your friend list.");
			alert.showAndWait();
		
			newUser.setText("");
			return;
		}
		String currname = AccessibleUsersList.masterUserList.userLoggedIn.getUsername();
		if(currname.equalsIgnoreCase(friendname)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Cannot Add Self!");
			alert.setContentText("Cannot add yourself to your own friends list, please enter a valid user.");
			alert.showAndWait();
		
			newUser.setText("");
			return;
		}
		
		User user = AccessibleUsersList.masterUserList.findUser(friendname);
		if(user == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Nonexistent User!");
			alert.setContentText("User does not exist.");
			alert.showAndWait();
		
			newUser.setText("");
			return;
		}
		
		AccessibleUsersList.masterUserList.userLoggedIn.addFriend(user);
		
		Stage stage = (Stage) cancel.getScene().getWindow();
		stage.close();
	}
	
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
	
	/**
	 * Closes the application
	 * @param event handles the button action
	 */
	public void closeWindow(ActionEvent event) {
		Stage stage = (Stage) cancel.getScene().getWindow();
		stage.close();
	}
}
