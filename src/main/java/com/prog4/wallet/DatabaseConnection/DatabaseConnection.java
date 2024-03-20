package com.prog4.wallet.DatabaseConnection;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    System.getenv("db_url"),
                    System.getenv("db_username"),
                    System.getenv("db_password")
            );
        } catch (SQLException e) {
            System.out.println("Error while initializing connection: " + e.getMessage());
        }
        return null;
    }

}