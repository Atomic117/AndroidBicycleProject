package com.example.projectcyclingmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
public class EventDetails extends AppCompatActivity{

    private Button buttonCreateEventDetails;
    private Button buttonReturn;
    private ListView listEvents;
    private List<Event> events;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String clubid;
    private DatabaseReference databaseEventDetails;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details_list);
        buttonCreateEventDetails = (Button) findViewById(R.id.btnCreateDetails);
        buttonReturn = (Button) findViewById(R.id.btnReturn);
        listEvents = findViewById(R.id.eventList);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        clubid = user.getUid();

        events = new ArrayList<>();

        databaseEventDetails = FirebaseDatabase.getInstance().getReference("event details");

        buttonCreateEventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoices();
            }
        });
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent(getApplicationContext(), CyclingClubOwner.class);
                startActivity(returnIntent);
                finish();
            }
        });

        listEvents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                editEvent(i);
                return true;
            }
        });


    }

    public void editEvent(int i){
        Event currentEvent = events.get(i);

        if (!currentEvent.getAttribute4().equals("")){
            AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(this);
            LayoutInflater inflater1 = getLayoutInflater();
            final View dialogView1 = inflater1.inflate(R.layout.event_edit_3, null);
            dialogBuilder1.setView(dialogView1);

            Button back = (Button) dialogView1.findViewById(R.id.cancelType);
            Button confirm = (Button) dialogView1.findViewById(R.id.saveType);
            Button delete = (Button) dialogView1.findViewById(R.id.deleteType);
            TextInputEditText inputedTitle = (TextInputEditText) dialogView1.findViewById(R.id.inputTitle);
            TextInputEditText inputedLocation = (TextInputEditText) dialogView1.findViewById(R.id.inputLocation);
            TextInputEditText inputedDate = (TextInputEditText) dialogView1.findViewById(R.id.inputDate);
            TextInputEditText inputedDetail1 = (TextInputEditText) dialogView1.findViewById(R.id.details1);
            TextInputEditText inputedDetail2 = (TextInputEditText) dialogView1.findViewById(R.id.details2);
            TextInputEditText inputedDetail3 = (TextInputEditText) dialogView1.findViewById(R.id.details3);
            TextInputEditText inputedDetail4 = (TextInputEditText) dialogView1.findViewById(R.id.details4);
            TextView displayType = (TextView) dialogView1.findViewById(R.id.type);
            TextView displayAttribute1 = (TextView) dialogView1.findViewById(R.id.display1);
            TextView displayAttribute2 = (TextView) dialogView1.findViewById(R.id.display2);
            TextView displayAttribute3 = (TextView) dialogView1.findViewById(R.id.display3);
            TextView displayAttribute4 = (TextView) dialogView1.findViewById(R.id.display4);

            displayType.setText(currentEvent.getEventType());
            displayAttribute1.setText(currentEvent.getAttributeName1());
            displayAttribute2.setText(currentEvent.getAttributeName2());
            displayAttribute3.setText(currentEvent.getAttributeName3());
            displayAttribute4.setText(currentEvent.getAttributeName4());
            inputedTitle.setText(currentEvent.getTitle());
            inputedLocation.setText(currentEvent.getLocation());
            inputedDate.setText(currentEvent.getDate());
            inputedDetail1.setText(currentEvent.getAttribute1());
            inputedDetail2.setText(currentEvent.getAttribute2());
            inputedDetail3.setText(currentEvent.getAttribute3());
            inputedDetail4.setText(currentEvent.getAttribute4());



            final AlertDialog e = dialogBuilder1.create();
            e.show();

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    e.dismiss();
                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = inputedTitle.getText().toString().trim();
                    String location = inputedLocation.getText().toString().trim();
                    String date = inputedDate.getText().toString().trim();
                    String name1 = inputedDetail1.getText().toString().trim();
                    String name2 = inputedDetail2.getText().toString().trim();
                    String name3 = inputedDetail3.getText().toString().trim();
                    String name4 = inputedDetail4.getText().toString().trim();

                    if (validateField(title) && validateField(location) && validateField(date) && validateField(name1)
                            && validateField(name2) && validateField(name3) && validateField(name4)) {
                        String eventID = currentEvent.geteventID();
                        Event event = new Event(eventID, currentEvent.getEventType(), title, location, date, name1, name2, name3, name4, currentEvent.getAttributeName1()
                                ,currentEvent.getAttributeName2(),currentEvent.getAttributeName3(),currentEvent.getAttributeName4(), clubid);
                        databaseEventDetails.child(eventID).setValue(event);
                        Toast.makeText(EventDetails.this, currentEvent.getEventType() +" editted", Toast.LENGTH_SHORT).show();
                        e.dismiss();
                    } else {
                        Toast.makeText(EventDetails.this, "INVALID ENTRIES", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("event details").child(currentEvent.geteventID());
                    dR.removeValue();
                    Toast.makeText(EventDetails.this, "EVENT DELETED", Toast.LENGTH_SHORT).show();
                    e.dismiss();
                }
            });

        } else if (!currentEvent.getAttribute3().equals("")){
            AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(this);
            LayoutInflater inflater1 = getLayoutInflater();
            final View dialogView1 = inflater1.inflate(R.layout.event_edit_2, null);
            dialogBuilder1.setView(dialogView1);

            Button back = (Button) dialogView1.findViewById(R.id.cancelType);
            Button confirm = (Button) dialogView1.findViewById(R.id.saveType);
            Button delete = (Button) dialogView1.findViewById(R.id.deleteType);
            TextInputEditText inputedTitle = (TextInputEditText) dialogView1.findViewById(R.id.inputTitle);
            TextInputEditText inputedLocation = (TextInputEditText) dialogView1.findViewById(R.id.inputLocation);
            TextInputEditText inputedDate = (TextInputEditText) dialogView1.findViewById(R.id.inputDate);
            TextInputEditText inputedDetail1 = (TextInputEditText) dialogView1.findViewById(R.id.details1);
            TextInputEditText inputedDetail2 = (TextInputEditText) dialogView1.findViewById(R.id.details2);
            TextInputEditText inputedDetail3 = (TextInputEditText) dialogView1.findViewById(R.id.details3);
            TextView displayType = (TextView) dialogView1.findViewById(R.id.type);
            TextView displayAttribute1 = (TextView) dialogView1.findViewById(R.id.display1);
            TextView displayAttribute2 = (TextView) dialogView1.findViewById(R.id.display2);
            TextView displayAttribute3 = (TextView) dialogView1.findViewById(R.id.display3);


            displayType.setText(currentEvent.getEventType());
            displayAttribute1.setText(currentEvent.getAttributeName1());
            displayAttribute2.setText(currentEvent.getAttributeName2());
            displayAttribute3.setText(currentEvent.getAttributeName3());
            inputedTitle.setText(currentEvent.getTitle());
            inputedLocation.setText(currentEvent.getLocation());
            inputedDate.setText(currentEvent.getDate());
            inputedDetail1.setText(currentEvent.getAttribute1());
            inputedDetail2.setText(currentEvent.getAttribute2());
            inputedDetail3.setText(currentEvent.getAttribute3());

            final AlertDialog e = dialogBuilder1.create();
            e.show();

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    e.dismiss();
                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = inputedTitle.getText().toString().trim();
                    String location = inputedLocation.getText().toString().trim();
                    String date = inputedDate.getText().toString().trim();
                    String name1 = inputedDetail1.getText().toString().trim();
                    String name2 = inputedDetail2.getText().toString().trim();
                    String name3 = inputedDetail3.getText().toString().trim();
                    String name4 = "";

                    if (validateField(title) && validateField(location) && validateField(date) && validateField(name1)
                            && validateField(name2) && validateField(name3)) {
                        String eventID = currentEvent.geteventID();
                        Event event = new Event(eventID, currentEvent.getEventType(), title, location, date, name1, name2, name3, name4, currentEvent.getAttributeName1()
                                ,currentEvent.getAttributeName2(),currentEvent.getAttributeName3(),currentEvent.getAttributeName4(), clubid);
                        databaseEventDetails.child(eventID).setValue(event);
                        Toast.makeText(EventDetails.this, currentEvent.getEventType() +" editted", Toast.LENGTH_SHORT).show();
                        e.dismiss();
                    } else {
                        Toast.makeText(EventDetails.this, "INVALID ENTRIES", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("event details").child(currentEvent.geteventID());
                    dR.removeValue();
                    Toast.makeText(EventDetails.this, "EVENT DELETED", Toast.LENGTH_SHORT).show();
                    e.dismiss();
                }
            });
        } else {
            AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(this);
            LayoutInflater inflater1 = getLayoutInflater();
            final View dialogView1 = inflater1.inflate(R.layout.event_edit_1, null);
            dialogBuilder1.setView(dialogView1);

            Button back = (Button) dialogView1.findViewById(R.id.cancelType);
            Button confirm = (Button) dialogView1.findViewById(R.id.saveType);
            Button delete = (Button) dialogView1.findViewById(R.id.deleteType);
            TextInputEditText inputedTitle = (TextInputEditText) dialogView1.findViewById(R.id.inputTitle);
            TextInputEditText inputedLocation = (TextInputEditText) dialogView1.findViewById(R.id.inputLocation);
            TextInputEditText inputedDate = (TextInputEditText) dialogView1.findViewById(R.id.inputDate);
            TextInputEditText inputedDetail1 = (TextInputEditText) dialogView1.findViewById(R.id.details1);
            TextInputEditText inputedDetail2 = (TextInputEditText) dialogView1.findViewById(R.id.details2);
            TextView displayType = (TextView) dialogView1.findViewById(R.id.type);
            TextView displayAttribute1 = (TextView) dialogView1.findViewById(R.id.display1);
            TextView displayAttribute2 = (TextView) dialogView1.findViewById(R.id.display2);


            displayType.setText(currentEvent.getEventType());
            displayAttribute1.setText(currentEvent.getAttributeName1());
            displayAttribute2.setText(currentEvent.getAttributeName2());
            inputedTitle.setText(currentEvent.getTitle());
            inputedLocation.setText(currentEvent.getLocation());
            inputedDate.setText(currentEvent.getDate());
            inputedDetail1.setText(currentEvent.getAttribute1());
            inputedDetail2.setText(currentEvent.getAttribute2());

            final AlertDialog e = dialogBuilder1.create();
            e.show();

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    e.dismiss();
                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = inputedTitle.getText().toString().trim();
                    String location = inputedLocation.getText().toString().trim();
                    String date = inputedDate.getText().toString().trim();
                    String name1 = inputedDetail1.getText().toString().trim();
                    String name2 = inputedDetail2.getText().toString().trim();
                    String name3 = "";
                    String name4 = "";

                    if (validateField(title) && validateField(location) && validateField(date) && validateField(name1)
                            && validateField(name2)) {
                        String eventID = currentEvent.geteventID();
                        Event event = new Event(eventID, currentEvent.getEventType(), title, location, date, name1, name2, name3, name4, currentEvent.getAttributeName1()
                                ,currentEvent.getAttributeName2(),currentEvent.getAttributeName3(),currentEvent.getAttributeName4(), clubid);
                        databaseEventDetails.child(eventID).setValue(event);
                        Toast.makeText(EventDetails.this, currentEvent.getEventType() +" editted", Toast.LENGTH_SHORT).show();
                        e.dismiss();
                    } else {
                        Toast.makeText(EventDetails.this, "INVALID ENTRIES", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("event details").child(currentEvent.geteventID());
                    dR.removeValue();
                    Toast.makeText(EventDetails.this, "EVENT DELETED", Toast.LENGTH_SHORT).show();
                    e.dismiss();
                }
            });
        }
    }


    public void showChoices(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_accountlist, null);
        dialogBuilder.setView(dialogView);

        Button back = (Button) dialogView.findViewById(R.id.btnBack);
        ListView listEvent = dialogView.findViewById(R.id.accountList);

        final AlertDialog d = dialogBuilder.create();
        d.show();

        List<EventType> typeList = new ArrayList<>();
        DatabaseReference typeDatabase = FirebaseDatabase.getInstance().getReference("events");

        typeDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                typeList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    EventType typeAdd = postSnapshot.getValue(EventType.class);
                    typeList.add(typeAdd);
                }
                EventTypeAdapter typeAdapter = new EventTypeAdapter(EventDetails.this, typeList);
                listEvent.setAdapter(typeAdapter);
            }

            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listEvent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                createEvent(i, typeList);
                d.dismiss();
                return true;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });



    }

    private void createEvent(int i, List<EventType> list){
        EventType selectedType = list.get(i);

        if (!selectedType.getAttributeName4().equals("")){
            AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(this);
            LayoutInflater inflater1 = getLayoutInflater();
            final View dialogView1 = inflater1.inflate(R.layout.event_creation_3, null);
            dialogBuilder1.setView(dialogView1);

            Button back = (Button) dialogView1.findViewById(R.id.cancelType);
            Button confirm = (Button) dialogView1.findViewById(R.id.saveType);
            TextInputEditText inputedTitle = (TextInputEditText) dialogView1.findViewById(R.id.inputTitle);
            TextInputEditText inputedLocation = (TextInputEditText) dialogView1.findViewById(R.id.inputLocation);
            TextInputEditText inputedDate = (TextInputEditText) dialogView1.findViewById(R.id.inputDate);
            TextInputEditText inputedDetail1 = (TextInputEditText) dialogView1.findViewById(R.id.details1);
            TextInputEditText inputedDetail2 = (TextInputEditText) dialogView1.findViewById(R.id.details2);
            TextInputEditText inputedDetail3 = (TextInputEditText) dialogView1.findViewById(R.id.details3);
            TextInputEditText inputedDetail4 = (TextInputEditText) dialogView1.findViewById(R.id.details4);
            TextView displayType = (TextView) dialogView1.findViewById(R.id.type);
            TextView displayAttribute1 = (TextView) dialogView1.findViewById(R.id.display1);
            TextView displayAttribute2 = (TextView) dialogView1.findViewById(R.id.display2);
            TextView displayAttribute3 = (TextView) dialogView1.findViewById(R.id.display3);
            TextView displayAttribute4 = (TextView) dialogView1.findViewById(R.id.display4);

            displayType.setText(selectedType.getEventType());
            displayAttribute1.setText(selectedType.getAttributeName1());
            displayAttribute2.setText(selectedType.getAttributeName2());
            displayAttribute3.setText(selectedType.getAttributeName3());
            displayAttribute4.setText(selectedType.getAttributeName4());

            final AlertDialog e = dialogBuilder1.create();
            e.show();

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    e.dismiss();
                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = inputedTitle.getText().toString().trim();
                    String location = inputedLocation.getText().toString().trim();
                    String date = inputedDate.getText().toString().trim();
                    String name1 = inputedDetail1.getText().toString().trim();
                    String name2 = inputedDetail2.getText().toString().trim();
                    String name3 = inputedDetail3.getText().toString().trim();
                    String name4 = inputedDetail4.getText().toString().trim();

                    if (validateField(title) && validateField(location) && validateField(date) && validateField(name1)
                            && validateField(name2) && validateField(name3) && validateField(name4)) {
                        String eventID = databaseEventDetails.push().getKey();
                        Event event = new Event(eventID, selectedType.getEventType(), title, location, date, name1, name2, name3, name4, selectedType.getAttributeName1()
                        ,selectedType.getAttributeName2(),selectedType.getAttributeName3(),selectedType.getAttributeName4(), clubid);
                        databaseEventDetails.child(eventID).setValue(event);
                        Toast.makeText(EventDetails.this, selectedType.getEventType() +" created", Toast.LENGTH_SHORT).show();
                        e.dismiss();
                    } else {
                        Toast.makeText(EventDetails.this, "INVALID ENTRIES", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else if(!selectedType.getAttributeName3().equals("")){
            AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(this);
            LayoutInflater inflater1 = getLayoutInflater();
            final View dialogView1 = inflater1.inflate(R.layout.event_creation_2, null);
            dialogBuilder1.setView(dialogView1);

            Button back = (Button) dialogView1.findViewById(R.id.cancelType);
            Button confirm = (Button) dialogView1.findViewById(R.id.saveType);
            TextInputEditText inputedTitle = (TextInputEditText) dialogView1.findViewById(R.id.inputTitle);
            TextInputEditText inputedLocation = (TextInputEditText) dialogView1.findViewById(R.id.inputLocation);
            TextInputEditText inputedDate = (TextInputEditText) dialogView1.findViewById(R.id.inputDate);
            TextInputEditText inputedDetail1 = (TextInputEditText) dialogView1.findViewById(R.id.details1);
            TextInputEditText inputedDetail2 = (TextInputEditText) dialogView1.findViewById(R.id.details2);
            TextInputEditText inputedDetail3 = (TextInputEditText) dialogView1.findViewById(R.id.details3);
            TextView displayType = (TextView) dialogView1.findViewById(R.id.type);
            TextView displayAttribute1 = (TextView) dialogView1.findViewById(R.id.display1);
            TextView displayAttribute2 = (TextView) dialogView1.findViewById(R.id.display2);
            TextView displayAttribute3 = (TextView) dialogView1.findViewById(R.id.display3);

            displayType.setText(selectedType.getEventType());
            displayAttribute1.setText(selectedType.getAttributeName1());
            displayAttribute2.setText(selectedType.getAttributeName2());
            displayAttribute3.setText(selectedType.getAttributeName3());

            final AlertDialog e = dialogBuilder1.create();
            e.show();

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    e.dismiss();
                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = inputedTitle.getText().toString().trim();
                    String location = inputedLocation.getText().toString().trim();
                    String date = inputedDate.getText().toString().trim();
                    String name1 = inputedDetail1.getText().toString().trim();
                    String name2 = inputedDetail2.getText().toString().trim();
                    String name3 = inputedDetail3.getText().toString().trim();
                    String name4 = "";

                    if (validateField(title) && validateField(location) && validateField(date) && validateField(name1)
                            && validateField(name2) && validateField(name3)) {
                        String eventID = databaseEventDetails.push().getKey();
                        Event event = new Event(eventID, selectedType.getEventType(), title, location, date, name1, name2, name3, name4, selectedType.getAttributeName1()
                                ,selectedType.getAttributeName2(),selectedType.getAttributeName3(),selectedType.getAttributeName4(), clubid);
                        databaseEventDetails.child(eventID).setValue(event);
                        Toast.makeText(EventDetails.this, selectedType.getEventType() +" created", Toast.LENGTH_SHORT).show();
                        e.dismiss();
                    } else {
                        Toast.makeText(EventDetails.this, "INVALID ENTRIES", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            AlertDialog.Builder dialogBuilder1 = new AlertDialog.Builder(this);
            LayoutInflater inflater1 = getLayoutInflater();
            final View dialogView1 = inflater1.inflate(R.layout.event_creation_1, null);
            dialogBuilder1.setView(dialogView1);

            Button back = (Button) dialogView1.findViewById(R.id.cancelType);
            Button confirm = (Button) dialogView1.findViewById(R.id.saveType);
            TextInputEditText inputedTitle = (TextInputEditText) dialogView1.findViewById(R.id.inputTitle);
            TextInputEditText inputedLocation = (TextInputEditText) dialogView1.findViewById(R.id.inputLocation);
            TextInputEditText inputedDate = (TextInputEditText) dialogView1.findViewById(R.id.inputDate);
            TextInputEditText inputedDetail1 = (TextInputEditText) dialogView1.findViewById(R.id.details1);
            TextInputEditText inputedDetail2 = (TextInputEditText) dialogView1.findViewById(R.id.details2);
            TextView displayType = (TextView) dialogView1.findViewById(R.id.type);
            TextView displayAttribute1 = (TextView) dialogView1.findViewById(R.id.display1);
            TextView displayAttribute2 = (TextView) dialogView1.findViewById(R.id.display2);

            displayType.setText(selectedType.getEventType());
            displayAttribute1.setText(selectedType.getAttributeName1());
            displayAttribute2.setText(selectedType.getAttributeName2());

            final AlertDialog e = dialogBuilder1.create();
            e.show();

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    e.dismiss();
                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = inputedTitle.getText().toString().trim();
                    String location = inputedLocation.getText().toString().trim();
                    String date = inputedDate.getText().toString().trim();
                    String name1 = inputedDetail1.getText().toString().trim();
                    String name2 = inputedDetail2.getText().toString().trim();
                    String name3 = "";
                    String name4 = "";

                    if (validateField(title) && validateField(location) && validateField(date) && validateField(name1)
                            && validateField(name2)) {
                        String eventID = databaseEventDetails.push().getKey();
                        Event event = new Event(eventID, selectedType.getEventType(), title, location, date, name1, name2, name3, name4, selectedType.getAttributeName1()
                                ,selectedType.getAttributeName2(),selectedType.getAttributeName3(),selectedType.getAttributeName4(), clubid);
                        databaseEventDetails.child(eventID).setValue(event);
                        Toast.makeText(EventDetails.this, selectedType.getEventType() +" created", Toast.LENGTH_SHORT).show();
                        e.dismiss();
                    } else {
                        Toast.makeText(EventDetails.this, "INVALID ENTRIES", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    public boolean validateField(String text){
        if(text.length()>0){
            return true;
        }
        else{
            return false;
        }
    }



    protected void onStart(){
        super.onStart();

        databaseEventDetails = FirebaseDatabase.getInstance().getReference("event details");
        databaseEventDetails.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Event event = postSnapshot.getValue(Event.class);
                    if (event.getClubID().equals(clubid)){
                        events.add(event);
                    }
                }

                EventList eventAdapter = new EventList(EventDetails.this, events);
                listEvents.setAdapter(eventAdapter);




            }

            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }
}
