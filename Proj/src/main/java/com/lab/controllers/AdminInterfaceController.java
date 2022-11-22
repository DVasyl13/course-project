package com.lab.controllers;

import com.lab.App;
import com.lab.database.DBUtil;
import com.lab.usersession.Admin;
import com.lab.usersession.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class AdminInterfaceController {
    Admin user;
    @FXML
    private Label name;
    @FXML
    private BorderPane borderPane;

    @FXML
    private void showProfile(ActionEvent event) {
        refreshInfo(event);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("admin-profile-page.fxml"));
        borderPane.getChildren().remove(borderPane.getCenter());
        try {
            borderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdminProfileController controller = fxmlLoader.getController();
        controller.setFields(user);
    }

    @FXML
    public void taxpayersOverview(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("admin-taxpayers-overview.fxml"));
        borderPane.getChildren().remove(borderPane.getCenter());
        try {
            borderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ATaxpayerOverviewController controller = fxmlLoader.getController();
        controller.setFields();
    }

    @FXML
    public void transactionWorkshop(ActionEvent event) {

    }

    @FXML
    public void refreshInfo(ActionEvent event) {
        DBUtil jdbc = new DBUtil();
        this.user = (Admin) jdbc.getUserByID("Admin", user.getId());
        name.setText(user.getFirstName() + " " + user.getLastName());
    }

    public void setUserInformation (User user){
        this.user = (Admin) user;
        name.setText(user.getFirstName() + " " + user.getLastName());
    }
}
