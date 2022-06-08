package com.pis.lab.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Data
@NoArgsConstructor
public class Client{

    private Long id;

    private String username;

    private Integer age;

    private Float weight;

    private Integer height;

    private Boolean smoking;

    private Boolean drinking;

    private Boolean sport;

    public Client(String username, Integer age, Float weight, Integer height, Boolean smoking,
           Boolean drinking, Boolean sport){
        this.username = username;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.smoking = smoking;
        this.drinking = drinking;
        this.sport = sport;

    }
    public Client(Long id, String username, Integer age, Float weight, Integer height, Boolean smoking,
           Boolean drinking, Boolean sport){
        this.id = id;
        this.username = username;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.smoking = smoking;
        this.drinking = drinking;
        this.sport = sport;

    }

    public String getTableName() {
        return "client";
    }

    public String getInsertQuery() {
        return "INSERT INTO " + getTableName() +
                " (username, age, weight, height, smoking, drinking, sport) VALUES(?,?,?,?,?,?,?)";
    }

    public String getUpdateQuery() {
        return "UPDATE " + getTableName() + " SET username=?, age=?, weight=?, height=?, smoking=?," +
                "drinking=?, sport=? WHERE id=?";
    }

    public void serialize(PreparedStatement statement) throws SQLException {
        statement.setString(1, username);
        statement.setInt(2, age);
        statement.setFloat(3, weight);
        statement.setInt(4, height);
        statement.setBoolean(5, smoking);
        statement.setBoolean(6, drinking);
        statement.setBoolean(7, sport);
    }
    public String toString(){
        return "\nId: " + id + "  Username: " + username + "  Age: " + age + "  Weight: " + weight +
                "  Height: " + height + "  Smoking: " + smoking + "  Drinking: " + drinking + "  Sport: " + sport ;
    }

}
