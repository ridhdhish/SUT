package com.example.ridhdhish.sut;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Ridhdhish on 31-01-2019.
 */

public class serviceAdapter extends BaseAdapter {

    ArrayList<serviceDataModel> arrayListServiceDataModel = new ArrayList<>();
    LayoutInflater inf;
    Context mcontext;
    PopupWindow mPopupWindow;

    public serviceAdapter(ArrayList<serviceDataModel> dmobj, Context context) {
        this.arrayListServiceDataModel = dmobj;
        this.inf = LayoutInflater.from(context);
        this.mcontext = context;
    }

    @Override
    public int getCount() {
        return arrayListServiceDataModel.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListServiceDataModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, final ViewGroup parent) {
        final viewHolder v;
        final serviceDataModel serviceDM = (serviceDataModel) getItem(position);

        if (view == null) {
            view = inf.inflate(R.layout.listview_service, null);
            v = new viewHolder();

            v.txtServiceInterface = (TextView) view.findViewById(R.id.txtServiceInterface);
            v.txtServiceInterfaceid = (TextView) view.findViewById(R.id.txtServiceInterfaceid);
            v.imgServiceInterface = (ImageView) view.findViewById(R.id.imgServiceInterface);
            v.layoutServiceInterface = (LinearLayout) view.findViewById(R.id.layoutServiceInterface);

            view.setTag(v);
        } else {
            v = (viewHolder) view.getTag();
        }
        v.txtServiceInterface.setText(serviceDM.getTxtServiceInterface().toString());
        v.txtServiceInterfaceid.setText(serviceDM.getLayoutServiceInterface().toString());

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                String src = serviceDM.getImage().toString();
//                final Bitmap b = getBitmapFromURL(src);
//                v.imgServiceInterface.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        v.imgServiceInterface.setImageBitmap(b);
//                    }
//                });
//
//            }
//        }).start();

        Picasso.with(mcontext)
                .load(serviceDM.getImage().toString())
                .placeholder(R.drawable.ic_image_blank)
                .fit()
                .centerCrop()
                .into(v.imgServiceInterface);

        v.layoutServiceInterface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickView) {


                Intent i = new Intent(mcontext, Services.class);
                i.putExtra("serviceid", v.txtServiceInterfaceid.getText().toString());
                i.putExtra("serviceName", v.txtServiceInterface.getText().toString());
                mcontext.startActivity(i);

                /*LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.activity_services, null);

                mPopupWindow = new PopupWindow(
                        customView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

                if (Build.VERSION.SDK_INT >= 21) {
                    mPopupWindow.setElevation(5.0f);
                }

                mPopupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);*/
            }
        });

        return view;
    }

    public class viewHolder {
        TextView txtServiceInterface;
        TextView txtServiceInterfaceid;
        ImageView imgServiceInterface;
        LinearLayout layoutServiceInterface;
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
