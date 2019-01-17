package com.example.andre.android56;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;

public class ViewSlideShow extends AppCompatActivity implements Serializable {

    public Photo currPhoto;
    public ImageView imageDisplayed;
    public int index;
    public int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slide_show);

        Button next = findViewById(R.id.next_button);
        Button prev = findViewById(R.id.prev_button);
        Button exit = findViewById(R.id.exit_slideshow_button);
        imageDisplayed = findViewById(R.id.slideshowImage);
        index = 0;
        size = AccessibleAlbumsList.masterAlbumList.selectedAlbum.getAlbumSize();

        //first picture
        currPhoto = AccessibleAlbumsList.masterAlbumList.selectedAlbum.getAlbum().get(index);

        openImage();

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == (size-1) ){
                    Toast.makeText(getApplicationContext(),"No Next Photo", Toast.LENGTH_SHORT).show();
                    return;
                }
                index = index + 1;
                currPhoto = AccessibleAlbumsList.masterAlbumList.selectedAlbum.getAlbum().get(index);
                openImage();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == 0){
                    Toast.makeText(getApplicationContext(),"No Prev Photo", Toast.LENGTH_SHORT).show();
                    return;
                }
                index = index - 1;
                currPhoto = AccessibleAlbumsList.masterAlbumList.selectedAlbum.getAlbum().get(index);
                openImage();
            }
        });

    }

    public void openImage(){
        //System.out.println("opening Image");
        //Intent intent = getIntent();
        //Bundle bundle = intent.getExtras();

        //System.out.println("opening Image2");
        Uri uri = Uri.parse(currPhoto.getPathToPhoto());
        imageDisplayed.setImageURI(uri);
    }
}
