package com.example.projectcyclingmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayDeque;
import java.util.Deque;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class EventTypeDisplay extends AppCompatActivity {
    private Button buttonCreateEventDetails;
    private Button buttonReturn;
    private ListView listEvents;
    private List<EventType> eventTypes;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String clubid;
    private DatabaseReference datebaseType;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details_list);
        buttonCreateEventDetails = (Button) findViewById(R.id.btnCreateDetails);
        buttonReturn = (Button) findViewById(R.id.btnReturn);
        listEvents = findViewById(R.id.eventList);

        eventTypes = new ArrayList<>();

        datebaseType = FirebaseDatabase.getInstance().getReference("events");

        buttonCreateEventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEventType();
            }
        });

        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminIntent = new Intent(getApplicationContext(), Admin.class);
                startActivity(adminIntent);
                finish();
            }
        });

        listEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                editEventType(i);
                return true;
            }
        });
    }

    private void editEventType(int i){
        EventType currentType = eventTypes.get(i);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.event_type_edit, null);
        dialogBuilder.setView(dialogView);

        Button delete = (Button) dialogView.findViewById(R.id.buttonfordelete);
        Button cancel = (Button) dialogView.findViewById(R.id.buttonforcancel);
        Button save = (Button) dialogView.findViewById(R.id.buttonforsave);
        TextInputEditText eventType = (TextInputEditText) dialogView.findViewById(R.id.inputType);
        TextInputEditText attribute1 = (TextInputEditText) dialogView.findViewById(R.id.details1);
        TextInputEditText attribute2 = (TextInputEditText) dialogView.findViewById(R.id.details2);
        TextInputEditText attribute3 = (TextInputEditText) dialogView.findViewById(R.id.details3);
        TextInputEditText attribute4 = (TextInputEditText) dialogView.findViewById(R.id.details4);

        eventType.setText(currentType.getEventType());
        attribute1.setText(currentType.getAttributeName1());
        attribute2.setText(currentType.getAttributeName2());
        attribute3.setText(currentType.getAttributeName3());
        attribute4.setText(currentType.getAttributeName4());

        final AlertDialog d = dialogBuilder.create();
        d.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(currentType.getEventID());
                dR.removeValue();
                Toast.makeText(EventTypeDisplay.this, "EVENT TYPE DELETED", Toast.LENGTH_SHORT).show();
                d.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayDeque<String> queue = new ArrayDeque<String>();

                String type = eventType.getText().toString().trim();
                String name1 = attribute1.getText().toString().trim();
                String name2 = attribute2.getText().toString().trim();
                String name3 = attribute3.getText().toString().trim();
                String name4 = attribute4.getText().toString().trim();


                if (type.length() <= 0){
                    Toast.makeText(EventTypeDisplay.this, "EVENT TYPE CAN'T BE EMPTY", Toast.LENGTH_SHORT).show();
                } else {
                    if (name1.length() > 0) {
                        queue.addLast(name1);
                    }
                    if (name2.length() > 0) {
                        queue.addLast(name2);
                    }
                    if (name3.length() > 0) {
                        queue.addLast(name3);
                    }
                    if (name4.length() > 0) {
                        queue.addLast(name4);
                    }

                    if (queue.size() >= 2){
                        String[] requirement = new String[]{"", "", "", ""};
                        int size = queue.size();
                        for (int i = 0; i < size; i ++){
                            requirement[i] = queue.pollFirst();
                        }

                        String eventTypeID = currentType.getEventID();
                        EventType addEventType = new EventType(eventTypeID, type, requirement[0], requirement[1], requirement[2], requirement[3]);
                        datebaseType.child(eventTypeID).setValue(addEventType);
                        eventType.setText("");
                        attribute1.setText("");
                        attribute2.setText("");
                        attribute3.setText("");
                        attribute4.setText("");
                        Toast.makeText(EventTypeDisplay.this, "EVENT TYPE EDITTED", Toast.LENGTH_SHORT).show();
                        d.dismiss();
                    } else {
                        Toast.makeText(EventTypeDisplay.this, "ENTER AT LEAST TWO REQUIREMENTS", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });





    }

    private void createEventType(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.event_type_creation, null);
        dialogBuilder.setView(dialogView);

        Button cancel = (Button) dialogView.findViewById(R.id.cancelType);
        Button save = (Button) dialogView.findViewById(R.id.saveType);
        TextInputEditText eventType = (TextInputEditText) dialogView.findViewById(R.id.inputType);
        TextInputEditText attribute1 = (TextInputEditText) dialogView.findViewById(R.id.details1);
        TextInputEditText attribute2 = (TextInputEditText) dialogView.findViewById(R.id.details2);
        TextInputEditText attribute3 = (TextInputEditText) dialogView.findViewById(R.id.details3);
        TextInputEditText attribute4 = (TextInputEditText) dialogView.findViewById(R.id.details4);


        final AlertDialog b = dialogBuilder.create();
        b.show();

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                eventType.setText("");
                attribute1.setText("");
                attribute2.setText("");
                attribute3.setText("");
                attribute4.setText("");
                b.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayDeque<String> queue = new ArrayDeque<String>();

                String type = eventType.getText().toString().trim();
                String name1 = attribute1.getText().toString().trim();
                String name2 = attribute2.getText().toString().trim();
                String name3 = attribute3.getText().toString().trim();
                String name4 = attribute4.getText().toString().trim();


                if (type.length() <= 0){
                    Toast.makeText(EventTypeDisplay.this, "EVENT TYPE CAN'T BE EMPTY", Toast.LENGTH_SHORT).show();
                } else {
                    if (name1.length() > 0) {
                        queue.addLast(name1);
                    }
                    if (name2.length() > 0) {
                        queue.addLast(name2);
                    }
                    if (name3.length() > 0) {
                        queue.addLast(name3);
                    }
                    if (name4.length() > 0) {
                        queue.addLast(name4);
                    }

                    if (queue.size() >= 2){
                        String[] requirement = new String[]{"", "", "", ""};
                        int size = queue.size();
                        for (int i = 0; i < size; i ++){
                            requirement[i] = queue.pollFirst();
                        }

                        String eventTypeID = datebaseType.push().getKey();
                        EventType addEventType = new EventType(eventTypeID, type, requirement[0], requirement[1], requirement[2], requirement[3]);
                        datebaseType.child(eventTypeID).setValue(addEventType);
                        eventType.setText("");
                        attribute1.setText("");
                        attribute2.setText("");
                        attribute3.setText("");
                        attribute4.setText("");
                        Toast.makeText(EventTypeDisplay.this, "NEW EVENT TYPE CREATED", Toast.LENGTH_SHORT).show();
                        b.dismiss();
                    } else {
                        Toast.makeText(EventTypeDisplay.this, "ENTER AT LEAST TWO REQUIREMENTS", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    protected void onStart(){
        super.onStart();

        datebaseType = FirebaseDatabase.getInstance().getReference("events");
        datebaseType.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventTypes.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    EventType eventType = postSnapshot.getValue(EventType.class);
                    eventTypes.add(eventType);
                }


                EventTypeAdapter eventAdapter = new EventTypeAdapter(EventTypeDisplay.this, eventTypes);
                listEvents.setAdapter(eventAdapter);


            }

            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }
}
