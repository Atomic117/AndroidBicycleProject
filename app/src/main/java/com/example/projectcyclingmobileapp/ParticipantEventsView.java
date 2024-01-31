package com.example.projectcyclingmobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParticipantEventsView extends AppCompatActivity {


    private Button btnReturn;

    private ListView listJoinedEvents;

    private List<EventsJoined> joinedEvents;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private String userID;
    private DatabaseReference databaseJoinedEvents;

    private DatabaseReference databaseFeedbackList;
    private String currentRating="";
    private String userName;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_joined_events);
        btnReturn = (Button) findViewById(R.id.btnReturn);
        listJoinedEvents = findViewById(R.id.eventList);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        userID = user.getUid();

        joinedEvents = new ArrayList<>();

        databaseJoinedEvents = FirebaseDatabase.getInstance().getReference("joined events");
        databaseFeedbackList = FirebaseDatabase.getInstance().getReference("feedback");


        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent(getApplicationContext(), Participant.class);
                startActivity(returnIntent);
                finish();
            }
        });

        listJoinedEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                EventsJoined currentEvent = joinedEvents.get(i);
                if(!currentEvent.getAttribute4().equals("")){
                    manageEvent3(currentEvent);
                }else if(!currentEvent.getAttribute3().equals("")){
                    manageEvent2(currentEvent);
                }else if(!currentEvent.getAttribute2().equals("")){
                    manageEvent1(currentEvent);
                }
                return true;
            }
        });
    }



    private void manageEvent1(EventsJoined currentEvent){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.event_edit_1, null);
        dialogBuilder.setView(dialogView);

        final TextView menuTitle = (TextView) dialogView.findViewById(R.id.type);
        final TextView eventTitle = (TextView) dialogView.findViewById(R.id.inputTitle);
        final TextView eventLocation = (TextView)  dialogView.findViewById(R.id.inputLocation);
        final TextView  eventDate = (TextView)  dialogView.findViewById(R.id.inputDate);
        final TextView  eventName1 =  (TextView) dialogView.findViewById(R.id.display1);
        final TextView  eventAttribute1 = (TextView)  dialogView.findViewById(R.id.details1);
        final TextView  eventName2 = (TextView)  dialogView.findViewById(R.id.display2);
        final TextView  eventAttribute2 = (TextView)  dialogView.findViewById(R.id.details2);
        final Button btnCancel = dialogView.findViewById(R.id.cancelType);
        final Button btnDelete = dialogView.findViewById(R.id.deleteType);
        final Button btnRate = dialogView.findViewById(R.id.saveType);


        menuTitle.setText("Manage Event");
        eventTitle.setText(currentEvent.getTitle());
        eventLocation.setText(currentEvent.getLocation());
        eventDate.setText(currentEvent.getDate());
        eventName1.setText(currentEvent.getAttributeName1());
        eventAttribute1.setText(currentEvent.getAttribute1());
        eventName2.setText(currentEvent.getAttributeName2());
        eventAttribute2.setText(currentEvent.getAttribute2());
        btnRate.setText("Rate Club");


        final AlertDialog c = dialogBuilder.create();
        c.show();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent(currentEvent.getJoinedID());
                c.dismiss();

            }
        });
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRating(currentEvent);
                c.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.dismiss();
            }
        });

    }
    private void manageEvent2(EventsJoined currentEvent){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.event_edit_2, null);
        dialogBuilder.setView(dialogView);

        final TextView menuTitle = (TextView) dialogView.findViewById(R.id.type);
        final TextView eventTitle = (TextView) dialogView.findViewById(R.id.inputTitle);
        final TextView eventLocation = (TextView)  dialogView.findViewById(R.id.inputLocation);
        final TextView  eventDate = (TextView)  dialogView.findViewById(R.id.inputDate);
        final TextView  eventName1 =  (TextView) dialogView.findViewById(R.id.display1);
        final TextView  eventAttribute1 = (TextView)  dialogView.findViewById(R.id.details1);
        final TextView  eventName2 = (TextView)  dialogView.findViewById(R.id.display2);
        final TextView  eventAttribute2 = (TextView)  dialogView.findViewById(R.id.details2);
        final TextView  eventName3 = (TextView)  dialogView.findViewById(R.id.display3);
        final TextView  eventAttribute3 = (TextView)  dialogView.findViewById(R.id.details3);
        final Button btnCancel = dialogView.findViewById(R.id.cancelType);
        final Button btnDelete = dialogView.findViewById(R.id.deleteType);
        final Button btnRate = dialogView.findViewById(R.id.saveType);

        menuTitle.setText("Manage Event");
        eventTitle.setText(currentEvent.getTitle());
        eventLocation.setText(currentEvent.getLocation());
        eventDate.setText(currentEvent.getDate());
        eventName1.setText(currentEvent.getAttributeName1());
        eventAttribute1.setText(currentEvent.getAttribute1());
        eventName2.setText(currentEvent.getAttributeName2());
        eventAttribute2.setText(currentEvent.getAttribute2());
        eventName3.setText(currentEvent.getAttributeName3());
        eventAttribute3.setText(currentEvent.getAttribute3());
        btnRate.setText("Rate Club");


        final AlertDialog c = dialogBuilder.create();
        c.show();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent(currentEvent.getJoinedID());
                c.dismiss();

            }
        });
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRating(currentEvent);
                c.dismiss();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.dismiss();
            }
        });


    }
    private void manageEvent3(EventsJoined currentEvent){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.event_edit_3, null);
        dialogBuilder.setView(dialogView);


        final TextView menuTitle = (TextView) dialogView.findViewById(R.id.type);
        final TextView eventTitle = (TextView) dialogView.findViewById(R.id.inputTitle);
        final TextView eventLocation = (TextView)  dialogView.findViewById(R.id.inputLocation);
        final TextView  eventDate = (TextView)  dialogView.findViewById(R.id.inputDate);
        final TextView  eventName1 =  (TextView) dialogView.findViewById(R.id.display1);
        final TextView  eventAttribute1 = (TextView)  dialogView.findViewById(R.id.details1);
        final TextView  eventName2 = (TextView)  dialogView.findViewById(R.id.display2);
        final TextView  eventAttribute2 = (TextView)  dialogView.findViewById(R.id.details2);
        final TextView  eventName3 = (TextView)  dialogView.findViewById(R.id.display3);
        final TextView  eventAttribute3 = (TextView)  dialogView.findViewById(R.id.details3);
        final TextView  eventName4 = (TextView) dialogView.findViewById(R.id.display4);
        final TextView  eventAttribute4 = (TextView) dialogView.findViewById(R.id.details4);
        final Button btnCancel = dialogView.findViewById(R.id.cancelType);
        final Button btnDelete = dialogView.findViewById(R.id.deleteType);
        final Button btnRate = dialogView.findViewById(R.id.saveType);


        menuTitle.setText("Manage Event");
        eventTitle.setText(currentEvent.getTitle());
        eventLocation.setText(currentEvent.getLocation());
        eventDate.setText(currentEvent.getDate());
        eventName1.setText(currentEvent.getAttributeName1());
        eventAttribute1.setText(currentEvent.getAttribute1());
        eventName2.setText(currentEvent.getAttributeName2());
        eventAttribute2.setText(currentEvent.getAttribute2());
        eventName3.setText(currentEvent.getAttributeName3());
        eventAttribute3.setText(currentEvent.getAttribute3());
        eventName4.setText(currentEvent.getAttributeName4());
        eventAttribute4.setText(currentEvent.getAttribute4());
        btnRate.setText("Rate Club");


        final AlertDialog c = dialogBuilder.create();
        c.show();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent(currentEvent.getJoinedID());
                c.dismiss();

            }
        });
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRating(currentEvent);
                c.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.dismiss();
            }
        });


    }

    private void addRating(EventsJoined currentEvent){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.club_rate_popup, null);
        dialogBuilder.setView(dialogView);

        final Button btnRank1 = dialogView.findViewById(R.id.Ranking1);
        final Button btnRank2 = dialogView.findViewById(R.id.Ranking2);
        final Button btnRank3 = dialogView.findViewById(R.id.Ranking3);
        final Button btnRank4 = dialogView.findViewById(R.id.Ranking4);
        final Button btnRank5 = dialogView.findViewById(R.id.Ranking5);
        final Button btnDone = dialogView.findViewById(R.id.DoneRanking);
        final Button btnCancel = dialogView.findViewById(R.id.CancelRanking);
        final EditText feedback = dialogView.findViewById(R.id.RankingComment);


        final AlertDialog b = dialogBuilder.create();
        b.show();
        btnRank1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRating = "1";
            }
        });
        btnRank2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRating = "2";
            }
        });

        btnRank3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRating = "3";
            }
        });

        btnRank4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRating = "4";
            }
        });

        btnRank5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRating = "5";
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentRating.equals("")) {
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference();
                    String userId = currentEvent.getUserID();
                    dR.child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            }
                            else {
                                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                User currentUser = task.getResult().getValue(User.class);
                                userName = currentUser.getUsername();
                                String feedbackComment = feedback.getText().toString().trim();
                                String feedbackID = databaseFeedbackList.push().getKey();

                                Feedback feedbackAdd = new Feedback(currentEvent.getClubID(), currentRating, feedbackComment, userName);
                                databaseFeedbackList.child(feedbackID).setValue(feedbackAdd);
                                Toast.makeText(ParticipantEventsView.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    b.dismiss();
                }else{
                    Toast.makeText(ParticipantEventsView.this, "Please Enter a valid rating!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();

            }
        });

    }

    private boolean deleteEvent(String eventID) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("joined events").child(eventID);
        dR.removeValue();
        Toast.makeText(ParticipantEventsView.this, "EVENT REMOVED", Toast.LENGTH_SHORT).show();
        return true;
    }


    protected void onStart(){
        super.onStart();

        databaseJoinedEvents = FirebaseDatabase.getInstance().getReference("joined events");
        databaseJoinedEvents.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                joinedEvents.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    EventsJoined events = postSnapshot.getValue(EventsJoined.class);
                    if (events.getUserID().equals(userID)){
                        joinedEvents.add(events);
                    }
                }

                EventsJoinedAdapter eventAdapter = new EventsJoinedAdapter(ParticipantEventsView.this, joinedEvents);
                listJoinedEvents.setAdapter(eventAdapter);

            }

            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }
}