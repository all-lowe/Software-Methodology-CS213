/*
 * Tag.java
 * 
 * Ronak Gandhi (rjg184)
 * Andrew Lowe (all157)
 * 
 */

package model;

import java.io.Serializable;

//Serializable -> allows the conversion of an instance of a class (object) into a series of bytes
/**
 * Tag Class that handles all events relating to tags
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 * 
 */
public class Tag implements Serializable, Comparable<Tag> {
	
	/**
	 * Serial ID to help with differing versions when serializing/deserializing
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * tag name
	 */
	private String name;
	/**
	 * tag value
	 */
	private String value;
	
	/**
	 * Constructor for the tag class
	 * @param name name of the tag
	 * @param value value of the tag
	 */
	public Tag(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * gets the name of the tag
	 * @return returns the name of the tag
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * sets the name of the tag
	 * @param name name of the tag
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * gets the value of the tag
	 * @return returns the value of the tag
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * sets the value of the tag
	 * @param value value of the tag
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return name + " : " + value;
	}
	
	@Override
	public int compareTo(Tag o) {	
		// check differences between 2 tag objects
		return 0;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(o == null || !(o instanceof Tag)) {
			return false;
		}
		
		Tag tag_obj = (Tag) o;
		
		return (tag_obj.name.equals(name)) && (tag_obj.value.equals(value));
	}

}
