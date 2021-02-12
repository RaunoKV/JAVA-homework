package com.homework.services.validator.rules;

import com.homework.models.Loan;
import com.homework.models.enums.ClientStatus;
import com.homework.services.validator.InvalidLoanException;

public class NotBlacklisted implements IValidationRule {

    @Override
    public void validate(Loan loan) throws InvalidLoanException {
        if(ClientStatus.BLACKLISTED.equals(loan.getClient().getStatus())){
            throw new InvalidLoanException("Borrower is blacklisted");
        }
    }
    
}
