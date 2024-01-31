package com.example.projectcyclingmobileapp;

public class EventsJoined extends Event{
    String userID;
    String joinedID;

    public EventsJoined(){}
    public EventsJoined(String eventID, String eventType, String title, String location, String date, String a1, String a2, String a3, String a4,
                 String b1, String b2, String b3, String b4, String clubID, String userID,String joinedID){
        super(eventID, eventType, title, location, date, a1, a2, a3, a4, b1, b2, b3, b4, clubID);
        this.userID = userID;
        this.joinedID = joinedID;
    }

    public String getUserID(){
        return userID;
    }

    public String getJoinedID(){
        return joinedID;
    }
}



