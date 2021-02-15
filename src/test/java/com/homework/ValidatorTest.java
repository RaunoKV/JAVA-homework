package com.homework;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.homework.models.Client;
import com.homework.models.Country;
import com.homework.models.Loan;
import com.homework.models.enums.ClientStatus;
import com.homework.repositories.ClientRepo;
import com.homework.repositories.LoanRepo;
import com.homework.services.validator.InvalidLoanException;
import com.homework.services.validator.LoanValidator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidatorTest {

    private static final int LOANS_LIMIT = 10;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private LoanRepo loanRepo;

    @Autowired
    private LoanValidator validator;

    @Test
    void blockedUserLoanShouldBeRejected() {
        var client = new Client();
        client.setStatus(ClientStatus.BLACKLISTED);

        var loan = new Loan(client);

        assertThrows(InvalidLoanException.class, () -> {
            validator.throwIfInvalid(loan);
        });
    }

    @Test
    void tooManyLoansFromCountryShouldBeRejected() throws InvalidLoanException {
        var country = new Country("EE");
        var client = new Client();

        // Fill loans up to limit, validator gives no exception
        for (int i = 0; i < LOANS_LIMIT; i++) {
            var loan = new Loan(client);
            loan.setCountry(country);

            validator.throwIfInvalid(loan);
        }
        clientRepo.save(client);

        assertEquals(LOANS_LIMIT, loanRepo.count());

        // Exceed limit, expect exception
        var loan = new Loan(new Client());
        loan.setCountry(country);
        assertThrows(InvalidLoanException.class, () -> {
            validator.throwIfInvalid(loan);
        });
    }
}
