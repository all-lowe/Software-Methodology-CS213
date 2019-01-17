package com.example.andre.android56;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MovePhoto extends AppCompatActivity implements Serializable {

    public ListView listView;
    Button cancel;
    public ArrayList<Album> otherAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_photo);

        listView = findViewById(R.id.other_albums_list);
        cancel = findViewById(R.id.cancel_move_button);

        otherAlbums = getOtherAlbums(AccessibleAlbumsList.masterAlbumList.getAlbumsList());

        listView.setAdapter(new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, otherAlbums));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Album dest_album = otherAlbums.get(position);

                //add photo reference to the new album
                System.out.println(dest_album);
                Photo photo = AccessibleAlbumsList.masterAlbumList.selectedAlbum.selectedPhoto;
                dest_album.addPhoto(photo);

                //remove reference from current album
                ArrayList<Photo> new_photos_list = removePhotoSafely();
                AccessibleAlbumsList.masterAlbumList.selectedAlbum.setAlbum(new_photos_list);
                Toast.makeText(getApplicationContext(), "Photo Moved to: " + dest_album.getName(), Toast.LENGTH_SHORT).show();

                //Serialize Here
                try {
                    serializeData(AccessibleAlbumsList.masterAlbumList);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void serializeData(ListOfAllAlbums list) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/data/data/com.example.andre.android56/files/serialized.txt"));
        oos.writeObject(list);
        oos.close();
        System.out.println("Serialized");
    }

    public ArrayList<Photo> removePhotoSafely(){
        ArrayList<Photo> temp = new ArrayList<Photo>();
        for(Photo p: AccessibleAlbumsList.masterAlbumList.selectedAlbum.getAlbum()){
            if(p == AccessibleAlbumsList.masterAlbumList.selectedAlbum.selectedPhoto){
                continue;
            }
            temp.add(p);
        }
        return temp;
    }

    public ArrayList<Album> getOtherAlbums(ArrayList<Album> albums){
        ArrayList<Album> temp = new ArrayList<Album>();
        for(Album a: albums){
            if(a == AccessibleAlbumsList.masterAlbumList.selectedAlbum){
                continue;
            }
            temp.add(a);
        }
        return temp;
    }
}
