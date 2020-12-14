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
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class admin_detail extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imgAdminDetailsProfilePicture;
    TextView txtAdminDetailsName, txtAdminDetailsContactNumber, txtAdminDetailsEmailid, txtAdminDetailsFlatNumber;
    private static String url = "";
    private static String NAMESPACE = "";
    private static String SOAP_ACTION = "";
    private static String METHOD_NAME = "GuestUser_AdminDetails_Select";
    private static String imgPath = "siteimages/profilePictures/";
    private static String imgUrl = "";
    private static String image = "";
    SharedPreferences sharedPreferences;
    String adminId;

    LinearLayout llAdminDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail);

        imgUrl = getString(R.string.WEBSERVER_IP) + imgPath;
        NAMESPACE = getString(R.string.NAMESPACE);
        SOAP_ACTION = NAMESPACE + METHOD_NAME;
        url = getString(R.string.url);

        toolbar = (Toolbar) findViewById(R.id.GustAdminProfileToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Secretary");

        llAdminDetail = (LinearLayout) findViewById(R.id.llAdminDetail);

        imgAdminDetailsProfilePicture = (ImageView) findViewById(R.id.imgAdminDetailsProfilePicture);
        txtAdminDetailsName = (TextView) findViewById(R.id.txtAdminDetailsName);
        txtAdminDetailsContactNumber = (TextView) findViewById(R.id.txtAdminDetailsContactNumber);
        txtAdminDetailsEmailid = (TextView) findViewById(R.id.txtAdminDetailsEmailid);
        txtAdminDetailsFlatNumber = (TextView) findViewById(R.id.txtAdminDetailsFlatNumber);

        sharedPreferences = getSharedPreferences("GUPref", Context.MODE_PRIVATE);
        adminId = sharedPreferences.getString("GUadminId", null); // second parameter is default value
       // Toast.makeText(admin_detail.this, "" + adminId, Toast.LENGTH_SHORT).show();

        new AsyncAdminDetail().execute(adminId);
    }

    public class AsyncAdminDetail extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(admin_detail.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // this = YourActivity
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            llAdminDetail.setVisibility(View.GONE);
            dialog.show();
        }

        @Override
        protected SoapObject doInBackground(String... params) {

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
            afterAdminDetail(resultXML);
            dialog.dismiss();
            llAdminDetail.setVisibility(View.VISIBLE);
        }
    }

    public void afterAdminDetail(SoapObject resultXML) {

        //Toast.makeText(this, resultXML.toString(), Toast.LENGTH_LONG).show();

        String KEY_ITEM = "Admins";
        String AdminName = "AdminName";
        String AdminEmailid = "AdminEmailid";
        String AdminMobileNumber = "AdminMobileNumber";
        String AdminProfilePicture = "AdminProfilePicture";
        String AdminFlatNumber = "AdminFlatNumber";
        SoapObject table = null;
        try {
            if (resultXML.getPropertyCount() > 0) {
                table = (SoapObject) resultXML.getProperty(KEY_ITEM);
                String name = table.getProperty(AdminName).toString();
                String emailId = table.getProperty(AdminEmailid).toString();
                String number = table.getProperty(AdminMobileNumber).toString();
                final String profilePicture = table.getProperty(AdminProfilePicture).toString();
                String flatNumber = table.getProperty(AdminFlatNumber).toString();

                txtAdminDetailsName.setText(name);
                txtAdminDetailsContactNumber.setText(number);
                txtAdminDetailsEmailid.setText(emailId);
                txtAdminDetailsFlatNumber.setText(flatNumber);
                Toast.makeText(this, imgUrl + profilePicture, Toast.LENGTH_LONG);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        final Bitmap b = getBitmapFromURL(imgUrl + profilePicture);
                        imgAdminDetailsProfilePicture.post(new Runnable() {
                            @Override
                            public void run() {
                                imgAdminDetailsProfilePicture.setImageBitmap(b);
                            }
                        });
                    }
                }).start();
            }
        } catch (Exception e) {
            //Toast.makeText(admin_detail.this, "" + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(admin_detail.this, Guest_Dashboard.class);
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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