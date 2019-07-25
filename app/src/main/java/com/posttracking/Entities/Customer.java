package com.posttracking.Entities;

public class Customer {
    int id = 0;
    String firstName = "";
    String lastName = "";
    String emailAddress = "";
    String password = "";
    int apiID = 0;

    public int getId() {
        return id;
    }

    public void setId(int customer_id) {
        this.id = customer_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String email) {
        this.emailAddress = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setApiID(int apiID) {
        this.apiID = apiID;
    }
    public int getApiID() {
        return this.apiID;
    }
    @Override
    public String toString() {
        return "("+getEmailAddress()+")\n"+getFirstName() + " " + getLastName();
    }
}
