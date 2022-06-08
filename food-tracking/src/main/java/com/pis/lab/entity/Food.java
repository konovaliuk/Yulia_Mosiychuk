package com.pis.lab.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Data
@NoArgsConstructor
public class Food {
    private Long id;

    private String name;

    private Float protein;

    private Float fat;

    private Float carbohydrate;

    public Food(Long id, String name, Float protein, Float fat, Float carbohydrate) {
        this.id = id;
        this.name = name;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
    }

    public Food(String name, Float protein, Float fat, Float carbohydrate) {
        this.name = name;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
    }

    public String getTableName() {
        return "food";
    }


    public String getInsertQuery() {
        return "INSERT INTO " + getTableName() +
                " (`name`, protein, fat, carbohydrate) VALUES(?,?,?,?)";
    }

    public String getUpdateQuery() {
        return "UPDATE " + getTableName() + " SET `name`=?, protein=?, fat=?, carbohydrate=? WHERE id=?";
    }


    public void serialize(PreparedStatement statement) throws SQLException {
        statement.setString(1, name);
        statement.setFloat(2, protein);
        statement.setFloat(3, fat);
        statement.setFloat(4, carbohydrate);
    }

    public String toString(){
        return "\nId: " + id + "  Name: " + name + " Protein: " + protein + "  Fat: " + fat +
                "  Carbohydrate: " + carbohydrate;
    }
}
