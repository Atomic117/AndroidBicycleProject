package com.example.projectcyclingmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Admin extends AppCompatActivity {

    Button btnView;
    Button btnOut;
    Button btnAcc;
    TextView userName;
    TextView userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnView = findViewById(R.id.AddEventType);
        btnOut = findViewById(R.id.LogOutButton);
        userName = findViewById(R.id.UserName);
        userType = findViewById(R.id.UserType);
        btnAcc = findViewById(R.id.EditAccounts);

        userName.setText("Admin");
        userType.setText("Admin");



        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logInIntent = new Intent(getApplicationContext(), LogIn.class); 
                startActivity(logInIntent);
                finish();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent eventTypeIntent = new Intent(getApplicationContext(), EventTypeDisplay.class);
                startActivity(eventTypeIntent);
                finish();
            }
            
        });

        btnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accountviewIntent = new Intent(getApplicationContext(), UserList.class);
                startActivity(accountviewIntent);
                finish();
            }
        });
    }
}