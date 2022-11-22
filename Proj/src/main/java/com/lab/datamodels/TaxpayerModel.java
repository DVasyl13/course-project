package com.lab.datamodels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TaxpayerModel {
    private SimpleIntegerProperty taxpayerID = new SimpleIntegerProperty(0);
    private SimpleStringProperty firstName = new SimpleStringProperty("");
    private SimpleStringProperty lastName = new SimpleStringProperty("");
    private SimpleStringProperty login = new SimpleStringProperty("");
    private SimpleStringProperty password = new SimpleStringProperty("");
    private SimpleStringProperty regDate = new SimpleStringProperty("");
    private SimpleStringProperty city = new SimpleStringProperty("");
    private SimpleStringProperty emailAddress = new SimpleStringProperty("");

    public TaxpayerModel(int taxpayerID, String firstName,String lastName,String login, String password,
                         String city, String date, String email) {
        setTaxpayerID(taxpayerID);
        setLogin(login);
        setFirstName(firstName);
        setLastName(lastName);
        setPassword(password);
        setRegDate(date);
        setCity(city);
        setEmailAddress(email);
    }

    public int getTaxpayerID() {
        return taxpayerID.get();
    }

    public void setTaxpayerID(int taxpayerID) {
        this.taxpayerID.set(taxpayerID);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getLogin() {
        return login.get();
    }


    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }


    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getRegDate() {
        return regDate.get();
    }

    public void setRegDate(String regDate) {
        this.regDate.set(regDate);
    }

    public String getCity() {
        return city.get();
    }


    public void setCity(String city) {
        this.city.set(city);
    }

    public String getEmailAddress() {
        return emailAddress.get();
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }
}
