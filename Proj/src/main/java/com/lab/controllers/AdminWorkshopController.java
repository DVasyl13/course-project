package com.lab.controllers;

import com.lab.database.DBUtil;
import com.lab.datamodels.Job;
import com.lab.datamodels.Transaction;
import com.lab.util.TaxCalculator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminWorkshopController implements Initializable {
    ObservableList<Transaction> data;

    @FXML
    private TableView<Transaction> table;
    @FXML
    private TableColumn<Transaction, Integer> id, amount, taxpayerID;
    @FXML
    private TableColumn<Transaction, String> type, status, date;

    @FXML
    private Label error;
    @FXML
    private TextField trID, amountTr, dateTr;
    @FXML
    private ChoiceBox<String> taxType;
    private final String [] taxes = {"income","prize","sale","transfer","perk"};

    @FXML
    public void createTransaction(ActionEvent event) {
        if (trID.getText().isEmpty()) {
            error.setText("Id field is empty!");
            return;
        }
        if (taxType.getSelectionModel().isEmpty()) {
            error.setText("You should choose tax type!");
            return;
        }
        String date = LocalDate.now().toString();
        System.out.println(LocalDate.now());
        System.out.println(date);
        if (!dateTr.getText().isEmpty()) {
            date = dateTr.getText();
        }
        DBUtil jdbc = new DBUtil();
        int id = Integer.parseInt(trID.getText());
        if (!jdbc.checkIfTaxpayerExists(id)) {
            error.setText("This taxpayer does not exist!");
            return;
        }
        if (taxType.getValue().equals("income")) {
            if (jdbc.checkIfHasJob(id)) {
                ObservableList<Job> jobs = jdbc.getJobById(id);
                for (Job j : jobs) {
                    int amount = j.getSalary();
                    jdbc.insertTransaction(id, "income",
                            TaxCalculator.calculate("income", amount), date);
                }
                this.data = jdbc.getAllTransactions();
            }
            else {
                error.setText("This person does not have any job!");
            }
        } else if (amountTr.getText().isEmpty()) {
            error.setText("Amount field is empty!");
            return;
        } else {
            int amount = Integer.parseInt(amountTr.getText());
            jdbc.insertTransaction(id, taxType.getValue(),
                    TaxCalculator.calculate(taxType.getValue(), amount), date);
            this.data = jdbc.getAllTransactions();
        }
        table.setItems(data);
    }

    public void setFields() {
        DBUtil jdbc = new DBUtil();
        this.data = jdbc.getAllTransactions();

        id.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        taxpayerID.setCellValueFactory(new PropertyValueFactory<>("taxpayerID"));
        amount.setCellValueFactory(new PropertyValueFactory<>("taxAmount"));
        type.setCellValueFactory(new PropertyValueFactory<>("taxType"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taxType.getItems().addAll(taxes);
    }
}
