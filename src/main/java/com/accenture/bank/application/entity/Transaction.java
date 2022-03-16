package com.accenture.bank.application.entity;

import java.sql.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "transazione")
public class Transaction {

	@Id
	@Column(name = "id_transazione")
	private int idTransaction;
	
	@Column(name = "id_conto_bancario")
	private int idBankAccount;
	
	@Column(name = "importo")
	private int amount;
	
	@Column(name = "tipo")
	private String type;
	
	@Column(name = "data_transazione")
	private Date dateTransaction;
}
