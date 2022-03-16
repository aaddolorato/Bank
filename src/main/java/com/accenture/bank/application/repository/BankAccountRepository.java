package com.accenture.bank.application.repository;

import org.springframework.data.jdbc.repository.query.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.bank.application.entity.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

	@Query("select count(*) from BankAccount where iban = ?")
	Integer findByIban(int iban);
}
