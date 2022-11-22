package com.lab.controllers;

import com.lab.App;
import com.lab.database.DBUtil;
import com.lab.usersession.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    @FXML
    private TextField loginField, passwordField;

    @FXML
    private Label error;
    @FXML
    private ChoiceBox<String> personType;
    private final String[] people = {"Admin", "Taxpayer"};
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        personType.getItems().addAll(people);
    }
    @FXML
    public void login(ActionEvent event) {
        error.setText("");

        if (loginField.getText().isEmpty()) {
            error.setText("You should enter login!");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            error.setText("You should enter login!");
            return;
        }
        if (personType.getSelectionModel().isEmpty()) {
            error.setText("You should choose type!");
            return;
        }
        DBUtil jdbc = new DBUtil();
        String login = loginField.getText();
        String password = passwordField.getText();
        String status = personType.getValue();

        if (jdbc.verifyUser(login, password, status)) {
            Parent root = null;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(status.toLowerCase() + "-interface.fxml"));
                root = fxmlLoader.load();
                if (status.equals("Admin")) {
                    AdminInterfaceController controller = fxmlLoader.getController();
                    System.out.println(controller);
                    controller.setUserInformation(jdbc.getUser(login, password, status));
                } else if (status.equals("Taxpayer")) {
                    TaxpayerInterfaceController controller = fxmlLoader.getController();
                    System.out.println(controller);
                    controller.setUserInformation(jdbc.getUser(login, password, status));
                }
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            error.setText("Whoops! It seems you are not registered.");
        }
    }

    public void switchToRegistrationPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("sign-in-page.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

}
