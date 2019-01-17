/*
 * User.java
 * 
 * Ronak Gandhi (rjg184)
 * Andrew Lowe (all157)
 * 
 */

package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Serializable -> allows the conversion of an instance of a class (object) into a series of bytes
/**
 * User Class that handles all events relating to users
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 * 
 */
public class User implements Serializable, Comparable<User> {
	
	/**
	 * Serial ID to help with differing versions when serializing/deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * File list for user
	 */
	public List<File> fileList;
	
	/**
	 * String username for the user
	 */
	private String username; //username of user
	/**
	 * list of albums for the user
	 */
	public ArrayList<Album> albums_list; //users have a list of albums
	/**
	 * list of the search result for the user
	 */
	public ArrayList<Photo> searchListPhotos = new ArrayList<Photo>(); 
	/**
	 * list of users which are the users friends
	 */
	public ArrayList<User> friend_list;
	/**
	 * selected album for the user
	 */
	public Album selectedAlbum;
	
	/**
	 * Constructor for the user class
	 * @param username for the user object
	 */
	public User(String username) {
		this.username = username;
		albums_list = new ArrayList<Album>();
		friend_list = new ArrayList<User>();
	}
	
	/**
	 * gets the username for the user
	 * @return returns the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * sets the username for the user
	 * @param username the username for the user
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * gets the list of albums for the user
	 * @return returns the list of albums for the user
	 */
	public ArrayList<Album> getAlbumsList() {
		return albums_list;
	}
	
	/**
	 * gets the other albums other than the selected album
	 * @return returns a list of the other albums
	 */
	public ArrayList<Album> getOtherAlbums() {
		ArrayList<Album> others = new ArrayList<Album>();
		for(Album a: this.albums_list) {
			if(a != selectedAlbum) {
				others.add(a);
			}
		}
		return others;
	}
	
	/**
	 * sets the friend list for the user
	 * @param friends list of friends for the user
	 */
	public void setFriendList(ArrayList<User> friends) {
		this.friend_list = friends;
	}
	
	/**
	 * gets the friend list for the user
	 * @return returns the friends for the user
	 */
	public ArrayList<User> getFriendList() {
		return this.friend_list;
	}
	
	/**
	 * adds the friend into the friends list
	 * @param user user to be added into the friends list
	 */
	public void addFriend(User user) {
		friend_list.add(user);
	}
	
	/**
	 * deletes the friend from the friends list
	 * @param user user to be deleted from the friends list
	 */
	public void delFriend(User user) {
		friend_list.remove(user);
	}
	
	/**
	 * sets the album list
	 * @param album album list to be set
	 */
	public void setAlbumList(ArrayList<Album> album) {
		this.albums_list = album;
	}
	
	/**
	 * adds an album to the album list
	 * @param album album to be added to the album list
	 */
	public void addAlbum(Album album) {
		
		if(albums_list.size() == 0) {
			return;
		} else {
			albums_list.add(album);
		}
	}
	
	/**
	 * deletes an album from the album list
	 * @param album album to be deleted from the album list
	 */
	public void deleteAlbum(Album album) {
		albums_list.remove(album);
	}
	
	/**
	 * checks the album name to see if it exists in the album list
	 * @param name name of the album to be checked for
	 * @return returns true if the album name exists already, false otherwise
	 */
	public boolean checkAlbumName(String name) {
		
		for(Album album : albums_list) {
			
			String album_name = album.getName(); //trim white spaces and make lowercase
			
			if(album_name.equalsIgnoreCase(name)) {
				return true; //album name already exists
			}
			
		}
		
		return false; //album name doesn't exist
	}
	
	/**
	 * Replaces the album name in the master list with the new album name
	 * @param newAlbumName the new album name to be replaces
	 * @param target target to look for in the album list
	 */
	public static void replaceAlbum(String newAlbumName, String target){
		
		Album album = null;
		
		for(int i = 0 ;  i< AccessibleUsersList.masterUserList.userLoggedIn.getAlbumsList().size(); i++){
			if(target.equals(AccessibleUsersList.masterUserList.userLoggedIn.getAlbumsList().get(i).getName())){
				AccessibleUsersList.masterUserList.userLoggedIn.getAlbumsList().get(i).setName(newAlbumName);
				break;
			}
		}
	}
	
	/**
	 * Finds the album in the album list
	 * @param albumName String name of the album to look for
	 * @return returns the album if found
	 */
	public Album findAlbum(String albumName){
		//find user in the list of users
		
		Album album = new Album(albumName);
		for(Album a: albums_list){
			if(albumName.equals(a.getName())) {
				album = a;
				return album;
			}
		}
		return album;
	}
	
	/**
	 * checks if the user is in the friends list
	 * @param user user to be checked for in the friends list
	 * @return returns true if the friend is already in the friends list, return false otherwise
	 */
	public boolean isInFriendsList(User user) {
		for(User u: this.friend_list) {
			if(u.equals(user)) {
				return true;
			}
		}
		return false;
	}
	
	
    @Override
	public String toString() {
		return username; //print/refer to user by their username
	}

	@Override
	public int compareTo(User o) {
		// check differences between 2 user objects
		return 0;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(o == null || !(o instanceof User)) {
			return false;
		}
		
		User user_obj = (User) o;
		
		return (user_obj.username.equals(username)); //check if 
	}

}
