package com.pis.lab.dao.impl;

import com.pis.lab.dao.IFoodDao;
import com.pis.lab.entity.Food;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDaoImpl extends DaoImpl implements IFoodDao{

    @Override
    public Long add(Food food) {
        try {
            String query = food.getInsertQuery();
            preparedStatement = connection.prepareStatement(query);
            food.serialize(preparedStatement);
            preparedStatement.executeUpdate();
            query = "SELECT LAST_INSERT_ID();";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            System.out.println("Element Added Successfully in Table " + food.getTableName());
            if (resultSet.next()) {
                return resultSet.getLong("LAST_INSERT_ID()");
            }
        } catch (SQLException e ) {
            System.out.println(e);
        }
        return 0L;
    }
    @Override
    public List<Food> findAll() {
        try {
            Food food = new Food();
            String query = "SELECT * FROM " + food.getTableName();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert resultSet != null;
        return fetchResults (resultSet);
    }

    private List<Food> fetchResults(ResultSet resultSet) {
        List<Food> foods = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Float protein = resultSet.getFloat("protein");
                Float fat = resultSet.getFloat("fat");
                Float carbohydrate = resultSet.getFloat("carbohydrate");
                Food food = new Food(id, name, protein, fat, carbohydrate);
                foods.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foods;
    }
    public Food findByName(String name){
        try {
            Food food = new Food();
            String query = "SELECT * FROM " + food.getTableName() + " WHERE name =?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Food> foods  = fetchResults(resultSet);
            if (!foods.isEmpty()) return foods.get(0);
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Food findById(Long id){
        try {
            Food food = new Food();
            String query = "SELECT * FROM " + food.getTableName() + " WHERE id=?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Food> foods  = fetchResults(resultSet);
            if (!foods.isEmpty()) return foods.get(0);
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(Long id){
        try {
            Food food = new Food();
            String query = "DELETE FROM " + food.getTableName() + " WHERE id=?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Data deleted Successfully from table " + food.getTableName());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateById(Long id, Food newFood){
        try {
            Food food = new Food();
            String query = food.getUpdateQuery();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(5, id);
            newFood.serialize(preparedStatement);
            preparedStatement.executeUpdate();
            System.out.println("Data updated Successfully from table " + food.getTableName());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void patchById(Long id, String name, Float protein, Float fat, Float carbohydrate) throws IllegalArgumentException{
        try {
            Food maybeFood = findById(id);
            if (maybeFood == null) throw new RuntimeException("Food not found");
            if (name != null && !name.isBlank()) maybeFood.setName(name);
            if (protein != null) maybeFood.setProtein(protein);
            if (fat != null) maybeFood.setFat(fat);
            if (carbohydrate != null) maybeFood.setCarbohydrate(carbohydrate);

            try {
                String query = maybeFood.getUpdateQuery();
                preparedStatement = connection.prepareStatement(query);
                maybeFood.serialize(preparedStatement);
                preparedStatement.setLong(5, id);
                preparedStatement.executeUpdate();
                System.out.println("Table " + maybeFood.getTableName() + " Updated Successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (RuntimeException e) {
            System.out.println("Food not found");
        }
    }
}
