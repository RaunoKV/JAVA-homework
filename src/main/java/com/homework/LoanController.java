package com.homework;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.homework.exceptions.CountryResolverException;
import com.homework.models.Client;
import com.homework.models.Loan;
import com.homework.models.enums.LoanStatus;
import com.homework.repositories.LoanRepo;
import com.homework.requests.ApplyForLoan;
import com.homework.services.CountryResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("loans")
    public Iterable<Loan> getAllLoans(){
        return loanRepo.findByStatus(LoanStatus.APPROVED);
    }

    @GetMapping("loan/{clientUUID}")
    public List<Loan> getClientsLoans(@PathVariable String clientUUID){
        return loanRepo.findByClientIdAndStatusEquals(UUID.fromString(clientUUID), LoanStatus.APPROVED);
    }

    @PostMapping("/loan")
    @ResponseBody
    public Loan createProduct(@Valid @RequestBody ApplyForLoan loanApplication, HttpServletRequest request) {
        try {
            var ip = "134.201.250.155"; // request.getRemoteAddr(); // when running locally "127.0.0.1" won't be resolved
            var loan = requestToModel(loanApplication, ip);
            // validate
            // TODO:

            // save
            loanRepo.save(loan);

            return loan;
        } catch (CountryResolverException e) {
            return null; // TODO
        }catch (Exception e) {
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