package com.example.ridhdhish.sut;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Ridhdhish on 06-04-2019.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    TelephonyManager telephonyManager;
    String IMEINumber, token;
    String deviceUniqueIdentifier = "";
    SharedPreferences deviceInfo;

    private static String SOAP_ACTION = "";
    private static String METHOD_NAME = "SetTokenAndIMEI";
    private static String NAMESPACE = "";
    private static String url = "";
    Context context;
    String androidid;

    //this method will be called
    //when the token is generated
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        context = this;
      //  Log.e("MyIMEINumber", "123456");

        //now we will have the token
        token = FirebaseInstanceId.getInstance().getToken();

        //for now we are displaying the token in the log
        //copy it as this method is called only when the new token is generated
        //and usually new token is only generated when the app is reinstalled or the data is cleared
        Log.e("MyRefreshedToken", token);

        //telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        /*if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("MyIMEINo", "123456");
            //return;
        }*/
       // IMEINumber = getImeiNumber();

        androidid = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        Log.e("TAG",Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ALLOWED_GEOLOCATION_ORIGINS));
        Log.e("TAG",Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD));

        Log.e("androidid", androidid);


        //IMEINumber = telephonyManager.getDeviceId();

        // Log.e("MyIMEINo", IMEINumber);
       // Log.e("MyIMEINo", IMEINumber);

        sendToken(androidid, token);
    }

    private String getImeiNumber() {
        final TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
               // ActivityCompat.requestPermissions(context, new String[]{android.Manifest.permission.READ_PHONE_STATE}, 1);
            }
            return telephonyManager.getImei();
        } else {
            return telephonyManager.getDeviceId();
        }
    }

    public void sendToken(String _IMEINum, String _Token) {
        deviceInfo = getSharedPreferences("deviceInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = deviceInfo.edit();
        editor.putString("Token", _Token);
        editor.putString("AndroidId", _IMEINum);
        editor.commit();
        new AsyncStoreIMEIReferenceToken().execute(_IMEINum, _Token);
    }

    public class AsyncStoreIMEIReferenceToken extends AsyncTask<String, Void, SoapObject> {

        @Override
        protected SoapObject doInBackground(String... params) {

            SOAP_ACTION = getResources().getString(R.string.NAMESPACE) + METHOD_NAME;
            url = getResources().getString(R.string.url);
            NAMESPACE = getResources().getString(R.string.NAMESPACE);


            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("IMEI");
            pi.setValue(params[0]);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("GeneratedToken");
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
        }

    }

}
