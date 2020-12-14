package com.example.ridhdhish.sut;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.squareup.picasso.Picasso;


/**
 * Created by Acer on 31-01-2019.
 */
public class announcementAdapter extends BaseAdapter {
    ArrayList<announcementDataModel> arrayListAnnouncementDataModel = new ArrayList<announcementDataModel>();
    LayoutInflater inf;
    Context mcontext;
    String imgId;

    announcementAdapter(ArrayList<announcementDataModel> arrayListAnnouncementDataModel, Context context) {
        this.arrayListAnnouncementDataModel = arrayListAnnouncementDataModel;
        this.inf = LayoutInflater.from(context);
        this.mcontext = context;
    }

    @Override
    public int getCount() {
        return arrayListAnnouncementDataModel.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListAnnouncementDataModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final viewHolder vh;
        final announcementDataModel announcementDM = (announcementDataModel) getItem(position);
        if (convertView == null) {
            convertView = inf.inflate(R.layout.listview_announcement, null);
            vh = new viewHolder();
            vh.txtAnnouncementMessage = (TextView) convertView.findViewById(R.id.txtAnnouncementMessage);
            vh.txtAnnouncementDateTime = (TextView) convertView.findViewById(R.id.txtAnnouncementDateTime);
            vh.txtAnnouncementTitle = (TextView) convertView.findViewById(R.id.txtAnnouncementTitle);
            vh.imgAnnouncement = (ImageView) convertView.findViewById(R.id.imgAnnouncement);
            vh.imgAnnouncementId = (TextView) convertView.findViewById(R.id.imgAnnouncementId);
            convertView.setTag(vh);
        } else {
            vh = (viewHolder) convertView.getTag();
        }
        vh.txtAnnouncementMessage.setText(announcementDM.getTxtAnnouncementMessage().toString());
        vh.txtAnnouncementDateTime.setText(announcementDM.getTxtAnnouncementDateTime().toString());
        vh.txtAnnouncementTitle.setText(announcementDM.getTxtAnnouncementTitle().toString());
        vh.imgAnnouncementId.setText(announcementDM.getTxtImgId().toString());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                String src = announcementDM.getImgAnnouncement().toString();
//                final Bitmap b = getBitmapFromURL(src);
//                vh.imgAnnouncement.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        vh.imgAnnouncement.setImageBitmap(b);
//                    }
//                });
//
//            }
//        }).start();


        if (arrayListAnnouncementDataModel.get(position).getIsImage().equals("false") == true) {
            vh.imgAnnouncement.setVisibility(View.GONE);
        } else {
            vh.imgAnnouncement.setVisibility(View.VISIBLE);
            Picasso.with(mcontext)
                    .load(announcementDM.getImgAnnouncement().toString())
                    .placeholder(R.drawable.ic_image_blank)
                    .fit()
                    .centerCrop()
                    .into(vh.imgAnnouncement);
        }

        vh.imgAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext, announcementDisplay.class);
                i.putExtra("imgId", vh.imgAnnouncementId.getText().toString());
                mcontext.startActivity(i);
            }
        });

        return convertView;
    }

    public class viewHolder {
        TextView txtAnnouncementMessage;
        TextView txtAnnouncementDateTime;
        TextView txtAnnouncementTitle;
        TextView imgAnnouncementId;
        ImageView imgAnnouncement;
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
}
