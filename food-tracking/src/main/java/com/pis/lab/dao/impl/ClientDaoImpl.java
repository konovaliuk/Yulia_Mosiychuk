package com.pis.lab.dao.impl;

import com.pis.lab.dao.IClientDao;
import com.pis.lab.entity.Client;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl extends DaoImpl implements IClientDao {

    @Override
    public Long add(Client client) {
        try {
            String query = client.getInsertQuery();
            preparedStatement = connection.prepareStatement(query);
            client.serialize(preparedStatement);
            preparedStatement.executeUpdate();
            query = "SELECT LAST_INSERT_ID();";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            System.out.println("Element Added Successfully in Table " + client.getTableName());
            if (resultSet.next()) {
                return resultSet.getLong("LAST_INSERT_ID()");
            }
        } catch (SQLException e ) {
            System.out.println(e);
        }
        return 0L;
    }
    @Override
    public List<Client> findAll() {
        try {
            Client client = new Client();
            String query = "SELECT * FROM " + client.getTableName();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert resultSet != null;
        return fetchResults (resultSet);
    }

    private List<Client> fetchResults(ResultSet resultSet) {
        List<Client> clients = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                Integer age = resultSet.getInt("age");
                Float weight = resultSet.getFloat("weight");
                Integer height = resultSet.getInt("height");
                Boolean smoking = resultSet.getBoolean("smoking");
                Boolean drinking = resultSet.getBoolean("drinking");
                Boolean sport = resultSet.getBoolean("sport");
                Client client = new Client(id, username, age, weight, height, smoking, drinking, sport);
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public void deleteById(Long id) {
        try {
            Client client = new Client();
            String query = "DELETE FROM " + client.getTableName() + " WHERE id=?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Data deleted Successfully from table " + client.getTableName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateById(Long id, Client newClient) {
        try {
            Client client = new Client();
            String query = client.getUpdateQuery();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(8, id);
            newClient.serialize(preparedStatement);
            preparedStatement.executeUpdate();
            System.out.println("Data updated Successfully from table " + client.getTableName());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client findById(Long id) {
        try {
            Client client = new Client();
            String query = "SELECT * FROM " + client.getTableName() + " WHERE id =?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Client> clients  = fetchResults(resultSet);
            if (!clients.isEmpty()) return clients.get(0);
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client findByUsername(String username) {
        try {
            Client client = new Client();
            String query = "SELECT * FROM " + client.getTableName() + " WHERE username =?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Client> clients  = fetchResults(resultSet);
            if (!clients.isEmpty()) return clients.get(0);
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void patchById(Long id, String username, Integer age, Float weight, Integer height, Boolean smoking,
                          Boolean drinking, Boolean sport) throws IllegalArgumentException{
        try {
            Client maybeClient = findById(id);
            if (maybeClient == null) throw new RuntimeException("Client not found");
            if (username != null && !username.isBlank()) maybeClient.setUsername(username);
            if (age != null) maybeClient.setAge(age);
            if (weight != null) maybeClient.setWeight(weight);
            if (height != null) maybeClient.setHeight(height);
            if (smoking != null) maybeClient.setSmoking(smoking);
            if (drinking != null) maybeClient.setDrinking(drinking);
            if (sport != null) maybeClient.setSport(sport);

            try {
                String query = maybeClient.getUpdateQuery();
                preparedStatement = connection.prepareStatement(query);
                maybeClient.serialize(preparedStatement);
                preparedStatement.setLong(8, id);
                preparedStatement.executeUpdate();
                System.out.println("Table " + maybeClient.getTableName() + " Updated Successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (RuntimeException e) {
            System.out.println("Client not found");
        }
    }
}
