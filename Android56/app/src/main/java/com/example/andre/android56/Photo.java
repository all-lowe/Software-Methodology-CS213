package com.example.andre.android56;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//import javafx.scene.image.ImageView;

/**
 * Photo Class that handles all events relating to photos
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 *
 */
public class Photo implements Serializable, Comparable<Photo> {

    /**
     * Serial ID to help with differing versions when serializing/deserializing
     */
    private static final long serialVersionUID = 1L;

    /**
     * list of tags for the photo
     */
    private ArrayList<Tag> tags_list; //photos has a list of tags
    /**
     * caption for the photo
     */
    private String caption;
    /**
     * Calender for the photo
     */
    private Calendar calendar;
    /**
     * Date for the photo
     */
    private Date date;
    /**
     * String path to the photo
     */
    public String pathToPhoto;
    /**
     * last modified date for the photo
     */
    public long lastModifiedDate;
    /*
     * ImageView object for the photo
     */
    //private transient ImageView image;

    /**
     * Photo Constructor for the photo
     * @param path path to the photo
     * @param lastModifiedDate last modified date for the photo
     */
    public Photo(String path) {
        //tags_list = new ArrayList<Tag>();
        //this.caption = "";
        //this.calendar = Calendar.getInstance();
        //this.date = calendar.getTime();
        this.pathToPhoto = path;
        //this.lastModifiedDate = lastModifiedDate;
        this.tags_list = new ArrayList<Tag>();
    }

    /**
     * get the tags for the photo
     * @return returns the list of tags for the photo
     */
    public ArrayList<Tag> getTags(){
        return tags_list;
    }

    /**
     * get the copy of the list of tags
     * @return returns the copy of the list of tags
     */
    public ArrayList<Tag> getCopyOfTags(){
        ArrayList<Tag> othTags = new ArrayList<Tag>();
        for(Tag t: this.tags_list) {
            othTags.add(t);
        }

        return othTags;
    }
    /**
     * set the tags for the photo
     * @param tags list of tags
     */
    public void setTags(ArrayList<Tag> tags){
        this.tags_list = tags;
    }

    /**
     * adds a tag to the photo
     * @param name name of the tag
     * @param value value of the tag
     */
    public void addTag(String name, String value) {
        Tag tag = new Tag(name, value);
        tags_list.add(tag);
    }

    /**
     * deletes a tag from the photo
     * @param tag to be be deleted from the photo
     */
    public void deleteTag(Tag tag) {

        String name = tag.getName();
        String val = tag.getValue();

        for(Tag t : tags_list) {
            if(name.equals(t.getName()) && val.equals(t.getValue())) {
                tags_list.remove(tag);
            }
        }
    }

    /**
     * sets the caption for the photo
     * @param caption caption to be attached to the photo
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * gets the caption for the photo
     * @return returns the caption from the photo
     */
    public String getCaption() {
        return caption;
    }

    /*
    public String toString() {
        return pathToPhoto;
    }
    */

    /**
     * sets the path to the photo
     * @param newpath the new path for the photo
     */
    public void setPathToPhoto(String newpath) {
        this.pathToPhoto = newpath;
    }

    /**
     * gets the path to the photo
     * @return returns the path to the photo
     */
    public String getPathToPhoto() {
        return this.pathToPhoto;
    }

    //Sets the ImageView for a photo
    /*
     * sets the image of a photo
     * @param img the image to be attached to the photo
     */
    /*
    public void setImage(ImageView img) {
        this.image = img;
    }
    */

    //Gets the ImageVIew from a photo
    /*
     * get the image of a photo
     * @return returns the image of a photo
     */
    /*
    public ImageView getImage() {
        return this.image;
    }
    */

    /**
     * gets the last modified date of a photo
     * @return returns the last modified date
     */
    public long getlastModifiedDate() {
        return this.lastModifiedDate;
    }

    /**
     * sets the last modified date of a photo
     * @param newvalue the last modified date of a photo to be set
     */
    public void setlastModifiedDate(long newvalue) {
        this.lastModifiedDate = newvalue;
    }

    /**
     * Checks if a tag exists in a photo
     * @param tagName the tagname
     * @param tagValue the tagvalue
     * @return returns true if the tag-value pair exists, returns false otherwise
     */
    public boolean checkIfTagExists(String tagName, String tagValue) {
        tagName = tagName.trim();
        tagValue = tagValue.trim();
        for(Tag tagForPhoto: tags_list){

            if(tagName.equalsIgnoreCase(tagForPhoto.getName().toLowerCase()) && tagForPhoto.getValue().toLowerCase().contains(tagValue.toLowerCase())){
                //System.out.println("value: " + tagValue + " - " + tagForPhoto.getValue());
                return true;
            }
        }
        return false;
    }

    public void deleteTag(int position){
        tags_list.remove(position);
    }

    @Override
    public int compareTo(Photo o) {
        String photo1 = pathToPhoto.toLowerCase();
        String photo2 = o.pathToPhoto.toLowerCase();

        int check = photo1.compareTo(photo2);

        return check;
    }

}
