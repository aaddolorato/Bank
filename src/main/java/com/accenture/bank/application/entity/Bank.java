package com.accenture.bank.application.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "banca")
public class Bank implements Serializable {
	
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_istituto_bancario")
	private int id;
	
	@Column(name = "nome_istituto")
	private String nome;
	
	@Column(name = "sede")
	private String sede;

	public Bank() {
		
	}
	
	public Bank(String nome, String sede) {
		this.nome = nome;
		this.sede = sede;
	}
	
	public Bank(int id, String nome, String sede) {
		this.id = id;
		this.nome = nome;
		this.sede = sede;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}
	
	@Override
	public String toString() {
		return "Bank: \n[id=" + id + "\nNome=" + nome + "\nSede=" + sede + "]";
	}
	
}
