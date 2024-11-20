package com.itp.pos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection conn;
    private DBConnection() throws ClassNotFoundException,
            SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn= DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/transaction_db",
                "root",
                "1234"
        );
    }
    public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
        return dbConnection==null?
                dbConnection=new DBConnection():dbConnection;
    }
    public Connection getConnection() {
        return conn;
    }
}
