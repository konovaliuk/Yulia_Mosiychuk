package com.pis.lab.dao.impl;

import com.pis.lab.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoImpl {
    public Connection connection = null;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;
    public DaoImpl() {
        try {
            connection = ConnectionFactory.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cleanUp() {
        try {
            System.out.println("--------------------------Cleaning up--------------------------");
            if (resultSet != null) {
                resultSet.close();
                System.out.println("---RESULT SET IS CLOSED---");
            }
            if (preparedStatement != null) {
                preparedStatement.close();
                System.out.println("---PREPARED STATEMENT IS CLOSED---");
            }
            if (connection != null) {
                connection.close();
                System.out.println("---CONNECTION IS CLOSED---");
            }
        } catch (Exception e) {
            System.out.println("Error while closing");
        }
    }
}
