package com.example.ridhdhish.sut;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Acer on 01-02-2019.
 */
public class usersAdapter extends BaseAdapter {
    ArrayList<usersDataModel> usersDM = new ArrayList<usersDataModel>();
    LayoutInflater inf;
    Context mcontext;

    usersAdapter(ArrayList<usersDataModel> usersDM, Context context) {
        this.usersDM = usersDM;
        this.inf = LayoutInflater.from(context);
        this.mcontext = context;
    }

    @Override
    public int getCount() {
        return usersDM.size();
    }

    @Override
    public Object getItem(int position) {
        return usersDM.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final viewHolder vh;
        final usersDataModel usersDM = (usersDataModel) getItem(position);
        if (convertView == null) {
            convertView = inf.inflate(R.layout.listview_users, null);
            vh = new viewHolder();
            vh.imgUsers = (ImageView) convertView.findViewById(R.id.imgUsers);
            vh.txtUsersName = (TextView) convertView.findViewById(R.id.txtUsersName);
            vh.txtUsersFlatNumber = (TextView) convertView.findViewById(R.id.txtUsersFlatNumber);
            convertView.setTag(vh);
        } else {
            vh = (viewHolder) convertView.getTag();
        }
        vh.txtUsersName.setText(usersDM.getTxtUsersName().toString());
        vh.txtUsersFlatNumber.setText(usersDM.getTxtUsersFlatNumber().toString());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String src = usersDM.getImgUsers().toString();
//                final Bitmap b = getBitmapFromURL(src);
//                vh.imgUsers.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        vh.imgUsers.setImageBitmap(b);
//                    }
//                });
//            }
//        }).start();

        Picasso.with(mcontext)
                .load(usersDM.getImgUsers().toString())
                .placeholder(R.drawable.user)
                .fit()
                .centerCrop()
                .into(vh.imgUsers);

        return convertView;
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }

    public class viewHolder {
        ImageView imgUsers;
        TextView txtUsersName;
        TextView txtUsersFlatNumber;
    }
}
