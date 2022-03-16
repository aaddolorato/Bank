package com.accenture.bank.application.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.bank.application.model.RequestBankAccount;
import com.accenture.bank.application.model.ResponseBankAccount;
import com.accenture.bank.application.service.BankAccountService;

@RestController
public class BankAccountController {

	@Autowired
	private BankAccountService baService;
	
	@RequestMapping(value = "/accounts/depositors/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getByIdDepositor(@PathVariable int id){
		List<ResponseBankAccount> accountsByIdDepositor = baService.getByIdDepositor(id);
		if(accountsByIdDepositor==null) {
			return new ResponseEntity<>("Non esistono conti intestati al correntista " + id + ".", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("La lista dei conti intestati al correntista " + id + " è stata recuperata.\n"
		+ accountsByIdDepositor, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/accounts/new", method = RequestMethod.POST)
	public ResponseEntity<Object> addAccount(@RequestBody RequestBankAccount requestBankAccount) {
		ResponseBankAccount responseBankAccount = baService.addAccount(requestBankAccount);
		if(responseBankAccount==null) {
			return new ResponseEntity<>("Non è possibile aggiungere il conto. Non ci sono correntisti con l'id specificato.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Dettagli conto aggiunto. \n" + responseBankAccount, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/accounts/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable int id) {
		ResponseBankAccount responseBankAccount = baService.delete(id);
		if(responseBankAccount==null) {
			return new ResponseEntity<>("Non è possibile eliminare il conto. Non ci sono conti con id " + id + ".", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Dettagli conto eliminato. \n" + responseBankAccount, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/accounts/iban/{iban}", method = RequestMethod.GET)
	public ResponseEntity<Object> getAccountByIban(@PathVariable int iban) {
		ResponseBankAccount responseBankAccount = baService.getAccountByIban(iban);
		if(responseBankAccount==null) {
			return new ResponseEntity<>(" Non ci sono conti con iban " + iban + " nel sistema.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Dettagli conto. \n" + responseBankAccount, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/accounts/balance/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getSaldoById(@PathVariable int id) {
		int balance = baService.getSaldoById(id);
		if(balance==0) {
			return new ResponseEntity<>(" Non ci sono conti con id " + id + " nel sistema.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Il saldo del conto " + id + " è pari a " + balance, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/accounts/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@PathVariable int id, @RequestBody RequestBankAccount requestBankAccount) {
		ResponseBankAccount responseBankAccount = baService.update(id, requestBankAccount);
		if(responseBankAccount==null) {
			return new ResponseEntity<>(" Non ci sono conti con id " + id + " nel sistema.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Dettagli conto aggiornato. \n" + responseBankAccount, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/accounts/interests/{id}/{p}/{d}", method = RequestMethod.GET)
	public ResponseEntity<Object> interestCalculation(@PathVariable int id, @PathVariable float p, @PathVariable float d) {
		float interests = baService.interestCalculation(id, p, d);
		if(interests==0) {
			return new ResponseEntity<>(" Non ci sono conti con id " + id + " nel sistema.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Gli interessi per il conto " + id + " sono pari a:\n" + interests, HttpStatus.OK);
	}
}
