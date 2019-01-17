package com.example.andre.android56;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AddAlbum extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_album);

        Button submit = findViewById(R.id.submit_add_album);
        Button cancel = findViewById(R.id.cancel_add_album);
        final EditText new_album_name = findViewById(R.id.album_name_text);
        TextView label = findViewById(R.id.album_name_label);

        /*
            Adds an album
         */
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String album_name = new_album_name.getText().toString().trim();
                Album temp = new Album(album_name);

                if(AccessibleAlbumsList.masterAlbumList.checkAlbumName(album_name.toLowerCase())){
                    AlertDialog alertDialog = new AlertDialog.Builder(AddAlbum.this).create();
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

                    return;
                }else{
                    AccessibleAlbumsList.masterAlbumList.addAlbum(temp);

                    System.out.println("MasterList: " + AccessibleAlbumsList.masterAlbumList.getAlbumsList());

                    finish();
                }
            }
        });

        /*
            Cancels the add
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
