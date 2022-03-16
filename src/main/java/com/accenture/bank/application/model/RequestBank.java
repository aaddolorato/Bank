package com.accenture.bank.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestBank {

	@JsonProperty("nome_istituto")
	private String nome;
	
	@JsonProperty("id_istituto_bancario")
	private int id;
	
	@JsonProperty("sede")
	private String sede;

	public String getNome() {
		return nome;
	}

	public RequestBank() {
		super();
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}
	
	
}
