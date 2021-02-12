package com.homework.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Country {

    @Id
    private String code;
    private String name;

    @OneToMany(mappedBy = "country")
    @JsonBackReference
    private List<Loan> loans = new ArrayList<>();

    public Country(String code) {
        this.code = code;
    }

    public Country() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

}
