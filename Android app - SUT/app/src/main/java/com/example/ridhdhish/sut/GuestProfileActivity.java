package com.example.ridhdhish.sut;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static maes.tech.intentanim.CustomIntent.customType;

public class GuestProfileActivity extends AppCompatActivity {

    private static String METHOD_NAME = "GuestUser_Profile_Select";
    private static String NAMESPACE = "";
    private static String SOAP_ACTION = "";
    private static String METHOD_NAME_Update = "GuestUser_Profile_Update";
    private static String SOAP_ACTION_Update = "";
    private static String url = "";

    SharedPreferences sharedPreferences;
    Toolbar toolbar;
    String USER_ID;

    LinearLayout llGuestProfile;

    Button btnedit, btnconfirm;
    TextView lblGuestProfileName, lblGuestProfileEmailId, lblGuestProfileMobile, lblGuestProfileGender;
    EditText edtGuestProfileName, edtGuestProfileEmailId, edtGuestProfileMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_profile);

        sharedPreferences = getSharedPreferences("GUPref", Context.MODE_PRIVATE);
        USER_ID = sharedPreferences.getString("guId", null);

        toolbar = (Toolbar) findViewById(R.id.GuestProfileToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Profile");

        btnedit=(Button) findViewById(R.id.btnedit);
        btnconfirm=(Button) findViewById(R.id.btnconfirm);
        llGuestProfile = (LinearLayout) findViewById(R.id.llGuestProfile);

        lblGuestProfileName=(TextView) findViewById(R.id.lblGuestProfileName);
        lblGuestProfileEmailId=(TextView) findViewById(R.id.lblGuestProfileEmailId);
        lblGuestProfileMobile=(TextView) findViewById(R.id.lblGuestProfileMobile);

        edtGuestProfileName=(EditText) findViewById(R.id.edtGuestProfileName);
        edtGuestProfileEmailId=(EditText) findViewById(R.id.edtGuestProfileEmailId);
        edtGuestProfileMobile=(EditText) findViewById(R.id.edtGuestProfileMobile);

        NAMESPACE = getString(R.string.NAMESPACE);
        SOAP_ACTION = NAMESPACE + METHOD_NAME;
        SOAP_ACTION_Update = NAMESPACE + METHOD_NAME_Update;
        url = getResources().getString(R.string.url);

        new AsyncGuestProfile().execute(USER_ID);

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnedit.setVisibility(View.GONE);
                btnconfirm.setVisibility(View.VISIBLE);

                lblGuestProfileName.setVisibility(View.GONE);
                edtGuestProfileName.setVisibility(View.VISIBLE);

                lblGuestProfileEmailId.setVisibility(View.GONE);
                edtGuestProfileEmailId.setVisibility(View.VISIBLE);

                lblGuestProfileMobile.setVisibility(View.GONE);
                edtGuestProfileMobile.setVisibility(View.VISIBLE);
            }
        });

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, email, mobile, gender = "", profile;
                name = edtGuestProfileName.getText().toString();
                email = edtGuestProfileEmailId.getText().toString();
                mobile = edtGuestProfileMobile.getText().toString();

                try {

                    new AsyncGuestProfileUpdate().execute(USER_ID, name, email, mobile);
                    Thread.sleep(1000);
                    new AsyncGuestProfile().execute(USER_ID);
//                    Intent i=new Intent(profile.this, profile.class);
//                    startActivity(i);
                } catch (Exception e) {

                }

                btnedit.setVisibility(View.VISIBLE);
                btnconfirm.setVisibility(View.GONE);

                lblGuestProfileName.setVisibility(View.VISIBLE);
                edtGuestProfileName.setVisibility(View.GONE);

                lblGuestProfileEmailId.setVisibility(View.VISIBLE);
                edtGuestProfileEmailId.setVisibility(View.GONE);

                lblGuestProfileMobile.setVisibility(View.VISIBLE);
                edtGuestProfileMobile.setVisibility(View.GONE);

                sharedPreferences = getSharedPreferences("GUPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("GUEmailid", email);
                editor.commit();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(GuestProfileActivity.this, GuestSettingActivity.class);
                customType(GuestProfileActivity.this,"left-to-right");
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public class AsyncGuestProfile extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(GuestProfileActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // this = YourActivity
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            llGuestProfile.setVisibility(View.GONE);
            dialog.show();
        }

        @Override
        protected SoapObject doInBackground(String... params) {

            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("guestId");
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
            afterAsyncProfile(resultXML);
            dialog.dismiss();
            llGuestProfile.setVisibility(View.VISIBLE);
        }
    }

    public void afterAsyncProfile(SoapObject resultXML) {

        //Toast.makeText(this, resultXML.toString(), Toast.LENGTH_LONG).show();

        String KEY_ITEM = "GuestUsers";
        String Emailid = "GUEmailid";
        String Name = "GUName";
        String Mobile = "GUMobile";
        SoapObject table = null;

        if (resultXML.getPropertyCount() > 0) {
            table = (SoapObject) resultXML.getProperty(KEY_ITEM);
            String e = table.getProperty(Emailid).toString();
            String n = table.getProperty(Name).toString();
            String m = table.getProperty(Mobile).toString();

            lblGuestProfileEmailId.setText(e);
            lblGuestProfileName.setText(n);
            lblGuestProfileMobile.setText(m);

            edtGuestProfileName.setText(n);
            edtGuestProfileEmailId.setText(e);
            edtGuestProfileMobile.setText(m);
        }

    }

    public class AsyncGuestProfileUpdate extends AsyncTask<String, Void, SoapObject> {
        ProgressDialog dialog = new ProgressDialog(GuestProfileActivity.this);

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
        protected SoapObject doInBackground(String... params) {

            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_Update);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("guestId");
            pi.setValue(params[0]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("name");
            pi.setValue(params[1]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("emailId");
            pi.setValue(params[2]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("mobile");
            pi.setValue(params[3]);
            pi.setType(String.class);
            request.addProperty(pi);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            try {
                HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
                androidHttpTransport.debug = true;
                androidHttpTransport.call(SOAP_ACTION_Update, envelope);

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
            dialog.dismiss();
            afterProfileUpdate(resultXML);
        }
    }

    public void afterProfileUpdate(SoapObject resultXML) {
        Toast.makeText(GuestProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(GuestProfileActivity.this, GuestSettingActivity.class);
        customType(GuestProfileActivity.this,"left-to-right");
        startActivity(i);
        finish();
    }

}
