package com.example.ridhdhish.sut;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import static maes.tech.intentanim.CustomIntent.customType;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences sharedPreferences, GUsharedPreferences;
    Intent i;
    private final int SplashScreenDisplayTime = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, 1);
            }
        }

        sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        GUsharedPreferences = getSharedPreferences("GUPref", Context.MODE_PRIVATE);

        if (!checkConnection()) {
            i = new Intent(this, NoConnection.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    //i = new Intent(SplashScreen.this, UserLogin.class);

                    Intent intent = getIntent();

                    String type = intent.getStringExtra("type");
                    String id = intent.getStringExtra("ID");
                    //Toast.makeText(SplashScreen.this, type, Toast.LENGTH_LONG).show();
                    if (type != null && type.equals("") == false) {
                        if (type.equals("maintenance")) {
                            i = new Intent(SplashScreen.this, maintenance.class);
                        } else if (type.equals("announcement")) {
                            i = new Intent(SplashScreen.this, announcement.class);
                        } else if (type.equals("meeting")) {
                            i = new Intent(SplashScreen.this, meeting.class);
                        }
                    } else {
                        i = new Intent(SplashScreen.this, UserLogin.class);
                        if (sharedPreferences != null || GUsharedPreferences != null) {
                            String userEmailid = sharedPreferences.getString("Emailid", null);
                            String GUEmailid = GUsharedPreferences.getString("GUEmailid", null);
                            if (userEmailid != null) {
                                i = new Intent(SplashScreen.this, navigationdrawer.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            } else if (GUEmailid != null) {
                                i = new Intent(SplashScreen.this, Guest_Dashboard.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            }
                        } else {
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            customType(SplashScreen.this, "bottom-to-top");
                            startActivity(i);
                        }
                    }
                    startActivity(i);
                    finish();
                }
            }, SplashScreenDisplayTime);
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkConnection() {
        if (!isOnline()) {
            return false;
        } else {
            return true;
        }
    }
}
