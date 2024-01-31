package com.example.projectcyclingmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
public class EventTypeList extends AppCompatActivity{
    private Button buttonNewEvent;
    private Button buttonReturn;
    private ListView listViewEvents;

    private List<Event> events;

    private DatabaseReference databaseEvents;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_type_list);
        listViewEvents = (ListView) findViewById(R.id.listViewEvents);
        buttonNewEvent = (Button) findViewById(R.id.newEventButton);
        buttonReturn = (Button) findViewById(R.id.returnButton);

        events = new ArrayList<>();
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");

        buttonNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showEventCreatePopup();
        }
        });
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent(getApplicationContext(), Admin.class);
                startActivity(returnIntent);
                finish();

            }
        });
        listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Event event = events.get(i);
                showEventEditMenu(event.getEventID(), event.getEventTitle());
            }
        });
    }
    private void showEventCreatePopup(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.event_type_create_popup, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextType = (EditText) dialogView.findViewById(R.id.editTextTitle);
        final EditText editTextRequirements = (EditText) dialogView.findViewById(R.id.editTextDetails);
        final Button buttonBack = (Button) dialogView.findViewById(R.id.buttonReturn);
        final Button buttonCreateEvent = (Button) dialogView.findViewById(R.id.buttonCreate);

        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventType = editTextType.getText().toString().trim();
                String eventRequirements = editTextRequirements.getText().toString().trim();
                String[] checkRequirements = eventRequirements.split(",");
                boolean isProperFormat = false;

                if(checkRequirements.length > 10){ //Ensuring the admin does not attempt to set too many requirements
                    Toast.makeText(EventTypeList.this, "You cannot have more than 10 requirements.", Toast.LENGTH_SHORT).show();
                }

                else{
                    //Checking that the requirements are in the correct format (name:type)
                    //where the type can either be integer (i), string (s), float(f), or boolean (b)
                    for(int i = 0; i < checkRequirements.length; i++){
                        if(checkRequirements[i].endsWith(":i") || checkRequirements[i].endsWith(":s") || checkRequirements[i].endsWith(":f") || checkRequirements[i].endsWith(":b")){
                            isProperFormat = true;
                        }
                    }

                    if(!isProperFormat){
                        Toast.makeText(EventTypeList.this, "You must strictly follow the provided guidelines for the format of the requirements.", Toast.LENGTH_SHORT).show();
                    }

                    else if (!TextUtils.isEmpty(eventType)) { //Ensuring the admin sets an event type
                        String eventID = databaseEvents.push().getKey();
                        Event event = new Event(eventID,eventType,eventRequirements);
                        databaseEvents.child(eventID).setValue(event);
                        editTextType.setText("");
                        editTextRequirements.setText("");
                        b.dismiss();
                    }

                    else{
                        Toast.makeText(EventTypeList.this, "You must enter a title.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                editTextType.setText("");
                editTextRequirements.setText("");
                b.dismiss();
            }
        });
    }
    protected void onStart(){
        super.onStart();
        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                events.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Event event = postSnapshot.getValue(Event.class);
                    events.add(event);
                }
                EventTypeAdapter eventsAdapter = new EventTypeAdapter(EventTypeList.this, events);
                listViewEvents.setAdapter(eventsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void showEventEditMenu(final String eventID, String eventTitle){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.event_type_edit_popup, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextTitle = (EditText) dialogView.findViewById(R.id.editTextTitle2);
        final EditText editTextDetails = (EditText) dialogView.findViewById(R.id.editTextDetails2);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);
        final Button buttonSave = (Button) dialogView.findViewById(R.id.buttonSave);

        dialogBuilder.setTitle(eventTitle);
        final AlertDialog a = dialogBuilder.create();
        a.show();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventType = editTextTitle.getText().toString().trim();
                String eventRequirements = editTextDetails.getText().toString().trim();
                String[] checkRequirements = eventRequirements.split(",");
                boolean isProperFormat = false;

                if(checkRequirements.length > 10){
                    Toast.makeText(EventTypeList.this, "You cannot have more than 10 requirements.", Toast.LENGTH_SHORT).show();
                }

                else{
                    //Checking that the requirements are in the correct format (name:type)
                    //where the type can either be integer (i), string (s), float(f), or boolean (b)
                    for(int i = 0; i < checkRequirements.length; i++){
                        if(checkRequirements[i].endsWith(":i") || checkRequirements[i].endsWith(":s") || checkRequirements[i].endsWith(":f") || checkRequirements[i].endsWith(":b")){
                            isProperFormat = true;
                        }
                    }

                    if(!isProperFormat){
                        Toast.makeText(EventTypeList.this, "You must strictly follow the provided guidelines for the format of the requirements.", Toast.LENGTH_SHORT).show();
                    }

                    else if (!TextUtils.isEmpty(eventType)) { //Ensuring the admin does not create an event type without a name
                        updateEvent(eventID, eventType, eventRequirements);
                        a.dismiss();
                    }

                    else{
                        Toast.makeText(EventTypeList.this, "You must enter a title.", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent(eventID);
                a.dismiss();
            }
        });
    }
    private void updateEvent(String eventID, String eventType, String eventRequirements){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(eventID);
        Event event = new Event(eventID, eventType, eventRequirements);
        dR.setValue(event);
    }
    private boolean deleteEvent(String eventID){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("events").child(eventID);
        dR.removeValue();
        return true;
    }
    }
