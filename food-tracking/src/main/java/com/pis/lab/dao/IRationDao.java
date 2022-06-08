package com.pis.lab.dao;

import com.pis.lab.entity.Ration;

import java.sql.Date;
import java.util.List;

public interface IRationDao{
    Long add(Ration ration);

    List<Ration> findAll() ;
    void deleteById(Long clientId, Long foodId, Date date);
    void updateById(Long clientId, Long foodId, Date date, Ration newRation);
    Ration findById(Long clientId, Long foodId, Date date);
    List<Ration> findAllByClientId(Long clientId);
    List<Ration> findAllByClientIdAndDate(Long clientId, Date date);

    void patchById(Long clientId, Long foodId, Date date, Long newFoodId, Float amount, Date newDate);

    void cleanUp();
}
