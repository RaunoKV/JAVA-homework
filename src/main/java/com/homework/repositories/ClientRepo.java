package com.homework.repositories;

import com.homework.models.Client;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
public interface ClientRepo extends PagingAndSortingRepository<Client, UUID> {

}
