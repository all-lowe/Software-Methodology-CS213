package com.example.andre.android56;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewPhotos extends AppCompatActivity implements Serializable {

    public final int REQUEST_CODE = 0;
    public final int REQUEST_CODE_2 = 0;
    public GridView gridView;
    public ArrayList<Photo> photos = new ArrayList<Photo>();
    public ArrayList<File> list;
    public PhotoImageAdapter photoImageAdapter;
    public int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photos);

        //list = imageReader(Environment.getExternalStorageDirectory());

        Button slideshowBtn = findViewById(R.id.slideshow_button);
        gridView = findViewById(R.id.gridview);
        photoImageAdapter = new PhotoImageAdapter(this, photos);
        //gridView.setAdapter(new GridAdapter());

        //populated the gridview
        update();
        photoImageAdapter.notifyDataSetChanged();
        gridView.setAdapter(photoImageAdapter);
        registerForContextMenu(gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Photo photo =   AccessibleAlbumsList.masterAlbumList.selectedAlbum.getAlbum().get(position);
                AccessibleAlbumsList.masterAlbumList.selectedAlbum.selectedPhoto = photo;
                viewSingleImage();
            }
        });

        slideshowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccessibleAlbumsList.masterAlbumList.selectedAlbum.getAlbum().size() == 0) {
                    AlertDialog alertDialog = new AlertDialog.Builder(ViewPhotos.this).create();
                    alertDialog.setTitle("Empty Album");
                    alertDialog.setMessage("Add a photo to view slideshow.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }else{
                    viewSlideShow();
                }
            }
        });
    }

    public void viewSlideShow(){
        Intent intent = new Intent(this, ViewSlideShow.class);
        startActivity(intent);
    }

    /*
        goes to the window that views a single image
    */
    public void viewSingleImage(){
        Intent viewFullImage = new Intent(this, ViewSingleImage.class);
        startActivity(viewFullImage);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.photo_options_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //return super.onContextItemSelected(item);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        pos = info.position;

        switch(item.getItemId()){
            case R.id.delete_photo:
                //System.out.println("Deleting Photo");
                AccessibleAlbumsList.masterAlbumList.selectedAlbum.deletePhoto(pos);

                gridView = findViewById(R.id.gridview);
                update();

                photoImageAdapter.notifyDataSetChanged();
                gridView.invalidateViews();
                gridView.setAdapter(photoImageAdapter);
                Toast.makeText(getApplicationContext(), "Photo Deleted", Toast.LENGTH_SHORT).show();

                //Serialize Here
                try {
                    serializeData(AccessibleAlbumsList.masterAlbumList);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            case R.id.move_photo:
                Photo photo =   AccessibleAlbumsList.masterAlbumList.selectedAlbum.getAlbum().get(pos);
                AccessibleAlbumsList.masterAlbumList.selectedAlbum.selectedPhoto = photo;
                movePhoto();

                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }

    public void movePhoto(){
        Intent intent = new Intent(this, MovePhoto.class);
        startActivityForResult(intent, REQUEST_CODE_2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photolist_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_photo:
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        //System.out.println("data: " + data);
        //System.out.println("Photos: " + AccessibleAlbumsList.masterAlbumList.selectedAlbum.getAlbum());

        if(requestCode == REQUEST_CODE && data != null){
            Uri uri = null;
            if(data != null){
                uri = data.getData();
            }

            String photopath = uri.toString();
            //System.out.println("photopath: " + photopath);
            Photo newPhoto = new Photo(photopath);
            AccessibleAlbumsList.masterAlbumList.selectedAlbum.addPhoto(newPhoto);
            //System.out.println("Photos: " + AccessibleAlbumsList.masterAlbumList.selectedAlbum.getAlbum());

            gridView = (GridView) findViewById(R.id.gridview);
            update();

            photoImageAdapter.notifyDataSetChanged();
            gridView.setAdapter(photoImageAdapter);

            //Serialize here
            try {
                serializeData(AccessibleAlbumsList.masterAlbumList);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(requestCode == REQUEST_CODE_2){
            update();
            photoImageAdapter.notifyDataSetChanged();
            gridView.setAdapter(photoImageAdapter);

            //Serialize Here
            try {
                serializeData(AccessibleAlbumsList.masterAlbumList);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{

        }
    }

    public static void serializeData(ListOfAllAlbums list) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/data/data/com.example.andre.android56/files/serialized.txt"));
        oos.writeObject(list);
        oos.close();
        System.out.println("Serialized");
    }

    /*
        updates the arrayList that contains the photo objects
     */
    public void update(){
        photos.clear();
        photos.addAll(AccessibleAlbumsList.masterAlbumList.selectedAlbum.getAlbum());
    }


}
