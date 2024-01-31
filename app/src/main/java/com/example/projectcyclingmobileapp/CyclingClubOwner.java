package com.example.projectcyclingmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class CyclingClubOwner extends AppCompatActivity {

    FirebaseAuth auth;
    Button btnEvent;
    Button btnOut;
    Button btnCreateAndEditProfile;
    Button btnViewProfile;
    Button btnViewFeedback;
    TextView userName;
    TextView userType;
    FirebaseUser user;
    FirebaseFirestore mstore;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycling_club_owner);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mstore = FirebaseFirestore.getInstance();
        btnOut = findViewById(R.id.LogOutButton);
        btnEvent = findViewById(R.id.AddEventType);
        userName = findViewById(R.id.UserName);
        userType = findViewById(R.id.UserType);
        btnCreateAndEditProfile = findViewById(R.id.buttonCreateAndEditProfile);
        btnViewProfile = findViewById(R.id.buttonViewProfile);
        btnViewFeedback = findViewById(R.id.feedBackView);

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
                    }
                }
            });
        }


        btnCreateAndEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(getApplicationContext(), ClubProfile.class);
                startActivity(profileIntent);
                finish();
            }
        });

        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewProfileIntent = new Intent(getApplicationContext(), ClubProfileView.class);
                startActivity(viewProfileIntent);
                finish();
            }
        });





        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent logInIntent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(logInIntent);
                finish();

            }
        });




        btnEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent eventTypeIntent = new Intent(getApplicationContext(), EventDetails.class);
                startActivity(eventTypeIntent);
                finish();
            }
            
        });
        btnViewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedbackPageIntent = new Intent(getApplicationContext(), ClubFeedbackView.class);
                startActivity(feedbackPageIntent);
                finish();
            }

        });


    }
}