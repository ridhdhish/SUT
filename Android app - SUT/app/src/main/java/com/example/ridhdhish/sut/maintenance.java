package com.example.ridhdhish.sut;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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


public class maintenance extends AppCompatActivity {

    Toolbar toolbar;
    private static String SOAP_ACTION = "";
    private static String METHOD_NAME = "Users_Maintenance_Select";
    private static String NAMESPACE = "";
    private static String url = "";
    String apartmentId;
    SharedPreferences sharedPreferences;

    SwipeRefreshLayout Maintenanceswiperefresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maintenance);

        SOAP_ACTION = getResources().getString(R.string.NAMESPACE) + METHOD_NAME;
        url = getResources().getString(R.string.url);
        NAMESPACE = getResources().getString(R.string.NAMESPACE);

        toolbar = (Toolbar) findViewById(R.id.maintenanceToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Maintenance");

        sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        apartmentId = sharedPreferences.getString("apartmentId", null).toString();

        Maintenanceswiperefresh = (SwipeRefreshLayout) findViewById(R.id.Maintenanceswiperefresh);
        Maintenanceswiperefresh.setColorSchemeResources(R.color.colorPrimary);
        Maintenanceswiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new AsyncMaintenance().execute(apartmentId);
                        Maintenanceswiperefresh.setRefreshing(false);
                    }
                }
        );

        new AsyncMaintenance().execute(apartmentId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(maintenance.this, navigationdrawer.class);
                customType(maintenance.this, "left-to-right");
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    ArrayList<maintenanceDataModel> getData(SoapObject resultXML) {
        ArrayList<maintenanceDataModel> data = new ArrayList<maintenanceDataModel>();
        maintenanceDataModel dm;

        String KEY_ITEM = "Maintenances";
        String month = "MaintenanaceMonthYear";
        String amount = "MaintenanceAmount";
        String dueDate = "MaintenanceDueDate";
        String penalty = "MaintenancePenaltyAmount";
        String postDate = "MaintenancePostDate";

        SoapObject table = null;
        try {
            if (resultXML.getPropertyCount() > 0) {
/*
            dm = new maintenanceDataModel();
            dm.setMonth("Month");
            dm.setAmount("Amount");
            dm.setDueDate("Due Date");
            dm.setPenalty("Penalty");
            dm.setPostDate("Post Date");
            data.add(dm);*/

                for (int i = 0; i < resultXML.getPropertyCount(); i++) {
                    table = (SoapObject) resultXML.getProperty(i);

                    dm = new maintenanceDataModel();
                    dm.setMonth(table.getProperty(month).toString());
                    dm.setAmount(table.getProperty(amount).toString());
                    dm.setDueDate(table.getProperty(dueDate).toString());
                    dm.setPenalty(table.getProperty(penalty).toString());
                    dm.setPostDate(table.getProperty(postDate).toString());
                    data.add(dm);

                }
            }
        }

        catch (Exception e)
        {
            Toast.makeText(this, "No maintenance records found", Toast.LENGTH_SHORT).show();
        }
        return data;
    }

    public class AsyncMaintenance extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(maintenance.this);

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
            pi.setName("apartmentId");
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
            afterAsyncMaintenance(resultXML);
            dialog.dismiss();
        }
    }

    public void afterAsyncMaintenance(SoapObject resultXML) {

        ArrayList<maintenanceDataModel> maintenanceDM = getData(resultXML);
        ListView l1 = (ListView) findViewById(R.id.listViewMaintenance);
        l1.setAdapter(new maintenanceAdapter(maintenanceDM, this));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(maintenance.this, navigationdrawer.class);
        customType(maintenance.this, "left-to-right");
        startActivity(i);
        finish();
    }

}
