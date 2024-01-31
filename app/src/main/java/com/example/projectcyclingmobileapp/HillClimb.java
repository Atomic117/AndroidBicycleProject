package com.example.projectcyclingmobileapp;

public class HillClimb extends EventTypeSuper{

    protected String ability;

    protected String location;

    protected String strength;

    protected String pace;

    HillClimb(String eventID, String eventType, String title, String date, String time, String ability, String location, String strength, String pace, String clubID){
        super(title, date, time,eventID,eventType, clubID);
        super.seteventID(eventID);
        super.setEventType(eventType);
        super.setTitle(title);
        super.setDate(date);
        super.setTime(time);
        this.ability = ability;
        this.location = location;
        this.strength = strength;
        this.pace = pace;
    }

    public void setAbility(String ability){
        this.ability = ability;
    }

    public String getAbility(){
        return ability;
    }

    public void setEndurance(String location){
        this.location = location;
    }

    public String getEndurance(){
        return location;
    }

    public void setStrength(String strength){
        this.strength = strength;
    }

    public String getStrength(){
        return strength;
    }

    public void setPace(String pace){
        this.pace = pace;
    }

    public String getPace(){
        return pace;
    }

    @Override
    public String toString(){
        return("Title: " + title + "\n Date: " + date + "\n Time: " + time + "\n Abilities: " + ability
                + "\n Endurance: " + location + "\n Strength: " + strength + "\n Pace: " + pace);
    }

}
