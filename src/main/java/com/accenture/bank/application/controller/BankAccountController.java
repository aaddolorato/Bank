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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Bank Account Controller", description = "Controller CRUD Bank Accounts")
@RestController
public class BankAccountController {

	@Autowired
	private BankAccountService baService;
	
	@Operation(summary = "Bank Account by Id Depositor", description = "Shows the Bank Account "
			+ "linked to the Depositor with the specified ID")
	@RequestMapping(value = "/accounts/depositors/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getByIdDepositor(@PathVariable int id){
		List<ResponseBankAccount> accountsByIdDepositor = baService.getByIdDepositor(id);
		if(accountsByIdDepositor==null) {
			return new ResponseEntity<>("Non esistono conti intestati al correntista " + id + ".", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(accountsByIdDepositor, HttpStatus.OK);
	}
	
	@Operation(summary = "Add Bank Accounts", description = "Adds the specified (by its Id) Bank Account and shows its details")
	@RequestMapping(value = "/accounts/new", method = RequestMethod.POST)
	public ResponseEntity<Object> addAccount(@RequestBody RequestBankAccount requestBankAccount) {
		ResponseBankAccount responseBankAccount = baService.addAccount(requestBankAccount);
		if(responseBankAccount==null) {
			return new ResponseEntity<>("Non è possibile aggiungere il conto. Non ci sono correntisti con l'id specificato.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseBankAccount, HttpStatus.OK);
	}
	
	@Operation(summary = "Delete Bank Account", description = "Deletes the specified Bank Account")
	@RequestMapping(value = "/accounts/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable int id) {
		ResponseBankAccount responseBankAccount = baService.delete(id);
		if(responseBankAccount==null) {
			return new ResponseEntity<>("Non è possibile eliminare il conto. Non ci sono conti con id " + id + ".", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseBankAccount, HttpStatus.OK);
	}
	
	@Operation(summary = "Bank Account By Iban", description = "Shows the specified (by its Iban) Bank Account's details")
	@RequestMapping(value = "/accounts/iban/{iban}", method = RequestMethod.GET)
	public ResponseEntity<Object> getAccountByIban(@PathVariable int iban) {
		ResponseBankAccount responseBankAccount = baService.getAccountByIban(iban);
		if(responseBankAccount==null) {
			return new ResponseEntity<>(" Non ci sono conti con iban " + iban + " nel sistema.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseBankAccount, HttpStatus.OK);
	}
	
	@Operation(summary = "Balance Bank Account", description = "Show the Balance of the specified (by its Id) Bank Account")
	@RequestMapping(value = "/accounts/balance/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getSaldoById(@PathVariable int id) {
		int balance = baService.getSaldoById(id);
		if(balance==0) {
			return new ResponseEntity<>(" Non ci sono conti con id " + id + " nel sistema.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(balance, HttpStatus.OK);
	}
	
	@Operation(summary = "Update Bank Account", description = "Updates the specified (by its Id) Bank Account")
	@RequestMapping(value = "/accounts/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@PathVariable int id, @RequestBody RequestBankAccount requestBankAccount) {
		ResponseBankAccount responseBankAccount = baService.update(id, requestBankAccount);
		if(responseBankAccount==null) {
			return new ResponseEntity<>(" Non ci sono conti con id " + id + " nel sistema.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseBankAccount, HttpStatus.OK);
	}
	
	@Operation(summary = "Interests Bank Account", description = "Calculates the specified (by its Id) Bank Account Interests")
	@RequestMapping(value = "/accounts/interests/{id}/{p}/{d}", method = RequestMethod.GET)
	public ResponseEntity<Object> interestCalculation(@PathVariable int id, @PathVariable float p, @PathVariable float d) {
		float interests = baService.interestCalculation(id, p, d);
		if(interests==0) {
			return new ResponseEntity<>(" Non ci sono conti con id " + id + " nel sistema.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(interests, HttpStatus.OK);
	}
}
