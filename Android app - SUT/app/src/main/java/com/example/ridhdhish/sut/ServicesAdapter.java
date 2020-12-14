package com.example.ridhdhish.sut;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Ridhdhish on 19-02-2019.
 */

public class ServicesAdapter extends BaseAdapter {

    ArrayList<ServicesDataModel> arrayListServicesDataModel = new ArrayList<>();
    LayoutInflater inf;
    Context mcontext;

    public ServicesAdapter(ArrayList<ServicesDataModel> dmobj, Context context)
    {
        this.arrayListServicesDataModel=dmobj;
        this.inf = LayoutInflater.from(context);
        this.mcontext=context;
    }

    @Override
    public int getCount() {
        return arrayListServicesDataModel.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListServicesDataModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final viewHolder v;
        final ServicesDataModel serviceDM = (ServicesDataModel) getItem(position);

        if (convertView == null) {
            convertView = inf.inflate(R.layout.listview_services, null);
            v = new ServicesAdapter.viewHolder();

            v.txtServicesInterfaceName = (TextView) convertView.findViewById(R.id.txtServicesInterfaceName);
            v.txtServiceInterfaceNumber = (TextView) convertView.findViewById(R.id.txtServiceInterfaceNumber);
//            v.imgServicesInterfaceProfile = (ImageView) convertView.findViewById(R.id.imgServicesInterfaceProfile);
            v.btnServicesInterfacePhone=(Button) convertView.findViewById(R.id.btnServicesInterfacePhone);

            convertView.setTag(v);
        }
        else {
            v = (ServicesAdapter.viewHolder) convertView.getTag();
        }
        v.txtServicesInterfaceName.setText(serviceDM.getTxtServicesInterfaceName().toString());
        v.txtServiceInterfaceNumber.setText(serviceDM.getTxtServiceInterfaceNumber().toString());

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                String src = serviceDM.getImgServicesInterfaceProfile().toString();
//                final Bitmap b = getBitmapFromURL(src);
//                v.imgServicesInterfaceProfile.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        v.imgServicesInterfaceProfile.setImageBitmap(b);
//                    }
//                });
//
//            }
//        }).start();

        v.btnServicesInterfacePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcontext.startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:"+serviceDM.getTxtServiceInterfaceNumber().toString())));
            }
        });
        return convertView;
    }

    public class viewHolder {
        TextView txtServicesInterfaceName;
        TextView txtServiceInterfaceNumber;
       // ImageView imgServicesInterfaceProfile;
        Button btnServicesInterfacePhone;
    }

//    public Bitmap getBitmapFromURL(String src) {
//        try {
//            java.net.URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("Exception", e.getMessage());
//            return null;
//        }
//    }
}
