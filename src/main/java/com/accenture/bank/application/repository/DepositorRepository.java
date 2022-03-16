package com.accenture.bank.application.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.bank.application.entity.Depositor;


public interface DepositorRepository extends JpaRepository<Depositor, Integer>{

	@Query("select count(*) from Depositor where codFisc = ?")
	public int countByCodFisc(String codFisc);
}
