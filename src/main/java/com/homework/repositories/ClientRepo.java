package com.homework.repositories;

import com.homework.models.Client;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
public interface ClientRepo extends CrudRepository<Client, UUID> {

    List<Client> findByPersonalId(String personalId);

    default Client findByPersonalIdOrNew(String personalId){
        List<Client> client = findByPersonalId(personalId);
        
        return client.isEmpty() ? new Client() : client.get(0);
    };
}
