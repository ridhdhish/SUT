package com.example.ridhdhish.sut;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import static maes.tech.intentanim.CustomIntent.customType;

public class GuestSettingActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout llGuestSettingProfile, llGuestSettingChangePassword, llGuestSettingLogout;
    Intent i;
    SharedPreferences sharedPreferences1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_setting);

        toolbar = (Toolbar) findViewById(R.id.GuestsettingToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Setting");

        llGuestSettingProfile=(LinearLayout) findViewById(R.id.llGuestSettingProfile);
        llGuestSettingChangePassword=(LinearLayout) findViewById(R.id.llGuestSettingChangePassword);

        llGuestSettingProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i= new Intent(GuestSettingActivity.this, GuestProfileActivity.class);
                startActivity(i);
            }
        });

        llGuestSettingChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(GuestSettingActivity.this, GuestChangePasswordActivity.class);
                startActivity(i);
            }
        });

        llGuestSettingLogout = (LinearLayout) findViewById(R.id.llGuestSettingLogout);
        llGuestSettingLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences1 = getSharedPreferences("GUPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.clear();
                editor.commit();

                Intent i = new Intent(GuestSettingActivity.this, guest_login.class);
                 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent i = new Intent(GuestSettingActivity.this, Guest_Dashboard.class);
                customType(GuestSettingActivity.this,"left-to-right");
                startActivity(i);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(GuestSettingActivity.this, Guest_Dashboard.class);
        customType(GuestSettingActivity.this,"left-to-right");
        startActivity(i);
        finish();
    }
}
