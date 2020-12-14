package com.example.ridhdhish.sut;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class changePassword extends AppCompatActivity {

    EditText edtOldPassword, edtNewPassword, edtConfirmPassword;
    Button submit;
    Toolbar toolbar;

    private static String url = "";
    private static String NAMESPACE = "";
    private static String SOAP_ACTION_Select = "";
    private static String SOAP_ACTION_Update = "";
    private static String METHOD_NAME_Select = "Users_ChangePassword_Select";
    private static String METHOD_NAME_Update = "Users_ChangePassword_Update";
    private static String originalOldPassword = "";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        toolbar = (Toolbar) findViewById(R.id.changePasswordToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Change Password");

        sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        final String userId = sharedPreferences.getString("Userid", null);

        NAMESPACE = getString(R.string.NAMESPACE);
        SOAP_ACTION_Select = NAMESPACE + METHOD_NAME_Select;
        SOAP_ACTION_Update = NAMESPACE + METHOD_NAME_Update;
        url = getString(R.string.url);

        edtOldPassword = (EditText) findViewById(R.id.edtChangePasswordOldPassword);
        edtNewPassword = (EditText) findViewById(R.id.edtChangePasswordNewPassword);
        edtConfirmPassword = (EditText) findViewById(R.id.edtChangePasswordNewConfirmPassword);
        submit = (Button) findViewById(R.id.btnSubmit);

        new AsyncChangePasswordSelect().execute(userId);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword, newPassword, confirmPassword;

                oldPassword = edtOldPassword.getText().toString();
                newPassword = edtNewPassword.getText().toString();
                confirmPassword = edtConfirmPassword.getText().toString();


                if ((!oldPassword.isEmpty()) && (!newPassword.isEmpty()) && (!confirmPassword.isEmpty())) {
                    if (originalOldPassword.equals(oldPassword)) {

                        if (newPassword.equals(confirmPassword)) {
                            if(newPassword.equals(originalOldPassword)){
                                Toast.makeText(changePassword.this, "Old Password and New Password Cannot be same", Toast.LENGTH_LONG).show();
                            }
                            else {
                            new AsyncChangePasswordUpdate().execute(userId, newPassword);
                            startActivity(new Intent(changePassword.this, setting.class));}
                        } else {
                            Toast.makeText(getApplicationContext(), "Password and confirm passwords Does not match", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(changePassword.this, "Old password is wrong", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "One or more fields are empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public class AsyncChangePasswordUpdate extends AsyncTask<String, Void, SoapObject> {


        @Override
        protected SoapObject doInBackground(String... params) {

            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_Update);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("userId");
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
            afterChangePasswordUpdate(resultXML);
        }
    }

    public void afterChangePasswordUpdate(SoapObject resultXML) {

        //Toast.makeText(this, resultXML.toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(changePassword.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
    }

    /*
    * Methods that will get old password
    * */
    public class AsyncChangePasswordSelect extends AsyncTask<String, Void, SoapObject> {
        @Override
        protected SoapObject doInBackground(String... params) {

            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_Select);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("userId");
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
            afterChangePasswordSelect(resultXML);
        }
    }

    public void afterChangePasswordSelect(SoapObject resultXML) {

        //Toast.makeText(this, resultXML.toString(), Toast.LENGTH_LONG).show();

        String KEY_ITEM = "Users";
        String UserPassword = "UserPassword";
        SoapObject table = null;

        if (resultXML.getPropertyCount() > 0) {
            table = (SoapObject) resultXML.getProperty(KEY_ITEM);
            originalOldPassword = table.getProperty(UserPassword).toString();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(changePassword.this, setting.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i=new Intent(changePassword.this, setting.class);
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

