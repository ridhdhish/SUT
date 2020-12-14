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


public class meeting extends AppCompatActivity {

    Toolbar toolbar;
    private String SOAP_ACTION = "";
    private static final String METHOD_NAME = "Users_Meetings_Select";
    private String NAMESPACE = "";
    private String url = "";
    SwipeRefreshLayout Meetingswiperefresh;

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_meeting);

        toolbar = (Toolbar) findViewById(R.id.meetingToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Meeting");

        SOAP_ACTION=getResources().getString(R.string.NAMESPACE)+ METHOD_NAME;
        NAMESPACE=getResources().getString(R.string.NAMESPACE);
        url=getResources().getString(R.string.url);

        sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);

        Meetingswiperefresh=(SwipeRefreshLayout) findViewById(R.id.Meetingswiperefresh);
        Meetingswiperefresh.setColorSchemeResources(R.color.colorPrimary);

        Meetingswiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new AsyncMeeting().execute(sharedPreferences.getString("apartmentId", null).toString());
                        Meetingswiperefresh.setRefreshing(false);
                    }
                }
        );
        new AsyncMeeting().execute(sharedPreferences.getString("apartmentId", null).toString());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i=new Intent(meeting.this, navigationdrawer.class);
                customType(meeting.this,"left-to-right");
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    ArrayList<meetingDataModel> getData(SoapObject resultXML)
    {
        ArrayList<meetingDataModel> data=new ArrayList<>();
        meetingDataModel dm;

        String KEY_ITEM = "Meetings";
        String meetingMessage = "MeetingMessage";
        String meetingDate = "MeetingDate";
        String meetingTime = "MeetingTime";

        SoapObject table=null;

        if(resultXML.getPropertyCount()>0){

            for(int i=0; i<resultXML.getPropertyCount(); i++)
            {
                table=(SoapObject) resultXML.getProperty(i);
                dm=new meetingDataModel();
                dm.setTxtMeetingInterfaceMessage(table.getProperty(meetingMessage).toString());
                dm.setTxtMeetingInterfaceDate(table.getProperty(meetingDate).toString());
                dm.setTxtMeetingInterfaceTime(table.getProperty(meetingTime).toString());
                data.add(dm);
            }
        }
        return data;
    }

    public class AsyncMeeting extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(meeting.this);

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
        protected SoapObject doInBackground(String... param) {
            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("apartmentId");
            pi.setValue(param[0]);
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
            afterAsyncMeeting(resultXML);
            dialog.dismiss();
        }

    }

    public void afterAsyncMeeting(SoapObject resultXML) {

        ArrayList<meetingDataModel> meetingDM = getData(resultXML);
        ListView l1 = (ListView) findViewById(R.id.listViewMeeting);
        l1.setAdapter(new meetingAdapter(meetingDM, this));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(meeting.this, navigationdrawer.class);
        customType(meeting.this,"left-to-right");
        startActivity(i);
        finish();
    }

}

