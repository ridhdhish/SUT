package com.example.ridhdhish.sut;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class guest_registration extends AppCompatActivity {

    EditText edtGuestUserRegistrationName, edtGuestUserRegistrationEmailId;
    EditText edtGuestUserRegistrationPassword, edtGuestUserRegistrationMobileNumber;
    Button btnGuestUserRegistrationSignUp;
    LinearLayout llGuestLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_registration);

        edtGuestUserRegistrationName = (EditText) findViewById(R.id.edtGuestUserRegistrationName);
        edtGuestUserRegistrationEmailId = (EditText) findViewById(R.id.edtGuestUserRegistrationEmailId);
        edtGuestUserRegistrationPassword = (EditText) findViewById(R.id.edtGuestUserRegistrationPassword);
        edtGuestUserRegistrationMobileNumber = (EditText) findViewById(R.id.edtGuestUserRegistrationMobileNumber);
        btnGuestUserRegistrationSignUp = (Button) findViewById(R.id.btnGuestUserRegistrationSignUp);

        btnGuestUserRegistrationSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), GuestRegistrationNext.class);
                intent.putExtra("edtGuestUserRegistrationName", edtGuestUserRegistrationName.getText().toString().toLowerCase());
                intent.putExtra("edtGuestUserRegistrationEmailId", edtGuestUserRegistrationEmailId.getText().toString().toLowerCase());
                intent.putExtra("edtGuestUserRegistrationPassword", edtGuestUserRegistrationPassword.getText().toString().toLowerCase());
                intent.putExtra("edtGuestUserRegistrationMobileNumber", edtGuestUserRegistrationMobileNumber.getText().toString());
                startActivity(intent);
            }
        });

        llGuestLogin=(LinearLayout) findViewById(R.id.llGuestLogin);
        llGuestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(guest_registration.this,guest_login.class);
                startActivity(i);
            }
        });
    }
}
