package com.example.projectcyclingmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Welcome extends AppCompatActivity {

    //Declaring design variables
    Button btnCon;
    Button btnOut;
    TextView userName;
    TextView userType;

    //Declaring Firebase variables
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore mstore;
    String userId;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnCon = findViewById(R.id.AddEventType);
        btnOut = findViewById(R.id.LogOutButton);
        userName = findViewById(R.id.UserName);
        userType = findViewById(R.id.UserType);
        
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mstore = FirebaseFirestore.getInstance();

        /* CODE BELOW USES ELEMENTS FROM FIREBASE DOCUMENTATION
         * FOR RETRIEVING DATA FROM THE CLOUD DATA
         * AND HOW TO HANDLE CRASH FAILURES
         * ACCESSED: 10/09/2023
         * URL: https://firebase.google.com/docs/database/admin/retrieve-data
         * */

        /// Checks if the user exists
        if (user == null){
            Intent loginIntent = new Intent(getApplicationContext(),LogIn.class);
            startActivity(loginIntent);
            finish();
        } else {
            /// Retrieves information from the cloud database previously stored from SignUp.java
            userId = auth.getCurrentUser().getUid();
            DatabaseReference dR = FirebaseDatabase.getInstance().getReference();
            dR.child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        User currentUser = task.getResult().getValue(User.class);
                        userName.setText(currentUser.getUsername());
                        userType.setText(currentUser.getType());
                        type = currentUser.getType();
                    }
                }
            });
        }

        ///Button to listen for log out clicks
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent loginIntent = new Intent(getApplicationContext(),LogIn.class);
                startActivity(loginIntent);
                finish();
            }
        });

        ///Button to listen for event details creation clicks
        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("Event Organizer")) {
                    Intent organizerIntent = new Intent(getApplicationContext(), CyclingClubOwner.class);
                    startActivity(organizerIntent);
                    finish();
                }else if (type.equals("Participant")) {
                    Intent participantIntent = new Intent(Welcome.this, Participant.class);
                    Toast.makeText(Welcome.this, "Participant Login Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(participantIntent);
                    Toast.makeText(Welcome.this, "Starting Activity", Toast.LENGTH_SHORT).show();
                    finish();

                }else {
                    Toast.makeText(Welcome.this, "NOT IMPLEMENTED", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}