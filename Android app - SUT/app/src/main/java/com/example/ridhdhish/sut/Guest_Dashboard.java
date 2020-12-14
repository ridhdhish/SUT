package com.example.ridhdhish.sut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Guest_Dashboard extends AppCompatActivity {

    Button btnFlatholder,btnAdmindetail,btnSocietydetai, btnSetting;
    Intent i=new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest__dashboard);

        btnFlatholder=(Button) findViewById(R.id.btnGuestDashboard_Flatholder);
        btnAdmindetail=(Button) findViewById(R.id.btnGuestDashboard_Admindetail);
        btnSocietydetai=(Button) findViewById(R.id.btnGuestDashboard_Societydetail);
        btnSetting=(Button) findViewById(R.id.btnSetting);

        btnFlatholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(Guest_Dashboard.this,guest_flatholder.class);
                startActivity(i);
            }
        });

        btnAdmindetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(Guest_Dashboard.this,admin_detail.class);
                startActivity(i);
            }
        });

        btnSocietydetai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(Guest_Dashboard.this,guest_society_detail.class);
                startActivity(i);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(Guest_Dashboard.this, GuestSettingActivity.class);
                startActivity(i);
            }
        });
    }
}
