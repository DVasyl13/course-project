package com.lab.controllers;

import com.lab.database.DBUtil;
import com.lab.datamodels.Job;
import com.lab.datamodels.TaxpayerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ATaxpayerOverviewController {

    ObservableList<TaxpayerModel> data;
    ObservableList<TaxpayerModel> reserve = FXCollections.observableArrayList();

    ObservableList<Job> dataJ;
    ObservableList<Job> reserveJ = FXCollections.observableArrayList();

    @FXML
    private TableView<TaxpayerModel> table;
    @FXML
    private TableView<Job> tableJobs;
    @FXML
    private TableColumn<TaxpayerModel, Integer> id, idJ;
    @FXML
    private TableColumn<TaxpayerModel, String > fname, lname, login, password, date, city, email, nameJ, salaryJ;
    @FXML
    private TextField taxpayerIDField;
    @FXML
    private Label errorID;

    @FXML
    public void showByID(ActionEvent event) {
        if (taxpayerIDField.getText().isEmpty()) {
            errorID.setText("ID field is empty!");
            return;
        }
        int i = Integer.parseInt(taxpayerIDField.getText());
        if (data.stream().anyMatch(t -> t.getTaxpayerID() == i)) {
            errorID.setText("");

            TaxpayerModel temporary = data.stream().filter(t -> t.getTaxpayerID() == i).findFirst().get();
            this.reserve.removeAll(data);
            this.reserve.add(temporary);

            List<Job> jobs = dataJ.stream().filter(j -> j.getId() == i).toList();
            this.reserveJ.removeAll(dataJ);
            this.reserveJ = FXCollections.observableArrayList(jobs);

            table.setItems(reserve);
            tableJobs.setItems(reserveJ);
        }
        else {
            errorID.setText("There is no such taxpayer!");
        }

    }
    @FXML
    public void showAll(ActionEvent event) {
        table.setItems(data);
        tableJobs.setItems(dataJ);
    }

    public void setFields() {
        DBUtil jdbc = new DBUtil();
        this.data = jdbc.getAllTaxpayers();
        this.dataJ = jdbc.getAllJobs();

        id.setCellValueFactory(new PropertyValueFactory<>("taxpayerID"));
        fname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        date.setCellValueFactory(new PropertyValueFactory<>("regDate"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        email.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));

        idJ.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameJ.setCellValueFactory(new PropertyValueFactory<>("name"));
        salaryJ.setCellValueFactory(new PropertyValueFactory<>("salary"));

        table.setItems(data);
        tableJobs.setItems(dataJ);
    }
}
