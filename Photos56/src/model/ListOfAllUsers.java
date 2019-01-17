/*
 * ListOfAllUsers.java
 * 
 * Ronak Gandhi (rjg184)
 * Andrew Lowe (all157)
 * 
 */

package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * ListOfAllUsers Class that encapsulates all data and the list of users
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 * 
 */
public class ListOfAllUsers implements Serializable {
	
	/**
	 * Serial ID to help with differing versions when serializing/deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * arraylist of all users in the application
	 */
	public ArrayList<User> users = new ArrayList<User>();
	/**
	 * the current user logged in
	 */
	public User userLoggedIn;
	/**
	 * the stock user
	 */
	public User stockUser;
	
	/*
	public ListOfAllUsers(){
		this.users = new ArrayList<User>();
		this.userLoggedIn = null;
	}
	*/
	
	/**
	 * Sets up the stock user the first time the application is run
	 */
	public void setupStock() {
		//System.out.println("Initialized stock!");
		stockUser = new User("stock");
		Album stockAlbum = new Album("stock");
		//stockUser.selectedAlbum = stockAlbum;
		
		File p1_file = new File("stockphotos/Bahamas.jpg");
		Photo p1 = new Photo("stockphotos/Bahamas.jpg", p1_file.lastModified());
		
		File p2_file = new File("stockphotos/Field.jpg");
		Photo p2 = new Photo("stockphotos/Field.jpg", p2_file.lastModified());
		
		File p3_file = new File("stockphotos/Lion.jpg");
		Photo p3 = new Photo("stockphotos/Lion.jpg", p3_file.lastModified());
		
		File p4_file = new File("stockphotos/Snow.jpg");
		Photo p4 = new Photo("stockphotos/Snow.jpg", p4_file.lastModified());
		
		File p5_file = new File("stockphotos/Racecar.jpg");
		Photo p5 = new Photo("stockphotos/Racecar.jpg", p5_file.lastModified());
		
		File p6_file = new File("stockphotos/City.jpg");
		Photo p6 = new Photo("stockphotos/City.jpg", p6_file.lastModified());
		
		stockAlbum.addPhoto(p1);
		stockAlbum.addPhoto(p2);
		stockAlbum.addPhoto(p3);
		stockAlbum.addPhoto(p4);
		stockAlbum.addPhoto(p5);
		stockAlbum.addPhoto(p6);
		
		stockUser.getAlbumsList().add(stockAlbum);
	}
	
	/**
	 * Searches for a user and returns true/false
	 * @param usr user object we are searching for
	 * @return returns true is user is found, false otherwise
	 */
	public boolean searchUser(User usr) {
		for(User u: users){
			if(usr.equals(u)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Searches for a user and returns true/false
	 * @param username String object we are searching for
	 * @return returns true if user is found, false otherwise
	 */
	public boolean searchUser(String username){
		//find user in the list of users
		
		for(User u: users){
			if(username.equals(u.getUsername())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Finds a user 
	 * @param username username we are trying to find
	 * @return returns the User if the user is found, null otherwise
	 */
	public User findUser(String username){
		//find user in the list of users
		
		User user = new User(username);
		for(User u: users){
			if(username.equalsIgnoreCase(u.getUsername())) {
				user = u;
				return user;
			}
		}
		user = null;
		return user;
	}
	
	//Given a username, returns the User object that the username corresponds to
	/**
	 * Gets a user 
	 * @param username String username of the user we are trying to find
	 * @return returns the user if found, null otherwise
	 */
	public User getUser(String username) {
		//System.out.println("List: " + users);
		//System.out.println("Username: " + username);
		for(User u: users) {
			if(username.equals(u.getUsername())) {
				return u;
			}
		}
		//System.out.println("did not find user.");
		return null;
	}
	
	/*
	//logged in user
	public void setLoggedInUser(User user) {
		this.userLoggedIn = user;
		//System.out.println(this.userLoggedIn.getUsername());
	}
	
	//gets the user thats currently logged in
	public User getLoggedInUser() {
		//System.out.println("WHole list of users: " + getUsers());
		//System.out.println("this user: " + this.userLoggedIn);
		for(User u: getUsers()) {
			if(u.equals(this.userLoggedIn)) {
				//System.out.println("found user");
				return this.userLoggedIn;
			}
		}
		//System.out.println("did not find user");
		return null;
	}
	*/
	
	//set the arraylist
	/**
	 * sets the list of users in the class
	 * @param users list of users to be set to
	 */
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
	//gets arraylist
	/**
	 * gets the list of users in the class
	 * @return returns the list of users
	 */
	public ArrayList<User> getUsers() {
		return this.users;
	}
	
	//adds a user to the ArrayList<User>
	/**
	 * adds a user to the list of users
	 * @param user user to be added
	 */
	public void addUser(User user) {
		this.users.add(user);
	}
	
	//removes a user to the ArrayList<User>
	/**
	 * removes a user from the list
	 * @param user user to be removed
	 */
	public void removeUser(User user) {
		this.users.remove(user);
	}
	
	/**
	 * sets the stock user 
	 * @param stockuser stock user to be set
	 */
	public void setStockUser(User stockuser) {
		this.stockUser = stockuser;
	}
	
}
