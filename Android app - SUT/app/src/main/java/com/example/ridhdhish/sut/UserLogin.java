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

public class UserLogin extends AppCompatActivity {
    EditText edtUserLoginEmailId, edtUserLoginPassword;
    Button btnUserLogin;
    LinearLayout llGuestUser, llUserLoginForgot;
    Intent i;
    private static String SOAP_ACTION = "";
    private static String METHOD_NAME = "Users_Login_Select";
    private static String NAMESPACE = "";
    private static String url = "";

    //MyFirebaseInstanceIdService fid = new MyFirebaseInstanceIdService();


    SharedPreferences sharedPreferences, GUsharedPreferences, deviceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        SOAP_ACTION = getResources().getString(R.string.NAMESPACE) + METHOD_NAME;
        url = getResources().getString(R.string.url);
        NAMESPACE = getResources().getString(R.string.NAMESPACE);
        llUserLoginForgot = (LinearLayout) findViewById(R.id.llUserLoginForgot);
        llGuestUser = (LinearLayout) findViewById(R.id.llGuestUser);

        llGuestUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(UserLogin.this, guest_login.class);
                startActivity(i);
            }
        });

        llUserLoginForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserLogin.this, forgot_password.class);
                i.putExtra("Forgot", "User");
                startActivity(i);
            }
        });
        edtUserLoginEmailId = (EditText) findViewById(R.id.edtUserLoginEmailId);
        edtUserLoginPassword = (EditText) findViewById(R.id.edtUserLoginPassword);

        btnUserLogin = (Button) findViewById(R.id.btnUserLogin);

        btnUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailid = "", password = "";
                emailid = edtUserLoginEmailId.getText().toString().toLowerCase();
                password = edtUserLoginPassword.getText().toString().toLowerCase();

                deviceInfo = getSharedPreferences("deviceInfo", Context.MODE_PRIVATE);
                String androidId = deviceInfo.getString("AndroidId", null);
                new AsyncLogin().execute(emailid, password, androidId);

            }
        });

//        fid.onTokenRefresh();
    }


    public class AsyncLogin extends AsyncTask<String, Void, SoapObject> {
        ProgressDialog dialog = new ProgressDialog(UserLogin.this);

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

            pi = new PropertyInfo();
            pi.setName("IMEI");
            pi.setValue(params[2]);
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
            afterAsyncLogin(resultXML);
            dialog.dismiss();
        }
    }

    public void afterAsyncLogin(SoapObject resultXML) {

        String KEY_ITEM = "Users";
        String Emailid = "UserEmailId";
        String Password = "UserPassword";
        String UserAdminId = "UserAdminId";
        String UserApartmentId = "UserApartmentId";
        String UserId = "UserId";
        SoapObject table = null;
        try {
            if (resultXML.getPropertyCount() > 0) {
                table = (SoapObject) resultXML.getProperty(KEY_ITEM);
                String emailId = table.getProperty(Emailid).toString();
                String userId = table.getProperty(UserId).toString();
                String adminId = table.getProperty(UserAdminId).toString();
                String apartmentId = table.getProperty(UserApartmentId).toString();

                sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Emailid", emailId);
                editor.putString("Userid", userId);
                editor.putString("adminId", adminId);
                editor.putString("apartmentId", apartmentId);
                editor.commit();

                Intent i = new Intent(UserLogin.this, navigationdrawer.class);
                startActivity(i);

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
            } else {
                Toast.makeText(UserLogin.this, "No data found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Wrong emailid or password", Toast.LENGTH_SHORT).show();
        }
    }
}