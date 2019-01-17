package com.example.andre.android56;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class DeleteAlbum extends AppCompatActivity implements Serializable{

    ArrayList<Album> selecteditems = new ArrayList<Album>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_album);

        setContentView(R.layout.activity_delete_album);

        Button submit = findViewById(R.id.submit_delete_album);
        Button cancel = findViewById(R.id.cancel_delete_album);
        ListView listview = findViewById(R.id.delete_album_list);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listview.setAdapter(new ArrayAdapter<Album>(this, R.layout.rowlayout,R.id.txt_lan, AccessibleAlbumsList.masterAlbumList.getAlbumsList()));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Album selectedItem = AccessibleAlbumsList.masterAlbumList.getAlbumsList().get(position);
                if(selecteditems.contains(selectedItem)){
                    selecteditems.remove(selectedItem); // uncheck item
                }else{
                    selecteditems.add(selectedItem);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Album a: selecteditems){
                    System.out.println(a);
                    AccessibleAlbumsList.masterAlbumList.deleteAlbum(a);
                }

                //Serialize Here

                System.out.println(AccessibleAlbumsList.masterAlbumList.getAlbumsList());
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
    public void showSelectedItems(){
        System.out.println("selecteditems: " + selecteditems);
    }
}
