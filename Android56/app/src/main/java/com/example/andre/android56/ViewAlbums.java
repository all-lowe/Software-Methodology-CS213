package com.example.andre.android56;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ViewAlbums extends AppCompatActivity implements Serializable {

    private ListView listview;
    //private ArrayList<Album> album_list;
    public File filename = new File("/data/data/com.example.andre.android56/files/serialized.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            ListOfAllAlbums loadedList = deserializeData();
            if(loadedList != null){
                AccessibleAlbumsList.masterAlbumList = loadedList;
            }
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_albums);

        if(!filename.exists()){
            Context context = this;
            File file= new File(context.getFilesDir(), "serialized.txt");

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        listview = findViewById(R.id.album_list);
        final Button searchButton = findViewById(R.id.search_photo_by_tag);

        displayList();

        System.out.println("SHOW UP ON TERMINAL:");
        System.out.println();

        // show movie for possible edit when tapped
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showAlbum(position);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPhoto();
            }
        });
    }

    public static void serializeData(ListOfAllAlbums list) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/data/data/com.example.andre.android56/files/serialized.txt"));
        oos.writeObject(list);
        oos.close();
        System.out.println("Serialized");
    }

    public ListOfAllAlbums deserializeData() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/data/data/com.example.andre.android56/files/serialized.txt"));
        ListOfAllAlbums loadedList = (ListOfAllAlbums) ois.readObject();
        ois.close();
        System.out.println("Deserialized");
        return loadedList;
    }
    
    public void searchPhoto(){
        Intent intent = new Intent(this, SearchPhotoByTag.class);
        startActivity(intent);
    }

    //opens the page where you add an album
    public void addAlbum(){
        Intent intent = new Intent(this, AddAlbum.class);
        startActivityForResult(intent, 1);
    }

    public void delAlbum(){
        Intent intent = new Intent(this, DeleteAlbum.class);
        //startActivity(intent);
        startActivityForResult(intent, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_album, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_album:
                System.out.println("Adding Album");
                addAlbum();
                //Toast.makeText(ViewAlbums.this,"Successfully Added Album", Toast.LENGTH_SHORT).show();
                //displayList();
                return true;

            case R.id.del_album:
                System.out.println("Deleting ALbum");
                delAlbum();
                //displayList();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAlbum(int pos) {
        Album album = AccessibleAlbumsList.masterAlbumList.getAlbumsList().get(pos);
        //TextView text = findViewById(R.id.clicked_item);
        //text.setText(album.getName());
        AccessibleAlbumsList.masterAlbumList.selectedAlbum = album;
        Intent intent = new Intent(this, AlbumInfo.class);
        startActivityForResult(intent, 1);

    }

    private void displayList(){
        System.out.println("refreshed.");
        listview.setAdapter(new ArrayAdapter<Album>(this, android.R.layout.simple_list_item_1, AccessibleAlbumsList.masterAlbumList.getAlbumsList()));

    }

    /*
        executes After a subsequent activity occurs, this is where i refresh the list
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        displayList();
        super.onActivityResult(requestCode, resultCode, data);

        //Serialize Here
        try {
            serializeData(AccessibleAlbumsList.masterAlbumList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
