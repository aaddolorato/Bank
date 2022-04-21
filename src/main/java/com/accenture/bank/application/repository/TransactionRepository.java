package com.accenture.bank.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.accenture.bank.application.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

	@Query(value = "select count(*), b.nome_istituto from banca b, transazione t, conto_bancario c "
			+ "where c.id_conto_bancario = t.id_conto_bancario and b.id_istituto_bancario = c.id_istituto group by id_istituto",
			nativeQuery = true)
	List<Object> countTransactionByBankName();
}
