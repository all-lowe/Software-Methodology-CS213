//Andrew Lowe (all157)
//Ronak Gandhi (rjg184)

package application;

import java.util.Comparator;

public class Song implements Comparable<Song> {

/* 
 * --------------------
 * Object Attributes
 * --------------------
 */
	
	private String title;
	private String artist;
	private String album;
	private String year;

/* 
 * --------------------
 * Object Getters / Setters
 * --------------------
 */
	
	/* 1 - Title */
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	/* 2 - Artist */
	public String getArtist() {
		return artist;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	/* 3 - Album */
	public String getAlbum() {
		return album;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}
	
	/* 4 - Year */
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
/* 
 * --------------------
 * Object Functions
 * --------------------
 */
	
	/*
	 * Checks if 2 song objects (title and artist) are equal - checking if duplicate
	 */
	public boolean equals(Song o) {
		if(o == null || !(o instanceof Song)) {
			return false;
		}
		
		Song other = (Song) o;
		return (title.equals(other.title)) && (artist.equals(other.artist));
	}
	
	/* 
	 * Compares 2 song objects for lexicographical ordering 
	 * 		-> Negative = title1 goes first
	 * 		-> Zero 	= both titles are the same
	 * 		-> Positive = title2 goes first
	 */
	@Override
	public int compareTo(Song o) {
		
		String title1 = title.toLowerCase();
		String title2 = o.title.toLowerCase();
		
		int check = title1.compareTo(title2);
		
		if(check == 0) {
			String artist1 = artist.toLowerCase();
			String artist2 = o.artist.toLowerCase();
			
			check = artist1.compareTo(artist2);
		}
		
		return check;
	}
	
	@Override
	public String toString() {
	    return title + " - " + artist;
	}
}
