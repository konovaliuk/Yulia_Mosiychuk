package com.pis.lab.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private final String url = "jdbc:mysql://localhost:3306/" + System.getenv("DB_SCHEMA");
    private final String login = System.getenv("MYSQL_LOGIN");
    private final String password = System.getenv("MYSQL_PASSWORD");
    private static ConnectionFactory connectionFactory;
    private static Connection connection;

    public ConnectionFactory() throws ClassNotFoundException {
        Class.forName(DRIVER_CLASS);
    }

    public static ConnectionFactory getInstance() throws ClassNotFoundException {
        if (connectionFactory == null) {
            System.out.println("---CONNECTION FACTORY IS CREATED---");
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            System.out.println("---CONNECTION IS OPENED---");
            connection =  DriverManager.getConnection(url, login, password);
        }
        return connection;
    }

    public void closeConnection(Connection connection) throws SQLException {
            connection.close();
    }
}
