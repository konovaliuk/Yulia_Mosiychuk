package com.pis.lab.dao;

import com.pis.lab.entity.Client;

import java.util.List;

public interface IClientDao {
    Long add(Client client);

    List<Client> findAll() ;
    void deleteById(Long id);
    void updateById(Long id, Client newClient);
    Client findById(Long id);
    Client findByUsername(String username);

    void patchById(Long id, String username, Integer age, Float weight, Integer height, Boolean smoking,
                   Boolean drinking, Boolean sport);

    void cleanUp();
}
