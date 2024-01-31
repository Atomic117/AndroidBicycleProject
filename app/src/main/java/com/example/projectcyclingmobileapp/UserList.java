package com.example.projectcyclingmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {


    FirebaseFirestore mstore;
    Button btnOut;
    ListView listAccount;
    List<User> users = new ArrayList<>();
    DatabaseReference userDatabase;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountlist);


        btnOut = findViewById(R.id.btnBack);
        listAccount = findViewById(R.id.accountList);
        mstore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        listAccount.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                User currentUser = users.get(i);
                String id = currentUser.getId();

                String email = currentUser.getEmail();
                String password = currentUser.getPassword();

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UserList.this, "ACCOUNT DELETED", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth newAuth = FirebaseAuth.getInstance();
                                    FirebaseUser user = newAuth.getCurrentUser();
                                    user.delete();
                                } else {
                                    Toast.makeText(UserList.this, "ERROR DELETING ACCOUNT", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("users").child(id);
                dR.removeValue();
                return true;
            }
        });

        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminIntent = new Intent(getApplicationContext(),Admin.class);
                startActivity(adminIntent);
                finish();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        userDatabase = FirebaseDatabase.getInstance().getReference("users");
        userDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User userAdd = postSnapshot.getValue(User.class);
                    users.add(userAdd);
                }
                UserAdapter userAdapter = new UserAdapter(UserList.this, users);
                listAccount.setAdapter(userAdapter);
            }

            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }
}
