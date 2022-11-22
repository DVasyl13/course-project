package com.lab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

final class ConnectionDB {
    private static final String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=ForCourseProject;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "myuser";
    private static final String PASSWORD = "myuser";


    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
