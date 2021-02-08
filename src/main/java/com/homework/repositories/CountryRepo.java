package com.homework.repositories;

import java.util.Date;
import java.util.Optional;

import com.homework.models.Country;

import org.springframework.data.repository.CrudRepository;

public interface CountryRepo extends CrudRepository<Country, String> {

    long countByCodeAndLoansAppliedAtAfter(String code, Date since);

    default Country findByIdOrNew(String code){
        Optional<Country> country = findById(code);
        
        return country.isPresent() ? country.get() : new Country();
    };

}
