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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Acer on 19-02-2019.
 */
public class GuestUserFlatHoldersAdapter extends BaseAdapter {
    ArrayList<GuestUserFlatHoldersDataModel> guestUserFlatHoldersDataModel = new ArrayList<>();
    LayoutInflater inf;
    Context mcontext;

    GuestUserFlatHoldersAdapter(ArrayList<GuestUserFlatHoldersDataModel> guestUserFlatHoldersDataModel, Context context) {
        this.guestUserFlatHoldersDataModel = guestUserFlatHoldersDataModel;
        this.inf = LayoutInflater.from(context);
        this.mcontext=context;
    }

    @Override
    public int getCount() {
        return guestUserFlatHoldersDataModel.size();
    }

    @Override
    public Object getItem(int position) {
        return guestUserFlatHoldersDataModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final viewHolder vh;
        final GuestUserFlatHoldersDataModel dm = (GuestUserFlatHoldersDataModel) getItem(position);

        if (convertView == null) {
            convertView = inf.inflate(R.layout.listview_guest_user_flatholders, null);
            vh = new viewHolder();

            vh.txtGuestUserFlatHoldersFlatNumber=(TextView) convertView.findViewById(R.id.txtGuestUserFlatHoldersFlatNumber);
            vh.txtGuestUserFlatHoldersName=(TextView) convertView.findViewById(R.id.txtGuestUserFlatHoldersName);
            vh.imgGuestUserFlatHolders=(ImageView) convertView.findViewById(R.id.imgGuestUserFlatHolders);
            vh.llGuestFlatHolder = (LinearLayout) convertView.findViewById(R.id.llGuestFlatHolder);
            convertView.setTag(vh);
        } else {
            vh = (viewHolder) convertView.getTag();
        }

        vh.txtGuestUserFlatHoldersFlatNumber.setText(dm.getTxtGuestUserFlatHoldersFlatNumber().toString());
        vh.txtGuestUserFlatHoldersName.setText(dm.getTxtGuestUserFlatHoldersName().toString());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                String src = dm.getImgGuestUserFlatHolders().toString();
//                final Bitmap b = getBitmapFromURL(src);
//                vh.imgGuestUserFlatHolders.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        vh.imgGuestUserFlatHolders.setImageBitmap(b);
//                    }
//                });
//
//            }
//        }).start();

        Picasso.with(mcontext)
                .load(dm.getImgGuestUserFlatHolders().toString())
                .placeholder(R.drawable.ic_image_blank)
                .fit()
                .centerCrop()
                .into(vh.imgGuestUserFlatHolders);

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
        ImageView imgGuestUserFlatHolders;
        TextView txtGuestUserFlatHoldersName;
        TextView txtGuestUserFlatHoldersFlatNumber;
        LinearLayout llGuestFlatHolder;
    }
}
