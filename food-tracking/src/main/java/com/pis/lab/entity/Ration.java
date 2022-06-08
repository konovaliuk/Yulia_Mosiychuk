package com.pis.lab.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@Data
@NoArgsConstructor
public class Ration{
    private Long clientId;
    private Long foodId;
    private Date date;

    private Float amount;

    public Ration(Long clientId, Long foodId, Float amount, Date date){
        this.clientId = clientId;
        this.foodId = foodId;
        this.amount = amount;
        this.date = date;
    }

    public String getTableName() {
        return "ration";
    }

    public String getInsertQuery() {
        return "INSERT INTO " + getTableName() +
                " (client_id, food_id, amount, `date`) VALUES(?,?,?,?)";
    }
    public String getDeleteQuery(){
        return "DELETE FROM " + getTableName() + " WHERE client_id=? and food_id=? and `date`=?";
    }
    public String getSelectQuery(){
        return "SELECT * FROM " + getTableName() + " WHERE client_id=? and `date`=?";
    }

    public String getUpdateQuery() {
        return "UPDATE " + getTableName() + " SET client_id=?, food_id=?, amount=?, `date`=? " +
                "WHERE client_id=? and food_id=? and `date`=?";
    }

    public void serialize(PreparedStatement statement) throws SQLException {
        statement.setLong(1, clientId);
        statement.setLong(2, foodId);
        statement.setFloat(3, amount);
        statement.setDate(4, date);
    }

    public String toString(){
        return "\nClient id: " + clientId + "  Food id: " + foodId + " Amount: " + amount + "  Date: " + date;
    }

}
