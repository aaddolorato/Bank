package com.accenture.bank.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.accenture.bank.application.entity.Depositor;


public interface DepositorRepository extends JpaRepository<Depositor, Integer>{

	@Query("select count(*) from Depositor where codFisc = :codFisc")
	public int countByCodFisc(String codFisc);
	
	@Query(
			value = "select count(*), b.nome_istituto from correntista c join banca b on b.id_istituto_bancario = c.id_istituto group by b.nome_istituto", 
			nativeQuery = true)
	public List<Object> countByBankName();
	
	@Query(
			value = "select b.nome_istituto, c.id_correntista from banca b, correntista c where b.id_istituto_bancario = c.id_istituto and c.id_correntista in :ids ",
			nativeQuery = true)
	public List<Object> selectById(@Param("ids") List<Integer> ids);
	
	@Query(
			value = "select count(*), c.id_istituto, b.nome_istituto from banca b, correntista c where b.id_istituto_bancario = c.id_istituto group by c.id_istituto",
			nativeQuery = true)
	public List<Object> countByIdBank();
}
