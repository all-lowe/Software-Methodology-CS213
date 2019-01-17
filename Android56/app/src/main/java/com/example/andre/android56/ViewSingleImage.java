package com.example.andre.android56;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ViewSingleImage extends AppCompatActivity implements Serializable {

    public ImageView imageView;
    public ListView listView_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_image);

        imageView = findViewById(R.id.singleImageView);
        listView_tag = findViewById(R.id.listview_tags);

        registerForContextMenu(listView_tag);

        openImage();
        displayTags();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.photo_tag_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //return super.onContextItemSelected(item);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int pos = (int) info.position;
        switch(item.getItemId()){
            case R.id.delete_tag:
                AccessibleAlbumsList.masterAlbumList.selectedAlbum.selectedPhoto.deleteTag(pos);
                displayTags();

                //Serialize Here
                try {
                    serializeData(AccessibleAlbumsList.masterAlbumList);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_photo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.add_location_tag:
                System.out.println("adding a location tag");
                AlertDialog.Builder alert = new AlertDialog.Builder(ViewSingleImage.this);
                alert.setTitle("Location Tag");
                alert.setMessage("Please Enter a name for this tag");

                final EditText input = new EditText(this);
                alert.setView(input);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newTagName = input.getText().toString().trim().toLowerCase();
                        if(newTagName.isEmpty()){
                            Context context = getApplicationContext();
                            CharSequence text = "Error! Tag cannot be blank";
                            int length = Toast.LENGTH_SHORT;
                            Toast.makeText(context, text, length).show();

                            return;
                        }
                        if(AccessibleAlbumsList.masterAlbumList.selectedAlbum.selectedPhoto.checkIfTagExists("location", newTagName)){
                            Context context = getApplicationContext();
                            CharSequence text = "Error! Tag already exists.";
                            int length = Toast.LENGTH_SHORT;
                            Toast.makeText(context, text, length).show();

                            return;
                        }

                        AccessibleAlbumsList.masterAlbumList.selectedAlbum.selectedPhoto.addTag("location", newTagName);
                        displayTags();

                        //Serialize Here
                        try {
                            serializeData(AccessibleAlbumsList.masterAlbumList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();

                return true;
            case R.id.add_person_tag:
                System.out.println("adding a person tag");
                AlertDialog.Builder alert2 = new AlertDialog.Builder(ViewSingleImage.this);
                alert2.setTitle("Person Tag");
                alert2.setMessage("Please Enter a name for this tag");

                final EditText input2 = new EditText(this);
                alert2.setView(input2);

                alert2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newTagName = input2.getText().toString().trim().toLowerCase();
                        if(newTagName.isEmpty()){
                            Context context = getApplicationContext();
                            CharSequence text = "Error! Tag cannot be blank";
                            int length = Toast.LENGTH_SHORT;
                            Toast.makeText(context, text, length).show();

                            return;
                        }
                        if(AccessibleAlbumsList.masterAlbumList.selectedAlbum.selectedPhoto.checkIfTagExists("person", newTagName)){
                            Context context = getApplicationContext();
                            CharSequence text = "Error! Tag already exists.";
                            int length = Toast.LENGTH_SHORT;
                            Toast.makeText(context, text, length).show();

                            return;
                        }

                        AccessibleAlbumsList.masterAlbumList.selectedAlbum.selectedPhoto.addTag("person", newTagName);
                        displayTags();

                        //Serialize here
                        try {
                            serializeData(AccessibleAlbumsList.masterAlbumList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

                alert2.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog2 = alert2.create();
                alertDialog2.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void displayTags(){
        listView_tag = findViewById(R.id.listview_tags);
        listView_tag.setAdapter(new ArrayAdapter<Tag>(this, android.R.layout.simple_list_item_1, AccessibleAlbumsList.masterAlbumList.selectedAlbum.selectedPhoto.getTags()));
    }

    public void openImage(){
        //System.out.println("opening Image");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //System.out.println("opening Image2");
        Uri uri = Uri.parse(AccessibleAlbumsList.masterAlbumList.selectedAlbum.selectedPhoto.getPathToPhoto());
        imageView.setImageURI(uri);
    }

    public static void serializeData(ListOfAllAlbums list) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/data/data/com.example.andre.android56/files/serialized.txt"));
        oos.writeObject(list);
        oos.close();
        System.out.println("Serialized");
    }
}
