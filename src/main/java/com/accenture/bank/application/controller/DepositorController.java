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

import com.accenture.bank.application.model.RequestDepositor;
import com.accenture.bank.application.model.ResponseDepositor;
import com.accenture.bank.application.service.DepositorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Depositor Controller", description = "Controller CRUD Depositor")
@RestController
public class DepositorController {

	@Autowired
	private DepositorService depositorService;

	@Operation(summary = "Depositors List", description = "Shows all Depositors details")
	@RequestMapping(value = "/depositors", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllDepositors(){
		List<ResponseDepositor> responseDepositorsList = depositorService.getAllDepositors();
		if(responseDepositorsList==null) {
			return new ResponseEntity<>("Non è stato possibile recuperare la lista dei correntisti.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseDepositorsList, HttpStatus.OK);

	}

	@Operation(summary = "Depositor By Id", description = "Shows the specified(by its Id) Depositor's details")
	@RequestMapping(value = "/depositors/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getById(@PathVariable int id) {
		ResponseDepositor responseDepositor = depositorService.getById(id);
		if(responseDepositor==null) {
			return new ResponseEntity<>("Non è stato possibile recuperare il correntista con id " + id + ".", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseDepositor, HttpStatus.OK);
	}

	@Operation(summary = "Depositor By Fiscal Code", description = "Shows the specified(by its Fiscal Code) Depositor's details")
	@RequestMapping(value = "/depositors/find/{codFisc}", method = RequestMethod.GET)
	public ResponseEntity<Object> getByCf(@PathVariable String codFisc) {
		ResponseDepositor responseDepositor = depositorService.getByCf(codFisc);
		if(responseDepositor==null) {
			return new ResponseEntity<>("Non è stato possibile recuperare il correntista con codice fiscale " + codFisc + "." , HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseDepositor, HttpStatus.OK);
	}

	@Operation(summary = "Delete Depositor", description = "Deletes the specified(by its Id) Depositor")
	@RequestMapping(value = "/depositors/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable int id) {
		ResponseDepositor responseDepositor = depositorService.delete(id);
		if(responseDepositor==null) {
			return new ResponseEntity<>("Non è possibile eliminare un correntista non presente nel sistema." , HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseDepositor, HttpStatus.OK);
	}

	@Operation(summary = "Add Depositor", description = "Adds new Depositor and shows its details")
	@RequestMapping(value = "/depositors/save", method = RequestMethod.POST)
	public ResponseEntity<Object> addDepositor(@RequestBody RequestDepositor requestDepositor) {
		ResponseDepositor responseDepositor = depositorService.addDepositor(requestDepositor);
		if(responseDepositor==null) {
			return new ResponseEntity<>("Non è possibile aggiungere questo correntista. Nel sistema è già presente." , HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(responseDepositor, HttpStatus.OK);
	}

	@Operation(summary = "Update Depositor", description = "Updates the specified(by its Id) Depositor and shows its details")
	@RequestMapping(value = "/depositors/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateDepositor(@PathVariable int id, @RequestBody RequestDepositor requestDepositor) {
		ResponseDepositor responseDepositor = depositorService.updateDepositor(id, requestDepositor);
		if(responseDepositor==null) {
			return new ResponseEntity<>("Non è possibile aggiornare questo correntista. Non è presente nel sistema." , HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(responseDepositor, HttpStatus.OK);
	}
}
