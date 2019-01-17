/*
 * AdminController.java
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.AccessibleUsersList;
import model.ListOfAllUsers;
import model.User;

/**
 * This is the Admin Controller that handles all functionality in the for the Admin Page
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 * 
 */

public class AdminController implements Serializable, Initializable {
	
	/**
	 * Serial ID to help with differing versions when serializing/deserializing
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * fxid for the list of users
	 */
	@FXML
	private ListView<User> listusers;
	
	/**
	 * fxid for the adding username textfield
	 */
	@FXML
	private TextField addUsername;
	
	/**
	 * file where we will serialize into
	 */
	public static final String userFile = "listOfUsers.txt";
	
	//Data Structures
	/**
	 * Observable list so we can display the users
	 */
	ObservableList<User> listOfUsers = FXCollections.observableArrayList();
	
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
	public static ObservableList<User> deserializeUsers() throws ClassNotFoundException, IOException {
		
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(userFile));
		} catch (IOException e) {
			System.out.println("No current list of users.");  //*****REMOVE THIS LATER******
			return null;
		}
		//ObservableList<User> users = (ObservableList<User>) ois.readObject();
		ArrayList<User> list = (ArrayList<User>) ois.readObject(); 
		ObservableList<User> users = FXCollections.observableList(list);
		return users;
	}
	*/
	
	/**
	 * Checks if a user already exists
	 * @param username the username of the user we want to add
	 * @return returns true if user already exists, returns false if otherwise
	 */
	public boolean checkUserExists(String username) {
		
		for(User user : AccessibleUsersList.masterUserList.users) {
			if(user.getUsername().equalsIgnoreCase(username)) {
				return true; //user already exists
			}
		}
		
		return false; //user doesn't exist
	}
	
	/*
	 * adds a user to the list of users
	 */
	/**
	 * Adds a user into the master list
	 * @param event handles the buttion action
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void addUser(ActionEvent event) throws FileNotFoundException, IOException {
		String username = addUsername.getText();
		username = username.trim();
		
		if(username.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Missing Field(s)");
			alert.setContentText("Username Field(s) blank.  Field must be filled");
			alert.showAndWait();
			
			addUsername.setText("");
			
			return;
		}
		if(checkUserExists(username) == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Duplicate Entry");
			alert.setContentText("Username already exists!");
			alert.showAndWait();
			
			addUsername.setText("");
			
			return;
		}
		
		User newuser = new User(username);
		AccessibleUsersList.masterUserList.addUser(newuser);
		//System.out.println("Current list of users: " + AccessibleUsersList.masterUserList.getUsers());
		
		printUsers();
		addUsername.setText("");
		
		//updates permanent list(Serialized List) of users
		serializeUsers(AccessibleUsersList.masterUserList);
	}
	
	/*
	 * deletes the user from the list of users
	 */
	/**
	 * Deletes the user from the list of users
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void deleteUser(ActionEvent event) throws IOException{
		ObservableList<User> names;
		names = listusers.getSelectionModel().getSelectedItems();
		for(User name: names) {
			//delUsername.setText(name.getUsername());
			//this is a confirmation
			Alert alert2 = new Alert(AlertType.CONFIRMATION);
			alert2.setTitle("Confirmation Dialog");
			alert2.setHeaderText("Confirm Action");
			alert2.setContentText("Are you sure?");
			Optional<ButtonType> result = alert2.showAndWait();
			
			if(result.get() == ButtonType.OK) {
				
				listOfUsers.remove(name);
				AccessibleUsersList.masterUserList.removeUser(name);
				
			}else {
				return;
			}
		}
		printUsers();

		
		
		//updates permanent list(Serialized List) of users
		try {
			serializeUsers(AccessibleUsersList.masterUserList);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * prints the current list of users onto the ListView (table on the left)
	 */
	/**
	 * Prints the updated list of users in the ListView
	 */
	public void printUsers() {
		if(AccessibleUsersList.masterUserList.getUsers() != null) {
			listOfUsers = FXCollections.observableList(AccessibleUsersList.masterUserList.getUsers());
			listusers.setItems(listOfUsers);
		}
	}
	
	
	/**
	 * Goes to the login page window
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void goToLoginPage(ActionEvent event) throws IOException{
		//this should return to the Login Page
		Parent user_page = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
		Scene userpage_scene = new Scene(user_page);
		Stage app_stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		app_stage.setScene(userpage_scene);
		app_stage.setTitle("Login Page");
		app_stage.show();
	}
	
	/**
	 * Closes the application
	 * @param event handles the button action
	 */
	public void closeApp(ActionEvent event) {
		System.exit(0);
	}

	//will deserialize and update listOfUsers;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//ObservableList<User> loadUsers = FXCollections.observableList(AccessibleUsersList.masterUserList.users);
		//listOfUsers = loadUsers;
		printUsers();
	}
}
