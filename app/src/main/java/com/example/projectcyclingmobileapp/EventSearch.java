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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
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
    public class EventSearch extends AppCompatActivity{
        FirebaseFirestore mstore;
        Button btnReturn, btnSearch;
        TextInputEditText searchBar;
        ListView listViewEventAccounts;
        List<User> users = new ArrayList<>();
        List<String> clubNames = new ArrayList<>();

        ListView listEvents;
        List<Event> events  = new ArrayList<>();
        DatabaseReference eventsDatabase;
        DatabaseReference userDatabase;
        FirebaseAuth auth;
        String filterElement="";
        String id;
        String clubName;
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.participant_search_events);
            searchBar = findViewById(R.id.eventSearchBar);
            btnReturn = findViewById(R.id.button3);
            btnSearch = findViewById(R.id.SearchConfirm);
            listViewEventAccounts = findViewById(R.id.CyclingClubNameList);
            listViewEventAccounts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    User currentUser = users.get(i);
                    id = currentUser.getId();
                    clubName = currentUser.getUsername();
                    Intent openClubPageIntent = new Intent(getApplicationContext(), ParticipantClubPageAccess.class);
                    openClubPageIntent.putExtra("ClubIDKey",id);
                    openClubPageIntent.putExtra("ClubNameKey",clubName);
                    startActivity(openClubPageIntent);
                    finish();
                    return true;
                }
            });
            btnReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent returnIntent = new Intent(getApplicationContext(),Participant.class);
                    startActivity(returnIntent);
                    finish();
                }
            });
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final TextInputEditText searchBar = findViewById(R.id.eventSearchBar);
                    filterElement = searchBar.getText().toString().trim();
                    onStart();
                    clubNames.clear();
                }
            });
        }
        protected void onStart() {
            super.onStart();
            eventsDatabase = FirebaseDatabase.getInstance().getReference("event details");
            userDatabase = FirebaseDatabase.getInstance().getReference("users");
            eventsDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        Event eventAdd = postSnapshot.getValue(Event.class);
                        if(eventAdd.getTitle().equals(filterElement)||eventAdd.getEventType().equals(filterElement)){
                            clubNames.add(eventAdd.getClubID());
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            userDatabase.addValueEventListener(new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {
                    users.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        User userAdd = postSnapshot.getValue(User.class);
                        if (userAdd.getType().equals("Event Organizer")) {
                         if(!filterElement.equals("")) {
                             if (userAdd.getUsername().equals(filterElement)) {
                                 users.add(userAdd);
                             }
                             if (!clubNames.isEmpty()) {
                                 for (int j = 0; j < clubNames.size(); j++) {
                                     if (userAdd.getId().equals(clubNames.get(j))) {
                                         users.add(userAdd);
                                     }
                                 }
                             }
                         }else{
                             users.add(userAdd);
                         }
                        }
                        UserAdapter userAdapter = new UserAdapter(EventSearch.this, users);
                        listViewEventAccounts.setAdapter(userAdapter);
                    }
                }
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }
        private String getFilterElement(String filterElement){
            return filterElement;

        }
    }
