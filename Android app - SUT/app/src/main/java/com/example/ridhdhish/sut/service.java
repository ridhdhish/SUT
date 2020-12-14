package com.example.ridhdhish.sut;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;


public class service extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout layoutServiceInterface;
    Context mContext;
    Activity mActivity;
    Button mButton;
    PopupWindow mPopupWindow;

    private String SOAP_ACTION = "";
    private static final String METHOD_NAME = "Users_ServicesType_Select";
    private String NAMESPACE = "";
    private String url = "";
    private static String imgurl = "";
    String img = "siteimages/servicetypeimage/";

    SwipeRefreshLayout Serviceswiperefresh;

    GridView l1;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_service);

        l1 = (GridView) findViewById(R.id.gridViewService);

        SOAP_ACTION = getResources().getString(R.string.NAMESPACE) + METHOD_NAME;
        url = getResources().getString(R.string.url);
        NAMESPACE = getResources().getString(R.string.NAMESPACE);
        imgurl = getString(R.string.WEBSERVER_IP) + img;

        toolbar = (Toolbar) findViewById(R.id.serviceToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Service");

        Serviceswiperefresh=(SwipeRefreshLayout) findViewById(R.id.Serviceswiperefresh);
        Serviceswiperefresh.setColorSchemeResources(R.color.colorPrimary);
        Serviceswiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new AsyncService().execute();
                        Serviceswiperefresh.setRefreshing(false);
                    }
                }
        );

        new AsyncService().execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(service.this, navigationdrawer.class);
                customType(service.this,"left-to-right");
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    ArrayList<serviceDataModel> getData(SoapObject resultXML) {
        ArrayList<serviceDataModel> data = new ArrayList<>();
        serviceDataModel dm;

        String KEY_ITEM = "ServiceTypes";
        String typeName = "ServiceTypeName";
        String typeId = "ServiceTypeId";
        String typeImage = "ServiceTypeImagePhone";

        SoapObject table = null;

        if (resultXML.getPropertyCount() > 0) {

            for (int i = 0; i < resultXML.getPropertyCount(); i++) {
                table = (SoapObject) resultXML.getProperty(i);
                dm = new serviceDataModel();
                dm.setTxtServiceInterface(table.getProperty(typeName).toString());
                dm.setTxtServiceInterfaceid(table.getProperty(typeId).toString());
                dm.setImage(imgurl + table.getProperty(typeImage).toString());
                data.add(dm);
            }

        }

        return data;
    }

    public class AsyncService extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(service.this);

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
        protected SoapObject doInBackground(String... param) {
            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

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
            afterAsyncService(resultXML);
            dialog.dismiss();
        }

    }

    public void afterAsyncService(SoapObject resultXML) {
        ArrayList<serviceDataModel> serviceDM = getData(resultXML);
        l1.setAdapter(new serviceAdapter(serviceDM, this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(service.this, navigationdrawer.class);
        customType(service.this,"left-to-right");
        startActivity(i);
        finish();
    }

}
