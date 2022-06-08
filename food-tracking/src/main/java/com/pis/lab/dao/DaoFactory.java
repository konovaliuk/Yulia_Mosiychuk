package com.pis.lab.dao;

import com.pis.lab.dao.impl.ClientDaoImpl;
import com.pis.lab.dao.impl.FoodDaoImpl;
import com.pis.lab.dao.impl.RationDaoImpl;

public class DaoFactory {
    public static DaoFactory daoFactory;

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }
    public IRationDao createRationDao(){
        return new RationDaoImpl();
    }
    public IClientDao createClientDao(){
        return new ClientDaoImpl();
    }
    public IFoodDao createFoodDao(){
        return new FoodDaoImpl();
    }
}
