package com.lab.datamodels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transaction {
    private SimpleIntegerProperty transactionID = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty taxpayerID = new SimpleIntegerProperty(0);
    private SimpleStringProperty taxType = new SimpleStringProperty("");
    private SimpleIntegerProperty taxAmount = new SimpleIntegerProperty(0);
    private SimpleStringProperty status = new SimpleStringProperty("");
    private SimpleStringProperty date = new SimpleStringProperty("");


    public Transaction(int transactionID, int taxpayerID, String taxType, int taxAmount, String status, String date) {
        setTransactionID(transactionID);
        setTaxpayerID(taxpayerID);
        setTaxType(taxType);
        setTaxAmount(taxAmount);
        setStatus(status);
        setDate(date);
    }

    public int getTransactionID() {
        return transactionID.get();
    }

    public void setTransactionID(int transactionID) {
        this.transactionID.set(transactionID);
    }

    public int getTaxpayerID() {
        return taxpayerID.get();
    }

    public void setTaxpayerID(int taxpayerID) {
        this.taxpayerID.set(taxpayerID);
    }

    public String getTaxType() {
        return taxType.get();
    }

    public void setTaxType(String taxType) {
        this.taxType.set(taxType);
    }

    public int getTaxAmount() {
        return taxAmount.get();
    }

    public void setTaxAmount(int taxAmount) {
        this.taxAmount.set(taxAmount);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
