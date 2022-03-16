package com.accenture.bank.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "conto_bancario")
public class BankAccount {

	@Id
	@Column (name = "id_conto_bancario")
	private int idAccount;
	
	@Column(name = "id_correntista")
	private int idDepositor;
	
	@Column(name = "id_istituto")
	private int idBank;
	
	@Column(name = "iban")
	private int iban;
	
	@Column(name = "balance")
	private int balance;
	
	
}
