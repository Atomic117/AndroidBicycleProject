package com.example.projectcyclingmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class SignUp extends AppCompatActivity {

    //declaring our text bars (email/password) and our buttons(login/register)
    TextInputEditText emailTextBar, passwordTextBar, userTextBar;
    Button signUpButton,loginButton;
    //declaring firebase authentication variable
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    Spinner accountType;
    String userType;
    String userID;

    DatabaseReference databaseUsers;


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
        setContentView(R.layout.activity_sign_up);

        //mapping our variables to our layout elements
        emailTextBar = findViewById(R.id.emailCreation);
        passwordTextBar = findViewById(R.id.passwordCreation);
        userTextBar = findViewById(R.id.usernameCreation);
        signUpButton = findViewById(R.id.btn_SignUpConfirm);
        loginButton = findViewById(R.id.btn_LogIn);
        mAuth = FirebaseAuth.getInstance();
        accountType = findViewById(R.id.accountTypeCreation);
        mStore = FirebaseFirestore.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, name;
                email = String.valueOf(emailTextBar.getText());
                password = String.valueOf(passwordTextBar.getText());
                name = String.valueOf(userTextBar.getText());
                userType = String.valueOf(accountType.getSelectedItem().toString());

                /* CODE BELOW USES ELEMENTS FROM FIREBASE DOCUMENTATION
                * FOR USER AUTHENTICATION
                * ACCESSED: 10/07/2023
                * URL: https://firebase.google.com/docs/auth/android/password-auth#java_3
                * */

                /* CODE BELOW USES ELEMENTS FROM FIREBASE DOCUMENTATION
                 * FOR SAVING DATA ON THE CLOUD DATABASE
                 * ACCESSED: 10/09/2023
                 * URL: https://firebase.google.com/docs/database/admin/save-data
                 * */


                ///Checks if each textbox is not null, and the password is strong
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignUp.this, "Enter a valid email",Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)){
                    Toast.makeText(SignUp.this, "Password can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.length() <= 6){
                    Toast.makeText(SignUp.this, "Password length needs to greater than 6",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(name)){
                    Toast.makeText(SignUp.this, "Username can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "createUserWithEmail:success");
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                    Toast.makeText(SignUp.this, userType + " account has been created. ", Toast.LENGTH_SHORT).show();
                                    ///Stores information in the cloud Firebase database using the Map class
                                    userID = mAuth.getCurrentUser().getUid();

                                    User newUser = new User(userID, name, userType, password, email);
                                    databaseUsers.child(userID).setValue(newUser);

                                    Intent logInIntent = new Intent(getApplicationContext(),LogIn.class);
                                    startActivity(logInIntent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUp.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(getApplicationContext(),LogIn.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }
}