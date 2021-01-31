package com.homework;

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
	public void deleteAllBeforeTests() throws Exception {
		loanRepo.deleteAll();
		clientRepo.deleteAll();
	}

	@Test
	void applyForLoan() throws Exception {
		var anyLoansExists = loanRepo.findAll().iterator().hasNext();
		assertFalse(anyLoansExists);

		mockMvc.perform(post("/loan").contentType(MediaType.APPLICATION_JSON)
				.content(getRequestJson(100, "Frodo", "Baggins", "54321", 1643659623882L))).andExpect(status().isOk())
				.andReturn();

		anyLoansExists = loanRepo.findAll().iterator().hasNext();
		assertTrue(anyLoansExists);
	}

	private String getRequestJson(long amount, String name, String surname, String personalId, long term) {
		return String.format(
				"{\"loanAmount\": %d, \"name\":\"%s\", \"surname\":\"%s\", \"personalId\": \"%s\", \"term\": %d}",
				amount, name, surname, personalId, term);
	}

}
