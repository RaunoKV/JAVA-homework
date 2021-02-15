package com.homework;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.homework.exceptions.CountryResolverException;
import com.homework.models.Loan;
import com.homework.models.enums.LoanStatus;
import com.homework.repositories.ClientRepo;
import com.homework.repositories.LoanRepo;
import com.homework.requests.ApplyForLoan;
import com.homework.services.CountryResolver;
import com.homework.services.validator.LoanValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    private ClientRepo clientRepo;

    @Autowired
    private LoanRepo loanRepo;

    @Autowired
    private LoanValidator validator;

    @Autowired
    private CountryResolver countryResolver;

    @Value("${localDev.mockIp:}")
    private String ipOverwrite;

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
    public ResponseEntity applyForLoan(@RequestBody ApplyForLoan loanApplication, HttpServletRequest request) {        
        try {
            var loan = requestToModel(loanApplication, getIpOrOverwritten(request));

            // validate
            validator.throwIfInvalid(loan);

            // save
            clientRepo.save(loan.getClient());

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loan);
        } catch (CountryResolverException e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Couldn't resolve country of origin");
        }catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
        }
    }

    private Loan requestToModel(ApplyForLoan req, String ip) throws CountryResolverException {
        var country = countryResolver.resolveCountry(ip);

        var loan = new Loan();
        loan.setAmount(req.getLoanAmount());
        loan.setTerm(req.getTerm());
        loan.setCountry(country);
        
        var client = clientRepo.findByPersonalIdOrNew(req.getPersonalId());
        client.setName(req.getName());
        client.setSurname(req.getSurname());
        client.setPersonalId(req.getPersonalId());
        client.assignLoan(loan);

        return loan;
    }

    private String getIpOrOverwritten(HttpServletRequest request) {
        return StringUtils.hasText(ipOverwrite) ? ipOverwrite : request.getRemoteAddr();
    }

}