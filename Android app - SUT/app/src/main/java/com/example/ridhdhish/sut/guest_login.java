package com.example.ridhdhish.sut;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class guest_login extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText GuestLoginEmailId, GuestLoginPassword;
    Button btnLogin;
    Intent i;
    LinearLayout llGuestRegister, llGuestForgot, llUserLogin;

    private static String SOAP_ACTION = "";
    private static String METHOD_NAME = "GuestUser_Login_Select";
    private static String NAMESPACE = "";
    private static String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_login);

        SOAP_ACTION = getResources().getString(R.string.NAMESPACE) + METHOD_NAME;
        url = getResources().getString(R.string.url);
        NAMESPACE = getResources().getString(R.string.NAMESPACE);

        llGuestRegister = (LinearLayout) findViewById(R.id.llGuestRegister);

        llGuestRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(guest_login.this, guest_registration.class);
                startActivity(i);
            }
        });

        llGuestForgot = (LinearLayout) findViewById(R.id.llGuestForgot);
        llGuestForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(guest_login.this, forgot_password.class);
                i.putExtra("Forgot", "Guest");
                startActivity(i);
            }
        });

        llUserLogin = (LinearLayout) findViewById(R.id.llUserLogin);
        llUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(guest_login.this, UserLogin.class);
                startActivity(i);
            }
        });

        GuestLoginEmailId = (EditText) findViewById(R.id.GuestLoginEmailId);
        GuestLoginPassword = (EditText) findViewById(R.id.GuestLoginPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailid = "", password = "";
                emailid = GuestLoginEmailId.getText().toString().toLowerCase();
                password = GuestLoginPassword.getText().toString().toLowerCase();

                new AsyncGuestLogin().execute(emailid, password);
            }
        });

        sharedPreferences = getSharedPreferences("GUPref", Context.MODE_PRIVATE);
    }

    public class AsyncGuestLogin extends AsyncTask<String, Void, SoapObject> {
        ProgressDialog dialog = new ProgressDialog(guest_login.this);

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
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("emailId");
            pi.setValue(params[0]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("password");
            pi.setValue(params[1]);
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
            afterAsyncGuestLogin(resultXML);
            dialog.dismiss();
        }
    }

    public void afterAsyncGuestLogin(SoapObject resultXML) {

        //Toast.makeText(this, resultXML.toString(), Toast.LENGTH_LONG).show();

        String KEY_ITEM = "GuestUser";
        String GUId = "GUId";
        String Emailid = "GUEmailid";
        String GUAdminId = "GUAdminId";
        String GUApartmentId = "GUApartmentId";
        SoapObject table = null;
        try {
            if (resultXML.getPropertyCount() > 0) {
                table = (SoapObject) resultXML.getProperty(KEY_ITEM);
                String emailId = table.getProperty(Emailid).toString();
                String apartmentId = table.getProperty(GUApartmentId).toString();
                String adminId = table.getProperty(GUAdminId).toString();
                String guId = table.getProperty(GUId).toString();


                sharedPreferences = getSharedPreferences("GUPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("GUEmailid", emailId);
                editor.putString("GUapartmentId", apartmentId);
                editor.putString("GUadminId", adminId);
                editor.putString("guId", guId);
                editor.commit();

                Intent i = new Intent(guest_login.this, Guest_Dashboard.class);
                startActivity(i);

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();

            }
        } catch (Exception e) {
            Toast.makeText(this, "Email Address or Password is invalid", Toast.LENGTH_SHORT).show();
        }

    }
}
