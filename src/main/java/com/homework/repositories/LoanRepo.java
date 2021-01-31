package com.homework.repositories;

import com.homework.models.*;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "loans", path = "loans")
public interface LoanRepo extends PagingAndSortingRepository<Loan, UUID> {

	Optional<Loan> findById(@Param("id") String id);

}
