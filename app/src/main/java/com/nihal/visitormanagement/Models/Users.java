package com.nihal.visitormanagement.Models;

public class Users {
    String profilePic, name , phoneNumber;

    public Users(String profilePic, String name, String phoneNumber) {
        this.profilePic = profilePic;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Users(){

    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
