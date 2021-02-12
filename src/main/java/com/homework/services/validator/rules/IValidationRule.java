package com.homework.services.validator.rules;

import com.homework.models.Loan;
import com.homework.services.validator.InvalidLoanException;

public interface IValidationRule {
    
    void validate(Loan loan) throws InvalidLoanException;
}
