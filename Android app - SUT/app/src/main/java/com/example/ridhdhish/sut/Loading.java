package com.example.ridhdhish.sut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Intent i;
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String id = intent.getStringExtra("ID");
        Toast.makeText(Loading.this, type, Toast.LENGTH_LONG).show();
        if (type == "maintenance") {
            i = new Intent(this, maintenance.class);
            startActivity(i);
        } else if (type == "announcement") {
            i = new Intent(this, announcement.class);
            startActivity(i);
        } else if (type == "meeting") {
            i = new Intent(this, meeting.class);
            startActivity(i);
        }
    }
}
