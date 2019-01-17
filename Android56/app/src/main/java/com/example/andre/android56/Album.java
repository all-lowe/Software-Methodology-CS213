package com.example.andre.android56;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

//import javafx.scene.image.ImageView;

/**
 * Album Class that handles all events relating to albums
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 *
 */
public class Album implements Serializable, Comparable<Album> {

    /**
     * Serial ID to help with differing versions when serializing/deserializing
     */
    private static final long serialVersionUID = 1L;
    /**
     * name of the album
     */
    private String name; //name of album
    /**
     * list of photos for the album
     */
    public ArrayList<Photo> photos_list; //album = a list of photos
    //public ImageView currentImage;

    /**
     * Constructor for the Album Class
     * @param name name of the album
     */
    public Album(String name) {
        this.name = name;
        photos_list = new ArrayList<Photo>();
    }

    /**
     * Gets the name of the album
     * @return returns the name of the album
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the album
     * @param name new name of the album
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the photo list
     * @return returns the photo list
     */
    public ArrayList<Photo> getAlbum(){
        return photos_list;

    }

    /**
     * sets the photo list
     * @param photos_list new list for the photo list
     */
    public void setAlbum(ArrayList<Photo> photos_list) {
        this.photos_list = photos_list;
    }

    public Photo selectedPhoto;

    /**
     * Gets the size of the album
     * @return returns the size of the album
     */
    public int getAlbumSize() {
        return photos_list.size();
    }

    /**
     * adds a photo to the photo list
     * @param photo the photo to be added
     */
    public void addPhoto(Photo photo) {
        photos_list.add(photo);
    }

    /**
     * deletes a photo from the photo list
     * @param photo photo to be deleted
     */
    public void deletePhoto(Photo photo) {
        photos_list.remove(photo);
    }

    public void deletePhoto(int position){
        photos_list.remove(position);
    }
/*
    public ArrayList<ImageView> getImages(){
        ArrayList<ImageView> result = new ArrayList<ImageView>();
        for(Photo photo: photos_list) {
            ImageView img = photo.getImage();
            result.add(img);
        }
        return result;
    }

    Photo getPhotoFromImage(ImageView img) {
        for(Photo photo: photos_list) {
            if(img == photo.getImage()) {
                return photo;
            }
        }
        return null;
    }
*/

    @Override
    public int compareTo(Album a) {

        String album1 = name.toLowerCase();
        String album2 = a.name.toLowerCase();

        int check = album1.compareTo(album2);

        return check;
    }

    public String toString() {
        return this.name;
    }


}