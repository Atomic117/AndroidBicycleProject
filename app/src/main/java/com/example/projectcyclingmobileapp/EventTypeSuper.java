package com.example.projectcyclingmobileapp;

public class EventTypeSuper {



    protected String title;

    protected String date;

    protected String time;
    protected String eventID;
    protected String eventType;
    protected String clubID;

    public EventTypeSuper(String title, String date, String time,String eventID, String eventType, String clubID){
        this.title = title;
        this.time = time;
        this.date = date;
        this.eventID=eventID;
        this.eventType=eventType;
        this.clubID = clubID;
    }

    public EventTypeSuper(){

    }
    public void setclubID(String clubID){ this.clubID = clubID;}
    public String getcludID(){return clubID;}
    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getTime(){
        return time;
    }
    public void seteventID(String eventID){
        this.eventID = eventID;
    }

    public String geteventID(){
        return eventID;
    }
    public void setEventType(String eventType){
        this.eventType = eventType;
    }

    public String getEventType(){
        return eventType;
    }

    @Override
    public String toString(){
        return("Title: " + title + "\n Date: " + date + "\n Time: " + time+ "\n Event ID: " + eventID+ "\n Event Type: " + eventType);
    }
}
