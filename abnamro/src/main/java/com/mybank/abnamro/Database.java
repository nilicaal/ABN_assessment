/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mybank.abnamro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author niels
 */
public class Database {
    private final String url = "jdbc:postgresql://localhost/mybank";
    private final String user = "niels";
    private final String password = System.getenv("ABN_DB_PASS");

    /**
     * Connect to the PostgreSQL database
     * Code originally from: https://www.postgresqltutorial.com/postgresql-jdbc/connecting-to-postgresql-database/
     * @return a Connection object
     */
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println("Connection to the PostgreSQL server unsuccessful.");
        }
        return conn;
    }
}
