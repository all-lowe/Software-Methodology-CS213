/*
 * AlbumController.java
 * 
 * Ronak Gandhi (rjg184)
 * Andrew Lowe (all157)
 * 
 */

package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.activation.MimetypesFileTypeMap;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.AccessibleUsersList;
import model.Album;
import model.ListOfAllUsers;
import model.Photo;
import model.Tag;
import javafx.scene.input.MouseEvent;

/**
 * This is the Album Controller that handles all functionality in the for the Album Page
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 * 
 */
public class AlbumController implements Serializable, Initializable{
	
	/**
	 * Serial ID to help with differing versions when serializing/deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * fxid for the button for the friends list button
	 */
	@FXML
	private Button FriendsListButton;
	
	/**
	 * fxid for the label for the last modified date
	 */
	@FXML
	private Label lastModifiedDate;
	
	/**
	 * fxid for the label for the friends tag name
	 */
	@FXML
	private Label tagNameLabel;
	
	/**
	 * fxid for the label for the tag value
	 */
	@FXML
	private Label tagValueLabel;
	
	/**
	 * fxid for the button for the submit new tag button
	 */
	@FXML
	private Button submitNewTagButton;
	
	/**
	 * fxid for the button for the cancel new tag button
	 */
	@FXML
	private Button cancelNewTagButton;
	
	/**
	 * fxid for the label for the current album title
	 */
	@FXML
	private Label currentAlbumTitle;
	
	/**
	 * fxid for the label for the old caption text
	 */
	@FXML
	private Label OldCaptionText;
	
	/**
	 * fxid for the textfield for the Photo Caption
	 */
	@FXML
	private TextField PhotoCaptionDisplay;
	
	/**
	 * fxid for the textfield for the new caption text
	 */
	@FXML
	private TextField NewCaptionText;
	
	/**
	 * fxid for the textfield for the new tag name
	 */
	@FXML
	private TextField newTagName;
	
	/**
	 * fxid for the textfield for the new tag value
	 */
	@FXML
	private TextField newTagValue;
	
	/**
	 * fxid for the listview for the displayed image
	 */
	@FXML
	private ListView<ImageView> DisplayedImage;
	
	/**
	 * fxid for the button for the back button
	 */
	@FXML
	private Button backButton;
	
	/**
	 * fxid for the listview for the list of photos
	 */
	@FXML
	private ListView<ImageView> PhotoListDisplay;
	
	/**
	 * fxid for the listview for the list of tags
	 */
	@FXML
	private ListView<Tag> TagListDisplay;
	
	/**
	 * fxid for the listview for the list of other albums
	 */
	@FXML
	private ListView<Album> OtherAlbumsDisplay;
	
	/**
	 * file where we will serialize into
	 */
	public static final String userFile = "listOfUsers.txt";
	
	/**
	 * Observable list for the list of photos
	 */
	public static ObservableList<ImageView> listOfPhotos = FXCollections.observableArrayList();
	/**
	 * Observable list for the displayed photo
	 */
	public static ObservableList<ImageView> displayedPhoto = FXCollections.observableArrayList(); //JUST THE SINGLE IMAGE, should only be 1 element in this at all times
	/**
	 * Observable list for the list of tags
	 */
	public static ObservableList<Tag> tagObsList = FXCollections.observableArrayList();
	/**
	 * Observable list for the list of other albums
	 */
	public static ObservableList<Album> otherAlbums = FXCollections.observableArrayList();
	
	/**
	 * Intger value for the current photo index
	 */
	int currPhotoIndex;
	
