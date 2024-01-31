package com.example.projectcyclingmobileapp;

import java.util.ArrayList;
import java.util.List;
public class User {
    private String _id;
    private String _username;
    private String _type;
    private String _email;
    private String _password;
    private String socialMediaLink;
    private String phoneNumber;
    private String mainContact;
    private String location;
    public User(){}
    public User(String id, String name, String type, String password, String email) {
        _id = id;
        _username = name;
        _type = type;
        _email = email;
        _password = password;
        this.socialMediaLink = "";
        this.phoneNumber = "";
        this.mainContact = "";
        this.location = "";
    }

    public User(String id, String name, String type, String password, String email, String link, String number,String contact, String place){
        _id = id;
        _username = name;
        _type = type;
        _email = email;
        _password = password;
        this.socialMediaLink = link;
        this.phoneNumber = number;
        this.mainContact = contact;
        this.location = place;
    }

    public void setId(String id) {
        _id = id;
    }
    public String getId() {
        return _id;
    }
    public void setUsername(String name) {
        _username = name;
    }
    public String getUsername() {
        return _username;
    }
    public void setType(String type) {
        _type = type;
    }
    public String getType() {
        return _type;
    }
    public void setPassword(String password){_password = password;}
    public String getPassword(){ return _password;}
    public void setEmail(String email){_email = email;}
    public String getEmail(){ return _email;}

    public void setSocialMediaLink(String link){
        this.socialMediaLink = link;
    }

    public void setPhoneNumber(String number){
        this.phoneNumber = number;
    }

    public void setMainContact(String contact){
        this.mainContact = contact;
    }

    public String getSocialMediaLink(){
        if(this.socialMediaLink == null){
            return "";
        }
        return this.socialMediaLink;
    }

    public String getPhoneNumber(){
        if(this.phoneNumber == null){
            return "";
        }
        return this.phoneNumber;
    }

    public String getMainContact(){
        if(this.mainContact == null){
            return "";
        }
        return this.mainContact;
    }

    public void setLocation(String place){
        this.location = place;
    }

    public String getLocation(){
        if(this.location == null){
            return "";
        }
        return this.location;
    }
}