package com.accenture.bank.application.model;

import java.sql.Date;

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
}
