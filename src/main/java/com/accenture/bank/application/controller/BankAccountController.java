package com.accenture.bank.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	private String msg = "Non ci sono conti con id";
	private String msg2 = " nel sistema.";
	
	@Operation(summary = "Bank Account by Id Depositor", description = "Shows the Bank Account "
			+ "linked to the Depositor with the specified ID")
	@GetMapping("/accounts/depositors/{id}")
	public ResponseEntity<Object> getByIdDepositor(@PathVariable int id){
		List<ResponseBankAccount> accountsByIdDepositor = baService.getByIdDepositor(id);
		if(accountsByIdDepositor.isEmpty()) {
			return new ResponseEntity<>("Non esistono conti intestati al correntista " + id + ".", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(accountsByIdDepositor, HttpStatus.OK);
	}
	
	@Operation(summary = "Add Bank Accounts", description = "Adds the specified (by its Id) Bank Account and shows its details")
	@PostMapping("/accounts/new")
	public ResponseEntity<Object> addAccount(@RequestBody RequestBankAccount requestBankAccount) {
		ResponseBankAccount responseBankAccount = baService.addAccount(requestBankAccount);
		if(responseBankAccount==null) {
			return new ResponseEntity<>("Non è possibile aggiungere il conto. Non ci sono correntisti con l'id specificato.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseBankAccount, HttpStatus.OK);
	}
	
	@Operation(summary = "Delete Bank Account", description = "Deletes the specified Bank Account")
	@DeleteMapping("/accounts/delete/{id}")
	public ResponseEntity<Object> delete(@PathVariable int id) {
		ResponseBankAccount responseBankAccount = baService.delete(id);
		if(responseBankAccount==null) {
			return new ResponseEntity<>("Non è possibile eliminare il conto. Non ci sono conti con id " + id + ".", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseBankAccount, HttpStatus.OK);
	}
	
	@Operation(summary = "Bank Account By Iban", description = "Shows the specified (by its Iban) Bank Account's details")
	@GetMapping("/accounts/iban/{iban}")
	public ResponseEntity<Object> getAccountByIban(@PathVariable int iban) {
		ResponseBankAccount responseBankAccount = baService.getAccountByIban(iban);
		if(responseBankAccount==null) {
			return new ResponseEntity<>(" Non ci sono conti con iban " + iban + msg2, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseBankAccount, HttpStatus.OK);
	}
	
	@Operation(summary = "Balance Bank Account", description = "Show the Balance of the specified (by its Id) Bank Account")
	@GetMapping("/accounts/balance/{id}")
	public ResponseEntity<Object> getSaldoById(@PathVariable int id) {
		int balance = baService.getSaldoById(id);
		if(balance==0) {
			return new ResponseEntity<>(msg + id + msg2, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(balance, HttpStatus.OK);
	}
	
	@Operation(summary = "Update Bank Account", description = "Updates the specified (by its Id) Bank Account")
	@PutMapping("/accounts/update/{id}")
	public ResponseEntity<Object> update(@PathVariable int id, @RequestBody RequestBankAccount requestBankAccount) {
		ResponseBankAccount responseBankAccount = baService.update(id, requestBankAccount);
		if(responseBankAccount==null) {
			return new ResponseEntity<>(msg + id + msg2, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseBankAccount, HttpStatus.OK);
	}
	
	@Operation(summary = "Interests Bank Account", description = "Calculates the specified (by its Id) Bank Account Interests")
	@GetMapping("/accounts/interests/{id}/{p}/{d}")
	public ResponseEntity<Object> interestCalculation(@PathVariable int id, @PathVariable float p, @PathVariable float d) {
		float interests = baService.interestCalculation(id, p, d);
		if(interests==0) {
			return new ResponseEntity<>(msg + id + msg2, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(interests, HttpStatus.OK);
	}
}
