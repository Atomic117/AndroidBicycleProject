package com.example.projectcyclingmobileapp;

public class TimeTrial extends EventTypeSuper{

    protected String course;

    protected String interval;

    protected String pace;

    TimeTrial(String eventID, String eventType, String title, String date, String time, String course, String interval, String pace, String clubID){
        super(title, date, time,eventID,eventType, clubID);
        super.seteventID(eventID);
        super.setEventType(eventType);
        super.setTitle(title);
        super.setDate(date);
        super.setTime(time);
        this.course = course;
        this.interval = interval;
        this.pace = pace;
    }

    public void setCourse(String course){
        this.course = course;
    }

    public String getCourse(){
        return course;
    }

    public void setInterval(String interval){
        this.interval = interval;
    }

    public String getInterval(){
        return interval;
    }

    public void setPace(String pace){
        this.pace = pace;
    }

    public String getPace(){
        return pace;
    }

    @Override
    public String toString(){
        return("Title: " + title + "\n Date: " + date + "\n Time: " + time + "\n Course: " + course
                + "\n Interval: " + interval + "\n Pace: " + pace);
    }

}
