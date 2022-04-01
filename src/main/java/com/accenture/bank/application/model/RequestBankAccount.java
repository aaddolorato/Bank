package com.accenture.bank.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestBankAccount {

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
	
	
	public RequestBankAccount(int idDepositor, int idBank, int iban, int balance) {
		super();
		this.idDepositor = idDepositor;
		this.idBank = idBank;
		this.balance = balance;
	}

	
}
