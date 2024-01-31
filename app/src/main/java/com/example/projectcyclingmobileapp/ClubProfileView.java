package com.example.projectcyclingmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class ClubProfileView extends AppCompatActivity {
    private TextView socialMediaLinkTextView;
    private TextView phoneNumberTextView;
    private TextView mainContactNameTextView;
    private TextView locationTextView;
    private Button btnReturn;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore mstore;
    private String userId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile_info);

        socialMediaLinkTextView = (TextView) findViewById(R.id.textViewSocialMediaLink);
        phoneNumberTextView = (TextView) findViewById(R.id.textViewPhoneNumber);
        mainContactNameTextView = (TextView) findViewById(R.id.textViewMainContactName);
        locationTextView = (TextView) findViewById(R.id.textViewLocation);
        btnReturn = (Button) findViewById(R.id.buttonReturnToWelcomeFromViewProfile);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mstore = FirebaseFirestore.getInstance();

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
                    socialMediaLinkTextView.setText("Social Media: " + currentUser.getSocialMediaLink());
                    phoneNumberTextView.setText("Phone Number: " + currentUser.getPhoneNumber());
                    locationTextView.setText("Location: " + currentUser.getLocation());
                    mainContactNameTextView.setText("Main Contact Name: " + currentUser.getMainContact());

                    if(currentUser.getSocialMediaLink().trim().equals("")){
                        Toast.makeText(ClubProfileView.this, "You haven't set your profile yet!", Toast.LENGTH_SHORT);
                    }
                }
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent(getApplicationContext(), CyclingClubOwner.class);
                startActivity(returnIntent);
                finish();
            }
        });


    }
}
