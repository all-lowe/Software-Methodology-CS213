/*
 * UserController.java
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.AccessibleUsersList;
import model.Album;
import model.ListOfAllUsers;
import model.Photo;
import model.Tag;
import model.User;
import javafx.scene.input.MouseEvent;

/**
 * This is the User Controller that handles all functionality in the for the User Page
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 * 
 */
public class UserController implements Serializable, Initializable {
	
	/**
	 * Serial ID to help with differing versions when serializing/deserializing
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * fxid for the button to go to friends list
	 */
	@FXML
	private Button FriendsListButton;
	
	/**
	 * fxid for the textfield of tag name search
	 */
	@FXML
	private TextField tagName;
	
	/**
	 * fxid for the textfield of tag value search
	 */
	@FXML
	private TextField tagValue;
	
	/**
	 * fxid for the textfield of tag1 name search AND/OR
	 */
	@FXML
	private TextField tagName1;
	
	/**
	 * fxid for the textfield of tag1 value search AND/OR
	 */
	@FXML
	private TextField tagValue1;
	
	/**
	 * fxid for the textfield of tag2 name search AND/OR
	 */
	@FXML
	private TextField tagName2;
	
	/**
	 * fxid for the textfield of tag2 value search AND/OR
	 */
	@FXML
	private TextField tagValue2;
	
	/**
	 * fxid for the Datepicker start of the date search
	 */
	@FXML
	private DatePicker startDateField;
	
	/**
	 * fxid for the Datepicker end of the date search
	 */
	@FXML
	private DatePicker endDateField;
	
	/**
	 * fxid for the label of the current user and rename field
	 */
	@FXML
	private Label currentUser, renameField;
	
	/**
	 * fxid for the Listview for albums
	 */
	@FXML
	private ListView<Album> albums;
	
	/**
	 * fxid for the textfield for album name and rename name
	 */
	@FXML
	private TextField albumName, renameName;
	
	/**
	 * fxid for the button for canceling a rename
	 */
	@FXML 
	private Button cancelRenameButton; 
	
	/**
	 * fxid for the button for updating a rename
	 */
	@FXML 
	private Button updateRenameButton; 
	
	/**
	 * fxid for the Listview of the album info
	 */
	@FXML
	private ListView<String> viewAlbumInfo;
	
	//public static final String albumFile = "listofAlbums.txt";
	/**
	 * file where we will serialize into
	 */
	public static final String userFile = "listOfUsers.txt";
	
	//Data Structures
	/**
	 * Observable list of the albums that is used to display the albums
	 */
	ObservableList<Album> listOfAlbums = FXCollections.observableArrayList();
	/**
	 * Observable list of the album info that is used to display the album info
	 */
	ObservableList<String> AlbumInfo = FXCollections.observableArrayList();
	
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
	 * Sets the current user
	 * @param user the user we want to set the current user to
	 */
	public void setCurrentUser(String user) {
		this.currentUser.setText(user);
	}
	
	/**
	 * Prints the current list of albums
	 */
	public void printAlbums() {
		if(AccessibleUsersList.masterUserList.getUsers() != null) {
			listOfAlbums = FXCollections.observableList(AccessibleUsersList.masterUserList.userLoggedIn.getAlbumsList());
			forceListRefreshOn(albums);
			FXCollections.sort(listOfAlbums);
			albums.setItems(listOfAlbums);
		}
	}
	
