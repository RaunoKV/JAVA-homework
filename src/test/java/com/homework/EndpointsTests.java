package com.homework;

import com.homework.models.Client;
import com.homework.models.Loan;
import com.homework.models.enums.LoanStatus;
import com.homework.repositories.ClientRepo;
import com.homework.repositories.LoanRepo;
import com.homework.requests.ApplyForLoan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.Date;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EndpointsTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private LoanController loanController;

	@Autowired
	private LoanRepo loanRepo;

	@Autowired
	private ClientRepo clientRepo;

	@BeforeEach
	public void deleteAllBeforeTests() {
		loanRepo.deleteAll();
		clientRepo.deleteAll();
	}

	@Test
	void applyForLoan() throws Exception {

		var loanApplication = new ApplyForLoan();
		loanApplication.setLoanAmount(new BigDecimal(100));
		loanApplication.setName("firstname");
		loanApplication.setSurname("surname");
		loanApplication.setPersonalId("personalId");
		loanApplication.setTerm(new Date());

		ReflectionTestUtils.setField(loanController, "ipOverwrite", "87.119.186.141");

		loanController.applyForLoan(loanApplication, null);

		assertEquals("EE", loanRepo.findAll().iterator().next().getCountry().getCode());

		assertTrue(loanRepo.count() == 1);
	}

	@Test
	void getAllApprovedLoans() throws Exception {
		// add 2 loans
		var client = new Client();
		new Loan(client);

		var deniedLoan = new Loan(client);
		deniedLoan.setStatus(LoanStatus.DENIED);
		clientRepo.save(client);

		assertEquals(2, loanRepo.count());

		// only see one approved loan
		mockMvc.perform(get("/loans").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))).andReturn();
	}

	@Test
	void findLoanByClient() throws Exception {
		var client = new Client();
		var loan = new Loan(client);

		loanRepo.save(loan);
		assertNotNull(client.getId());

		var loans = loanRepo.findByClientId(client.getId());

		mockMvc.perform(get("/loan/" + client.getId().toString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1))).andReturn();

		assertEquals(1, loans.size());
	}

}
