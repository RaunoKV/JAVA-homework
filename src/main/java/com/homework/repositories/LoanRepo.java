package com.homework.repositories;

import com.homework.models.*;
import com.homework.models.enums.LoanStatus;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
public interface LoanRepo extends CrudRepository<Loan, UUID> {

	List<Loan> findByStatus(LoanStatus status);

	List<Loan> findByClientId(UUID clientId);

	List<Loan> findByClientIdAndStatusEquals(UUID clientId, LoanStatus status);

}
