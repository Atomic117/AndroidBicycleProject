package com.example.projectcyclingmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.*;

public class LogIn extends AppCompatActivity {
    //declaring our text bars (email/password) and our buttons(login/register)
    TextInputEditText emailTextBar, passwordTextBar;
    Button signUpButton,loginButton;
    //declaring firebase authentication variable
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;


    /* CODE BELOW USES ELEMENTS FROM FIREBASE DOCUMENTATION
     * FOR USER AUTHENTICATION
     * ACCESSED: 10/07/2023
     * URL: https://firebase.google.com/docs/auth/android/password-auth#java_3
     * */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent welcomeIntent = new Intent(getApplicationContext(),Welcome.class);
            startActivity(welcomeIntent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mapping our variables to our layout elements
        emailTextBar = findViewById(R.id.Username);
        passwordTextBar = findViewById(R.id.Password);
        loginButton = findViewById(R.id.btn_LogInConfirm   );
        signUpButton = findViewById(R.id.btn_SignUp);
        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(emailTextBar.getText().toString().trim());
                password = String.valueOf(passwordTextBar.getText().toString().trim());

                /* CODE BELOW USES ELEMENTS FROM FIREBASE DOCUMENTATION
                 * FOR USER AUTHENTICATION
                 * ACCESSED: 10/07/2023
                 * URL: https://firebase.google.com/docs/auth/android/password-auth#java_3
                 * */

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LogIn.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LogIn.this, "Enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.equals("admin") && password.equals("admin")) {
                    Toast.makeText(LogIn.this, "Admin login successful.", Toast.LENGTH_SHORT).show();
                    Intent adminLoginIntent = new Intent(getApplicationContext(), Admin.class);
                    startActivity(adminLoginIntent);
                    finish();
                } else {

                    String finalEmail = email;
                    String finalPassword = password;

                    if (email.equals("gccadmin") && password.equals("GCCRocks!")) {
                        email = "gccadmin@admin.com";
                        password = "GCCRocks!";
                    }

                    else if (email.equals("cyclingaddict") && password.equals("cyclingIsLife!")){
                        email = "cyclingaddict@gmail.com";
                        password = "cyclingIsLife!";
                    }


                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LogIn.this, "Login successful.", Toast.LENGTH_SHORT).show();

                                        FirebaseAuth auth = FirebaseAuth.getInstance();
                                        String userId = auth.getCurrentUser().getUid();

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

                                                    /*

                                                    if(currentUser.getSocialMediaLink().trim().equals("")){
                                                        Intent profileIntent = new Intent(getApplicationContext(), ClubProfile.class);
                                                        startActivity(profileIntent);
                                                        finish();
                                                    }

                                                     */

                                                    Intent welcomeIntent = new Intent(getApplicationContext(), Welcome.class);
                                                    startActivity(welcomeIntent);
                                                    finish();
                                                }
                                            }
                                        });
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LogIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }
                                }
                            });
                }
            }
        });




        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(signUpIntent);
                finish();
            }
        });
    }}
