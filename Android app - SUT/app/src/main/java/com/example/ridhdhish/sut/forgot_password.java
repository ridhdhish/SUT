package com.example.ridhdhish.sut;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class forgot_password extends AppCompatActivity {

    EditText edtForgotPasswordEmailId, edtOTP, edtForgotPasswordNewPassword, edtForgotPasswordConfirmPassword;
    Button btnSend, btnconfirm, btnForgotSubmit;
    String OTP, Forgot;

    private static String url = "";
    private static String NAMESPACE = "";
    private static String SOAP_ACTION_Select = "";
    private static String SOAP_ACTION_Update = "";
    private static String METHOD_NAME_Select = "";
    private static String METHOD_NAME_Update = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edtForgotPasswordEmailId = (EditText) findViewById(R.id.edtForgotPasswordEmailId);
        edtOTP = (EditText) findViewById(R.id.edtOTP);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnconfirm = (Button) findViewById(R.id.btnconfirm);
        edtForgotPasswordConfirmPassword = (EditText) findViewById(R.id.edtForgotPasswordConfirmPassword);
        edtForgotPasswordNewPassword = (EditText) findViewById(R.id.edtForgotPasswordNewPassword);
        btnForgotSubmit = (Button) findViewById(R.id.btnForgotSubmit);

        NAMESPACE = getString(R.string.NAMESPACE);
        SOAP_ACTION_Select = NAMESPACE + METHOD_NAME_Select;
        SOAP_ACTION_Update = NAMESPACE + METHOD_NAME_Update;
        url = getString(R.string.url);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            Forgot = b.getString("Forgot");
        }

        if (Forgot == "Guest") {
            METHOD_NAME_Select = "GuestUser_ForgotPassword_Select";
            METHOD_NAME_Update = "GuestUser_ForgotPassword_Update";
            SOAP_ACTION_Select = NAMESPACE + METHOD_NAME_Select;
            SOAP_ACTION_Update = NAMESPACE + METHOD_NAME_Update;
        } else if (Forgot == "User") {
            METHOD_NAME_Select = "Users_ForgotPassword_Select";
            METHOD_NAME_Update = "Users_ForgotPassword_Update";
            SOAP_ACTION_Select = NAMESPACE + METHOD_NAME_Select;
            SOAP_ACTION_Update = NAMESPACE + METHOD_NAME_Update;
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncForgotPasswordSelect().execute(edtForgotPasswordEmailId.getText().toString());
                    btnSend.setVisibility(View.GONE);
                    edtForgotPasswordEmailId.setVisibility(View.GONE);
                    btnconfirm.setVisibility(View.VISIBLE);
                    edtOTP.setVisibility(View.VISIBLE);
            }
        });

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = edtOTP.getText().toString().toLowerCase();
                if (str.equals(OTP)) {
                    edtForgotPasswordNewPassword.setVisibility(View.VISIBLE);
                    edtForgotPasswordConfirmPassword.setVisibility(View.VISIBLE);
                    btnForgotSubmit.setVisibility(View.VISIBLE);
                    btnconfirm.setVisibility(View.GONE);
                    edtOTP.setVisibility(View.GONE);
                } else {
                    Toast.makeText(forgot_password.this, "OTP is Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnForgotSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtForgotPasswordNewPassword.getText().toString().equals(edtForgotPasswordConfirmPassword.getText().toString())) {
                    new AsyncForgotPasswordUpdate().execute(edtForgotPasswordEmailId.getText().toString(), edtForgotPasswordConfirmPassword.getText().toString());
                } else {
                    Toast.makeText(forgot_password.this, "New Password and Confirm New Password Does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class AsyncForgotPasswordSelect extends AsyncTask<String, Void, SoapPrimitive> {
        ProgressDialog dialog = new ProgressDialog(forgot_password.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Loading");
            dialog.setMessage("Please Wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected SoapPrimitive doInBackground(String... params) {

            SoapPrimitive documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_Select);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("EmailId");
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

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                documentElement = response;
            } catch (Exception E) {
                Log.e("Error-->> ", E.toString());
            }

            return documentElement;
        }

        @Override
        protected void onPostExecute(SoapPrimitive resultXML) {
            super.onPostExecute(resultXML);
            dialog.dismiss();
            afterAsyncForgotpasswordSelect(resultXML);
        }
    }

    public void afterAsyncForgotpasswordSelect(SoapPrimitive xml) {
        OTP = "";
        try {
            OTP = xml.toString().toLowerCase();
            Toast.makeText(this, "" + OTP, Toast.LENGTH_SHORT).show();
            if (OTP.isEmpty()) {
                Toast.makeText(this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
                btnSend.setVisibility(View.VISIBLE);
                edtForgotPasswordEmailId.setVisibility(View.VISIBLE);
                btnconfirm.setVisibility(View.GONE);
                edtOTP.setVisibility(View.GONE);
            } else {
                Toast.makeText(forgot_password.this, "OTP has been sent to your registered email address", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
            btnSend.setVisibility(View.VISIBLE);
            edtForgotPasswordEmailId.setVisibility(View.VISIBLE);
            edtForgotPasswordEmailId.setText("");
            btnconfirm.setVisibility(View.GONE);
            edtOTP.setVisibility(View.GONE);
        }
    }


    public class AsyncForgotPasswordUpdate extends AsyncTask<String, Void, SoapObject> {
        ProgressDialog dialog = new ProgressDialog(forgot_password.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
            afterForgotPasswordUpdate(resultXML);
        }
    }

    public void afterForgotPasswordUpdate(SoapObject resultXML) {

        //Toast.makeText(this, resultXML.toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(forgot_password.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(forgot_password.this, UserLogin.class);
        startActivity(i);
    }
}
