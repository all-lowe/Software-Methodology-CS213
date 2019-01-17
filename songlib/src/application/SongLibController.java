//Andrew Lowe (all157)
//Ronak Gandhi (rjg184)

package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SongLibController implements Initializable{

	@FXML 
	private ListView<Song> songlist;
	
	@FXML
	private Label songInfoSong;
	
	@FXML
	private Label songInfoArtist;
	
	@FXML
	private Label songInfoAlbum;
	
	@FXML
	private Label songInfoYear;
	
	@FXML
	private Label deleteSong;
	
	@FXML
	private Label deleteArtist;
	
	@FXML
	private TextField inputAddSong;
	
	@FXML
	private TextField inputAddArtist;
	
	@FXML
	private TextField inputAddAlbum;
	
	@FXML
	private TextField inputAddYear;
	
	@FXML
	private TextField inputEditSong;
	
	@FXML
	private TextField inputEditArtist;
	
	@FXML
	private TextField inputEditAlbum;
	
	@FXML
	private TextField inputEditYear;
	
	ObservableList<Song> songs = FXCollections.observableArrayList();
	
	private void writeToCSV() {
		
		String csvFile = "SongList.csv";
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		try {
			
			fw = new FileWriter(csvFile);
			bw = new BufferedWriter(fw);

			for(int i = 0; i < songs.size(); i++) {
				
				Song song = songs.get(i); //get song object at index i
				
				// Title of the Song - required attribute
				String title = song.getTitle(); //get song's title attribute
				
				// Artist of the Song - required attribute
				String artist = song.getArtist(); //get song's artist attribute
				
				// Album the Song is a part of - optional attribute
				String album = song.getAlbum(); //get song's album attribute
				if(album == null){
					album = ""; //write album as empty string
				}
				
				// Year the Song was released - optional attribute
				String year = song.getYear(); //get song's year attribute
				if(year == null){
					year = ""; //write year as empty string
				}
				
				// Write to CSV (format = title, artist, album, year)
				String content = title + "," + artist + "," + album + "," + year + "\n";
				bw.write(content);
			
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bw != null){
					bw.close();
				}
				if(fw != null){
					fw.close();
				}
			} catch (IOException ex){
				ex.printStackTrace();
			}
		}
		
	} //end of 'writeToCSV' method
	
	private void readCSV() {
		
		String csvFile = "SongList.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while((line = br.readLine()) != null) {
            	
            	String[] songAttributes = new String[4];
                String[] token = line.split(cvsSplitBy);
                
                for(int i = 0; i < token.length; i++) {
                	songAttributes[i] = token[i];
                }
                
                // Create 'song' object
                Song song = new Song();
                song.setTitle(songAttributes[0]);
                song.setArtist(songAttributes[1]);
                song.setAlbum(songAttributes[2]);
                song.setYear(songAttributes[3]);
                
                // Add 'song' object to ArrayList
                songs.add(song);
            }
            
            FXCollections.sort(songs);
    		songlist.setItems(songs);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		
	} //end of 'readCSV()' method
	
	private void createCSV() {
		
		File csvFile = new File("SongList.csv");
		
		if(csvFile.exists() == false) {
			try {
				//System.out.println("Success.");
				csvFile.createNewFile();
			} catch (IOException e) {
				//System.out.println("Failure.");
				e.printStackTrace();
			}
		} else {
			readCSV();
		}
		
	} //end of 'createCSV()' method
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		songlist.setItems(songs);
		createCSV();
		
		songlist.scrollTo(0);
	    songlist.getSelectionModel().select(0);
	    ActionEvent event = null;
		clickSong2(event);
		
	}
	
	public void refreshList(ActionEvent event) {
		FXCollections.sort(songs);
		songlist.setItems(songs);
		writeToCSV();
		
	}
	
	public void clickSong(MouseEvent event) {
		ObservableList<Song> names;
		names = songlist.getSelectionModel().getSelectedItems();
		for(Song name : names) { //looping through the items to find the name of item you clicked
			songInfoSong.setText(name.getTitle());
			songInfoArtist.setText(name.getArtist());
			songInfoAlbum.setText(name.getAlbum());
			songInfoYear.setText(name.getYear());
			deleteSong.setText(name.getTitle());
			deleteArtist.setText(name.getArtist());
			inputEditSong.setText(name.getTitle());
			inputEditArtist.setText(name.getArtist());
			inputEditAlbum.setText(name.getAlbum());
			inputEditYear.setText(name.getYear());
		}
	}
	
	//same as clicksong but in button form rather than click form
	public void clickSong2(ActionEvent event) {
		ObservableList<Song> names;
		names = songlist.getSelectionModel().getSelectedItems();
		for(Song name : names) { //looping through the items to find the name of item you clicked
			songInfoSong.setText(name.getTitle());
			songInfoArtist.setText(name.getArtist());
			songInfoAlbum.setText(name.getAlbum());
			songInfoYear.setText(name.getYear());
			deleteSong.setText(name.getTitle());
			deleteArtist.setText(name.getArtist());
			inputEditSong.setText(name.getTitle());
			inputEditArtist.setText(name.getArtist());
			inputEditAlbum.setText(name.getAlbum());
			inputEditYear.setText(name.getYear());
		}
	}
	
	//Deleting a song from the ArrayList
	public void deletesong(ActionEvent event) throws IOException {
		ObservableList<Song> names;
		names = songlist.getSelectionModel().getSelectedItems();
		
		
		
		for(Song name: names) { //looping through the items to find the name of item you clicked
			//this is a confirmation
			Alert alert2 = new Alert(AlertType.CONFIRMATION);
			alert2.setTitle("Confirmation Dialog");
			alert2.setHeaderText("Confirm Action");
			alert2.setContentText("Are you sure?");
			Optional<ButtonType> result = alert2.showAndWait();
			if(result.get() == ButtonType.OK) {
				int index = songlist.getSelectionModel().getSelectedIndex();
				//System.out.println(index);
				songs.remove(name);
				writeToCSV();
				
				if(names.isEmpty()) {
					songInfoSong.setText("");
					songInfoArtist.setText("");
					songInfoAlbum.setText("");
					songInfoYear.setText("");
					deleteSong.setText("");
					deleteArtist.setText("");
					inputEditSong.setText("");
					inputEditArtist.setText("");
					inputEditAlbum.setText("");
					inputEditYear.setText("");
					return;
				}
				
				//need to select next song in list or prev song(if no next songs
		        songlist.scrollTo(index);
		        songlist.getSelectionModel().select(index);
		        clickSong2(event);
		        
				if(songs.isEmpty()) {
					//just clear out all the fields after an action
					songInfoSong.setText("");
					songInfoArtist.setText("");
					songInfoAlbum.setText("");
					songInfoYear.setText("");
					deleteSong.setText("");
					deleteArtist.setText("");
					inputEditSong.setText("");
					inputEditArtist.setText("");
					inputEditAlbum.setText("");
					inputEditYear.setText("");
				}
			}else {
				//just clear out all the fields after an action
				songInfoSong.setText("");
				songInfoArtist.setText("");
				songInfoAlbum.setText("");
				songInfoYear.setText("");
				deleteSong.setText("");
				deleteArtist.setText("");
				inputEditSong.setText("");
				inputEditArtist.setText("");
				inputEditAlbum.setText("");
				inputEditYear.setText("");
				return;
			}	
		}
	}
	
	//Adding a song to the ArrayList
	public void addingsong(ActionEvent event) throws IOException {
		//checks if songname and artist is empty
		String newsong = inputAddSong.getText();
		String newartist = inputAddArtist.getText();
		newsong = newsong.trim();
		newartist = newartist.trim();
		if(newsong.isEmpty() == true || newartist.isEmpty() == true) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error!");
			alert.setHeaderText("Missing Field(s)");
			alert.setContentText("Song or Artist Field(s) blank.  Both Fields must be filled");
			alert.showAndWait();
			
			return;
		}
		String newalbum = inputAddAlbum.getText();
		String newyear = inputAddYear.getText();
		newalbum.trim();
		newyear.trim();
		
		//this is a confirmation
		Alert alert2 = new Alert(AlertType.CONFIRMATION);
		alert2.setTitle("Confirmation Dialog");
		alert2.setHeaderText("Confirm Action");
		alert2.setContentText("Are you sure?");
		Optional<ButtonType> result = alert2.showAndWait();
		if(result.get() == ButtonType.OK) {
			//the action that should be adding the song into the library
			Song temp = new Song();
			temp.setTitle(newsong);
			temp.setArtist(newartist);
			temp.setAlbum(newalbum);
			temp.setYear(newyear);
			
			//checks duplicate entry, returns if finds duplicate
			Iterator<Song> itr = songs.iterator();
			while(itr.hasNext()) {
				Song itrobj = new Song();
				itrobj = itr.next();
				if(itrobj.getTitle().toLowerCase().equals(temp.getTitle().toLowerCase()) && itrobj.getArtist().toLowerCase().equals(temp.getArtist().toLowerCase())) { //if true, then duplicate entry detected
					Alert alert3 = new Alert(AlertType.INFORMATION);
					alert3.setTitle("Error!");
					alert3.setHeaderText("Duplicate Entry");
					alert3.setContentText("Song and Artist idential to existing entry in song list");
					alert3.showAndWait();
					
					//clears out fields
					inputAddSong.setText("");
					inputAddArtist.setText("");
					inputAddAlbum.setText("");
					inputAddYear.setText("");
					return;
				}
			}
			songs.add(temp);
			FXCollections.sort(songs);
			songlist.setItems(songs);
			//songlist.getSelectionModel().select(temp);
			writeToCSV();
	
			//just clear out all the fields after an action
			inputAddSong.setText("");
			inputAddArtist.setText("");
			inputAddAlbum.setText("");
			inputAddYear.setText("");
			songInfoSong.setText("");
			songInfoArtist.setText("");
			songInfoAlbum.setText("");
			songInfoYear.setText("");
			deleteSong.setText("");
			deleteArtist.setText("");
			inputEditSong.setText("");
			inputEditArtist.setText("");
			inputEditAlbum.setText("");
			inputEditYear.setText("");
			
			songlist.scrollTo(songs.indexOf(temp));
	        songlist.getSelectionModel().select(songs.indexOf(temp));
	        clickSong2(event);
		}else {
			//just clear out all the fields after an action
			inputAddSong.setText("");
			inputAddArtist.setText("");
			inputAddAlbum.setText("");
			inputAddYear.setText("");
			songInfoSong.setText("");
			songInfoArtist.setText("");
			songInfoAlbum.setText("");
			songInfoYear.setText("");
			deleteSong.setText("");
			deleteArtist.setText("");
			inputEditSong.setText("");
			inputEditArtist.setText("");
			inputEditAlbum.setText("");
			inputEditYear.setText("");
			return;
		}		
	}
	
	//updates a song from the list
	public void editsong(ActionEvent event) throws IOException {
		//checks if songname and artist are empty
		String currsong = inputEditSong.getText();
		String currartist = inputEditArtist.getText();
		if(currsong != null) {
			currsong = currsong.trim();
		}
		if(currartist != null) {
			currartist = currartist.trim();
		}
		
		//checks for missing fields
		if(currsong.isEmpty() == true || currartist.isEmpty() == true) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error!");
			alert.setHeaderText("Missing Field(s)");
			alert.setContentText("Song or Artist Field(s) blank.  Both Fields must be filled");
			alert.showAndWait();
			
			return;
		}
		//load album and year from textfield
		String curralbum = inputEditAlbum.getText();
		String curryear = inputEditYear.getText();
		if(curralbum != null) {
			curralbum = curralbum.trim();
		}
		if(curryear != null) {
			curryear = curryear.trim();
		}
		
		
		//this is a confirmation
		Alert alert2 = new Alert(AlertType.CONFIRMATION);
		alert2.setTitle("Confirmation Dialog");
		alert2.setHeaderText("Confirm Action");
		alert2.setContentText("Are you sure?");
		Optional<ButtonType> result = alert2.showAndWait();
		if(result.get() == ButtonType.OK) {
			//the action that should get the Song object, then update, the fields in the Song Object
			
			ObservableList<Song> names;
			names = songlist.getSelectionModel().getSelectedItems();
			for(Song name: names) { //looping through the items to find the name of item you clicked
				//updates the object that you have selected with the items in the textbox
				if(name.getTitle().equals(currsong) && name.getArtist().equals(currartist)) {
					name.setTitle(currsong);
					name.setArtist(currartist);
					name.setAlbum(curralbum);
					name.setYear(curryear);
					FXCollections.sort(songs);
					songlist.setItems(songs);
					writeToCSV();
				}else {
					//checks duplicate entry, returns if finds duplicate
					Iterator<Song> itr = songs.iterator();
					while(itr.hasNext()) {
						Song itrobj = new Song();
						itrobj = itr.next();
						if(itrobj.getTitle().toLowerCase().equals(currsong.toLowerCase()) && itrobj.getArtist().toLowerCase().equals(currartist.toLowerCase())) { //if true, then duplicate entry detected
							Alert alert3 = new Alert(AlertType.INFORMATION);
							alert3.setTitle("Error!");
							alert3.setHeaderText("Duplicate Entry");
							alert3.setContentText("Song and Artist idential to existing entry in song list");
							alert3.showAndWait();
							
							//clears out fields
							songInfoSong.setText("");
							songInfoArtist.setText("");
							songInfoAlbum.setText("");
							songInfoYear.setText("");
							deleteSong.setText("");
							deleteArtist.setText("");
							inputEditSong.setText("");
							inputEditArtist.setText("");
							inputEditAlbum.setText("");
							inputEditYear.setText("");
							return;
						}
					}
					name.setTitle(currsong);
					name.setArtist(currartist);
					name.setAlbum(curralbum);
					name.setYear(curryear);
					FXCollections.sort(songs);
					songlist.setItems(songs);
					writeToCSV();
				}
				
				
				
			}
			
			//should now REFRESH the song list
			
			//just clear out all the fields after an action
			songInfoSong.setText("");
			songInfoArtist.setText("");
			songInfoAlbum.setText("");
			songInfoYear.setText("");
			deleteSong.setText("");
			deleteArtist.setText("");
			inputEditSong.setText("");
			inputEditArtist.setText("");
			inputEditAlbum.setText("");
			inputEditYear.setText("");
			
		}else {
			//just clear out all the fields after an action
			songInfoSong.setText("");
			songInfoArtist.setText("");
			songInfoAlbum.setText("");
			songInfoYear.setText("");
			deleteSong.setText("");
			deleteArtist.setText("");
			inputEditSong.setText("");
			inputEditArtist.setText("");
			inputEditAlbum.setText("");
			inputEditYear.setText("");
			return;
		}	
	}
}
