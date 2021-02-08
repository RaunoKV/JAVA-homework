package com.homework;

import com.homework.models.Client;
import com.homework.models.Loan;
import com.homework.repositories.ClientRepo;
import com.homework.repositories.LoanRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoanApplicationTests {

	@Autowired
	private MockMvc mockMvc;

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
		var anyLoansExists = loanRepo.count() > 0;
		assertFalse(anyLoansExists);

		mockMvc.perform(post("/loan").contentType(MediaType.APPLICATION_JSON)
				.content(createRequestJson(100, "Frodo", "Baggins", "54321", "01-01-2022 00:00:00")))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.amount").value(100))
				.andExpect(jsonPath("$.client.name").value("Frodo"))
				.andReturn();

		anyLoansExists = loanRepo.count() > 0;
		assertTrue(anyLoansExists);
	}

	@Test
	void getAllApprovedLoans() {
		// TODO assert won't see loans in denied status

		// TODO assert will see approved loans
	}

	@Test
	void findLoanByClient() throws Exception {
		var client = new Client();
		var loan = new Loan(client);
		
		loanRepo.save(loan);
		assertNotNull(client.getId());

		var loans = loanRepo.findByClientId(client.getId());

		mockMvc.perform(get("/loan/" + client.getId().toString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andReturn();

		assertEquals(1, loans.size());
	}

	private String createRequestJson(long amount, String name, String surname, String personalId, String term) {
		return String.format(
				"{\"loanAmount\": %d, \"name\":\"%s\", \"surname\":\"%s\", \"personalId\": \"%s\", \"term\": \"%s\"}",
				amount, name, surname, personalId, term);
	}

}
