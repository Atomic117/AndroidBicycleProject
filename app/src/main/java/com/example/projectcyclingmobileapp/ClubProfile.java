package com.example.projectcyclingmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

public class ClubProfile extends AppCompatActivity {
    private EditText socialMediaLinkEditText;
    private EditText phoneNumberEditText;
    private EditText mainContactNameEditText;
    private EditText locationEditText;
    private Button enterButton;
    private Button exitButton;
    private DatabaseReference userDatabase;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore mstore;
    private String userID;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userType;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_profile_creation);

        socialMediaLinkEditText = (EditText) findViewById(R.id.socialMediaEditText);
        phoneNumberEditText = (EditText) findViewById(R.id.editTextPhoneNumber);
        mainContactNameEditText = (EditText) findViewById(R.id.editTextMainContact);
        locationEditText = (EditText) findViewById(R.id.editTextLocation);
        enterButton = (Button) findViewById(R.id.enterClubProfileButton);
        exitButton = (Button) findViewById(R.id.exitClubProfileInfoButton);

        userDatabase = FirebaseDatabase.getInstance().getReference("users");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mstore = FirebaseFirestore.getInstance();

        // Checks if the user exists
        if (user == null){
            Intent loginIntent = new Intent(getApplicationContext(),LogIn.class);
            startActivity(loginIntent);
            finish();
        }

        else {
            userID = auth.getCurrentUser().getUid();
            DatabaseReference dR = FirebaseDatabase.getInstance().getReference();
            dR.child("users").child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }

                    else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        User currentUser = task.getResult().getValue(User.class);
                        userName = currentUser.getUsername();
                        userEmail = currentUser.getEmail();
                        userPassword = currentUser.getPassword();
                        userType = currentUser.getType();
                    }
                }
            });
        }

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = socialMediaLinkEditText.getText().toString().trim();
                String number = phoneNumberEditText.getText().toString().trim();
                String contactName = mainContactNameEditText.getText().toString().trim();
                String location = locationEditText.getText().toString().trim();

                if(TextUtils.isEmpty(link) || TextUtils.isEmpty(number) || TextUtils.isEmpty(location)){
                    Toast.makeText(ClubProfile.this, "You must input all mandatory fields", Toast.LENGTH_SHORT).show();
                }

                /*
                 * THE FOLLOWING ELSE IF STATEMENT CONTAINS ELEMENTS FROM
                 * ANDROID DEVELOPERS DOCUMENTATION ABOUT THE PATTERNS CLASS
                 * WITH RESPECT TO THE URL VERIFYING METHOD
                 * MOST RECENT ACCESS: 11/22/2023
                 * URL: https://developer.android.com/reference/android/util/Patterns#WEB_URL
                 * */
                else if(!Patterns.WEB_URL.matcher(link).matches()){ //if the URL is invalid, don't accept
                    Toast.makeText(ClubProfile.this, "That is not a valid social media link.", Toast.LENGTH_SHORT).show();
                }

                else{
                    User newUser = new User(userID, userName, userType, userPassword, userEmail, link, number, contactName, location);
                    userDatabase.child(userID).setValue(newUser);

                    Intent returnIntent = new Intent(getApplicationContext(), CyclingClubOwner.class);
                    startActivity(returnIntent);
                    finish();
                }

            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent(getApplicationContext(),CyclingClubOwner.class);
                startActivity(returnIntent);
                finish();
            }
        });


    }


}
