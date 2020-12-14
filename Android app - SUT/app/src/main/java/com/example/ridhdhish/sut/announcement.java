package com.example.ridhdhish.sut;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;


public class announcement extends AppCompatActivity {

    Toolbar toolbar;
    private static String url = "";
    private static String NAMESPACE = "";
    private static String SOAP_ACTION = "";
    private static String METHOD_NAME = "Users_Announcement_Select";
    private static String imgUrl = "";
    String img = "siteimages/announcements/";
    SharedPreferences sharedPreferences;

    SwipeRefreshLayout swiperefresh;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_announcement);

        imgUrl = getString(R.string.WEBSERVER_IP) + img;
        NAMESPACE = getString(R.string.NAMESPACE);
        SOAP_ACTION = NAMESPACE + METHOD_NAME;
        url = getString(R.string.url);

        toolbar = (Toolbar) findViewById(R.id.announcementToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Announcement");

        swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeResources(R.color.colorPrimary);
        sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);

        swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new AsyncAnnouncement().execute(sharedPreferences.getString("adminId", null).toString());
                        swiperefresh.setRefreshing(false);
                    }
                }
        );

        new AsyncAnnouncement().execute(sharedPreferences.getString("adminId", null).toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(announcement.this, navigationdrawer.class);
                customType(announcement.this, "left-to-right");
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<announcementDataModel> getData(SoapObject resultXML) {
        ArrayList<announcementDataModel> data = new ArrayList<announcementDataModel>();
        announcementDataModel announcementDM;

        String KEY_ITEM = "Announcements";
        String title = "AnnouncementTitle";
        String message = "AnnouncementMessage";
        String time = "AnnouncementTime";
        String date = "AnnouncementDate";
        String image = "AnnouncementImage";
        String important = "AnnouncementImportant";
        String imgId = "AnnouncementId";

        SoapObject table = null;
        if (resultXML.getPropertyCount() > 0) {
            for (int i = 0; i < resultXML.getPropertyCount(); i++) {
                table = (SoapObject) resultXML.getProperty(i);

                announcementDM = new announcementDataModel();
                if (table.getProperty(image).toString().equals("false") == true) {
                    announcementDM.setIsImage("false");
                } else {
                    announcementDM.setIsImage("true");
                    announcementDM.setImgAnnouncement(imgUrl + table.getProperty(image).toString());
                }
                announcementDM.setImgName(table.getProperty(image).toString());
                announcementDM.setTxtAnnouncementMessage(table.getProperty(message).toString());
                announcementDM.setTxtAnnouncementDateTime(table.getProperty(date).toString() + " " + table.getProperty(time).toString());
                announcementDM.setTxtAnnouncementTitle(table.getProperty(title).toString());
                announcementDM.setTxtImgId(table.getProperty(imgId).toString());
                /*
                if(imp) then do something
                * */
                data.add(announcementDM);
            }
        }
        return data;
    }

    public class AsyncAnnouncement extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(announcement.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // this = YourActivity
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected SoapObject doInBackground(String[] params) {
            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("adminId");
            pi.setValue(params[0]);
            pi.setType(String.class);
            request.addProperty(pi);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            try {
                HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
                androidHttpTransport.debug = true;
                androidHttpTransport.call(SOAP_ACTION, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                SoapObject diffgram = (SoapObject) response.getProperty("diffgram");

                if (diffgram.getPropertyCount() > 0) {
                    documentElement = (SoapObject) diffgram.getProperty("DocumentElement");
                }
            } catch (Exception E) {
                Log.e("Error-->> ", E.toString());
            }

            return documentElement;
        }

        @Override
        protected void onPostExecute(SoapObject resultXML) {
            super.onPostExecute(resultXML);
            afterAsyncAnnouncement(resultXML);
            dialog.dismiss();
        }
    }

    public void afterAsyncAnnouncement(SoapObject resultXML) {
        ArrayList<announcementDataModel> announcementDM = getData(resultXML);
        ListView l1 = (ListView) findViewById(R.id.listviewAnnouncements);
        l1.setAdapter(new announcementAdapter(announcementDM, this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(announcement.this, navigationdrawer.class);
        customType(announcement.this, "left-to-right");
        startActivity(i);
        finish();
    }
}
