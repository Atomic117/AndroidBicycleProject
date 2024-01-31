/*
MAY NEED TO DELETE DUE TO EVENTTYPESUPER EXISTING
 */

/*
OVERHAUL NEEDED IN DELIVARABLE 4
 */

package com.example.projectcyclingmobileapp;

public class EventType {
    private String eventID;
    private String eventType;
    private String attributeName1;
    private String attributeName2;
    private String attributeName3;
    private String attributeName4;

    public EventType(){}

    public EventType(String eventID, String eventType, String attributeName1, String attributeName2, String attributeName3, String attributeName4){
        this.eventID = eventID;
        this.eventType = eventType;
        this.attributeName1 = attributeName1;
        this.attributeName2 = attributeName2;
        this.attributeName3 = attributeName3;
        this.attributeName4 = attributeName4;
    }

    public void setEventType(String input){
        eventType = input;
    }

    public void setAttributeName1(String input){
        attributeName1 = input;
    }

    public void setAttributeName2(String input){
        attributeName2 = input;
    }

    public void setAttributeName3(String input){
        attributeName3 = input;
    }

    public void setAttributeName4(String input){
        attributeName4 = input;
    }

    public String getEventType(){
        return eventType;
    }

    public String getAttributeName1(){
        return attributeName1;
    }

    public String getAttributeName2(){
        return attributeName2;
    }

    public String getAttributeName3(){
        return attributeName3;
    }

    public String getAttributeName4(){
        return attributeName4;
    }

    public String getEventID(){
        return eventID;
    }


}
