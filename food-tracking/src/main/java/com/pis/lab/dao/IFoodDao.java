package com.pis.lab.dao;

import com.pis.lab.entity.Food;

import java.util.List;

public interface IFoodDao {
    Long add(Food food);

    List<Food> findAll() ;
    void deleteById(Long id);
    void updateById(Long id, Food newFood);
    Food findById(Long id);
    Food findByName(String name);
    void patchById(Long id, String name, Float protein, Float fat, Float carbohydrate);

    void cleanUp();
}
