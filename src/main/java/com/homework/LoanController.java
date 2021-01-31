package com.homework;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.homework.exceptions.CountryResolverException;
import com.homework.models.Client;
import com.homework.models.Loan;
import com.homework.repositories.LoanRepo;
import com.homework.requests.ApplyForLoan;
import com.homework.services.CountryResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@Validated
public class LoanController {

    @Autowired
    private LoanRepo loanRepo;

    @Autowired
    private CountryResolver countryResolver;

    @PostMapping("/loan")
    @ResponseBody
    public Loan createProduct(@Valid @RequestBody ApplyForLoan loanApplication, HttpServletRequest request) {
        try {
            var loan = requestToModel(loanApplication, request.getRemoteAddr());
            // validate
            // TODO:

            // save
            loanRepo.save(loan);

            return loan;
        } catch (CountryResolverException e) {
            return null; // TODO
        }
    }

    // TODO: location
    private Loan requestToModel(ApplyForLoan req, String ip) throws CountryResolverException {
        var country = countryResolver.resolveCountry(ip);

        var loan = new Loan();
        loan.setAmount(req.getLoanAmount());
        loan.setTerm(req.getTerm());
        loan.setCountry(country);

        var client = new Client();
        client.setName(req.getName());
        client.setSurname(req.getSurname());
        client.setPersonalId(req.getPersonalId());
        client.assignLoan(loan);

        return loan;
    }

}