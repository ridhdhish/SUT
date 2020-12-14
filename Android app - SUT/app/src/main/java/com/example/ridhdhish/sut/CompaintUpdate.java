package com.example.ridhdhish.sut;

import android.app.ProgressDialog;
import android.content.Intent;
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

import java.util.ArrayList;

public class CompaintUpdate extends AppCompatActivity {

    Toolbar toolbar;
    Button btnComplaintUpdate;
    String complaintId, complaintMessage, complaintTitle;
    private String SOAP_ACTION_UPDATE = "";
    private static final String METHOD_NAME_UPDATE = "Users_Complaints_Update";
    private String NAMESPACE = "";
    private String url = "";

    EditText edtComplaintUpdateTitle, edtComplaintUpdateMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compaint_update);

        SOAP_ACTION_UPDATE = getResources().getString(R.string.NAMESPACE) + METHOD_NAME_UPDATE;
        url = getResources().getString(R.string.url);
        NAMESPACE = getResources().getString(R.string.NAMESPACE);

        edtComplaintUpdateMessage = (EditText) findViewById(R.id.edtComplaintUpdateMessage);
        edtComplaintUpdateTitle = (EditText) findViewById(R.id.edtComplaintUpdateTitle);
        btnComplaintUpdate = (Button) findViewById(R.id.btnComplaintUpdate);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            complaintId = b.getString("complaintId");
            complaintMessage = b.getString("message");
            complaintTitle = b.getString("title");
        }

        edtComplaintUpdateMessage.setText(complaintMessage);
        edtComplaintUpdateTitle.setText(complaintTitle);

        btnComplaintUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtComplaintUpdateMessage.getText().toString().isEmpty() || edtComplaintUpdateTitle.getText().toString().isEmpty()) {
                    Toast.makeText(CompaintUpdate.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    new AsyncComplaintUpdate().execute(complaintId, edtComplaintUpdateMessage.getText().toString(), edtComplaintUpdateTitle.getText().toString());
                }
            }
        });

        toolbar = (Toolbar) findViewById(R.id.complaintUpdateToolBar);
        setSupportActionBar(toolbar);

        //toolbar.bringToFront();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Update Complaint");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(CompaintUpdate.this, complaint.class);
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class AsyncComplaintUpdate extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(CompaintUpdate.this);

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
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_UPDATE);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("complaintId");
            pi.setValue(params[0]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("title");
            pi.setValue(params[1]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("message");
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
                androidHttpTransport.call(SOAP_ACTION_UPDATE, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                SoapObject diffgram = (SoapObject) response.getProperty("diffgram");

                String sssss = String.valueOf(diffgram.getPropertyCount());

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
            afterAsyncComplaintUpdate(resultXML);
        }

    }

    public void afterAsyncComplaintUpdate(SoapObject resultXML) {
        Intent i = new Intent(CompaintUpdate.this, complaint.class);
        startActivity(i);
        Toast.makeText(CompaintUpdate.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
    }

}
