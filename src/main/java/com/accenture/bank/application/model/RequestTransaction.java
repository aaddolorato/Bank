package com.accenture.bank.application.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestTransaction {

	@JsonProperty("id_transazione")
	private int idTransaction;
	
	@JsonProperty("id_conto_bancario")
	private int idBankAccount;
	
	@JsonProperty("importo")
	private int amount;
	
	@JsonProperty("tipo")
	private String type;
	
	@JsonProperty("data_transazione")
	private Date dateTransaction;

	public RequestTransaction(int idBankAccount, int amount, String type, Date dateTransaction) {
		this.idBankAccount = idBankAccount;
		this.amount = amount;
		this.type = type;
		this.dateTransaction = dateTransaction;
	}
	
	
}
