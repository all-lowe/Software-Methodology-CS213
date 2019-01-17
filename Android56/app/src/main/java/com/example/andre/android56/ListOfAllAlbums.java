package com.example.andre.android56;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListOfAllAlbums implements Serializable{
    /**
     * Serial ID to help with differing versions when serializing/deserializing
     */
    private static final long serialVersionUID = 1L;

    public ArrayList<Album> albums = new ArrayList<Album>();

    /**
     * selected album for the user
     */
    public Album selectedAlbum;
    /**
     * gets the list of albums for the user
     * @return returns the list of albums for the user
     */
    public ArrayList<Album> getAlbumsList() {
        return albums;
    }

    /**
     * sets the album list
     * @param album album list to be set
     */
    public void setAlbumList(ArrayList<Album> album) {
        this.albums = album;
    }

    /**
     * gets the other albums other than the selected album
     * @return returns a list of the other albums
     */
    public ArrayList<Album> getOtherAlbums() {
        ArrayList<Album> others = new ArrayList<Album>();
        for(Album a: this.albums) {
            if(a != selectedAlbum) {
                others.add(a);
            }
        }
        return others;
    }

    /**
     * adds an album to the album list
     * @param album album to be added to the album list
     */
    public void addAlbum(Album album) {
        albums.add(album);
    }

    /**
     * deletes an album from the album list
     * @param album album to be deleted from the album list
     */
    public void deleteAlbum(Album album) {
        albums.remove(album);
    }

    /**
     * checks the album name to see if it exists in the album list
     * @param name name of the album to be checked for
     * @return returns true if the album name exists already, false otherwise
     */
    public boolean checkAlbumName(String name) {

        for(Album album : albums) {

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

        for(int i = 0 ;  i< AccessibleAlbumsList.masterAlbumList.getAlbumsList().size(); i++){
            if(target.equals(AccessibleAlbumsList.masterAlbumList.getAlbumsList().get(i).getName())){
                AccessibleAlbumsList.masterAlbumList.getAlbumsList().get(i).setName(newAlbumName);
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
        for(Album a: albums){
            if(albumName.equals(a.getName())) {
                album = a;
                return album;
            }
        }
        return album;
    }



}
