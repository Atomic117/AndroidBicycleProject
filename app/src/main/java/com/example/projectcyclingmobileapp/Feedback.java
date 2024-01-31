package com.example.projectcyclingmobileapp;

public class Feedback {
    String clubID;
    String rating;
    String comment;
    String userName;
    public Feedback(){}
    public Feedback(String clubID, String rating, String comment, String userName){
        this.clubID=clubID;
        this.rating=rating;
        this.comment=comment;
        this.userName=userName;

    }
    public String getClubID(){
        return clubID;
    }
    public String getRating(){
        return rating;
    }
    public String getComment(){
        return comment;
    }
    public String getUserName(){
        return userName;
    }
}