	/**
	 * String value for the selected album
	 */
	String selectedAlbum = "";
	/**
	 * Stage value for a new stage
	 */
	Stage myNewStage;
	
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
	 * loads photos from the master list into an imageview Observable list
	 */
	/**
	 * loads photos from the master list into an imageview observable list
	 */
	public void printPhotos() {
		//System.out.println("Loading photos: " + AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getAlbum());
		
		if(AccessibleUsersList.masterUserList.getUsers() != null) {
			//System.out.println(listOfPhotos);
			listOfPhotos.clear();
			listOfPhotos = FXCollections.observableArrayList();
			PhotoListDisplay.setItems(listOfPhotos);
			
			for(Photo phot: AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getAlbum()) {
				//System.out.println("displaying photo");
				String path = phot.getPathToPhoto(); 
				//Pass to FileInputStream  
				FileInputStream inputstream = null;
				try {
					inputstream = new FileInputStream(path);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				//Instantiate an Image object 
				Image image1 = new Image(inputstream);
				
				//Image image1 = photo.photo;
				ImageView pic = new ImageView();
				pic.setFitWidth(340);
				pic.setFitHeight(180);
				pic.setImage(image1);
				listOfPhotos.add(pic);
				phot.setImage(pic);
				//System.out.println("after adding: " + listOfPhotos);
			}
			
			//System.out.println("after if stmt: " + listOfPhotos);
			PhotoListDisplay.setItems(listOfPhotos);
			
			//System.out.println("after setitems: " + listOfPhotos);
			return;
		}
		
		try {
			serializeUsers(AccessibleUsersList.masterUserList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(listOfPhotos);
	}
	
	/**
	 * Refreshed the list of photos to show any updates
	 */
	public void refreshPhotos() {
		if(AccessibleUsersList.masterUserList.getUsers() != null) {
			forceListRefreshOn(PhotoListDisplay);
			PhotoListDisplay.setItems(listOfPhotos);
		}
	}
	
	/*
	public void viewPhoto(ActionEvent event) {
		
		ObservableList<ImageView> image;
		image = PhotoListDisplay.getSelectionModel().getSelectedItems();
		
		if(image.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Photo Selected");
			alert.setContentText("No photo selected.  Failed to display image.");
			alert.showAndWait();
			
			return;
		}
		
		ImageView img = (ImageView) PhotoListDisplay.getSelectionModel().getSelectedItem();
		
		//test that you got the right image!
		Photo p = AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getPhotoFromImage(img);
		//System.out.println("Path to this image is: " + p.getPathToPhoto());
		PhotoCaptionDisplay.setText(p.getCaption());
		
		currPhotoIndex = listOfPhotos.indexOf(img);
		System.out.println(currPhotoIndex);
		ImageView newimg = new ImageView();
		newimg.setImage(img.getImage());
		newimg.setFitWidth(480);
		newimg.setFitHeight(330);
		displayedPhoto.clear();
		displayedPhoto = FXCollections.observableArrayList();
		displayedPhoto.add(newimg);
		DisplayedImage.setItems(displayedPhoto);
		
		//printPhotos();
	}
	*/
	
	/**
	 * Goes to the next photo in the list. If last photo, goes to the first photo
	 * @param event handles the button action
	 */
	public void viewNextPhoto(ActionEvent event) {
		
		//System.out.println("right check: " + listOfPhotos);
		
		if (displayedPhoto.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Getting the first photo");
			alert.setContentText("No photo selected.  Failed to display image.");
			alert.showAndWait();
			
			return;
		}
		
		int photosList_size = listOfPhotos.size();
		
		if(currPhotoIndex == photosList_size - 1) {
			currPhotoIndex = 0;
		} else {
			currPhotoIndex = currPhotoIndex + 1;
		}
		
		ImageView nextPhoto = listOfPhotos.get(currPhotoIndex);
		currPhotoIndex = listOfPhotos.indexOf(nextPhoto);
		//System.out.println(currPhotoIndex);
		ImageView newimg = new ImageView();
		newimg.setImage(nextPhoto.getImage());
		newimg.setFitWidth(480);
		newimg.setFitHeight(330);
		displayedPhoto.clear();
		displayedPhoto = FXCollections.observableArrayList();
		displayedPhoto.add(newimg);
		DisplayedImage.setItems(displayedPhoto);
		
		
		/*
		
		//System.out.println("Displayed Photo: " + displayedPhoto);
		ImageView img = displayedPhoto.get(0);
		System.out.println("PhotoList: " + listOfPhotos);
		ImageView img2 = listOfPhotos.get(0);
		//System.out.println("img2: " + img2);
		
		currentImageIndex = listOfPhotos.indexOf(img2);
		System.out.println(currentImageIndex);
		
		*/
		
	}
	
	/**
	 * Goes to the previous photo in the list. If first photo, goes to the last photo
	 * @param event handles the button action
	 */
	public void viewPrevPhoto(ActionEvent event) {
		
		//System.out.println("left check: " + listOfPhotos);
		
		if (displayedPhoto.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Cannot find next image!");
			alert.setContentText("No photo selected.  Failed to display next image.");
			alert.showAndWait();
			
			return;
		}
		
		
		int photosList_size = listOfPhotos.size();
		
		if(currPhotoIndex == 0) {
			currPhotoIndex = photosList_size - 1;
		} else {
			currPhotoIndex = currPhotoIndex - 1;
		}
		
		ImageView nextPhoto = listOfPhotos.get(currPhotoIndex);
		currPhotoIndex = listOfPhotos.indexOf(nextPhoto);
		//System.out.println(currPhotoIndex);
		ImageView newimg = new ImageView();
		newimg.setImage(nextPhoto.getImage());
		newimg.setFitWidth(480);
		newimg.setFitHeight(330);
		displayedPhoto.clear();
		displayedPhoto = FXCollections.observableArrayList();
		displayedPhoto.add(newimg);
		DisplayedImage.setItems(displayedPhoto);
	}
	
	/**
	 * Adds a photo to the album
	 * @param event handles the button action
	 * @throws IOException
	 */
	public void addPhoto(ActionEvent event) throws IOException {
		//System.out.println("ITEM ADDED______________________________________________");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Photo"); 
		AccessibleUsersList.masterUserList.userLoggedIn.fileList = fileChooser.showOpenMultipleDialog(this.myNewStage);
		
		boolean checkIfImages = true;
		
		if(AccessibleUsersList.masterUserList.userLoggedIn.fileList != null){
			
			for(File file : AccessibleUsersList.masterUserList.userLoggedIn.fileList){
				
				MimetypesFileTypeMap mimetype= new MimetypesFileTypeMap();
				mimetype.addMimeTypes("image png jpg jpeg JPG PNG");	
				
				String mimetype1 = mimetype.getContentType(file);
				String type = mimetype1.split("/")[0];
				
				if(type.contains("image")){
			           //System.out.println("It's an image");
				} else {   
			    	 //System.out.println("It's NOT an image");
			    	 Alert majorBagAlert = new Alert(AlertType.ERROR);
				     majorBagAlert.setTitle("Error");
				     majorBagAlert.setHeaderText("One or more images is not an image file type.");
				     majorBagAlert.setContentText("Please enter photo(s) again");
				     majorBagAlert.showAndWait();
			    	 
				     //adding comment
			         checkIfImages = false; 
			     }
			}
         }
		

		//iterate through the logged in user's albums 
		//this is the album we have to add our photos to 
		this.selectedAlbum = AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getName();  
		Album foundAlbum = null; 
		long lastModifiedDate = 0; 
		String path; 
		
		//if we are certain we only collected files of type jpg, png or jpeg 
		if(checkIfImages){
			
			if(AccessibleUsersList.masterUserList.userLoggedIn.fileList != null) {
				
				for(File file : AccessibleUsersList.masterUserList.userLoggedIn.fileList){
					
					path = file.getAbsolutePath();
					FileInputStream inputstream = new FileInputStream(path); 
					Image image1 = new Image(inputstream); 
														
					//iterate through the logged in user's albums 
					int x=0; 
					for(Album album: AccessibleUsersList.masterUserList.userLoggedIn.albums_list){	
						
						//looking for the current album to add our photos into 
						if(album.getName().equals(this.selectedAlbum)){
							
							foundAlbum = AccessibleUsersList.masterUserList.userLoggedIn.albums_list.get(x);
							lastModifiedDate = file.lastModified();
			
							Photo albumPhoto = new Photo(path, lastModifiedDate); 
							foundAlbum.getAlbum().add(albumPhoto);	 
							//System.out.println(foundAlbum.getAlbum());
							//foundAlbum.photos.add(new model.Photo(image1, lastModifiedDate));
							
							ImageView pic = new ImageView();
							pic.setFitWidth(340);
							pic.setFitHeight(180);
							pic.setImage(image1);
							listOfPhotos.add(pic);
							albumPhoto.setImage(pic);
							
							//System.out.println("photo added");
							//System.out.println(albumPhoto.getPathToPhoto());
							//System.out.println(AccessibleUsersList.masterUserList.userLoggedIn);
							//System.out.println(AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum);
							
							//AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.addPhoto(albumPhoto); <-----cause of bug, added photo TWICE, X
							break; 
						} 
						x++; 
					}
 			
				}
			}
		}
		
		try {
			serializeUsers(AccessibleUsersList.masterUserList);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	/**
	 * Deletes a photo from the album
	 * @param event handles the button action
	 */
	public void deletePhoto(ActionEvent event) {
		ObservableList<ImageView> pho;
		pho = PhotoListDisplay.getSelectionModel().getSelectedItems();
		
		//System.out.println("before deletion: " + AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getAlbum());
		
		if(pho.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Photo Selected");
			alert.setContentText("No photo selected.  Delete Failed!");
			alert.showAndWait();
			
			return;
		}
		
		for(ImageView p: pho) {
			//delUsername.setText(name.getUsername());
			//this is a confirmation
			Alert alert2 = new Alert(AlertType.CONFIRMATION);
			alert2.setTitle("Confirmation Dialog");
			alert2.setHeaderText("Confirm Action");
			alert2.setContentText("Are you sure?");
			Optional<ButtonType> result = alert2.showAndWait();
			
			if(result.get() == ButtonType.OK) {
				//removed it from observable list
				listOfPhotos.remove(p);
				displayedPhoto.remove(0);
				
				//removing photo from the master list
				Photo getPhoto = AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getPhotoFromImage(p);
				//System.out.println("Path to this image is: " + getPhoto.getPathToPhoto());
				AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getAlbum().remove(getPhoto);
				
				//System.out.println("removing album");
				displayedPhoto.clear();
				displayedPhoto = FXCollections.observableArrayList();
				DisplayedImage.setItems(displayedPhoto);
				
				PhotoCaptionDisplay.setText("");
				OldCaptionText.setText("");
				
				ArrayList<Tag> list = new ArrayList<Tag>();
				tagObsList = FXCollections.observableList(list);
				TagListDisplay.setItems(tagObsList);
				
				//System.out.println("after: " + AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getAlbum());
				
				
				//*******************CHECK FOR BUG HERE, (deleting newly added user creates zombie photos
				//System.out.println("1: " + AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getAlbum());
				//System.out.println("2: " + listOfPhotos);
				//System.out.println("3: " + displayedPhoto);
			}else {
				return;
			}
		}
		
		refreshPhotos();
		
		//Note: MUST SERIALIZE AFTER DELETING (also after adding)
		
		//updates permanent list(Serialized List) of users
		try {
			serializeUsers(AccessibleUsersList.masterUserList);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Deletes a Photo from the list.  Modified for the MovePhoto method
	 * @param event handles the button action
	 */
	public void deletePhotoForMovePhoto(ActionEvent event) {
		ObservableList<ImageView> pho;
		pho = PhotoListDisplay.getSelectionModel().getSelectedItems();
		
		//System.out.println("before deletion: " + AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getAlbum());
		
		if(pho.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Photo Selected");
			alert.setContentText("No photo selected.  Delete Failed!");
			alert.showAndWait();
			
			return;
		}
		
		for(ImageView p: pho) {
			//delUsername.setText(name.getUsername());
			//this is a confirmation
			
			//removed it from observable list
			listOfPhotos.remove(p);
			displayedPhoto.remove(0);
			
			//removing photo from the master list
			Photo getPhoto = AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getPhotoFromImage(p);
			//System.out.println("Path to this image is: " + getPhoto.getPathToPhoto());
			AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getAlbum().remove(getPhoto);
			
			//System.out.println("removing album");
			displayedPhoto.clear();
			displayedPhoto = FXCollections.observableArrayList();
			DisplayedImage.setItems(displayedPhoto);
			
			PhotoCaptionDisplay.setText("");
			OldCaptionText.setText("");
			
			ArrayList<Tag> list = new ArrayList<Tag>();
			tagObsList = FXCollections.observableList(list);
			TagListDisplay.setItems(tagObsList);
			
			//System.out.println("after: " + AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getAlbum());
			
			
			//*******************CHECK FOR BUG HERE, (deleting newly added user creates zombie photos
			//System.out.println("1: " + AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getAlbum());
			//System.out.println("2: " + listOfPhotos);
			//System.out.println("3: " + displayedPhoto);
			
		}
		
		refreshPhotos();
		
		//Note: MUST SERIALIZE AFTER DELETING (also after adding)
		
		//updates permanent list(Serialized List) of users
		try {
			serializeUsers(AccessibleUsersList.masterUserList);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * Displays image and all related contents when you click on its thumbnail in the ImageView List
	 */
	/**
	 * Displays the image and all related contents when you click on its thumbnail in the ImageView List
	 * @param event handles the button action
	 */
	public void clickPhoto(MouseEvent event) {
		//System.out.println("clicked!");
		ObservableList<ImageView> images;
		images = PhotoListDisplay.getSelectionModel().getSelectedItems();
		for(ImageView img: images) {
			Photo p = AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getPhotoFromImage(img);
			//System.out.println("path is: " + p.getPathToPhoto());
			//System.out.println(AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getAlbum());
			OldCaptionText.setText(p.getCaption());
			
		}
		
		ObservableList<ImageView> image;
		image = PhotoListDisplay.getSelectionModel().getSelectedItems();
		
		if(image.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Photo Selected");
			alert.setContentText("No photo selected.  Failed to display image.");
			alert.showAndWait();
			
			return;
		}
		
		ImageView img = (ImageView) PhotoListDisplay.getSelectionModel().getSelectedItem();
		
		//test that you got the right image!
		Photo p = AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getPhotoFromImage(img);
		
		
		//sets the caption of the photo
		PhotoCaptionDisplay.setText(p.getCaption());
		
		//displays the photo
		currPhotoIndex = listOfPhotos.indexOf(img);
		//System.out.println(currPhotoIndex);
		ImageView newimg = new ImageView();
		newimg.setImage(img.getImage());
		newimg.setFitWidth(480);
		newimg.setFitHeight(330);
		displayedPhoto.clear();
		displayedPhoto = FXCollections.observableArrayList();
		displayedPhoto.add(newimg);
		DisplayedImage.setItems(displayedPhoto);
		
		//List the tags for the specific Photo
		ArrayList<Tag> listTags = p.getTags();
		tagObsList = FXCollections.observableList(listTags);
		TagListDisplay.setItems(tagObsList);
		
		//List the date of the photo
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateformat = sdf.format(p.lastModifiedDate);
		lastModifiedDate.setText(dateformat);
		
		newTagName.setText("");
		newTagValue.setText("");
		
		tagNameLabel.setVisible(false);
    	tagValueLabel.setVisible(false);
    	submitNewTagButton.setVisible(false);
    	cancelNewTagButton.setVisible(false);
    	newTagName.setVisible(false);
    	newTagValue.setVisible(false);
	}
	
	
	/**
	 * Updates the caption for a photo
	 * @param event handles the button action
	 */
	public void updateCaption(ActionEvent event) {
		ObservableList<ImageView> pho;
		pho = PhotoListDisplay.getSelectionModel().getSelectedItems();
		
		if(pho.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Photo Selected");
			alert.setContentText("No photo selected.  Delete Failed!");
			alert.showAndWait();
			
			NewCaptionText.setText("");
			
			return;
		}
		
		Alert alert2 = new Alert(AlertType.CONFIRMATION);
		alert2.setTitle("Confirmation Dialog");
		alert2.setHeaderText("Confirm Action");
		alert2.setContentText("Are you sure?");
		Optional<ButtonType> result = alert2.showAndWait();
		
		if(result.get() == ButtonType.OK) {
			ImageView img = (ImageView) PhotoListDisplay.getSelectionModel().getSelectedItem();
			
			//test that you got the right image!
			Photo p = AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getPhotoFromImage(img);
			
			//System.out.println("Path to this image is: " + p.getPathToPhoto());
			
			p.setCaption(NewCaptionText.getText());
			//System.out.println("updated the caption to: " + NewCaptionText.getText());
			OldCaptionText.setText("");
			NewCaptionText.setText("");
			PhotoCaptionDisplay.setText(p.getCaption());
		}else {
			return;
		}
		
		try {
			serializeUsers(AccessibleUsersList.masterUserList);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Shows the fields to add a new tag
	 * @param event handles the button action
	 */
	public void addTagButton(ActionEvent event) {
		ObservableList<ImageView> pho;
		pho = PhotoListDisplay.getSelectionModel().getSelectedItems();
		
		if(pho.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Photo Selected");
			alert.setContentText("No photo selected.  Delete Failed!");
			alert.showAndWait();
			
			newTagName.setText("");
			newTagValue.setText("");
			
			tagNameLabel.setVisible(false);
	    	tagValueLabel.setVisible(false);
	    	submitNewTagButton.setVisible(false);
	    	cancelNewTagButton.setVisible(false);
	    	newTagName.setVisible(false);
	    	newTagValue.setVisible(false);
			
			return;
		}
		
		tagNameLabel.setVisible(true);
    	tagValueLabel.setVisible(true);
    	submitNewTagButton.setVisible(true);
    	cancelNewTagButton.setVisible(true);
    	newTagName.setVisible(true);
    	newTagValue.setVisible(true);
	}
	
	/**
	 * Adds a tag to the selected photo
	 * @param event handles the button action
	 */
	public void addTag(ActionEvent event) {
		ObservableList<ImageView> pho;
		pho = PhotoListDisplay.getSelectionModel().getSelectedItems();
		
		if(pho.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Photo Selected");
			alert.setContentText("No photo selected.  Delete Failed!");
			alert.showAndWait();
			
			newTagName.setText("");
			newTagValue.setText("");
			
			tagNameLabel.setVisible(false);
	    	tagValueLabel.setVisible(false);
	    	submitNewTagButton.setVisible(false);
	    	cancelNewTagButton.setVisible(false);
	    	newTagName.setVisible(false);
	    	newTagValue.setVisible(false);
			
			return;
		}
		
		String tagname = newTagName.getText().trim().toLowerCase();
		String tagvalue = newTagValue.getText().trim().toLowerCase();
		
		if(tagname.equals("") || tagvalue.equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Field(s) Missing");
			alert.setContentText("Tag name or Tag value missing.  Add Failed!");
			alert.showAndWait();
			
			newTagName.setText("");
			newTagValue.setText("");
			
			tagNameLabel.setVisible(false);
	    	tagValueLabel.setVisible(false);
	    	submitNewTagButton.setVisible(false);
	    	cancelNewTagButton.setVisible(false);
	    	newTagName.setVisible(false);
	    	newTagValue.setVisible(false);
			
			return;
		}
		
		ImageView img = (ImageView) PhotoListDisplay.getSelectionModel().getSelectedItem();
		Photo p = AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getPhotoFromImage(img);
		
		if(p.checkIfTagExists(tagname, tagvalue)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("Duplicate Tag!");
			alert.setContentText("Tag name and Tag value combination already exists.  Add Failed!");
			alert.showAndWait();
			
			newTagName.setText("");
			newTagValue.setText("");
			
			tagNameLabel.setVisible(false);
	    	tagValueLabel.setVisible(false);
	    	submitNewTagButton.setVisible(false);
	    	cancelNewTagButton.setVisible(false);
	    	newTagName.setVisible(false);
	    	newTagValue.setVisible(false);
			
			return;
		}
		
		p.addTag(tagname, tagvalue);
		ArrayList<Tag> listTags = p.getTags();
		
		tagObsList = FXCollections.observableList(listTags);
		TagListDisplay.setItems(tagObsList);
		
		newTagName.setText("");
		newTagValue.setText("");
		
		tagNameLabel.setVisible(false);
    	tagValueLabel.setVisible(false);
    	submitNewTagButton.setVisible(false);
    	cancelNewTagButton.setVisible(false);
    	newTagName.setVisible(false);
    	newTagValue.setVisible(false);
    	
    	try {
			serializeUsers(AccessibleUsersList.masterUserList);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Cancels the adding of a tag
	 * @param event handles the button action
	 */
	public void cancelAddTag(ActionEvent event) {
		newTagName.setText("");
		newTagValue.setText("");
		
		tagNameLabel.setVisible(false);
    	tagValueLabel.setVisible(false);
    	submitNewTagButton.setVisible(false);
    	cancelNewTagButton.setVisible(false);
    	newTagName.setVisible(false);
    	newTagValue.setVisible(false);
	}
	
	/**
	 * Deletes a tag from the photo
	 * @param event handles the button action
	 */
	public void deleteTag(ActionEvent event) {
		ObservableList<Tag> list;
		list = TagListDisplay.getSelectionModel().getSelectedItems();
		
		if(list.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Tag Selected");
			alert.setContentText("No tag selected.  Delete Failed!");
			alert.showAndWait();
			
			return;
		}
		Alert alert2 = new Alert(AlertType.CONFIRMATION);
		alert2.setTitle("Confirmation Dialog");
		alert2.setHeaderText("Confirm Action");
		alert2.setContentText("Are you sure?");
		Optional<ButtonType> result = alert2.showAndWait();
		
		if(result.get() == ButtonType.OK) {
			Tag tag = (Tag) TagListDisplay.getSelectionModel().getSelectedItem();
			
			ImageView img = (ImageView) PhotoListDisplay.getSelectionModel().getSelectedItem();
			Photo p = AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getPhotoFromImage(img);
			ArrayList<Tag> listTags = p.getTags();
			
			listTags.remove(tag);
			
			tagObsList = FXCollections.observableList(listTags);
			TagListDisplay.setItems(tagObsList);
		}else {
			return;
		}
	
		try {
			serializeUsers(AccessibleUsersList.masterUserList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		try {
			serializeUsers(AccessibleUsersList.masterUserList);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		*/
	}
	
	/**
	 * Moves a photo from one album to another
	 * @param event handles the button action
	 */
	public void movePhoto(ActionEvent event) {
		
		//checks if photo is selected
		ObservableList<ImageView> pho;
		pho = PhotoListDisplay.getSelectionModel().getSelectedItems();
		if(pho.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Photo Selected");
			alert.setContentText("No photo selected.  Move Photo Failed!");
			alert.showAndWait();
			
			return;
		}
		
		//checks if other album is selected
		ObservableList<Album> al;
		al = OtherAlbumsDisplay.getSelectionModel().getSelectedItems();
		if(al.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Album Selected");
			alert.setContentText("No album selected.  Move Photo Failed!");
			alert.showAndWait();
			
			return;
		}
		
		//now we must remove the selected photo from the current album and add the photo into the other album
		ImageView img = (ImageView) PhotoListDisplay.getSelectionModel().getSelectedItem();
		Photo p = AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getPhotoFromImage(img);
		Album oth_album = (Album) OtherAlbumsDisplay.getSelectionModel().getSelectedItem();
		
		oth_album.addPhoto(p);
		//AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getAlbum().remove(p);
		deletePhotoForMovePhoto(event); //doesn't have a confirmation dialog
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Photo Successfully Moved");
		alert.setContentText("Photo moved to "+ oth_album.getName() + ".");
		alert.showAndWait();
		
	}
	
	/**
	 * Copies a photo from one album to another album
	 * @param event handles the button action
	 */
	public void copyPhoto(ActionEvent event) {
		//checks if photo is selected
		ObservableList<ImageView> pho;
		pho = PhotoListDisplay.getSelectionModel().getSelectedItems();
		if(pho.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Photo Selected");
			alert.setContentText("No photo selected.  Move Photo Failed!");
			alert.showAndWait();
			
			return;
		}
		
		//checks if other album is selected
		ObservableList<Album> al;
		al = OtherAlbumsDisplay.getSelectionModel().getSelectedItems();
		if(al.isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText("No Album Selected");
			alert.setContentText("No album selected.  Move Photo Failed!");
			alert.showAndWait();
			
			return;
		}
		
		//now we must remove the selected photo from the current album and add the photo into the other album
		ImageView img = (ImageView) PhotoListDisplay.getSelectionModel().getSelectedItem();
		Photo p = AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getPhotoFromImage(img);
		
		String path = p.getPathToPhoto();
		long date = p.getlastModifiedDate();
		ArrayList<Tag> tags = p.getCopyOfTags();
		String cap = p.getCaption();
		Photo copied_photo = new Photo(path, date);
		copied_photo.setCaption(cap);
		copied_photo.setTags(tags);
		copied_photo.setImage(img);
		
		Album oth_album = (Album) OtherAlbumsDisplay.getSelectionModel().getSelectedItem();
		
		oth_album.addPhoto(copied_photo);
		//AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum.getAlbum().remove(p);
		//deletePhoto(event);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText("Photo Successfully Copied");
		alert.setContentText("Photo copied to "+ oth_album.getName() + ".");
		alert.showAndWait();
	}
	
	//refreshes listview
	/**
	 * Refreshes the listview to show any updates
	 * @param lsv the listview to be updated
	 */
	private <T> void forceListRefreshOn(ListView<T> lsv) {
		//System.out.println("refreshing");
		ObservableList<T> items = lsv.<T>getItems();
		lsv.<T>setItems(null);
		lsv.<T>setItems(items);
	}
	
	/**
	 * Goes the the login page window
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
	 * Goes to the user page window
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
	 * Goes the the friend list page window
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
	public void closeApp(ActionEvent event) {
		System.exit(0);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//System.out.println("Album selected is: + " + AccessibleUsersList.masterUserList.userLoggedIn + "/" + AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum);
		
		currentAlbumTitle.setText(AccessibleUsersList.masterUserList.userLoggedIn + "/ " + AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum);
		
		displayedPhoto = FXCollections.observableArrayList();
		/*
		if(AccessibleUsersList.masterUserList.userLoggedIn.getUsername().equals("stock")) {
			backButton.setVisible(false);
		}
		*/
		
		/*
		File file = new File("stockphotos/Bahamas.jpg");
        Image image = new Image(file.toURI().toString());
        //DisplayedImage.setImage(image);
        ImageView pic = new ImageView();
		pic.setFitWidth(400);
		pic.setFitHeight(330);
		pic.setImage(image);
		displayedPhoto.add(pic);
        DisplayedImage.setItems(displayedPhoto);
		*/
		
        Album currAlbum = AccessibleUsersList.masterUserList.userLoggedIn.selectedAlbum;
        //System.out.println("Accessing Album: " + currAlbum.getName());
        ArrayList<Photo> phots = currAlbum.getAlbum();
        //System.out.println(phots);
        //listOfPhotos = FXCollections.observableList(phots);
        //photoDisplay.setItems(listOfPhotos);
        printPhotos();
        
        //load the OTHER Albums
        ArrayList<Album> oAlbums = AccessibleUsersList.masterUserList.userLoggedIn.getOtherAlbums();
        otherAlbums = FXCollections.observableList(oAlbums);
        OtherAlbumsDisplay.setItems(otherAlbums);
        
        tagNameLabel.setVisible(false);
    	tagValueLabel.setVisible(false);
    	submitNewTagButton.setVisible(false);
    	cancelNewTagButton.setVisible(false);
    	newTagName.setVisible(false);
    	newTagValue.setVisible(false);
    	FriendsListButton.setDisable(false);
    	
    	if(AccessibleUsersList.masterUserList.userLoggedIn.getUsername().equals("stock")) {
    		FriendsListButton.setDisable(true);
    	}
        
		//DisplayedImage.setImage(image1);
		
	}
}