	//refreshes listview
	/**
	 * refreshes the list
	 * @param lsv the listview we are refreshing on
	 */
	private <T> void forceListRefreshOn(ListView<T> lsv) {
		//System.out.println("refreshing");
	    ObservableList<T> items = lsv.<T>getItems();
	    lsv.<T>setItems(null);
	    lsv.<T>setItems(items);
	}
	
	
	/*
	 * prints out the details of the album
	 * 1. Name
	 * 2. Size
	 * 3. Earliest Date - Latest Date
	 */
	/**
	 * prints the album info in the listview when you click on an album
	 * @param event handles the Mouse click event
	 */
	public void printAlbumInfo(MouseEvent event) {
		
		ObservableList<Album> list;
		list = albums.getSelectionModel().getSelectedItems();
		
		if(list.isEmpty()) {
			return;
		}
		
		Album alb = albums.getSelectionModel().getSelectedItem();
		String name = "album name: " + alb.getName();
		String size = Integer.toString(alb.getAlbumSize()) + " photos";
		
		ArrayList<String> info = new ArrayList<String>();
		info.add(name);
		info.add(size);
		//info.addAll(earliestDate + " - " + latestDate);
		
		String earliestDateString = ""; 
		String latestDateString = ""; 
		LocalDate earliestDate = null; 
		LocalDate latestDate = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int num = 0; 
		 
		 for(Photo photo: alb.getAlbum()){
			 
			 String dateformat = sdf.format(photo.lastModifiedDate);
			 
			 if(num == 0){
				 earliestDate = LocalDate.parse(dateformat); 
				 earliestDateString = dateformat; 
				 
				 latestDate = LocalDate.parse(dateformat); 
				 latestDateString = dateformat; 
				 num++; 
			 }
			 
			 LocalDate dateOfPhoto = LocalDate.parse(dateformat);
			 
			 if(dateOfPhoto.isBefore(earliestDate)){
					earliestDate = dateOfPhoto; 
					earliestDateString = dateformat; 
			 }
			 
			 if(dateOfPhoto.isAfter(latestDate)){
				 
					latestDate = dateOfPhoto;
					latestDateString = dateformat; 
			 }
			 
		 }
		 
		 String beginningAndEndDate = earliestDateString + " - " + latestDateString; //earliest and latest photo date for an album
		 
		 if( !beginningAndEndDate.equals(" - ") ){ //only adds if not empty (if empty album, then we dont display any date ranges)
			 info.add(beginningAndEndDate);
		 }
		 
		 AlbumInfo = FXCollections.observableList(info);
		 viewAlbumInfo.setItems(AlbumInfo);
		
	}
	
