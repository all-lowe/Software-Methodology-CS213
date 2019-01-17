package com.example.andre.android56;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchPhotoByTag extends AppCompatActivity {

    public EditText tag_value;
    public ArrayList<Photo> photos = new ArrayList<Photo>();
    public Spinner dropDownOptions;
    public PhotoImageAdapter photoImageAdapter;
    public GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_photo_by_tag);

        dropDownOptions = findViewById(R.id.option_spinner);

        String[] options = new String[]{"location", "person", "both tags"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        dropDownOptions.setAdapter(adapter);

        photoImageAdapter = new PhotoImageAdapter(SearchPhotoByTag.this, photos);
        gridView = findViewById(R.id.search_gridview);
        gridView.setAdapter(photoImageAdapter);

        //be sure to check if this is empty
        tag_value = findViewById(R.id.search_tag_value);
        Button search_Btn = findViewById(R.id.search_for_photos_button);

        search_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(dropDownOptions.getSelectedItem().toString());
                //System.out.println(tag_value.getText().toString());
                String type = dropDownOptions.getSelectedItem().toString();
                //System.out.println(type);
                if(type.equals("location") || type.equals("person")){
                    photos = findPhotos();
                    if(photos.size() < 1){
                        AlertDialog alertDialog = new AlertDialog.Builder(SearchPhotoByTag.this).create();
                        alertDialog.setTitle("Empty Search");
                        alertDialog.setMessage("No photos found that match the search criteria.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                    displayResultImages();

                }else if(type.equals("both tags")){
                    photos = findBothPhotos();
                    if(photos.size() < 1){
                        AlertDialog alertDialog = new AlertDialog.Builder(SearchPhotoByTag.this).create();
                        alertDialog.setTitle("Empty Search");
                        alertDialog.setMessage("No photos found that match the search criteria.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                    displayResultImages();
                }else{
                    Toast.makeText(getApplicationContext(), "No Tag Type Selected.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    public ArrayList<Photo> findPhotos(){
        System.out.println("trying to find photos");
        ArrayList<Photo> result = new ArrayList<>();
        for(Album a: AccessibleAlbumsList.masterAlbumList.getAlbumsList()){
            for(Photo p: a.getAlbum()){
                String tag_name = dropDownOptions.getSelectedItem().toString();
                if(p.checkIfTagExists(tag_name, tag_value.getText().toString().trim().toLowerCase())){
                    result.add(p);
                }
            }
        }
        System.out.println("result: "+ result);
        return result;
    }

    public ArrayList<Photo> findBothPhotos(){
        System.out.println("trying to find photos");
        ArrayList<Photo> result = new ArrayList<>();
        for(Album a: AccessibleAlbumsList.masterAlbumList.getAlbumsList()){
            for(Photo p: a.getAlbum()){
                if(p.checkIfTagExists("location", tag_value.getText().toString().trim().toLowerCase()) || p.checkIfTagExists("person", tag_value.getText().toString().trim().toLowerCase())){
                    result.add(p);
                }
            }
        }
        System.out.println("result: "+ result);
        return result;
    }



    public void displayResultImages(){
        gridView = findViewById(R.id.search_gridview);
        photoImageAdapter = new PhotoImageAdapter(SearchPhotoByTag.this, photos);
        gridView.setAdapter(photoImageAdapter);
    }

}
