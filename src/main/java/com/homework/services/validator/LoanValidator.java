package com.homework.services.validator;

import java.util.List;

import com.homework.models.Loan;
import com.homework.repositories.CountryRepo;
import com.homework.services.validator.rules.IValidationRule;
import com.homework.services.validator.rules.NotBlacklisted;
import com.homework.services.validator.rules.NotTooFrequent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanValidator {

    @Autowired
    private CountryRepo countryRepo;

    public void throwIfInvalid(Loan loan) throws InvalidLoanException {

        for (IValidationRule rule : getRules()) {
            rule.validate(loan);
        }
    }

    private List<IValidationRule> getRules() {
        return List.of(new NotBlacklisted(), new NotTooFrequent(countryRepo));
    }
}
