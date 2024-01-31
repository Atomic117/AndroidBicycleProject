package com.example.projectcyclingmobileapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
public class ParticipantClubPageAccess extends AppCompatActivity {
    private Button btnReturn, btnRateClub;
    private ListView listEvents;
    private List<Event> eventsWithDetails;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String clubID;
    private String clubName;
    private DatabaseReference databaseEventDetails;
    private DatabaseReference databaseUserJoinedEvents;


    private TextView clubTitle;
    int currentRating;
    String feedbackComment;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cycling_club_display_events);

        btnReturn = findViewById(R.id.ReturnToSearch);
        listEvents = findViewById(R.id.CyclingClubEvents);
        clubTitle = findViewById(R.id.CyclingClubNameDisplay);
        databaseEventDetails = FirebaseDatabase.getInstance().getReference("event details");
        eventsWithDetails = new ArrayList<>();
        Intent openClubPageIntent = getIntent();
        clubID = openClubPageIntent.getStringExtra("ClubIDKey");
        clubName = openClubPageIntent.getStringExtra("ClubNameKey");
        auth = FirebaseAuth.getInstance();


        databaseUserJoinedEvents = FirebaseDatabase.getInstance().getReference("joined events");


        clubTitle.setText(clubName);


        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent(getApplicationContext(), EventSearch.class);
                startActivity(returnIntent);
                finish();
            }
        });


        listEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event currentEvent = eventsWithDetails.get(i);
                if(!currentEvent.getAttribute4().equals("")){
                    addEvent3(currentEvent);
                }else if(!currentEvent.getAttribute3().equals("")){
                    addEvent2(currentEvent);
                }else if(!currentEvent.getAttribute2().equals("")){
                    addEvent1(currentEvent);
                }

                return true;
            }
        });
    }



    private void addEvent1(Event currentEvent){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.event_creation_1, null);
        dialogBuilder.setView(dialogView);

        final TextView menuTitle = (TextView) dialogView.findViewById(R.id.type);
        final TextView eventTitle = (TextView) dialogView.findViewById(R.id.inputTitle);
        final TextView eventLocation = (TextView)  dialogView.findViewById(R.id.inputLocation);
        final TextView  eventDate = (TextView)  dialogView.findViewById(R.id.inputDate);
        final TextView  eventName1 =  (TextView) dialogView.findViewById(R.id.display1);
        final TextView  eventAttribute1 = (TextView)  dialogView.findViewById(R.id.details1);
        final TextView  eventName2 = (TextView)  dialogView.findViewById(R.id.display2);
        final TextView  eventAttribute2 = (TextView)  dialogView.findViewById(R.id.details2);
        final Button btnJoinEvent = dialogView.findViewById(R.id.saveType);
        final Button btnCancel = dialogView.findViewById(R.id.cancelType);

        menuTitle.setText("Join an Event");
        eventTitle.setText(currentEvent.getTitle());
        eventLocation.setText(currentEvent.getLocation());
        eventDate.setText(currentEvent.getDate());
        eventName1.setText(currentEvent.getAttributeName1());
        eventAttribute1.setText(currentEvent.getAttribute1());
        eventName2.setText(currentEvent.getAttributeName2());
        eventAttribute2.setText(currentEvent.getAttribute2());


        final AlertDialog c = dialogBuilder.create();
        c.show();

        btnJoinEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinEvent(currentEvent);
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

    private void addEvent2(Event currentEvent){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.event_creation_2, null);
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
        final Button btnJoinEvent = dialogView.findViewById(R.id.saveType);
        final Button btnCancel = dialogView.findViewById(R.id.cancelType);

        menuTitle.setText("Join an Event");
        eventTitle.setText(currentEvent.getTitle());
        eventLocation.setText(currentEvent.getLocation());
        eventDate.setText(currentEvent.getDate());
        eventName1.setText(currentEvent.getAttributeName1());
        eventAttribute1.setText(currentEvent.getAttribute1());
        eventName2.setText(currentEvent.getAttributeName2());
        eventAttribute2.setText(currentEvent.getAttribute2());
        eventName3.setText(currentEvent.getAttributeName3());
        eventAttribute3.setText(currentEvent.getAttribute3());

        final AlertDialog c = dialogBuilder.create();
        c.show();

        btnJoinEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinEvent(currentEvent);
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

    private void addEvent3(Event currentEvent){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.event_creation_3, null);
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
        final Button btnJoinEvent = dialogView.findViewById(R.id.saveType);
        final Button btnCancel = dialogView.findViewById(R.id.cancelType);

        menuTitle.setText("Join an Event");
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

        final AlertDialog c = dialogBuilder.create();
        c.show();

        btnJoinEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinEvent(currentEvent);
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

    private void joinEvent(Event currentEvent){
        String eventID = databaseUserJoinedEvents.push().getKey();
        EventsJoined event = new EventsJoined(currentEvent.geteventID(), currentEvent.getEventType(), currentEvent.getTitle(), currentEvent.getLocation(), currentEvent.getDate(), currentEvent.getAttribute1(), currentEvent.getAttribute2(), currentEvent.getAttribute3(), currentEvent.getAttribute4(), currentEvent.getAttributeName1(),
                currentEvent.getAttributeName2(), currentEvent.getAttributeName3(), currentEvent.getAttributeName4(), currentEvent.getClubID(),auth.getCurrentUser().getUid(),eventID);
        databaseUserJoinedEvents.child(eventID).setValue(event);

        Toast.makeText(ParticipantClubPageAccess.this, "Event Joined", Toast.LENGTH_SHORT).show();

    }
    protected void onStart(){
        super.onStart();
        databaseEventDetails = FirebaseDatabase.getInstance().getReference("event details");
        databaseEventDetails.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventsWithDetails.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Event events = postSnapshot.getValue(Event.class);
                    if (events.getClubID().equals(clubID)){
                        eventsWithDetails.add(events);
                    }
                }
                EventList eventAdapter = new EventList(ParticipantClubPageAccess.this, eventsWithDetails);
                listEvents.setAdapter(eventAdapter);
            }
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}