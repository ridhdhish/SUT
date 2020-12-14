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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static maes.tech.intentanim.CustomIntent.customType;

public class GuestChangePasswordActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edtGuestOldPassword, edtGuestNewPassword, edtGuestConfirmPassword;
    Button submit;
    SharedPreferences sharedPreferences;

    private static String url = "";
    private static String NAMESPACE = "";
    private static String SOAP_ACTION_Select = "";
    private static String SOAP_ACTION_Update = "";
    private static String METHOD_NAME_Select = "GuestUser_ChangePassword_Select";
    private static String METHOD_NAME_Update = "GuestUser_ChangePassword_Update";
    private static String originalOldPassword = "";

    String guId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_change_password);

        toolbar = (Toolbar) findViewById(R.id.GuestChangePasswordToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Change Password");

        sharedPreferences = getSharedPreferences("GUPref", Context.MODE_PRIVATE);
        guId = sharedPreferences.getString("guId", null).toString();
        //Toast.makeText(this, "" + sharedPreferences.getString("guId", null).toString(), Toast.LENGTH_SHORT).show();

        NAMESPACE = getString(R.string.NAMESPACE);
        SOAP_ACTION_Select = NAMESPACE + METHOD_NAME_Select;
        SOAP_ACTION_Update = NAMESPACE + METHOD_NAME_Update;
        url = getString(R.string.url);

        edtGuestOldPassword = (EditText) findViewById(R.id.edtGuestOldPassword);
        edtGuestNewPassword = (EditText) findViewById(R.id.edtGuestNewPassword);
        edtGuestConfirmPassword = (EditText) findViewById(R.id.edtGuestConfirmPassword);
        submit = (Button) findViewById(R.id.btnSubmit);

        new AsyncGuestChangePasswordSelect().execute(guId);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword, newPassword, confirmPassword;

                oldPassword = edtGuestOldPassword.getText().toString();
                newPassword = edtGuestNewPassword.getText().toString();
                confirmPassword = edtGuestConfirmPassword.getText().toString();


                if ((!oldPassword.isEmpty()) && (!newPassword.isEmpty()) && (!confirmPassword.isEmpty())) {
                    if (originalOldPassword.equals(oldPassword)) {

                        if (newPassword.equals(confirmPassword)) {
                            if (newPassword.equals(originalOldPassword)) {
                                Toast.makeText(GuestChangePasswordActivity.this, "Old Password and New Password Cannot be same", Toast.LENGTH_LONG).show();
                            } else {
                            /*
                            * Get UserId from SharedPrefernces
                            * */
                                new AsyncGuestChangePasswordUpdate().execute(sharedPreferences.getString("guId", null).toString(), newPassword);
                                startActivity(new Intent(GuestChangePasswordActivity.this, GuestSettingActivity.class));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Password and confirm passwords not match", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(GuestChangePasswordActivity.this, "Old password is wrong", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "One or more fields are empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public class AsyncGuestChangePasswordUpdate extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(GuestChangePasswordActivity.this);

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
            pi.setName("guId");
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
            afterGuestChangePasswordUpdate(resultXML);
            dialog.dismiss();
        }
    }

    public void afterGuestChangePasswordUpdate(SoapObject resultXML) {

        //Toast.makeText(this, resultXML.toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(GuestChangePasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
    }

    public class AsyncGuestChangePasswordSelect extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(GuestChangePasswordActivity.this);

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
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_Select);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("guId");
            pi.setValue(params[0]);
            //pi.setValue("1");
            pi.setType(String.class);
            request.addProperty(pi);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            try {
                HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
                androidHttpTransport.debug = true;
                androidHttpTransport.call(SOAP_ACTION_Select, envelope);

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
            afterGuestChangePasswordSelect(resultXML);
            dialog.dismiss();
        }
    }

    public void afterGuestChangePasswordSelect(SoapObject resultXML) {

        // Toast.makeText(this, resultXML.toString(), Toast.LENGTH_LONG).show();

        String KEY_ITEM = "GuestUser";
        String UserPassword = "GUPassword";
        SoapObject table = null;

        if (resultXML.getPropertyCount() > 0) {
            table = (SoapObject) resultXML.getProperty(KEY_ITEM);
            originalOldPassword = table.getProperty(UserPassword).toString();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(GuestChangePasswordActivity.this, GuestSettingActivity.class);
        customType(GuestChangePasswordActivity.this,"left-to-right");
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(GuestChangePasswordActivity.this, GuestSettingActivity.class);
                customType(GuestChangePasswordActivity.this,"left-to-right");
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
