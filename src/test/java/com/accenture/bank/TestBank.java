package com.accenture.bank;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.accenture.bank.application.model.RequestBank;
import com.fasterxml.jackson.databind.ObjectMapper;

class TestBank extends BankApplicationTests{

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldGetBanks() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/banks")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.*", Matchers.hasSize(46)))
		.andDo(print());
	}

	@Test
	void shouldGetByIdBank() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/banks/{id}", 1)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id_istituto_bancario", is(1)))
		.andExpect(jsonPath("$.nome_istituto", is("Pluto")))
		.andExpect(jsonPath("$.sede", is("Napoli")))
		.andDo(print());	
	}

	@Test
	void shouldNotGetByIdBank() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/banks/{id}", 1)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}

	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void shouldSaveBank() throws Exception {

		RequestBank bank = new RequestBank("nome1", "sede1");

		mockMvc.perform(MockMvcRequestBuilders.post("/banks/save")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(bank)))
		.andExpect(status().isOk());
	}

	@Test
	void shoulNotSaveBank() throws Exception {
		RequestBank bank = new RequestBank("nome", "sede");

		mockMvc.perform(MockMvcRequestBuilders.post("/banks/save")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(bank)))
		.andExpect(status().isAlreadyReported());
	}

	@Test
	void shouldUpdateBank() throws Exception {

		RequestBank bank = new RequestBank("nome2", "sede2");

		mockMvc.perform(MockMvcRequestBuilders.put("/banks/save/{id}", 51)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(bank)))
		.andExpect(status().isOk());
	}

	@Test
	void shouldNotUpdateBank() throws Exception {

		RequestBank bank = new RequestBank("nome2", "sede2");

		mockMvc.perform(MockMvcRequestBuilders.put("/banks/save/{id}", 1)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(bank)))
		.andExpect(status().isNotFound());
	}

	@Test
	void shouldDeleteBank() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/banks/delete/{id}", 1)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	void shouldNotDeleteBank() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/banks/delete/{id}", 1)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}

}
