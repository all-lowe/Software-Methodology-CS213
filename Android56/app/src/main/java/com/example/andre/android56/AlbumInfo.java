package com.example.andre.android56;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/*
    When you click on an album, it will give you the name of the album if you want to modify it

    It also gives you the option to view the Photos in the album.
 */
public class AlbumInfo extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_info);

        Button save = findViewById(R.id.save_button);
        Button goBack = findViewById(R.id.back_button);
        final Button viewPhotos = findViewById(R.id.view_photos_button);
        final EditText al_text = findViewById(R.id.album_info_album_text);

        final Album curr_album = AccessibleAlbumsList.masterAlbumList.selectedAlbum;
        al_text.setText(curr_album.getName());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newname = al_text.getText().toString().trim();

                if(AccessibleAlbumsList.masterAlbumList.checkAlbumName(newname.toLowerCase())){
                    AlertDialog alertDialog = new AlertDialog.Builder(AlbumInfo.this).create();
                    alertDialog.setTitle("Duplicate Album!");
                    alertDialog.setMessage("Album name already exists. Please enter a different name.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                    al_text.setText(curr_album.getName());
                    return;
                }

                AccessibleAlbumsList.masterAlbumList.selectedAlbum.setName(newname);

                //Serialize Here
                try {
                    serializeData(AccessibleAlbumsList.masterAlbumList);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                finish();
            }
        });

        viewPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPhotoPage();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
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

    public void goToPhotoPage(){
        Intent intent = new Intent(this, ViewPhotos.class);
        startActivityForResult(intent, 1);
    }

}
