package com.accenture.bank;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.accenture.bank.application.model.RequestBankAccount;
import com.fasterxml.jackson.databind.ObjectMapper;

class TestBankAccount extends BankApplicationTests{

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Disabled("")
	void shoulGetBankAccountByIdDepositor() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/depositors/{id}", 1)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id_conto_bancario", is(1)))
		.andExpect(jsonPath("$[0].id_correntista", is(1)))
		.andExpect(jsonPath("$[0].id_istituto", is(4)))
		.andExpect(jsonPath("$[0].iban", is(82361)))
		.andExpect(jsonPath("$[0].balance", is(60780)))
		.andDo(print());
	}

	@Test
	@Disabled("")
	void shoulNotGetBankAccountByIdDepositor() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/depositors/{id}", 27)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andDo(print());
	}

	static String asJsonString(final Object obj) {

		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@Disabled("")
	void shouldAddAccount() throws Exception {

		RequestBankAccount bankAccount = new RequestBankAccount(1, 51, 0, 3000);

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/new")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(bankAccount)))
		.andExpect(status().isOk());

	}

	@Test
	@Disabled("")
	void shouldNotAddAccount() throws Exception {

		RequestBankAccount bankAccount = new RequestBankAccount(27, 51, 0, 3000);

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/new")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(bankAccount)))
		.andExpect(status().isNotFound());

	}

	@Test
	@Disabled("")
	void shouldDeleteAccount() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/accounts/delete/{id}", 5)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

	}

	@Test
	@Disabled("")
	void shouldNotDeleteAccount() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/accounts/delete/{id}", 27)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());

	}

	@Test
	@Disabled("")
	void shoulGetBankAccountByIban() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/iban/{iban}", 82361)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id_conto_bancario", is(1)))
		.andExpect(jsonPath("$.id_correntista", is(1)))
		.andExpect(jsonPath("$.id_istituto", is(4)))
		.andExpect(jsonPath("$.iban", is(82361)))
		.andExpect(jsonPath("$.balance", is(60780)))
		.andDo(print());
	}

	@Test
	@Disabled("")
	void shoulNotGetBankAccountByIban() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/iban/{iban}", 12345)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}

	@Test
	@Disabled("")
	void shoulGetSaldoById() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/balance/{id}", 1)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string("60780"))
		.andDo(print());

	}

	@Test
	@Disabled("")
	void shoulNotGetSaldoById() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/balance/{id}", 27)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());

	}

	@Test
	@Disabled("")
	void shouldUpdateAccount() throws Exception {

		RequestBankAccount bankAccount = new RequestBankAccount(13, 51, 12345, 5000);

		mockMvc.perform(MockMvcRequestBuilders.put("/accounts/update/{id}", 17)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(bankAccount)))
		.andExpect(status().isOk());

	}

	@Test
	@Disabled("")
	void shouldNotUpdateAccount() throws Exception {

		RequestBankAccount bankAccount = new RequestBankAccount(13, 51, 12345, 5000);

		mockMvc.perform(MockMvcRequestBuilders.put("/accounts/update/{id}", 29)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(bankAccount)))
		.andExpect(status().isNotFound());

	}
	
	@Test
	@Disabled("")
	void shouldCalculateInterests() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/interests/{id}/{p}/{d}", 17, 5, 30)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string("7500.0"))
		.andDo(print());
	}

	@Test
	@Disabled("")
	void shouldNotCalculateInterests() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/interests/{id}/{p}/{d}", 29, 5, 30)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}

}
