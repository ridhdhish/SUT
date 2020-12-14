package com.example.ridhdhish.sut;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class guest_flatholder extends AppCompatActivity {

    Toolbar toolbar;
    private static String url = "";
    private static String NAMESPACE = "";
    private static String SOAP_ACTION = "";
    private static String METHOD_NAME = "GuestUser_FlatHolder_Select";
    private static String imgUrl = "";
    String img="siteimages/profilePictures/";

    TextView txtGuestFlatHolder;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_flatholder);

        toolbar = (Toolbar) findViewById(R.id.GuestFlatHolderToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Flat Holders");
        txtGuestFlatHolder=(TextView) findViewById(R.id.txtGuestFlatHolder);

        imgUrl = getString(R.string.WEBSERVER_IP) + img;
        NAMESPACE = getString(R.string.NAMESPACE);
        SOAP_ACTION = NAMESPACE + METHOD_NAME;
        url = getString(R.string.url);

        sharedPreferences = getSharedPreferences("GUPref", Context.MODE_PRIVATE);
       // Toast.makeText(this, ""+ sharedPreferences.getString("GUapartmentId", null).toString(), Toast.LENGTH_SHORT).show();

        new AsyncGuestUserFlatHolders().execute(sharedPreferences.getString("GUapartmentId", null).toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(guest_flatholder.this, Guest_Dashboard.class);
                customType(guest_flatholder.this,"left-to-right");
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<GuestUserFlatHoldersDataModel> getData(SoapObject resultXML){
        ArrayList<GuestUserFlatHoldersDataModel> data = new ArrayList<GuestUserFlatHoldersDataModel>();
        GuestUserFlatHoldersDataModel dm;

        String KEY_ITEM = "Users";
        String name = "UserName";
        String flatNumber = "UserFlatNumber";
        String img = "UserProfilePicture";

        SoapObject table = null;
        try {
            if (resultXML.getPropertyCount() > 0) {
                for (int i = 0; i < resultXML.getPropertyCount(); i++) {
                    table = (SoapObject) resultXML.getProperty(i);
                    dm = new GuestUserFlatHoldersDataModel();
                    dm.setImgGuestUserFlatHolders(imgUrl + table.getProperty(img).toString());
                    dm.setTxtGuestUserFlatHoldersName(table.getProperty(name).toString());
                    dm.setTxtGuestUserFlatHoldersFlatNumber(table.getProperty(flatNumber).toString());
                    data.add(dm);
                }
            }
            txtGuestFlatHolder.setVisibility(View.GONE);
        }
        catch (Exception e){
           // txtGuestFlatHolder.setVisibility(View.VISIBLE);
           // Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
        }
        return data;
    }
    public class AsyncGuestUserFlatHolders extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(guest_flatholder.this);

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
            afterAsyncGuestUserFlatHolders(resultXML);
            dialog.dismiss();
        }
    }

    public void afterAsyncGuestUserFlatHolders(SoapObject resultXML) {
        ArrayList<GuestUserFlatHoldersDataModel> dm = getData(resultXML);
        ListView l1 = (ListView) findViewById(R.id.listviewGuestUserFlatHolders);
        l1.setAdapter(new GuestUserFlatHoldersAdapter(dm, this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(guest_flatholder.this, Guest_Dashboard.class);
        customType(guest_flatholder.this,"left-to-right");
        startActivity(i);
        finish();
    }
}
