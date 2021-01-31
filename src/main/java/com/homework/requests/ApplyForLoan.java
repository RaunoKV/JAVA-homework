package com.homework.requests;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ApplyForLoan {

    // @NotNull(message = "Please provide loanAmount")
    private BigDecimal loanAmount;

    // @NotNull(message = "Please provide term")
    private Date term;

    // @NotEmpty(message = "Please provide name")
    private String name;

    // @NotNull
    // @NotEmpty(message = "Please provide surname")
    private String surname;

    // @NotEmpty(message = "Please provide personalId")
    private String personalId;

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Date getTerm() {
        return term;
    }

    public void setTerm(Date term) {
        this.term = term;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

}
