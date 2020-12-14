package com.example.ridhdhish.sut;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class complaint extends AppCompatActivity {

    Toolbar toolbar;
    private static String url = "";
    private static String NAMESPACE = "";
    private static String SOAP_ACTION_SELECT = "";
    private static String METHOD_NAME_SELECT = "Users_Complaints_Select";
    private static String SOAP_ACTION_INSERT = "";
    private static String METHOD_NAME_INSERT = "Users_Complaints_Insert";

    EditText edtComplaintTitle, edtComplaintMessage;
    Button btnComplaintSubmit;

    SwipeRefreshLayout Complaintswiperefresh;

    public TextView txtCompaintId;

    String userId, adminId, apartmentId, title, msg;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_complaint);

        NAMESPACE = getString(R.string.NAMESPACE);
        SOAP_ACTION_INSERT = NAMESPACE + METHOD_NAME_INSERT;
        SOAP_ACTION_SELECT = NAMESPACE + METHOD_NAME_SELECT;
        url = getString(R.string.url);

//        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
//        drawerLayout.bringToFront();

        toolbar = (Toolbar) findViewById(R.id.complaintToolBar);
        setSupportActionBar(toolbar);

        //toolbar.bringToFront();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Complaint");

        sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        adminId = sharedPreferences.getString("adminId", null).toString();
        apartmentId = sharedPreferences.getString("apartmentId", null).toString();
        userId = sharedPreferences.getString("Userid", null).toString();

        Complaintswiperefresh=(SwipeRefreshLayout) findViewById(R.id.Complaintswiperefresh);
        Complaintswiperefresh.setColorSchemeResources(R.color.colorPrimary);
        Complaintswiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new AsyncComplaintSelect().execute(userId);
                        Complaintswiperefresh.setRefreshing(false);
                    }
                }
        );

        edtComplaintTitle = (EditText) findViewById(R.id.edtComplaintTitle);
        edtComplaintMessage = (EditText) findViewById(R.id.edtComplaintMessage);
        btnComplaintSubmit = (Button) findViewById(R.id.btnComplaintSubmit);

        new AsyncComplaintSelect().execute(userId);

        btnComplaintSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = edtComplaintTitle.getText().toString();
                msg = edtComplaintMessage.getText().toString();


                if (title.isEmpty() || msg.isEmpty()) {
                    Toast.makeText(complaint.this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    edtComplaintMessage.setText("");
                    edtComplaintTitle.setText("");
                    new AsyncComplaintInsert().execute(userId, title, msg, adminId, apartmentId);
                }

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(complaint.this, navigationdrawer.class);
                customType(complaint.this,"left-to-right");
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<complaintDataModel> getData(SoapObject resultXML) {
        ArrayList<complaintDataModel> data = new ArrayList<complaintDataModel>();
        complaintDataModel complaintDM;

        String KEY_ITEM = "Complaints";
        String title = "ComplaintTitle";
        String message = "ComplaintMessage";
        String date = "ComplaintDate";
        String complaintId = "ComplaintId";

        SoapObject table = null;
        try{
        if (resultXML.getPropertyCount() > 0) {
            for (int i = 0; i < resultXML.getPropertyCount(); i++) {
                table = (SoapObject) resultXML.getProperty(i);

                complaintDM = new complaintDataModel();
                complaintDM.setTxtComplaintMessage(table.getProperty(message).toString());
                complaintDM.setTxtComplaintDate(table.getProperty(date).toString());
                complaintDM.setTxtComplaintTitle(table.getProperty(title).toString());
                complaintDM.setTxtComplaintId(table.getProperty(complaintId).toString());
                /*
                if(imp) then do something
                * */
                data.add(complaintDM);
            }
        }}
        catch (Exception e)
        {
            Toast.makeText(this, "No complaint found", Toast.LENGTH_SHORT).show();
        }
        return data;
    }

    public class AsyncComplaintInsert extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(complaint.this);

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
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_INSERT);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("userId");
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

            pi = new PropertyInfo();
            pi.setName("adminId");
            pi.setValue(params[3]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("apartmentId");
            pi.setValue(params[4]);
            pi.setType(String.class);
            request.addProperty(pi);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            try {
                HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
                androidHttpTransport.debug = true;
                androidHttpTransport.call(SOAP_ACTION_INSERT, envelope);

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
            afterAsyncComplaintInsert(resultXML);
        }
    }

    public void afterAsyncComplaintInsert(SoapObject resultXML) {
        Toast.makeText(this, "Complaint Inserted", Toast.LENGTH_SHORT).show();
        new AsyncComplaintSelect().execute(userId);
    }

    public class AsyncComplaintSelect extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(complaint.this);

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
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_SELECT);

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
                androidHttpTransport.call(SOAP_ACTION_SELECT, envelope);

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
            afterAsyncComplaintSelect(resultXML);
        }
    }

    public void afterAsyncComplaintSelect(SoapObject resultXML) {
        // ArrayList<complaintDataModel> complaintDM = getData(resultXML);
        //  ListView l1 = (ListView) findViewById(R.id.listViewComplaint);
        //l1.setAdapter(new complaintAdapter(complaintDM, this));

        RecyclerView l1 = (RecyclerView) findViewById(R.id.listViewComplaint);
        l1.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManagerComplaint = new LinearLayoutManager(this);
        //MyLayoutManagerComplaint.setOrientation(LinearLayoutManager.VERTICAL);
        ArrayList<complaintDataModel> complaintDM = getData(resultXML);
        l1.setAdapter(new complaintAdapter(complaintDM, this));
        l1.setLayoutManager(MyLayoutManagerComplaint);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(complaint.this, navigationdrawer.class);
        customType(complaint.this,"left-to-right");
        startActivity(i);
        finish();
    }
}