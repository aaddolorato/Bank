package com.accenture.bank.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.bank.application.repository.DepositorRepository;
import com.accenture.bank.application.repository.TransactionRepository;

@RestController
public class StatisticsController {

	@Autowired
	private DepositorRepository depositorRepo;
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	@GetMapping("/depositors/groupbybank")
	public List<Object> countDepositorByBankName() {
		return depositorRepo.countByBankName();
	}
	
	@GetMapping("/transaction/groupbybank")
	public List<Object> countTransactionByBankName(){
		return transactionRepo.countTransactionByBankName();
	}
	
	@GetMapping("/depositors/bank/{ids}")
	public List<Object> getDepositorsByIdList(@PathVariable List<Integer> ids){
		return depositorRepo.selectById(ids);
	}
	
	@GetMapping("/depositor/groupbybankid")
	public List<Object> countDepositorByBankId(){
		return depositorRepo.countByIdBank();
	}
}
