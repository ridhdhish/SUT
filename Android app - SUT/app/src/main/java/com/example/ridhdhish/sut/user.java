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
import android.view.MenuItem;
import android.widget.ListView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import static maes.tech.intentanim.CustomIntent.customType;

public class user extends AppCompatActivity {

    Toolbar toolbar;
    private static String url = "";
    private static String NAMESPACE = "";
    private static String SOAP_ACTION = "";
    private static String METHOD_NAME = "Users_FlatHolders_Select";
    private static String imgUrl = "";
    String img = "siteimages/profilePictures/";
    SwipeRefreshLayout Userswiperefresh;

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user);

        imgUrl = getString(R.string.WEBSERVER_IP) + img;
        NAMESPACE = getString(R.string.NAMESPACE);
        SOAP_ACTION = NAMESPACE + METHOD_NAME;
        url = getResources().getString(R.string.url);

        sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);


        toolbar = (Toolbar) findViewById(R.id.userToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Flat Holders");

        Userswiperefresh=(SwipeRefreshLayout) findViewById(R.id.Userswiperefresh);
        Userswiperefresh.setColorSchemeResources(R.color.colorPrimary);
        Userswiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new AsyncUser().execute(sharedPreferences.getString("apartmentId", null).toString());
                        Userswiperefresh.setRefreshing(false);
                    }
                }
        );

        new AsyncUser().execute(sharedPreferences.getString("apartmentId", null).toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(user.this, navigationdrawer.class);
                customType(user.this,"left-to-right");
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public ArrayList<usersDataModel> getData(SoapObject resultXML) {
        ArrayList<usersDataModel> data = new ArrayList<usersDataModel>();
        usersDataModel usersDM;

        String KEY_ITEM = "Users";
        String name = "UserName";
        String flatNumber = "UserFlatNumber";
        String profilePicture = "UserProfilePicture";

        SoapObject table = null;
        if (resultXML.getPropertyCount() > 0) {
            for (int i = 0; i < resultXML.getPropertyCount(); i++) {
                table = (SoapObject) resultXML.getProperty(i);

                usersDM = new usersDataModel();
                usersDM.setTxtUsersName(table.getProperty(name).toString());
                usersDM.setTxtUsersFlatNumber(table.getProperty(flatNumber).toString());
                if (table.getProperty(profilePicture).toString().isEmpty() || table.getProperty(profilePicture).toString()==null) {
                    usersDM.setImgUsers(imgUrl + "defaultUser.png");
                } else {
                    usersDM.setImgUsers(imgUrl + table.getProperty(profilePicture).toString());
                }
                data.add(usersDM);
            }
        }
        return data;
    }

    public class AsyncUser extends AsyncTask<String, Void, SoapObject> {

        ProgressDialog dialog = new ProgressDialog(user.this);

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
            afterAsyncUser(resultXML);
            dialog.dismiss();
        }
    }

    public void afterAsyncUser(SoapObject resultXML) {
        ArrayList<usersDataModel> userDM = getData(resultXML);
        ListView l1 = (ListView) findViewById(R.id.listviewUsers);
        l1.setAdapter(new usersAdapter(userDM, this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(user.this, navigationdrawer.class);
        customType(user.this,"left-to-right");
        startActivity(i);
        finish();
    }
}


