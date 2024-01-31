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

public class Participant extends AppCompatActivity {
    Button btnSearchEvents, btnViewEvents, btnLogOut;
    TextView userName, userType;
    FirebaseAuth auth;

    FirebaseUser user;
    FirebaseFirestore mstore;
    String userId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mstore = FirebaseFirestore.getInstance();
        userName = findViewById(R.id.UsernameParticipant);
        userType = findViewById(R.id.UserTypeParticipant);

        btnLogOut = findViewById(R.id.LogOutParticipant);

        btnViewEvents = findViewById(R.id.ViewEvents);
        btnSearchEvents = findViewById(R.id.SearchEvents);


        if (user == null) {
            Intent loginIntent = new Intent(getApplicationContext(), LogIn.class);
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
                    } else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        User currentUser = task.getResult().getValue(User.class);
                        userName.setText(currentUser.getUsername());
                        userType.setText(currentUser.getType());
                    }
                }
            });
        }


        btnLogOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                FirebaseAuth.getInstance().signOut();
                Intent logInIntent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(logInIntent);
                finish();
            }
        });
        btnViewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent participantEventViewIntent = new Intent(getApplicationContext(),ParticipantEventsView.class);
                startActivity(participantEventViewIntent);
                finish();
            }

        });

        btnSearchEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent eventSearchViewIntent = new Intent(getApplicationContext(), EventSearch.class);
                startActivity(eventSearchViewIntent);
                finish();
            }

        });


    }
}