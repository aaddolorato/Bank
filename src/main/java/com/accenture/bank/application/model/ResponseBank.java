package com.accenture.bank.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ResponseBank {
	
	@JsonProperty("nome_istituto")
	private String nome;
	
	@JsonProperty("id_istituto_bancario")
	private int id;
	
	@JsonProperty("sede")
	private String sede;

	public ResponseBank() {
		
	}

	public ResponseBank(String nome, int id, String sede) {
		
		this.nome = nome;
		this.id = id;
		this.sede = sede;
	}



	public String getNome() {
		return nome;
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

	@Override
	public String toString() {
		return "ResponseBank: \nnome=" + nome + "\nid=" + id + "\nsede=" + sede + "]";
	}
	
	

}
