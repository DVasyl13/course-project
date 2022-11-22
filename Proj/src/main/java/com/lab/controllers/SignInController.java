package com.lab.controllers;

import com.lab.App;
import com.lab.database.DBUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    @FXML
    private TextField firstNameTextField, lastNameTextField, loginTextField;
    @FXML
    private Label error;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private ChoiceBox<String> personType;
    private final String[] people = {"Admin", "Taxpayer"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        personType.getItems().addAll(people);
    }

    @FXML
    public void register(ActionEvent event) {
        error.setText("");

        if (firstNameTextField.getText().isEmpty()) {
            error.setText("You should enter first name!");
            return;
        }
        if (lastNameTextField.getText().isEmpty()) {
            error.setText("You should enter last name!");
            return;
        }
        if (loginTextField.getText().isEmpty()) {
            error.setText("You should enter login!");
            return;
        }
        if (passwordTextField.getText().isEmpty()) {
            error.setText("You should enter password!");
            return;
        }
        if (personType.getSelectionModel().isEmpty()) {
            error.setText("You should choose type!");
            return;
        }

        String status = personType.getValue();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String login = loginTextField.getText();
        String password = passwordTextField.getText();

        DBUtil jdbc = new DBUtil();
        if (!jdbc.checkIfLoginPasswordIsValid(status, login, password)){
            error.setText("You can not use that login/password!");
            return;
        }
        if(jdbc.insertNewUser(firstName, lastName, login, password, status)) {
            try {
                Parent root = null;
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(status.toLowerCase() + "-interface.fxml"));
                root = fxmlLoader.load();
                if (status.equals("Admin")) {
                    AdminInterfaceController controller = fxmlLoader.getController();
                    controller.setUserInformation(jdbc.getUser(login, password, status));
                } else if (status.equals("Taxpayer")) {
                    TaxpayerInterfaceController controller = fxmlLoader.getController();
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
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Database error!");
            a.show();
        }
    }

    public void switchToLoginPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("log-in-page.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

}