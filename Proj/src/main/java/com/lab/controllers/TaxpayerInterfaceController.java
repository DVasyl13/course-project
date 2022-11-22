package com.lab.controllers;

import com.lab.App;
import com.lab.database.DBUtil;
import com.lab.usersession.Taxpayer;
import com.lab.usersession.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TaxpayerInterfaceController {
    Taxpayer user;
    @FXML
    private Label name;

    @FXML
    private BorderPane borderPane;

    @FXML
    private void showProfile(ActionEvent event) {
        refreshInfo(event);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("profile-page.fxml"));
        borderPane.getChildren().remove(borderPane.getCenter());
        try {
            borderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        TaxpayerProfileController controller = fxmlLoader.getController();
        controller.setFields(user);
    }

    @FXML
    public void refreshInfo(ActionEvent event) {
        DBUtil jdbc = new DBUtil();
        this.user = (Taxpayer) jdbc.getUserByID("Taxpayer", user.getId());
        name.setText(user.getFirstName() + " " + user.getLastName());
    }

    @FXML
    public void editJobs(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("edit-taxpayer-jobs.fxml"));
        borderPane.getChildren().remove(borderPane.getCenter());
        try {
            borderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        EditTaxpayerJobsController controller = fxmlLoader.getController();
        controller.setFields(user);
    }

    @FXML
    public void transactionOverview(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("taxpayer-overview-transaction.fxml"));
        borderPane.getChildren().remove(borderPane.getCenter());
        try {
            borderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        TTransactionOverviewController controller = fxmlLoader.getController();
        controller.setField(user);
    }

    public void setUserInformation (User user){
        this.user = (Taxpayer) user;
        name.setText(user.getFirstName() + " " + user.getLastName());
    }
}