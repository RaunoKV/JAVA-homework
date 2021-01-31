package com.homework.repositories;

import com.homework.models.Client;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "clients", path = "clients")
public interface ClientRepo extends PagingAndSortingRepository<Client, UUID> {

	@EntityGraph(attributePaths = { "loans" })
	List<Client> findByPersonalId(@Param("personalId") String personalId);

}
