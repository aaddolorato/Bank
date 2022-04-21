package com.accenture.bank;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.accenture.bank.application.model.RequestDepositor;
import com.fasterxml.jackson.databind.ObjectMapper;

class TestDepositor extends BankApplicationTests{

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Disabled("")
	void shouldGetDepositors() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/depositors")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[2].id_correntista", is(5)))
		.andExpect(jsonPath("$[2].id_istituto", is(13)))
		.andExpect(jsonPath("$[2].cod_fiscale", is("CLDN5009")))
		.andExpect(jsonPath("$[2].nome", is("Claudia")))
		.andExpect(jsonPath("$[2].cognome", is("Dante")))
		.andExpect(jsonPath("$[2].data_nascita", is("1950-09-28")))
		.andDo(print());
	}

	@Test
	@Disabled("")
	void shouldGetDepositorById() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/depositors/{id}", 5)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id_correntista", is(5)))
		.andExpect(jsonPath("$.id_istituto", is(13)))
		.andExpect(jsonPath("$.cod_fiscale", is("CLDN5009")))
		.andExpect(jsonPath("$.nome", is("Claudia")))
		.andExpect(jsonPath("$.cognome", is("Dante")))
		.andExpect(jsonPath("$.data_nascita", is("1950-09-28")))
		.andDo(print());
	}
	
	@Test
	@Disabled("")
	void shouldNotGetDepositorById() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/depositors/{id}", 80)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andDo(print());
	}
	
	@Test
	@Disabled("")
	void shouldGetDepositorByCodFisc() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/depositors/find/{codFisc}", "CLDN5009")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id_correntista", is(5)))
		.andExpect(jsonPath("$.id_istituto", is(13)))
		.andExpect(jsonPath("$.cod_fiscale", is("CLDN5009")))
		.andExpect(jsonPath("$.nome", is("Claudia")))
		.andExpect(jsonPath("$.cognome", is("Dante")))
		.andExpect(jsonPath("$.data_nascita", is("1950-09-28")))
		.andDo(print());
	}
	
	@Test
	@Disabled("")
	void shouldNotGetDepositorByCodFisc() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/depositors/find/{codFisc}", "CLDN500")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andDo(print());
	}
	
	@Test
	@Disabled("")
	void shouldDeleteDepositor() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/depositors/delete/{id}", 12)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

	}

	@Test
	@Disabled("")
	void shouldNotDeleteDepositor() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/depositors/delete/{id}", 12)
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
	@Disabled("")
	void shouldAddDepositor() throws Exception {

		String sDate="31/12/1998";
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
		
		RequestDepositor depositor = new RequestDepositor(51, "MMM123", "Mamma", "Mia", date);

		mockMvc.perform(MockMvcRequestBuilders.post("/depositors/save")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(depositor)))
		.andExpect(status().isOk());

	}
	
	@Test
	@Disabled("")
	void shouldNotAddDepositor() throws Exception {

		String sDate="31/12/1998";
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
		
		RequestDepositor depositor = new RequestDepositor(51, "MMM123", "Mamma", "Mia", date);

		mockMvc.perform(MockMvcRequestBuilders.post("/depositors/save")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(depositor)))
		.andExpect(status().isBadRequest());

	}
	
	@Test
	@Disabled("")
	void shouldUpdateDepositor() throws Exception {
		
		String sDate="31/12/1998";
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
		
		RequestDepositor depositor = new RequestDepositor(51, "MMM1234", "Mamma", "Mia", date);

		mockMvc.perform(MockMvcRequestBuilders.put("/depositors/save/{id}", 51)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(depositor)))
		.andExpect(status().isOk());

	}
	
	@Test
	@Disabled("")
	void shouldNotUpdateDepositor() throws Exception {
		
		String sDate="31/12/1998";
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
		
		RequestDepositor depositor = new RequestDepositor(51, "MMM1234", "Mamma", "Mia", date);

		mockMvc.perform(MockMvcRequestBuilders.put("/depositors/save/{id}", 52)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(depositor)))
		.andExpect(status().isNotFound());

	}
	
}
