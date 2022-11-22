package com.lab.database;

import com.lab.datamodels.Job;
import com.lab.datamodels.TaxpayerModel;
import com.lab.datamodels.Transaction;
import com.lab.usersession.Admin;
import com.lab.usersession.Taxpayer;
import com.lab.usersession.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBUtil {
    public boolean insertNewUser(String firstName, String lastName, String login, String password, String personType) {
        String sql = "INSERT INTO [dbo].[" + personType +"] (FirstName, LastName, Login, Password) VALUES ( ?, ?, ?, ? )";

        try(Connection connection = ConnectionDB.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, login);
            preparedStatement.setString(4, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }

    public User getUser(String login, String password, String type) {
        String sql = "SELECT * FROM [dbo].[" + type + "] WHERE Login = '"+ login+"' AND Password = '"+ password+"'";
        try(Connection connection = ConnectionDB.getConnection();
            Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            if (type.equals("Admin")) {
                while (resultSet.next()) {
                    return new Admin(resultSet.getInt("AdminID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("Login"),
                            resultSet.getString("Password"),
                            resultSet.getDate("RegistrationDate"),
                            resultSet.getString("EmailAddress"));
                }
            }
            else if (type.equals("Taxpayer")) {
                while (resultSet.next()) {
                    return new Taxpayer(resultSet.getInt("TaxpayerID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("Login"),
                            resultSet.getString("Password"),
                            resultSet.getDate("RegistrationDate"),
                            resultSet.getString("City"),
                            resultSet.getString("EmailAddress"));
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    public User getUserByID(String type, int id) {
        String sql = "SELECT * FROM [dbo].[" + type + "] WHERE " + type + "ID=?";
        try(Connection connection = ConnectionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, "" + id);
            ResultSet resultSet = statement.executeQuery();
            if (type.equals("Admin")) {
                while (resultSet.next()) {
                    return new Admin(resultSet.getInt("AdminID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("Login"),
                            resultSet.getString("Password"),
                            resultSet.getDate("RegistrationDate"),
                            resultSet.getString("EmailAddress"));
                }
            }
            else if (type.equals("Taxpayer")) {
                while (resultSet.next()) {
                    return new Taxpayer(resultSet.getInt("TaxpayerID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("Login"),
                            resultSet.getString("Password"),
                            resultSet.getDate("RegistrationDate"),
                            resultSet.getString("City"),
                            resultSet.getString("EmailAddress"));
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    public boolean verifyUser(String login, String password, String type) {
        String sql = "SELECT * FROM [dbo].[" + type + "] WHERE Login = '"+ login+"' AND Password = '"+ password+"'";
        try(Connection connection = ConnectionDB.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println("Verification....");
            while (resultSet.next()) {
                return resultSet != null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList<TaxpayerModel> getAllTaxpayers() {
        String sql = "SELECT * FROM [dbo].[Taxpayer]";

        try(Connection connection = ConnectionDB.getConnection();
            Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(sql);
            ObservableList<TaxpayerModel> list = FXCollections.observableArrayList();

            while (rs.next()) {
                list.add(new TaxpayerModel(rs.getInt("TaxpayerID"),
                        rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Login"), rs.getString("Password"),
                         rs.getString("City"), rs.getDate("RegistrationDate").toString(),
                        rs.getString("EmailAddress")));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateTaxpayerInfo(int id, String fname, String lname, String login, String password, String email, String city) {
        String sql = "UPDATE [dbo].[Taxpayer] SET FirstName=? , LastName=?," +
                    " Login=? , Password=?, City=?, EmailAddress=? WHERE TaxpayerID="+id;

        try(Connection connection = ConnectionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, fname);
            statement.setString(2, lname);
            statement.setString(3, login);
            statement.setString(4, password);
            statement.setString(5, city);
            statement.setString(6, email);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateAdminInfo(int id, String fname, String lname, String login, String password, String email) {
        String sql = "UPDATE [dbo].[Admin] SET FirstName=? , LastName=?," +
                    " Login=? , Password=?, EmailAddress=? WHERE AdminID="+id;

        try(Connection connection = ConnectionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, fname);
            statement.setString(2, lname);
            statement.setString(3, login);
            statement.setString(4, password);
            statement.setString(5, email);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ObservableList<Transaction> getTransactionById(int id) {
        String sql = "SELECT * FROM [dbo].[Transactions] WHERE TaxpayerID="+id;
        try(Connection connection = ConnectionDB.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            ObservableList<Transaction> transactions = FXCollections.observableArrayList();

            while (rs.next()) {
                transactions.add( new Transaction(rs.getInt("TransactionID"), rs.getInt("TaxpayerID")
                    , rs.getString("TaxType"), rs.getInt("TaxAmount"), rs.getString("Status")
                    , rs.getDate("StartDate").toString()));
            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateTransaction(int userID, int transactionID) {
        String sql = "UPDATE [dbo].[Transactions] SET Status='paid' WHERE TaxpayerID=? AND TransactionID=?";
        try(Connection connection = ConnectionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userID);
            statement.setInt(2, transactionID);
            int i = statement.executeUpdate();
            if (i == 1) {
                System.out.println("Transaction payment had been done successfully.");
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList<Job> getJobById(int id) {
        String sql = "SELECT * FROM [dbo].[Jobs] WHERE TaxpayerID="+id;
        try(Connection connection = ConnectionDB.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            ObservableList<Job> jobs = FXCollections.observableArrayList();

            while (rs.next()) {
                jobs.add(new Job(rs.getInt("TaxpayerID"),
                        rs.getString("JobName"), rs.getInt("MonthSalary")));
            }
            return jobs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertJob(int id, String name, int salary) {
        String sql = "INSERT INTO [dbo].[Jobs] (TaxpayerID, JobName, MonthSalary) VALUES ( ?, ?, ? )";
        try(Connection connection = ConnectionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id );
            statement.setString(2, name);
            statement.setInt(3, salary);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteJob(int id, String name, int salary) {
        String sql = "DELETE FROM [dbo].[Jobs] WHERE TaxpayerID=? AND JobName=? AND MonthSalary=?";
        try(Connection connection = ConnectionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id );
            statement.setString(2, name);
            statement.setInt(3, salary);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkSameJob(int id, String name, int salary) {
        String sql = "SELECT * FROM [dbo].[Jobs] WHERE TaxpayerID=? AND JobName=? AND MonthSalary=?";
        try(Connection connection = ConnectionDB.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id );
            statement.setString(2, name);
            statement.setInt(3, salary);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (id == rs.getInt("TaxpayerID") &&
                        rs.getString("JobName").equals(name) &&
                        salary == rs.getInt("MonthSalary"))  {
                    System.out.println("This is a duplicate.");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
