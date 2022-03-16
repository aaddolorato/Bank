package com.accenture.bank.application.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.bank.application.entity.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {

	@Query("select count(b) from Bank b where ((b.nome IN :nome) and (b.sede IN :sede))")
	public int countByNomeAndSede(@Param ("nome") String nome, @Param("sede") String sede);
	
}
