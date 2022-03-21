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

import com.accenture.bank.application.model.RequestBank;
import com.accenture.bank.application.model.ResponseBank;
import com.accenture.bank.application.service.BankService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Bank Controller", description = "Controller CRUD Bank")
@RestController
public class BankController {

	@Autowired
	private BankService bankService;
	
	@Operation(summary = "Banks List", description = "Shows all Banks details")
	@RequestMapping(value = "/banks", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getBanks(){
		List<ResponseBank> banksList = bankService.getAllBanks();
		if(banksList==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Non è stato possibile recuperare la lista delle banche.");
		}
		return ResponseEntity.ok(banksList);

	}
	
	@Operation(summary = "Bank", description = "Shows the specified (by its Id) Bank's details")
	@RequestMapping(value = "/banks/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getBankById(@PathVariable int id) {
		ResponseBank responseBank = bankService.getBankById(id);
		if(responseBank==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Non è stata trovata nessuna banca con id " + id + ".");
		}
		return ResponseEntity.ok(responseBank);
	}
	
	@Operation(summary = "Add Bank", description = "Adds a new Bank and shows its details")
	@RequestMapping(value = "/banks/save", method = RequestMethod.POST)
	public ResponseEntity<Object> saveBank(@RequestBody RequestBank requestBank) {
		ResponseBank responseBank = bankService.saveBank(requestBank);
		if(responseBank==null) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("La banca è già presente nel sistema.");
		}
		return new ResponseEntity<>(responseBank, HttpStatus.OK);
	}
	
	@Operation(summary = "Update Bank", description = "Updates the specified (by its Id) Bank and shows its details ")
	@RequestMapping(value = "/banks/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateBank(@PathVariable int id, @RequestBody RequestBank requestBank) {
		ResponseBank responseBank = bankService.updateBank(id, requestBank);
		if(responseBank==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Non è possibile aggiornare una banca non presente nel sistema.");
		}
		return new ResponseEntity<>(responseBank, HttpStatus.OK);
	}
	
	@Operation(summary = "Delete Bank", description = "Deletes the specified (by its Id) Bank")
	@RequestMapping(value = "/banks/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteBank(@PathVariable int id) {
		ResponseBank responseBank = bankService.removeBank(id);
		if(responseBank==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Non è possibile eliminare una banca non presente nel sistema.");
		}
		return new ResponseEntity<>("Banca eliminata con successo.", HttpStatus.OK);
	}
}
