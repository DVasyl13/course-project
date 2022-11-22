package com.lab.usersession;

import java.util.Date;

public class Taxpayer extends User {
    private String city;

    public Taxpayer(int id, String firstName, String lastName, String login,
                                       String password, Date registrationDate, String city,
                                       String emailAddress) {
        super(id, firstName, lastName, login, password, registrationDate, emailAddress);
        this.city = city;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
}