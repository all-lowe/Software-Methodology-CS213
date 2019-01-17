/*
 * LoginController.java
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
//import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ListOfAllUsers;
import model.User;
import model.AccessibleUsersList;

/**
 * This is the Login Controller that handles all functionality in the for the Login Page
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 * 
 */
public class LoginController implements Initializable, Serializable{
	
	/**
	 * Serial ID to help with differing versions when serializing/deserializing
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * fxid for the textfield of the username
	 */
	@FXML
	public TextField user;
	
	/**
	 * file where we will serialize into
	 */
	public static final String userFile = "listOfUsers.txt";
	//public static final String stockFile = "stockUser.txt";
	
	//Data Structures
	//ListOfAllUsers masterUserList = new ListOfAllUsers();
	
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
	 * Deserializes or loads the data from the textfile onto the master list
	 * @return returns the list filled with the data from the text file
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static ListOfAllUsers deserializeUsers() throws ClassNotFoundException, IOException {
		
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(userFile));
		} catch (IOException e) {
			//System.out.println("No current list of users.");  //*****REMOVE THIS LATER******
			return null;
		}
		//ObservableList<User> users = (ObservableList<User>) ois.readObject();
		ListOfAllUsers list = (ListOfAllUsers) ois.readObject(); 
		return list;
	}
	
	/**
	 * Handles the button when we try to log in and redirects which window to go to based off the input
	 * @param event handles the login button
	 * @throws IOException
	 */
	public void pressLogin(ActionEvent event) throws IOException {
		//System.out.println("All users: " + AccessibleUsersList.masterUserList.users);
		//System.out.println(AccessibleUsersList.masterUserList.users.get(0).getUsername());
		String nameOfUser = user.getText();
		//System.out.println("Name of user: " + nameOfUser);
		//System.out.println(AccessibleUsersList.masterUserList.searchUser(nameOfUser));
		nameOfUser = nameOfUser.trim();
		
		if(nameOfUser.equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Missing Field(s)");
			alert.setContentText("Username field blank. Must enter a username");
			alert.showAndWait();
			return;
		}
		
		//System.out.println(AccessibleUsersList.masterUserList.searchUser(nameOfUser));
		
		if(nameOfUser.toLowerCase().equals("admin")) {
			goToAdminPage(event);
		}else if(nameOfUser.toLowerCase().equals("stock")){
			goToStockUserPage(event);
		}else if(AccessibleUsersList.masterUserList.searchUser(nameOfUser)) {
			User user = new User(nameOfUser);
			user = AccessibleUsersList.masterUserList.findUser(nameOfUser);
			AccessibleUsersList.masterUserList.userLoggedIn = user;
			goToUserPage(event);
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Nonexistent User!");
			alert.setContentText("User does not exist.");
			alert.showAndWait();
			
			user.setText("");
			return;
		}
	}
	
	/**
	 * Goes to the Admin Page Window
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void goToAdminPage(ActionEvent event) throws IOException { //this is going to close the current window and open a new window
		//this should also display the Admin Page
		Parent user_page = FXMLLoader.load(getClass().getResource("/view/Admin_Page.fxml"));
		Scene userpage_scene = new Scene(user_page);
		Stage app_stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		app_stage.setScene(userpage_scene);
		app_stage.setTitle("Admin Page");
		app_stage.show();
	}
	
	/**
	 * Goes to the User Page Window for stock user
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void goToStockUserPage(ActionEvent event) throws IOException { //this is going to close the current window and open a new window
		//this should also display the Admin Page
		
		if(AccessibleUsersList.masterUserList.stockUser == null) { //stock user doesnt exist -> first time opening app
			//System.out.println("initialized stock!"); //sets up stock only if first time logging into stock photos
			AccessibleUsersList.masterUserList.setupStock();
			//AccessibleUsersList.masterUserList.userLoggedIn = AccessibleUsersList.masterUserList.stockUser;
			
			try {
				serializeUsers(AccessibleUsersList.masterUserList);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		AccessibleUsersList.masterUserList.userLoggedIn = AccessibleUsersList.masterUserList.stockUser;
		//System.out.println(AccessibleUsersList.masterUserList.stockUser.getUsername());
		
		Parent album_page = FXMLLoader.load(getClass().getResource("/view/User_Page.fxml"));
		Scene albumpage_scene = new Scene(album_page);
		Stage app_stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		app_stage.setScene(albumpage_scene);
		app_stage.setTitle("User Page");
		app_stage.show();
	}
	
	/**
	 * Goes to the User Page Window for all users
	 * @param event hanldes the button action
	 * @throws IOException
	 */
	@FXML
	public void goToUserPage(ActionEvent event) throws IOException { //this is going to close the current window and open a new window
		//this should also display the User Page
		
		Parent user_page = FXMLLoader.load(getClass().getResource("/view/User_Page.fxml"));
		Scene userpage_scene = new Scene(user_page);
		Stage app_stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		app_stage.setScene(userpage_scene);
		app_stage.setTitle("User Page");
		app_stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			ListOfAllUsers loadedUsers = deserializeUsers();
			if(loadedUsers != null) {
				AccessibleUsersList.masterUserList = loadedUsers;
			}
			
			//System.out.println(AccessibleUsersList.masterUserList.getUsers());
		} catch (ClassNotFoundException | IOException e) {
			System.out.println(e.getMessage());
		}	
		//System.out.print(masterUserList.users);
	}
	
}