	/**
	 * Adds an album to the user's Arraylist of albums
	 * @param event handles the button event
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void addAlbum(ActionEvent event) throws FileNotFoundException, IOException {
		//get the user then get his LIST of albums, then add that album to that list, DONT forget to serialize master list after adding album
		User currUser = AccessibleUsersList.masterUserList.userLoggedIn;
		ArrayList<Album> usersAlbumList = currUser.getAlbumsList();
		
		String album_name = albumName.getText();
		album_name = album_name.trim();
		
		if(album_name.isEmpty() == true) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error!");
			alert.setHeaderText("Missing Field(s)");
			alert.setContentText("Album Field blank.  Field must be filled");
			alert.showAndWait();
			
			albumName.setText("");
			
			return;
		}
		
		if(currUser.checkAlbumName(album_name) == true) { //then duplicate album name entry
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error!");
			alert.setHeaderText("Duplicate Field");
			alert.setContentText("Album with name already exists!  Name must be unique!");
			alert.showAndWait();
			
			albumName.setText("");
			
			return;
		}
		
		Album a1 = new Album(album_name);
		
		usersAlbumList.add(a1);
		//AccessibleUsersList.masterUserList.userLoggedIn.getAlbumsList().add(a1);
		//System.out.println("adding album");
		albumName.setText("");
		//System.out.println(AccessibleUsersList.masterUserList.userLoggedIn.getAlbumsList());
		printAlbums();
		
		serializeUsers(AccessibleUsersList.masterUserList);
	}
	
	/**
	 * Deletes an album from a user's album arraylist
	 * @param event handles the button event
	 */
	public void deleteAlbum(ActionEvent event) {
		ObservableList<Album> abs;
		abs = albums.getSelectionModel().getSelectedItems();
		
		if(abs.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Album Selected");
			alert.setContentText("No album selected.  Delete Failed!");
			alert.showAndWait();
			
			return;
		}
		
		for(Album album: abs) {
			//delUsername.setText(name.getUsername());
			//this is a confirmation
			Alert alert2 = new Alert(AlertType.CONFIRMATION);
			alert2.setTitle("Confirmation Dialog");
			alert2.setHeaderText("Confirm Action");
			alert2.setContentText("Are you sure?");
			Optional<ButtonType> result = alert2.showAndWait();
			
			if(result.get() == ButtonType.OK) {
				listOfAlbums.remove(album);
				AccessibleUsersList.masterUserList.userLoggedIn.getAlbumsList().remove(album);
				//System.out.println("removing album");
			}else {
				return;
			}
		}
		
		printAlbums();
		//delUsername.setText("");
		
		//updates permanent list(Serialized List) of users
		try {
			serializeUsers(AccessibleUsersList.masterUserList);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Handles showing the rename fields
	 * @param event handles the button action
	 */
	public void renameAlbum(ActionEvent event) {
		
		ObservableList<Album> abs;
		abs = albums.getSelectionModel().getSelectedItems();
		
		if(abs.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Album Selected");
			alert.setContentText("No album selected.  Rename Failed!");
			alert.showAndWait();
			
			return;
		}
		
		renameField.setVisible(true);
		renameName.setVisible(true);
		cancelRenameButton.setVisible(true);
		updateRenameButton.setVisible(true);
		
		updateRenameButton.setOnAction(arg0 -> {
			performRename();
			renameField.setVisible(false);
			renameName.setVisible(false);
			cancelRenameButton.setVisible(false);
			updateRenameButton.setVisible(false);
			renameName.setText("");
		});
		cancelRenameButton.setOnAction(arg0 -> {
			
			renameField.setVisible(false);
			renameName.setVisible(false);
			cancelRenameButton.setVisible(false);
			updateRenameButton.setVisible(false);
			renameName.setText("");
		});
	}
	
	/**
	 * Renames an album
	 */
	public void performRename() {
		ObservableList<Album> albs;
		albs = albums.getSelectionModel().getSelectedItems();
		
		String getAlbum;
		getAlbum =  renameName.getText().trim(); //what user input
		
		if(getAlbum.equals("")) {
			Alert majorBagAlert = new Alert(AlertType.ERROR);
		    majorBagAlert.setTitle("Error");
		    majorBagAlert.setHeaderText("Error renaming album");
		    majorBagAlert.setContentText("No name input detected. Please enter different name");
		    majorBagAlert.showAndWait();
		    return; 
		}
		
		//checking for duplicate names
		for(Album album: AccessibleUsersList.masterUserList.userLoggedIn.getAlbumsList()){
			if(album.getName().equalsIgnoreCase(getAlbum)) {
				Alert majorBagAlert = new Alert(AlertType.ERROR);
			    majorBagAlert.setTitle("Error");
			    majorBagAlert.setHeaderText("Error renaming album");
			    majorBagAlert.setContentText("Name is already in use. Please enter different name");
			    majorBagAlert.showAndWait();
			    return; 
			}
		}
		
		//int index = albums.getSelectionModel().getSelectedIndex();
		Album al = (Album) albums.getSelectionModel().getSelectedItem();
		//System.out.println(al.getName());
		
		for(Album album: albs) {
			if(album.getName().equals(al.getName())) {
				//System.out.println("Renamed");
				album.setName(getAlbum);
			}
		}
		model.User.replaceAlbum(getAlbum, al.getName());
		
		try {
			serializeUsers(AccessibleUsersList.masterUserList);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		printAlbums();
	}
	
	/*
	 * _______________________SEARCH ALGORITHMS____________________________
	 */
	
	/*
	 * searched by Date Range
	 */
	/**
	 * Searches for photos from a list of albums by date range
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void searchByDateRange(ActionEvent event) throws IOException {
		
		LocalDate startDate = startDateField.getValue(); 
	 	LocalDate endDate = endDateField.getValue(); 
	 	
	 	// Empty Start Date or End Date
	 	if(startDate == null || endDate == null) {
	 		Alert majorBagAlert = new Alert(AlertType.ERROR);
	        majorBagAlert.setTitle("Error");
	        majorBagAlert.setHeaderText("Invalid Dates");
	        majorBagAlert.setContentText("Date field(s) are empty! Please enter two valid dates.");
	        majorBagAlert.showAndWait();
	        return; 
	 	}
	 	
	 	String pattern = "yyyy-MM-dd";
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
	 	
	    // Invalid start or end date
	    if(startDate.isAfter(endDate)){
	    	Alert majorBagAlert = new Alert(AlertType.ERROR);
	        majorBagAlert.setTitle("Error");
	        majorBagAlert.setHeaderText("Invalid Dates");
	        majorBagAlert.setContentText("Start Date must come before End Date! Please enter two valid dates.");
	        majorBagAlert.showAndWait();
	        
	        return; 
	    }
	 	
	 	int x = 0; 
	 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 	
	 	//created a resultPhotos it doesn't keep stacking photos into the AccessibleUsersList.masterUserList.userLoggedIn.searchListPhotos without refresh
	 	ArrayList<Photo> resultPhotos = new ArrayList<Photo>();
	 	
	 	// Search album for photos in the date range
		for(Album album: AccessibleUsersList.masterUserList.userLoggedIn.albums_list) {
			
			for(Photo photo: album.photos_list) {
				
				String dateformat = sdf.format(photo.lastModifiedDate);
				LocalDate dateOfPhoto = LocalDate.parse(dateformat);
				
				// Results found in-between range of dates
				if(startDate.isBefore(dateOfPhoto) && endDate.isAfter(dateOfPhoto)) { 
					x++;
					resultPhotos.add(photo); 
				}
			}
		}
		
		//if there's no photos to be displayed, then no need to go to the SearchPhotosController
		if(resultPhotos.isEmpty()) {
			Alert majorBagAlert = new Alert(AlertType.INFORMATION);
		    majorBagAlert.setTitle("Error");
		    majorBagAlert.setHeaderText("No Photos Found");
		    majorBagAlert.setContentText("No photos found that match the search criteria.");
		    majorBagAlert.showAndWait();
		    
		    tagName.setText("");
		    tagValue.setText("");
			
			return;
		}
		
		//update masterlist so it can be accessed in SearchPhotosController.java
		AccessibleUsersList.masterUserList.userLoggedIn.searchListPhotos = resultPhotos;
		
		// No results found
		if(x == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("No Results Found");
			alert.setContentText("There are no photos that match the search criteria!");

			alert.showAndWait();
			return; 
		}
		
		for(Photo p : AccessibleUsersList.masterUserList.userLoggedIn.searchListPhotos) {
			//System.out.println(p);
		}
		
		
		
		goToSearchPhotosPage(event);
	}
	
	/*
	 * searches by 1 tag value pair
	 */
	/**
	 * Searches for photos from a list of albums by tag value 
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void searchByTagValuePair(ActionEvent event) throws IOException {
		//given a user, search through ALL albums for the tag-value pair
		/*
		 * tagName
		 * tagValue
		 */
		String tName = tagName.getText().trim().toLowerCase();
		String tValue = tagValue.getText().trim().toLowerCase();
		
		//ensures both fields are filled out
		if(tName.equals("") || tValue.equals("")) {
			Alert majorBagAlert = new Alert(AlertType.ERROR);
		    majorBagAlert.setTitle("Error");
		    majorBagAlert.setHeaderText("Missing Field(s)");
		    majorBagAlert.setContentText("tagName or tagValue missing. Please fill out both fields.");
		    majorBagAlert.showAndWait();
		    
		    tagName.setText("");
		    tagValue.setText("");
		    
		    return;
		}
		
		User currUser = AccessibleUsersList.masterUserList.userLoggedIn;
		ArrayList<Album> currUserAlbums = currUser.getAlbumsList();
		ArrayList<Photo> resultPhotos = new ArrayList<Photo>();
		
		for(Album a : currUserAlbums) {
			ArrayList<Photo> AlbumPhotos = a.getAlbum();
			for(Photo p : AlbumPhotos) {
				ArrayList<Tag> PhotoTags = p.getTags();
				//System.out.println("looking at photo");
				for(Tag t : PhotoTags) {
					String name = t.getName();
					String value = t.getValue();
					
					if(tName.equals(name) && value.contains(tValue)) {
						//System.out.println("value: " + tValue + " - " + value);
						resultPhotos.add(p);
						//System.out.println("break");
						break;
					}
				}
			}
		}
		
		//System.out.println(resultPhotos);
		
		if(resultPhotos.isEmpty()) {
			Alert majorBagAlert = new Alert(AlertType.INFORMATION);
		    majorBagAlert.setTitle("Error");
		    majorBagAlert.setHeaderText("No Photos Found");
		    majorBagAlert.setContentText("No photos found that match the search criteria.");
		    majorBagAlert.showAndWait();
		    
		    tagName.setText("");
		    tagValue.setText("");
			
			return;
		}
		
		AccessibleUsersList.masterUserList.userLoggedIn.searchListPhotos = resultPhotos;
		
		goToSearchPhotosPage(event);
	}
	
	/**
	 * Searches for photos from a list of albums by 1st and 2nd tag-value pair
	 * @param event
	 * @throws IOException
	 */
	public void searchByANDTagValuePair(ActionEvent event) throws IOException {
		/*
		 * tagName1
		 * tagValue1
		 * 
		 * tagName2
		 * tagValue2
		 */
		String tName1 = tagName1.getText().trim().toLowerCase();
		String tValue1 = tagValue1.getText().trim().toLowerCase();
		
		String tName2 = tagName2.getText().trim().toLowerCase();
		String tValue2 = tagValue2.getText().trim().toLowerCase();
		
		//ensures both fields are filled out
		if(tName1.equals("") || tValue1.equals("") || tName2.equals("") || tValue2.equals("")) {
			Alert majorBagAlert = new Alert(AlertType.ERROR);
		    majorBagAlert.setTitle("Error");
		    majorBagAlert.setHeaderText("Missing Field(s)");
		    majorBagAlert.setContentText("tagNames or tagValues missing. Please fill out all fields.");
		    majorBagAlert.showAndWait();
		    
		    tagName1.setText("");
		    tagValue1.setText("");
		    tagName2.setText("");
		    tagValue2.setText("");
		    
		    return;
		}
		
		User currUser = AccessibleUsersList.masterUserList.userLoggedIn;
		ArrayList<Album> currUserAlbums = currUser.getAlbumsList();
		ArrayList<Photo> resultPhotos = new ArrayList<Photo>();
		
		for(Album a : currUserAlbums) {
			ArrayList<Photo> AlbumPhotos = a.getAlbum();
			for(Photo p : AlbumPhotos) {
				ArrayList<Tag> PhotoTags = p.getTags();
				//System.out.println(p);
				//System.out.println(p.checkIfTagExists(tName1, tName1));
				//System.out.println(p.checkIfTagExists(tName2, tValue2));
				
				if(p.checkIfTagExists(tName1, tValue1)==true && p.checkIfTagExists(tName2, tValue2)==true) {
					resultPhotos.add(p);
				}
			}
		}
		
		//System.out.println(resultPhotos);
		
		if(resultPhotos.isEmpty()) {
			Alert majorBagAlert = new Alert(AlertType.INFORMATION);
		    majorBagAlert.setTitle("Error");
		    majorBagAlert.setHeaderText("No Photos Found");
		    majorBagAlert.setContentText("No photos found that match the search criteria.");
		    majorBagAlert.showAndWait();
		    
		    tagName.setText("");
		    tagValue.setText("");
			
			return;
		}
		
		AccessibleUsersList.masterUserList.userLoggedIn.searchListPhotos = resultPhotos;
		
		goToSearchPhotosPage(event);
	}
	
	/**
	 * Searches for photos from a list of albums by 1st OR 2nd tag-value pair
	 * @param event
	 * @throws IOException
	 */
	public void searchByORTagValuePair(ActionEvent event) throws IOException {
		/*
		 * tagName1
		 * tagValue1
		 * 
		 * tagName2
		 * tagValue2
		 */
		String tName1 = tagName1.getText().trim().toLowerCase();
		String tValue1 = tagValue1.getText().trim().toLowerCase();
		
		String tName2 = tagName2.getText().trim().toLowerCase();
		String tValue2 = tagValue2.getText().trim().toLowerCase();
		
		//ensures both fields are filled out
		if(tName1.equals("") || tValue1.equals("") || tName2.equals("") || tValue2.equals("")) {
			Alert majorBagAlert = new Alert(AlertType.ERROR);
		    majorBagAlert.setTitle("Error");
		    majorBagAlert.setHeaderText("Missing Field(s)");
		    majorBagAlert.setContentText("tagNames or tagValues missing. Please fill out all fields.");
		    majorBagAlert.showAndWait();
		    
		    tagName1.setText("");
		    tagValue1.setText("");
		    tagName2.setText("");
		    tagValue2.setText("");
		    
		    return;
		}
		
		User currUser = AccessibleUsersList.masterUserList.userLoggedIn;
		ArrayList<Album> currUserAlbums = currUser.getAlbumsList();
		ArrayList<Photo> resultPhotos = new ArrayList<Photo>();
		
		for(Album a : currUserAlbums) {
			ArrayList<Photo> AlbumPhotos = a.getAlbum();
			for(Photo p : AlbumPhotos) {
				ArrayList<Tag> PhotoTags = p.getTags();
				//System.out.println("looking at photo");
				for(Tag t : PhotoTags) {
					String name = t.getName();
					String value = t.getValue();
					
					if((tName1.equals(name) && value.contains(tValue1)) || (tName2.equals(name) && value.contains(tValue2))) {
						resultPhotos.add(p);
						//System.out.println("break");
						break;
					}
				}
			}
		}
		
		//System.out.println(resultPhotos);
		
		if(resultPhotos.isEmpty()) {
			Alert majorBagAlert = new Alert(AlertType.INFORMATION);
		    majorBagAlert.setTitle("Error");
		    majorBagAlert.setHeaderText("No Photos Found");
		    majorBagAlert.setContentText("No photos found that match the search criteria.");
		    majorBagAlert.showAndWait();
		    
		    tagName.setText("");
		    tagValue.setText("");
			
			return;
		}
		
		AccessibleUsersList.masterUserList.userLoggedIn.searchListPhotos = resultPhotos;
		
		goToSearchPhotosPage(event);
	}
	
	/**
	 * Goes to the login page window
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void goToLoginPage(ActionEvent event) throws IOException{
		//this should also display the Admin Page
		Parent user_page = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
		Scene userpage_scene = new Scene(user_page);
		Stage app_stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		app_stage.setScene(userpage_scene);
		app_stage.setTitle("Login Page");
		app_stage.show();
	}
	
	/**
	 * Goes to the Search Photos Page Window
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void goToSearchPhotosPage(ActionEvent event) throws IOException {
		Parent user_page = FXMLLoader.load(getClass().getResource("/view/SearchPhotos.fxml"));
		Scene userpage_scene = new Scene(user_page);
		Stage app_stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		app_stage.setScene(userpage_scene);
		app_stage.setTitle("Photo Search Result");
		app_stage.show();
	}
	
	/**
	 * Goes to the Album Page Window
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void goToAlbumPage(ActionEvent event) throws IOException { //this is going to close the current window and open a new window
		//this should also display the Admin Page
		ObservableList<Album> abs;
		abs = albums.getSelectionModel().getSelectedItems();
		
		if(abs.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Album Selected");
			alert.setContentText("No album selected.  View Album Failed!");
			alert.showAndWait();
			
			return;
		}
		
		Album al = (Album) albums.getSelectionModel().getSelectedItem();
		Album sel_album = new Album(al.getName());
		sel_album = AccessibleUsersList.masterUserList.userLoggedIn.findAlbum(al.getName());
		AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum = sel_album;
		
		Parent album_page = FXMLLoader.load(getClass().getResource("/view/Album_Page.fxml"));
		Scene albumpage_scene = new Scene(album_page);
		Stage app_stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		app_stage.setScene(albumpage_scene);
		app_stage.setTitle("Album Page");
		app_stage.show();
	}
	
	/**
	 * Goes to the Friend List Page Window
	 * @param event
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
	 * Closes or ends the application
	 * @param event handles the button action
	 */
	public void closeApp(ActionEvent event) {
		System.exit(0);
	}

	/*
	 * The first that that runs when you first get to the controller
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		User currUser = AccessibleUsersList.masterUserList.userLoggedIn;
		//System.out.println("Getting logged in member: " + currUser.getUsername());
		ArrayList<Album> albs = currUser.getAlbumsList();
		listOfAlbums = FXCollections.observableList(albs);
		
		currentUser.setText(AccessibleUsersList.masterUserList.userLoggedIn.getUsername());
		
		renameField.setVisible(false);
		renameName.setVisible(false);
		cancelRenameButton.setVisible(false);
		updateRenameButton.setVisible(false);
		FriendsListButton.setDisable(false);
    	
    	if(AccessibleUsersList.masterUserList.userLoggedIn.getUsername().equals("stock")) {
    		FriendsListButton.setDisable(true);
    	}
		
		
		printAlbums();
	}
	
}
