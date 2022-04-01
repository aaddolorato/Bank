package com.accenture.bank;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.accenture.bank.application.model.RequestTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;

class TestTransaction extends BankApplicationTests{

	@Autowired
	private MockMvc mockMvc;
	
	static String asJsonString(final Object obj) {

		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	void shouldDeposit() throws Exception {
		
		String sDate="01/04/2022";
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
		
		RequestTransaction transaction = new RequestTransaction(4, 200, "deposit", date);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/transaction/deposit/{id}", 4)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(transaction)))
		.andExpect(status().isOk());
	}
	
	@Test
	void shouldNotDepositDueToIdNotFound() throws Exception {
		
		String sDate="01/04/2022";
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
		
		RequestTransaction transaction = new RequestTransaction(21, 200, "deposit", date);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/transaction/deposit/{id}", 21)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(transaction)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void shouldNotDepositDueToInsufficientAmount() throws Exception {
		
		String sDate="01/04/2022";
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
		
		RequestTransaction transaction = new RequestTransaction(4, 0, "deposit", date);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/transaction/deposit/{id}", 4)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(transaction)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void shouldGetByIdBankAccount() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get("/transaction/bankaccount/{id}", 1)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id_transazione", is(4)))
		.andExpect(jsonPath("$[0].id_conto_bancario", is(1)))
		.andExpect(jsonPath("$[0].importo", is(300)))
		.andExpect(jsonPath("$[0].tipo", is("deposit")))
		.andExpect(jsonPath("$[0].data_transazione", is("2022-03-15")))
		.andDo(print());
	}
	
	@Test
	void shouldNotGetByIdBankAccount() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get("/transaction/bankaccount/{id}", 21)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void shouldGetLastByIdBankAccount() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get("/transaction/bankaccount/last/{id}", 4)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id_transazione", is(38)))
		.andExpect(jsonPath("$.id_conto_bancario", is(1)))
		.andExpect(jsonPath("$.importo", is(200)))
		.andExpect(jsonPath("$.tipo", is("withdraw")))
		.andExpect(jsonPath("$.data_transazione", is("2022-04-01")))
		.andDo(print());
	}
	
	@Test
	void shouldNotGetLastByIdBankAccount() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get("/transaction/bankaccount/last/{id}", 21)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void shouldUpdate() throws Exception {
		
		String sDate="01/04/2022";
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
		
		RequestTransaction transaction = new RequestTransaction(4, 300, "withdraw", date);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/transaction/update/{id}", 38)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(transaction)))
		.andExpect(status().isOk());
	}
	
	@Test
	void shouldNotUpdateDueToIdNotFount() throws Exception {
		
		String sDate="01/04/2022";
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
		
		RequestTransaction transaction = new RequestTransaction(4, 300, "withdraw", date);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/transaction/update/{id}", 51)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(transaction)))
		.andExpect(status().isBadRequest());
		
	}
	
	@Test
	void shouldNotUpdateDueToInvalidAmount() throws Exception {
		
		String sDate="01/04/2022";
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
		
		RequestTransaction transaction = new RequestTransaction(35, 0, "deposit", date);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/transaction/update/{id}", 51)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(transaction)))
		.andExpect(status().isBadRequest());
		
	}
	
	@Test
	void shouldWithdraw() throws Exception {
		
		String sDate="01/04/2022";
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
		
		RequestTransaction transaction = new RequestTransaction(4, 200, "withdraw", date);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/transaction/withdraw/{id}", 4)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(transaction)))
		.andExpect(status().isOk());
	}
	
	@Test
	void shouldNotWithdrawDueToIdNotFound() throws Exception {
		
		String sDate="01/04/2022";
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
		
		RequestTransaction transaction = new RequestTransaction(21, 200, "withdraw", date);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/transaction/withdraw/{id}", 21)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(transaction)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void shouldNotWithdrawDueToInsufficientAmount() throws Exception {
		
		String sDate="01/04/2022";
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
		
		RequestTransaction transaction = new RequestTransaction(4, 241600, "withdraw", date);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/transaction/withdraw/{id}", 4)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(transaction)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void shouldGetByIdTransaction() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get("/transaction/{id}", 38)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id_transazione", is(38)))
		.andExpect(jsonPath("$.id_conto_bancario", is(4)))
		.andExpect(jsonPath("$.importo", is(300)))
		.andExpect(jsonPath("$.tipo", is("withdraw")))
		.andExpect(jsonPath("$.data_transazione", is("2022-04-01")))
		.andDo(print());;
	}
	
	@Test
	void shouldNotGetByIdTransaction() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get("/transaction/{id}", 51)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
}
