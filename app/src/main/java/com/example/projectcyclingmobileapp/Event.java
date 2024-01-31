package com.example.projectcyclingmobileapp;

/*
CLASS THAT ORGANISERS WILL MAKE INSTANCES OFF

Attributes represent random requirements set by the Admin.
All events require title, date and time.
 */

public class Event {

    private String title;

    private String date;

    private String location;
    private String eventID;
    private String eventType;
    private String clubID;
    private String attribute1;
    private String attribute2;

    private String attribute3;
    private String attribute4;
    private String attributeName1;
    private String attributeName2;
    private String attributeName3;
    private String attributeName4;

    public Event(String eventID, String eventType, String title, String location, String date, String a1, String a2, String a3, String a4,
                 String b1, String b2, String b3, String b4, String clubID){
        this.title = title;
        this.location = location;
        this.date = date;
        this.eventID=eventID;
        this.eventType=eventType;
        this.clubID = clubID;
        this.attribute1 = a1;
        this.attribute2 = a2;
        this.attribute3 = a3;
        this.attribute4 = a4;
        this.attributeName1 = b1;
        this.attributeName2 = b2;
        this.attributeName3 = b3;
        this.attributeName4 = b4;
    }

    public Event(String eventID, String eventType, String title, String date, String time, String a1, String a2, String a3, String a4, String clubID){
        this.title = title;
        this.location = time;
        this.date = date;
        this.eventID=eventID;
        this.eventType=eventType;
        this.clubID = clubID;
        this.attribute1 = a1;
        this.attribute2 = a2;
        this.attribute3 = a3;
        this.attribute4 = a4;

    }
    public Event(String eventID, String eventType, String title, String date, String time, String a1, String a2, String a3, String clubID){
        this.title = title;
        this.location = time;
        this.date = date;
        this.eventID=eventID;
        this.eventType=eventType;
        this.clubID = clubID;
        this.attribute1 = a1;
        this.attribute2 = a2;
        this.attribute3 = a3;

    }

    public Event(String title, String date, String time, String eventID, String eventType, String clubID){
        this.title = title;
        this.location = time;
        this.date = date;
        this.eventID=eventID;
        this.eventType=eventType;
        this.clubID = clubID;

    }


    public Event(){
    }

    public void setclubID(String clubID){ this.clubID = clubID;}
    public String getClubID(){return clubID;}
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
        this.location = time;
    }

    public String getTime(){
        return location;
    }

    public void setlocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
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


    public void setAttribute1(String a1){
        this.attribute1 = a1;
    }

    public String getAttribute1(){
        return attribute1;
    }
    public void setAttribute2(String a2){
        this.attribute2 = a2;
    }

    public String getAttribute2(){
        return attribute2;
    }
    public void setAttribute3(String a3){
        this.attribute3 = a3;
    }

    public String getAttribute3(){
        return attribute3;
    }
    public void setAttribute4(String a4){
        this.attribute4 = a4;
    }

    public String getAttribute4(){
        return attribute4;
    }

    public void setAttributeName1(String a4){
        this.attributeName1 = a4;
    }

    public String getAttributeName1(){
        return attributeName1;
    }

    public void setAttributeName2(String a4){
        this.attributeName2 = a4;
    }

    public String getAttributeName2(){
        return attributeName2;
    }

    public void setAttributeName3(String a4){
        this.attributeName3 = a4;
    }

    public String getAttributeName3(){
        return attributeName3;
    }

    public void setAttributeName4(String a4){
        this.attributeName4 = a4;
    }

    public String getAttributeName4(){
        return attributeName4;
    }

    //public String getEventTypeID(){ return eventTypeID;}

}
