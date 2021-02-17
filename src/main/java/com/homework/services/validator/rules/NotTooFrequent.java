package com.homework.services.validator.rules;

import java.util.Calendar;
import java.util.Date;

import com.homework.models.Loan;
import com.homework.repositories.CountryRepo;
import com.homework.services.validator.InvalidLoanException;

public class NotTooFrequent implements IValidationRule {

    private final static long MAX_REQUESTS = 10;
    private final static int TIMEFRAME_IN_DAYS = 2;

    private CountryRepo countryRepo;

    public NotTooFrequent(CountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }

    @Override
    public void validate(Loan loan) throws InvalidLoanException {
        String countryCode = loan.getCountry().getCode();

        long count = countryRepo.countByCodeAndLoansAppliedAtAfter(countryCode, findStartFromDate());

        if (count >= MAX_REQUESTS) {
            throw new InvalidLoanException(
                    String.format("Too many requests for the given country - %s", countryCode));
        }
    }

    private Date findStartFromDate() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -TIMEFRAME_IN_DAYS);
        return cal.getTime();
    }

}
