package com.accenture.bank.application.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDepositor {
	
	@JsonProperty("id_istituto")
	private int idBank;
	
	@JsonProperty("cod_fiscale")
	private String codFisc;
	
	@JsonProperty("nome")
	private String name;
	
	@JsonProperty("cognome")
	private String surname;
	
	@JsonProperty("data_nascita")
	private Date birth;

	public RequestDepositor(int idBank, String codFisc, String name, String surname, Date birth) {
		super();
		this.idBank = idBank;
		this.codFisc = codFisc;
		this.name = name;
		this.surname = surname;
		this.birth = birth;
	}
	
	
}
