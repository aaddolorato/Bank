package com.accenture.bank.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseBankAccount {

	@JsonProperty("id_conto_bancario")
	private int idAccount;
	
	@JsonProperty("id_correntista")
	private int idDepositor;
	
	@JsonProperty("id_istituto")
	private int idBank;
	
	@JsonProperty("iban")
	private int iban;
	
	@JsonProperty("balance")
	private int balance;
}
