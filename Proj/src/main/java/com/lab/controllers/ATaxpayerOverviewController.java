package com.lab.controllers;

import com.lab.database.DBUtil;
import com.lab.datamodels.TaxpayerModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ATaxpayerOverviewController {

    ObservableList<TaxpayerModel> data;

    @FXML
    private TableView<TaxpayerModel> table;
    @FXML
    private TableColumn<TaxpayerModel, Integer> id;
    @FXML
    private TableColumn<TaxpayerModel, String > fname, lname, login, password, date, city, email;

    public void setFields() {
        DBUtil jdbc = new DBUtil();
        this.data = jdbc.getAllTaxpayers();

        id.setCellValueFactory(new PropertyValueFactory<>("taxpayerID"));
        fname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        date.setCellValueFactory(new PropertyValueFactory<>("regDate"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        email.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        table.setItems(data);
    }
}
