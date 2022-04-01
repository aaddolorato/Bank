package com.accenture.bank.application.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.bank.application.model.RequestTransaction;
import com.accenture.bank.application.model.ResponseTransaction;
import com.accenture.bank.application.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Transaction Controller", description = "Controller CRUD Transaction")
@RestController
public class TransactionController {

	@Autowired
	private TransactionService tService;

	@Operation(summary = "Add Deposit Transaction", description = "Adds a Deposit Transaction linked to the specified (by its Id) Bank Account")
	@PostMapping("/transaction/deposit/{id}")
	public ResponseEntity<Object> deposit(@PathVariable int id, @RequestBody  RequestTransaction requestTransaction) {
		ResponseTransaction responseTransaction = tService.deposit(id, requestTransaction.getAmount());
		if(responseTransaction==null) {
			return new ResponseEntity<>("Non è stato possibile effettuare l'operazione di deposito", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(responseTransaction, HttpStatus.OK);
	}

	@Operation(summary = "Bank Account's Transactions", description = "Shows the specified (by its Id) Bank Account's Transactions details")
	@GetMapping("/transaction/bankaccount/{id}")
	public ResponseEntity<Object> getByIdBankAccount(@PathVariable int id){
		List<ResponseTransaction> responseTransactionsList = tService.getByIdBankAccount(id);
		if(responseTransactionsList.isEmpty()) {
			return new ResponseEntity<>("Non è stato possibile recuperare la lista delle transazione del conto con id " + id + ".", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseTransactionsList, HttpStatus.OK);
	}
	
	@Operation(summary = "Bank Account's last Transaction", description = "Shows the specified (by its Id) Bank Account's last Transaction's details")
	@GetMapping("/transaction/bankaccount/last/{id}")
	public ResponseEntity<Object> getLastByIdBankAccount(@PathVariable int id) {
		ResponseTransaction responseTransaction = tService.getLastByIdBankAccount(id);
		if(responseTransaction==null) {
			return new ResponseEntity<>("Non è presente nessuna transazione per il conto con id " + id + " nel sistema.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseTransaction, HttpStatus.OK);
	}
	
	@Operation(summary = "Update Transaction", description = "Updates a specified (by its Id) Transaction and shows its details")
	@PutMapping("/transaction/update/{id}")
	public ResponseEntity<Object> updateTransaction(@RequestBody RequestTransaction requestTransaction, @PathVariable int id) {
		ResponseTransaction responseTransaction = tService.updateTransaction(requestTransaction, id);
		if(responseTransaction==null) {
			return new ResponseEntity<>("Non è stato possibile effettuare l'operazione", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(responseTransaction, HttpStatus.OK);
	}

	@Operation(summary = "Add a Withdraw Transaction", description = "Adds a withdraw Transaction linked to the specified (by its Id) Bank Account and shows its details")
	@PostMapping("/transaction/withdraw/{id}")
	public ResponseEntity<Object> withdraw(@PathVariable int id, @RequestBody RequestTransaction requestTransaction) throws ParseException {
		ResponseTransaction responseTransaction = tService.withdraw(requestTransaction.getAmount(), id);
		if(responseTransaction==null) {
			return new ResponseEntity<>("Non è stato possibile effettuare l'operazione di prelievo", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(responseTransaction, HttpStatus.OK);
	}
	
	@Operation(summary = "Transaction", description = "Shows the specified (by its Id) Transaction")
	@GetMapping("/transaction/{id}")
	public ResponseEntity<Object> getByIdTransaction(@PathVariable int id) {
		ResponseTransaction responseTransaction = tService.getByIdTransaction(id);
		if(responseTransaction==null) {
			return new ResponseEntity<>("Non è presente nessuna transazione con id " + id + " nel sistema.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseTransaction, HttpStatus.OK);
	}


}
