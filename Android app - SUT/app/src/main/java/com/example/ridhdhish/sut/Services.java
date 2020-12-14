package com.example.ridhdhish.sut;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class Services extends AppCompatActivity {

    String serviceid = "", serviceName = "";
    Button btnServicesInterfacePhone;
    private String SOAP_ACTION = "";
    private static final String METHOD_NAME = "Users_ServicesProviders_Select";
    private String NAMESPACE = "";
    private String url = "";

    SwipeRefreshLayout Servicesswiperefresh;

    Toolbar toolbar;
    //private static final String imgurl = "http://192.168.0.104/SUT/siteimages/servicetypeimage/";

    ListView l1;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        SOAP_ACTION = getResources().getString(R.string.NAMESPACE) + METHOD_NAME;
        url = getResources().getString(R.string.url);
        NAMESPACE = getResources().getString(R.string.NAMESPACE);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            serviceid = b.getString("serviceid");
            serviceName = b.getString("serviceName");
        }

        toolbar = (Toolbar) findViewById(R.id.servicesToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(serviceName);

        sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);

        Servicesswiperefresh=(SwipeRefreshLayout) findViewById(R.id.Servicesswiperefresh);
        Servicesswiperefresh.setColorSchemeResources(R.color.colorPrimary);
        Servicesswiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new AsyncServices().execute(sharedPreferences.getString("adminId", null).toString(), serviceid);
                        Servicesswiperefresh.setRefreshing(false);
                    }
                }
        );

        btnServicesInterfacePhone = (Button) findViewById(R.id.btnServicesInterfacePhone);

        l1 = (ListView) findViewById(R.id.gridViewServiceChild);
        //Toast.makeText(this, serviceid, Toast.LENGTH_SHORT).show();

//        btnServicesInterfacePhone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:8128680633")));
//                } catch (Exception e) {
//                    Log.e("Calling", "" + e.getMessage());
//                }
//            }
//        });

        new AsyncServices().execute(sharedPreferences.getString("adminId", null).toString(), serviceid);


    }

    ArrayList<ServicesDataModel> getData(SoapObject resultXML) {
        ArrayList<ServicesDataModel> data = new ArrayList<>();
        ServicesDataModel dm;

        String KEY_ITEM = "Services";
        String typeName = "SPName";
        String typeNumber = "SPContactNumber";

        SoapObject table = null;
        try {
            if (resultXML.getPropertyCount() > 0) {

                for (int i = 0; i < resultXML.getPropertyCount(); i++) {
                    table = (SoapObject) resultXML.getProperty(i);
                    dm = new ServicesDataModel();
                    dm.setTxtServiceInterfaceNumber(table.getProperty(typeNumber).toString());
                    dm.setTxtServicesInterfaceName(table.getProperty(typeName).toString());
                    // dm.setImgServicesInterfaceProfile(imgurl + table.getProperty(typeImage).toString());
                    data.add(dm);

                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

        return data;
    }


    public class AsyncServices extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(Services.this);

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
            pi.setName("adminId");
            pi.setValue(params[0]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("typeId");
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
            afterAsyncService(resultXML);
            dialog.dismiss();
        }

    }

    public void afterAsyncService(SoapObject resultXML) {

        ArrayList<ServicesDataModel> serviceDM = getData(resultXML);
        l1.setAdapter(new ServicesAdapter(serviceDM, this));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Services.this, service.class);
        customType(Services.this, "left-to-right");
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(Services.this, service.class);
                customType(Services.this, "left-to-right");
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
