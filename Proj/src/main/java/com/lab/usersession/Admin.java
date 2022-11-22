package com.lab.usersession;

import java.util.Date;

public class Admin extends User {
    public Admin(int id, String firstName, String lastName, String login, String password, Date registrationDate, String emailAddress) {
        super(id, firstName, lastName, login, password, registrationDate, emailAddress);
    }
}
