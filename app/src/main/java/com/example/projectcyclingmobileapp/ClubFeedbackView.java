package com.example.projectcyclingmobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ClubFeedbackView extends AppCompatActivity {
    private Button btnReturn;
    private TextView pageTitle, instructionText;
    private ListView feedbackListView;
    private List<Feedback> feedbackList;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String userID;

    private DatabaseReference databaseUserFeedbackList;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_joined_events);
        btnReturn = findViewById(R.id.btnReturn);
        pageTitle = findViewById(R.id.eventsTitle);
        instructionText = findViewById(R.id.infoText);
        feedbackListView = findViewById(R.id.eventList);

        pageTitle.setText("Club Feedback");
        instructionText.setText("");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userID = user.getUid();

        feedbackList = new ArrayList<>();

        databaseUserFeedbackList = FirebaseDatabase.getInstance().getReference("feedback");

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent(getApplicationContext(), CyclingClubOwner.class);
                startActivity(returnIntent);
                finish();
            }
        });
        feedbackListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Feedback currentFeedbackElement = feedbackList.get(i);
                showFeedbackElement(currentFeedbackElement);
                return true;
            }
        });
    }

    private void showFeedbackElement(Feedback currentFeedbackElement){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.feedback_element_view, null);
        dialogBuilder.setView(dialogView);

        final TextView userNameShow = dialogView.findViewById(R.id.userNameShow);
        final TextView ratingShow = dialogView.findViewById(R.id.userRatingShow);
        final TextView commentShow= dialogView.findViewById(R.id.commentShow);
        final Button btnReturn2 = dialogView.findViewById(R.id.buttonReturn);

        userNameShow.setText(currentFeedbackElement.getUserName());
        ratingShow.setText(currentFeedbackElement.getRating());
        commentShow.setText(currentFeedbackElement.getComment());

        final AlertDialog c = dialogBuilder.create();
        c.show();
        btnReturn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.dismiss();
            }
        });

    }

    protected void onStart(){
        super.onStart();

        databaseUserFeedbackList = FirebaseDatabase.getInstance().getReference("feedback");
        databaseUserFeedbackList.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                feedbackList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Feedback feedbackElements = postSnapshot.getValue(Feedback.class);
                    if (feedbackElements.getClubID().equals(userID)){
                        feedbackList.add(feedbackElements);
                    }
                }
                Toast.makeText(ClubFeedbackView.this, "WORKS HERE", Toast.LENGTH_SHORT).show();


                FeedbackAdapter feedbackAdapter = new FeedbackAdapter(ClubFeedbackView.this, feedbackList);
                feedbackListView.setAdapter(feedbackAdapter);

            }

            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }}


