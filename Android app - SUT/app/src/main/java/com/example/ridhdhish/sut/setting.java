package com.example.ridhdhish.sut;

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
import android.widget.LinearLayout;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static maes.tech.intentanim.CustomIntent.customType;

public class setting extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout llSettingProfile;
    LinearLayout llSettingLogout,llSettingChangePassword;
    SharedPreferences sharedPreferences;

    private static String SOAP_ACTION = "";
    private static String METHOD_NAME = "DeleteTokenAndIMEI";
    private static String NAMESPACE = "";
    private static String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar = (Toolbar) findViewById(R.id.settingToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Settings");
        llSettingChangePassword=(LinearLayout) findViewById(R.id.llSettingChangePassword);

        llSettingChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(setting.this, changePassword.class);
                startActivity(i);
            }
        });

    /*
        if (sharedPreferences != null) {

        } else {
            *//* If Shared Preference is null than it will redirect to Login activity *//*
            Intent i = new Intent(setting.this, UserLogin.class);
            startActivity(i);
        }
*/
        llSettingProfile = (LinearLayout) findViewById(R.id.llSettingProfile);
        llSettingProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(setting.this, profile.class);
                startActivity(i);
            }
        });

        llSettingLogout = (LinearLayout) findViewById(R.id.llSettingLogout);
        llSettingLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                SharedPreferences deviceInfo = getSharedPreferences("deviceInfo", Context.MODE_PRIVATE);
                new AsyncDeviceIdDelete().execute(sharedPreferences.getString("AndroidId", null));

                Intent i = new Intent(setting.this, UserLogin.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });


    }


    public class AsyncDeviceIdDelete extends AsyncTask<String, Void, SoapObject> {

        @Override
        protected SoapObject doInBackground(String... params) {

            SOAP_ACTION = getResources().getString(R.string.NAMESPACE) + METHOD_NAME;
            url = getResources().getString(R.string.url);
            NAMESPACE = getResources().getString(R.string.NAMESPACE);

            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("IMEI");
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
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i=new Intent(setting.this, navigationdrawer.class);
                customType(setting.this,"left-to-right");
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(setting.this, navigationdrawer.class);
        customType(setting.this,"left-to-right");
        startActivity(i);
        finish();
    }
}
