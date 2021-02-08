package com.homework.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.homework.models.enums.LoanStatus;

@Entity
@Table(indexes = @Index(columnList = "appliedAt"))
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private BigDecimal amount;
    private Date term;
    private LoanStatus status = LoanStatus.APPROVED;

    private Date appliedAt = new Date();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clientId")
    @JsonManagedReference
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "countryId")
    @JsonManagedReference
    private Country country;


    public Loan(Client client) {
        client.assignLoan(this);
    }    

    public Loan() {}

    public UUID getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal bigDecimal) {
        this.amount = bigDecimal;
    }

    public Date getTerm() {
        return term;
    }

    public void setTerm(Date term) {
        this.term = term;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(Date appliedAt) {
        this.appliedAt = appliedAt;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

}
