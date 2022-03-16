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
@Table(name = "correntista")
public class Depositor {

	@Id
	@Column(name = "id_correntista")
	private int idDepositor;
	
	@Column(name = "id_istituto")
	private int idBank;
	
	@Column(name = "cod_fiscale")
	private String codFisc;
	
	@Column(name = "nome")
	private String name;
	
	@Column(name = "cognome")
	private String surname;
	
	@Column(name = "data_nascita")
	private Date birth;
}
