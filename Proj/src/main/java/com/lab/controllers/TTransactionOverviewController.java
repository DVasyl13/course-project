package com.lab.controllers;

import com.lab.database.DBUtil;
import com.lab.datamodels.Transaction;
import com.lab.usersession.Taxpayer;
import com.lab.usersession.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TTransactionOverviewController {
    Taxpayer user;

    ObservableList<Transaction> data;
    ObservableList<Transaction> unpaid;
    @FXML
    private TextField idField,cardNumField;

    @FXML
    private TextField yearField;

    @FXML
    private Label error, errorYear;

    @FXML
    private TextArea textAreaForReport;
    @FXML
    private RadioButton rb1;
    @FXML
    private TableView<Transaction> table;
    @FXML
    private TableColumn<Transaction, Integer> id, amount;
    @FXML
    private TableColumn<Transaction, String> type, status, date;

    @FXML
    public void makePayment(ActionEvent event) {
        if (idField.getText().isEmpty() ||
            cardNumField.getText().isEmpty()) {
            error.setText("You have to fill Transaction id/card number fields!");
            return;
        }
        DBUtil jdbc = new DBUtil();
        int number = 0;
        try {
            number = Integer.parseInt(idField.getText());
            if (!jdbc.updateTransaction(user.getId(),number)) {
                error.setText("There is no such transaction.");
                return;
            }
        } catch (NumberFormatException e) {
            error.setText("Wrong input!");
            e.printStackTrace();
            return;
        }
        int finalNumber = number;
        data.stream().filter(t -> t.getTransactionID() == finalNumber).findFirst().get().setStatus("paid");
        table.setItems(data);
        parseDataList(data);
    }

    @FXML
    public void onlyUnpaid(ActionEvent event) {
        if (rb1.isSelected()) {
            table.setItems(unpaid);
        }
        else {
            table.setItems(data);
        }
    }

    @FXML
    public void showReport(ActionEvent event) {
        if (yearField.getText().isEmpty()) {
            errorYear.setText("You have to fill year field!");
            return;
        }
        errorYear.setText("");
        try {
            int year = Integer.parseInt(yearField.getText());
            List<Transaction> forSum = data.stream().filter(t -> t.getStatus().equals("paid")
                    && t.getDate().contains(""+year)).toList();
            AtomicInteger sumPaid = new AtomicInteger();
            forSum.stream().forEach(t -> sumPaid.addAndGet(t.getTaxAmount()));
            forSum = data.stream().filter(t -> t.getStatus().equals("unpaid")
                    && t.getDate().contains(""+year)).toList();
            AtomicInteger sumUnpaid = new AtomicInteger();
            forSum.stream().forEach(t -> sumUnpaid.addAndGet(t.getTaxAmount()));
            textAreaForReport.setText("Year -> " + year + "\nSum of paid transactions -> "+sumPaid+ "\nSum of unpaid transactions -> "+ sumUnpaid);
        } catch (NumberFormatException e) {
            errorYear.setText("Wrong input!");
            e.printStackTrace();
        }
    }

    public void setField(User user) {
        this.user = (Taxpayer) user;

        this.data = getTransaction();

        parseDataList(data);

        id.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        amount.setCellValueFactory(new PropertyValueFactory<>("taxAmount"));
        type.setCellValueFactory(new PropertyValueFactory<>("taxType"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.setItems(data);
    }

    public ObservableList<Transaction> getTransaction() {
        DBUtil jdbc = new DBUtil();
        return jdbc.getTransactionById(user.getId());
    }

    public void parseDataList(ObservableList<Transaction> data) {
        List<Transaction> temporary = data.stream().filter(t -> t.getStatus().equals("unpaid")).toList();
        this.unpaid = FXCollections.observableList(temporary);
    }

}
