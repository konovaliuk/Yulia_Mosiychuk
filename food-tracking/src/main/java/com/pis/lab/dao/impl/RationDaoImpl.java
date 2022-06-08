package com.pis.lab.dao.impl;

import com.pis.lab.dao.IRationDao;
import com.pis.lab.entity.Ration;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RationDaoImpl extends DaoImpl implements IRationDao {

    @Override
    public Long add(Ration ration) {
        try {
            String query = ration.getInsertQuery();
            preparedStatement = connection.prepareStatement(query);
            ration.serialize(preparedStatement);
            preparedStatement.executeUpdate();
            query = "SELECT LAST_INSERT_ID();";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            System.out.println("Element Added Successfully in Table " + ration.getTableName());
            if (resultSet.next()) {
                return resultSet.getLong("LAST_INSERT_ID()");
            }
        } catch (SQLException e ) {
            System.out.println(e);
        }
        return 0L;
    }
    @Override
    public List<Ration> findAll() {
        try {
            Ration ration = new Ration();
            String query = "SELECT * FROM " + ration.getTableName();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert resultSet != null;
        return fetchResults (resultSet);
    }

    private List<Ration> fetchResults(ResultSet resultSet) {
        List<Ration> rations = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long client_id = resultSet.getLong("client_id");
                Long food_id = resultSet.getLong("food_id");
                Float amount = resultSet.getFloat("amount");
                Date date = resultSet.getDate("date");
                Ration ration = new Ration(client_id, food_id, amount, date);
                rations.add(ration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rations;
    }

    @Override
    public void deleteById(Long clientId, Long foodId, Date date) {
        try {
            Ration ration = new Ration();
            String query = ration.getDeleteQuery();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, clientId);
            preparedStatement.setLong(2, foodId);
            preparedStatement.setDate(3, date);
            preparedStatement.executeUpdate();
            System.out.println("Data deleted Successfully from table " + ration.getTableName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateById(Long clientId, Long foodId, Date date, Ration newRation) {
        try {
            Ration ration = new Ration();
            String query = ration.getUpdateQuery();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(5, clientId);
            preparedStatement.setLong(6, foodId);
            preparedStatement.setDate(7, date);
            newRation.serialize(preparedStatement);
            preparedStatement.executeUpdate();
            System.out.println("Data updated Successfully from table " + ration.getTableName());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ration findById(Long clientId, Long foodId, Date date) {
        try {
            Ration ration = new Ration();
            String query = ration.getSelectQuery() + " and food_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, clientId);
            preparedStatement.setLong(3, foodId);
            preparedStatement.setDate(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ration> clients  = fetchResults(resultSet);
            if (!clients.isEmpty()) return clients.get(0);
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ration> findAllByClientId(Long clientId) {
        try {
            Ration ration = new Ration();
            String query = "SELECT * FROM " + ration.getTableName() + " WHERE client_id =?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) return fetchResults (resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ration> findAllByClientIdAndDate(Long clientId, Date date) {
        try {
            Ration ration = new Ration();
            String query = ration.getSelectQuery();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, clientId);
            preparedStatement.setDate(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) return fetchResults (resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void patchById(Long clientId, Long foodId, Date date, Long newFoodId, Float amount, Date newDate){
        try {
            Ration maybeRation = findById(clientId, foodId, date);
            if (maybeRation == null) throw new RuntimeException("Ration not found");
            if (newFoodId != null) maybeRation.setFoodId(newFoodId);
            if (newDate != null) maybeRation.setDate(newDate);
            if (amount != null) maybeRation.setAmount(amount);

            try {
                String query = maybeRation.getUpdateQuery();
                preparedStatement = connection.prepareStatement(query);
                maybeRation.serialize(preparedStatement);
                preparedStatement.setLong(5, clientId);
                preparedStatement.setLong(6, foodId);
                preparedStatement.setDate(7, date);
                preparedStatement.executeUpdate();
                System.out.println("Table " + maybeRation.getTableName() + " Updated Successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (RuntimeException e) {
            System.out.println("Ration not found");
        }

    }
}
