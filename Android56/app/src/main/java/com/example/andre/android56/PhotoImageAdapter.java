package com.example.andre.android56;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

public class PhotoImageAdapter extends BaseAdapter implements Serializable {

    public Context mContext;
    public ArrayList<Photo> photolist;
    private View view;
    private LayoutInflater layoutInflater;

    public PhotoImageAdapter (Context mContext, ArrayList<Photo> photolist){
        this.mContext = mContext;
        this.photolist = photolist;
    }

    @Override
    public int getCount() {
        return photolist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            view = new View(mContext);
            view = layoutInflater.inflate(R.layout.photoview, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageview);

            Uri uri = Uri.parse(photolist.get(position).getPathToPhoto());
            imageView.setImageURI(uri);
        }else{

        }

        return view;
    }
}
