package com.example.ridhdhish.sut;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static maes.tech.intentanim.CustomIntent.customType;

public class guest_society_detail extends AppCompatActivity {

    Toolbar toolbar;
    private static String url = "";
    private static String NAMESPACE = "";
    private static String SOAP_ACTION_SOCIETY = "";
    private static String SOAP_ACTION_ROADMAP = "";
    private static String METHOD_NAME_Society = "GuestUser_Society_Details";
    private static String METHOD_NAME_Roadmap = "GuestUser_RoadMap_Select";
    private static String imgUrl = "";
    private static String image = "";
    String img = "siteimages/roadmaps/";

    LinearLayout llGuestSocietyDetailShow;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_society_detail);

        imgUrl = getString(R.string.WEBSERVER_IP) + img;
        NAMESPACE = getString(R.string.NAMESPACE);
        SOAP_ACTION_ROADMAP = NAMESPACE + METHOD_NAME_Roadmap;
        SOAP_ACTION_SOCIETY = NAMESPACE + METHOD_NAME_Society;
        url = getString(R.string.url);

        toolbar = (Toolbar) findViewById(R.id.GuestSocietyDetailToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Society Details");

        llGuestSocietyDetailShow=(LinearLayout) findViewById(R.id.llGuestSocietyDetailShow);

        sharedPreferences = getSharedPreferences("GUPref", Context.MODE_PRIVATE);

        new AsyncSocietyDetails().execute(sharedPreferences.getString("GUapartmentId", null).toString());
        new AsyncRoadmap().execute(sharedPreferences.getString("GUapartmentId", null).toString());
    }

    public class AsyncSocietyDetails extends AsyncTask<String, Void, SoapObject> {

        @Override
        protected SoapObject doInBackground(String... params) {

            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_Society);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("apartmentId");
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
                androidHttpTransport.call(SOAP_ACTION_SOCIETY, envelope);

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
            AfterSocietyDetails(resultXML);
        }
    }

    public void AfterSocietyDetails(SoapObject resultXML) {
        String KEY_ITEM = "Apartments";
        String ApartmentName = "ApartmentName";
        String ApartmentWingName = "ApartmentWingName";
        String ApartmentAddress = "ApartmentAddress";
        String ApartmentPincode = "ApartmentPincode";
        String ApartmentCity = "ApartmentCity";
        SoapObject table = null;

        try {
            if (resultXML.getPropertyCount() > 0) {
                table = (SoapObject) resultXML.getProperty(KEY_ITEM);
                String name = table.getProperty(ApartmentName).toString();
                String wingName = table.getProperty(ApartmentWingName).toString();
                String address = table.getProperty(ApartmentAddress).toString();
                String pincode = table.getProperty(ApartmentPincode).toString();
                String city = table.getProperty(ApartmentCity).toString();

                TextView txtSocietyAddress = (TextView) findViewById(R.id.txtGuestUserSocietyDetailsAddress);
                String fullAddress = wingName + " - " + name + ", " + address + ", " + city + " - " + pincode;
                txtSocietyAddress.setText(fullAddress);
            }
        }
        catch (Exception e)
        {

        }
    }



    public class AsyncRoadmap extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(guest_society_detail.this);

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
            llGuestSocietyDetailShow.setVisibility(View.GONE);
        }

        @Override
        protected SoapObject doInBackground(String... params) {
            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_Roadmap);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("apartmentId");
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
                androidHttpTransport.call(SOAP_ACTION_ROADMAP, envelope);

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
            AfterRoadmap(resultXML);
            dialog.dismiss();
            llGuestSocietyDetailShow.setVisibility(View.VISIBLE);
        }
    }

    public void AfterRoadmap(SoapObject resultXML) {

        String KEY_ITEM = "Roadmaps";
        String RoadMapImage = "RoadMapImage";
        SoapObject table = null;
        final ImageView imgGuestUserSocietyDetailsRoadmap;

        try {

            if (resultXML.getPropertyCount() > 0) {
                table = (SoapObject) resultXML.getProperty(KEY_ITEM);
                image = table.getProperty(RoadMapImage).toString();

                imgGuestUserSocietyDetailsRoadmap = (ImageView) findViewById(R.id.imgGuestUserSocietyDetailsRoadmap);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap b = getBitmapFromURL(imgUrl + image);
                        imgGuestUserSocietyDetailsRoadmap.post(new Runnable() {
                            @Override
                            public void run() {
                                imgGuestUserSocietyDetailsRoadmap.setImageBitmap(b);
                            }
                        });

                    }
                }).start();
            }
        }
        catch (Exception e){

        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(guest_society_detail.this, Guest_Dashboard.class);
        customType(guest_society_detail.this,"left-to-right");
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(guest_society_detail.this, Guest_Dashboard.class);
                customType(guest_society_detail.this,"left-to-right");
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
