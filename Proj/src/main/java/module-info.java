module com.example.proj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.lab to javafx.fxml;
    exports com.lab;
    exports com.lab.controllers;
    opens com.lab.controllers to javafx.fxml;
    opens com.lab.datamodels to  javafx.base;
}