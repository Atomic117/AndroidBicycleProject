package com.example.projectcyclingmobileapp;

public class RoadStageRace extends EventTypeSuper{

    protected String endDate;

    protected String endTime;

    protected String route;

    protected String experienceLevel;

    public RoadStageRace(String eventID, String eventType, String title, String date, String endDate, String time,  String endTime,  String route, String experienceLevel, String clubID){
        super(title, date, time,eventID,eventType, clubID);
        super.seteventID(eventID);
        super.setEventType(eventType);
        super.setTitle(title);
        super.setDate(date);
        super.setTime(time);
        this.endDate = endDate;
        this.endTime = endTime;
        this.route = route;
        this.experienceLevel = experienceLevel;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    public String getEndDate(){
        return endDate;
    }

    public void setRoute(String route){
        this.route = route;
    }

    public String getRoute(){
        return route;
    }

    public void setExperienceLevel(String experienceLevel){
        this.experienceLevel = experienceLevel;
    }

    public String getExperienceLevel(){
        return experienceLevel;
    }

    @Override
    public String toString(){
        return("Title: " + title + "\n Start Date: " + date + "\n End Date: " + endDate + "\n Start Time: " + time + "\n End Time: " + endTime
                + "\n Route: " + route + "\n Experience Level: " + experienceLevel);
    }

}
