package com.pis.lab.runner;

import com.pis.lab.dao.DaoFactory;
import com.pis.lab.dao.IClientDao;
import com.pis.lab.dao.IFoodDao;
import com.pis.lab.dao.IRationDao;
import com.pis.lab.entity.Client;
import com.pis.lab.entity.Food;
import com.pis.lab.entity.Ration;

import java.sql.*;
import java.util.List;

public class TestConnection {
    
    public static void main (String[] args) {
        try {
            System.out.println("--------------------------Foods test--------------------------");
            DaoFactory daoFactory = DaoFactory.getInstance();
            IFoodDao foodDao = daoFactory.createFoodDao();
            /*
            Food newFood = new Food("potato", 2F, 0.1F, 19.7F);
            foodDao.add(newFood);
            foodDao.add(new Food("batata", 2F, 0.1F, 17.0F));
            foodDao.add(new Food("banana", 1.5F, 0.15F, 22.4F));*/
            List<Food> foods = foodDao.findAll();
            System.out.println(foods);
            System.out.println("--------------------------After adding food--------------------------");
            Long foodId =  foodDao.add(new Food("cheese", 26.8F, 27.3F, 0F));
            System.out.println(foodDao.findAll());
            foodDao.updateById(foodId, new Food("cheese", 38F, 31F, 0F));
            System.out.println("--------------------------After updating food--------------------------");
            System.out.println(foodDao.findById(foodId));
            foodDao.patchById(foodId, null, 26.8F, 27.3F, null);
            System.out.println("--------------------------After patching food--------------------------");
            System.out.println(foodDao.findByName("cheese"));
            System.out.println("--------------------------After deleting food--------------------------");
            foodDao.deleteById(foodId);
            System.out.println(foodDao.findAll());

            System.out.println("--------------------------Clients test--------------------------");
            IClientDao clientDao = daoFactory.createClientDao();
            List<Client> clients = clientDao.findAll();
            System.out.println(clients);
            /*clientDao.add(new Client("test3", 34, 67.5F, 168, true, false, false));
            clientDao.add(new Client("kitty1", 21, 56F, 167, false, false, false));*/
            System.out.println(clientDao.findAll());
            Long clientId = clientDao.add(new Client("test4", 34, 67.5F, 168,
                    true, false, false));
            System.out.println("--------------------------After adding client--------------------------");
            System.out.println(clientDao.findAll());
            clientDao.updateById(clientId, new Client("test4", 35, 67.0F,
                    168, true, true, false));
            System.out.println("--------------------------After updating client--------------------------");
            System.out.println(clientDao.findById(clientId));
            clientDao.patchById(clientId, null, 34, 67.5F, null, null, null, null);
            System.out.println("--------------------------After patching client--------------------------");
            System.out.println(clientDao.findByUsername("test4"));
            System.out.println("--------------------------After deleting client--------------------------");
            clientDao.deleteById(clientId);
            System.out.println(clientDao.findAll());

            System.out.println("--------------------------Rations test--------------------------");
            IRationDao rationDao = daoFactory.createRationDao();
            List<Ration> rations = rationDao.findAll();
            System.out.println(rations);
            /*
            rationDao.add(new Ration(1L, 1L, 10F, Date.valueOf("2022-05-29")));
            rationDao.add(new Ration(1L, 2L, 11F, Date.valueOf("2022-05-27")));
            rationDao.add(new Ration(2L, 3L, 11F, Date.valueOf("2022-05-28")));*/

            rations = rationDao.findAll();
            System.out.println(rations);
            Long rationClientId = rations.get(rations.size()-1).getClientId();
            Long rationFoodId = rations.get(rations.size()-1).getFoodId();
            Date rationDate = rations.get(rations.size()-1).getDate();
            rationDao.updateById(rationClientId, rationFoodId, rationDate,
                    new Ration(2L, 1L, 31F, Date.valueOf("2022-05-27")));
            System.out.println("--------------------------After updating food--------------------------");
            rationFoodId = 1L; rationDate = Date.valueOf("2022-05-27");
            System.out.println(rationDao.findById(rationClientId, rationFoodId, rationDate));
            rationDao.patchById(rationClientId, rationFoodId, rationDate, null, 26.8F,  null);
            System.out.println("--------------------------After patching food--------------------------");
            System.out.println(rationDao.findAllByClientId(2L));
            System.out.println("--------------------------After deleting food--------------------------");
            rationDao.deleteById(rationClientId, rationFoodId, rationDate);
            System.out.println(rationDao.findAllByClientId(2L));
            System.out.println(rationDao.findAllByClientIdAndDate(1L, Date.valueOf("2022-05-27")));
            rationDao.cleanUp();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
