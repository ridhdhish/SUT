package com.example.ridhdhish.sut;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

public class GuestRegistrationNext extends AppCompatActivity {
    private static String url = "";
    private static String NAMESPACE = "";
    private static String SOAP_ACTION_Select = "";
    private static String SOAP_ACTION_Insert = "";
    private static String METHOD_NAME_Select = "GuestUser_Registration_Select";
    private static String METHOD_NAME_Insert = "GuestUser_Registration_Insert";
    List<String> apartmentIds;
    List<String> apartments;
    List<String> adminIds;
    gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner spinner;
    Button btnGuestRegistrationSignUp;
    static int apartmentId;
    static int adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_registration_next);

        NAMESPACE = getString(R.string.NAMESPACE);
        SOAP_ACTION_Select = NAMESPACE + METHOD_NAME_Select;
        SOAP_ACTION_Insert = NAMESPACE + METHOD_NAME_Insert;
        url = getString(R.string.url);

        apartmentIds = new ArrayList<>();
        apartments = new ArrayList<>();
        adminIds = new ArrayList<>();
        btnGuestRegistrationSignUp = (Button) findViewById(R.id.btnGuestRegistrationSignUp);

        final Bundle extras = getIntent().getExtras();

        new AsyncGuestUserRegistrationSelect().execute();
        spinner = (gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner) findViewById(R.id.spinnerGuestRegistrationApartment);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, apartments);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(mOnItemSelectedListener);

        btnGuestRegistrationSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(GuestRegistrationNext.this, "" + extras.getString("edtGuestUserRegistrationName").toString(), Toast.LENGTH_LONG).show();

                new AsyncGuestUserRegistrationInsert().execute(extras.getString("edtGuestUserRegistrationName").toString(), extras.getString("edtGuestUserRegistrationEmailId").toString(), extras.getString("edtGuestUserRegistrationPassword").toString(), extras.getString("edtGuestUserRegistrationMobileNumber").toString(), String.valueOf(apartmentId), String.valueOf(adminId));

                Intent i = new Intent(GuestRegistrationNext.this, guest_login.class);
                startActivity(i);
            }
        });
        // new AsyncGuestUserRegistrationInsert().execute(extras.getString("edtGuestUserRegistrationName"),extras.getString("edtGuestUserRegistrationEmailId"),extras.getString("edtGuestUserRegistrationPassword"),extras.getString("edtGuestUserRegistrationMobileNumber"));

    }

    private OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(View view, int position, long id) {
            //Toast.makeText(GuestRegistrationNext.this, "" + apartmentIds.get(position), Toast.LENGTH_SHORT).show();
            apartmentId = Integer.parseInt(apartmentIds.get(position));
            Toast.makeText(GuestRegistrationNext.this, "" + apartmentId, Toast.LENGTH_SHORT).show();
            adminId = Integer.parseInt(adminIds.get(position));
        }

        @Override
        public void onNothingSelected() {

        }
    };

    public class AsyncGuestUserRegistrationSelect extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(GuestRegistrationNext.this);

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
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_Select);

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
            afterGuestUserRegistrationSelect(resultXML);
            dialog.dismiss();
        }
    }

    public void afterGuestUserRegistrationSelect(SoapObject resultXML) {
        String KEY_ITEM = "Apartments";
        String ApartmentId = "ApartmentId";
        String ApartmentDetails = "ApartmentDetails";
        String AdminId = "AdminId";
        SoapObject table = null;

        if (resultXML.getPropertyCount() > 0) {

            for (int i = 0; i < resultXML.getPropertyCount(); i++) {

                table = (SoapObject) resultXML.getProperty(i);

                String apartmentId = table.getProperty(ApartmentId).toString();
                String apartmentDetails = table.getProperty(ApartmentDetails).toString();
                String adminId = table.getProperty(AdminId).toString();
                apartmentIds.add(apartmentId);
                apartments.add(apartmentDetails);
                adminIds.add(adminId);
            }

        }
    }

    /*
    Code Remaining Insert
    * */
    public class AsyncGuestUserRegistrationInsert extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(GuestRegistrationNext.this);

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
        protected SoapObject doInBackground(String[] params) {
            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_Insert);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("name");
            pi.setValue(params[0]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("emailId");
            pi.setValue(params[1]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("password");
            pi.setValue(params[2]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("mobile");
            pi.setValue(params[3]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("apartmentId");
            pi.setValue(params[4]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("adminId");
            pi.setValue(params[5]);
            pi.setType(String.class);
            request.addProperty(pi);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);


            try {
                HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
                androidHttpTransport.debug = true;
                androidHttpTransport.call(SOAP_ACTION_Insert, envelope);

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
            afterGuestUserRegistrationInsert(resultXML);
        }
    }

    public void afterGuestUserRegistrationInsert(SoapObject resultXML) {
        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show();
    }
}
