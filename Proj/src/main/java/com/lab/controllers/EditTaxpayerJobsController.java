package com.lab.controllers;

import com.lab.database.DBUtil;
import com.lab.datamodels.Job;
import com.lab.usersession.Taxpayer;
import com.lab.usersession.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditTaxpayerJobsController {
    Taxpayer user;
    ObservableList<Job> data;
    @FXML
    private Label userId, flname, error;
    @FXML
    private TextField jobName, monthSalary;
    @FXML
    private TableView<Job> table;
    @FXML
    private TableColumn<Job, Integer> salary, id;
    @FXML
    private TableColumn<Job, String > name;

    public void setFields(User user) {
        userId.setText("" + user.getId());
        flname.setText(user.getFirstName() + " " + user.getLastName());

        this.user = (Taxpayer) user;
        data = getJobs();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        table.setItems(data);
    }

    public ObservableList<Job> getJobs() {
        DBUtil jdbc = new DBUtil();
        return jdbc.getJobById(user.getId());
    }
    @FXML
    public void addJob(ActionEvent event) {
        if (jobName.getText().isEmpty() || monthSalary.getText().isEmpty()) {
            error.setText("You have to fill job name/month salary fields!");
            return;
        }
        error.setText("");
        try {
            DBUtil jdbc = new DBUtil();
            int salary = Integer.parseInt(monthSalary.getText());
            if (jdbc.checkSameJob(user.getId(), jobName.getText(), salary)) {
                error.setText("This object already exists in list!");
                return;
            }
            data.add(new Job(user.getId(), jobName.getText(), salary));
            if (jdbc.insertJob(user.getId(), jobName.getText(), salary)) {
                table.setItems(data);
            }
        } catch (NumberFormatException e) {
            error.setText("Wrong input!");
            e.printStackTrace();
        }
    }

    @FXML
    public void removeJob(ActionEvent event) {
        if (jobName.getText().isEmpty() || monthSalary.getText().isEmpty()) {
            error.setText("You have to fill job name/month salary fields!");
            return;
        }
        error.setText("");

        DBUtil jdbc = new DBUtil();
        int salary = Integer.parseInt(monthSalary.getText());
        if (!jdbc.checkSameJob(user.getId(), jobName.getText(), salary)) {
            error.setText("There is no such object!");
            return;
        }
        else {
            Job job = data.stream().filter( x -> x.getId() == user.getId()
                            && x.getName().equals(jobName.getText()) && x.getSalary() == salary)
                            .findFirst().get();
            data.remove(job);
            jdbc.deleteJob(user.getId(), jobName.getText(), salary);
            System.out.println("Job was successfully deleted");
        }
    }
}
