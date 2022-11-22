package com.lab.controllers;

import com.lab.database.DBUtil;
import com.lab.usersession.Taxpayer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TaxpayerProfileController {
    @FXML
    private TextField firstNameField, lastNameField, loginField, passwordField, cityField, emailField;

    @FXML
    private Label regDateField, idField, error;

    public void setFields(Taxpayer user) {
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        loginField.setText(user.getLogin());
        passwordField.setText(user.getPassword());
        cityField.setText(user.getCity());
        emailField.setText(user.getEmailAddress());
        regDateField.setText("  " + user.getRegistrationDate().toString());
        idField.setText("" + user.getId());
    }

    @FXML
    public void saveChanges(ActionEvent event) {
        if (firstNameField.getText().isEmpty() ||
            lastNameField.getText().isEmpty() ||
            loginField.getText().isEmpty() ||
            passwordField.getText().isEmpty()) {
            error.setText("Fields Firstname/Lastname/Login/Password should be filled");
            System.out.println("Action was dropped because of empty fields.");
            return;
        }
        error.setText("");
        DBUtil jdbc = new DBUtil();
        if (jdbc.updateTaxpayerInfo(Integer.parseInt(idField.getText()), firstNameField.getText(),
                lastNameField.getText(), loginField.getText(), passwordField.getText(),
                emailField.getText(), cityField.getText())) {
            System.out.println("Changes saved.");
        }
    }
}
