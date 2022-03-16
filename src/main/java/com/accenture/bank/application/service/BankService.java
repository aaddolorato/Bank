package com.accenture.bank.application.service;

import java.util.ArrayList;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.accenture.bank.application.entity.Bank;
import com.accenture.bank.application.model.RequestBank;
import com.accenture.bank.application.model.ResponseBank;
import com.accenture.bank.application.repository.BankRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BankService {

	@Autowired
	private BankRepository bankRepository;

	public BankService() {

	}

	public List<ResponseBank> getAllBanks(){
		List<Bank> bankList = bankRepository.findAll();
		if(bankList.isEmpty()) {
			log.error("Non è stato possibile recuperare la lista delle banche.", new ResponseStatusException(HttpStatus.NOT_FOUND));
			return null;
		}
		else {
			List<ResponseBank> responseBankList = new ArrayList<ResponseBank>();
			for(Bank b : bankList) {
				ResponseBank responseBank = new ResponseBank(b.getNome(), b.getId(), b.getSede());
				responseBankList.add(responseBank);
			}
			log.info("Lista delle banche recuperata.");
			return responseBankList;
		}
	}

	public ResponseBank getBankById(int id) {
		if(bankRepository.findById(id).isEmpty()) {
			log.error("Non è stata trovata nessuna banca con id {}.", id, new ResponseStatusException(HttpStatus.NOT_FOUND));
			return null;
		}
		else {
			Bank bank = bankRepository.findById(id).get();
			ModelMapper mapper = new ModelMapper();
			ResponseBank rBank = new ResponseBank();
			mapper.map(bank, rBank);
			log.info("Banca recuperata con successo.");
			return rBank;
		}
	}

	public ResponseBank saveBank(RequestBank requestBank) {
		ModelMapper map = new ModelMapper();
		Bank bank = new Bank();
		map.map(requestBank, bank);
		if(existingBank(bank.getNome(), bank.getSede())!=0) {
			log.info("La banca è già presente nel sistema. \n{}", bank);
			return null;
		}
		else {
			bankRepository.save(bank);
			ResponseBank responseBank = new ResponseBank();
			map.map(bank, responseBank);
			log.info("La banca è stata aggiunta. \n{}", bank);
			return responseBank;			
		}
	}

	public ResponseBank updateBank(int id, RequestBank requestBank){
		if(bankRepository.findById(id).isEmpty()) {
			log.error("La banca con id {} non è presente nel sistema.", id, new ResponseStatusException(HttpStatus.NOT_FOUND));
			return null;
		}
		else {
			Bank bank = bankRepository.findById(id).get();
			ModelMapper mapper = new ModelMapper();
			mapper.map(requestBank, bank);
			bank.setId(id);
			bankRepository.save(bank);
			ResponseBank responseBank = new ResponseBank();
			mapper.map(bank, responseBank);
			log.info("La banca è stata modificata. \n{}", bank);
			return responseBank;
		}
	}

	public ResponseBank removeBank(int id) {
		if(bankRepository.findById(id).isEmpty()) {
			log.error("Errore, la banca non esiste", new ResponseStatusException(HttpStatus.NOT_FOUND));
			return null;
		}
		else {
			Bank bank = bankRepository.findById(id).get();
			bankRepository.delete(bank);
			ModelMapper mapper = new ModelMapper();
			ResponseBank responseBank = new ResponseBank();
			mapper.map(bank, responseBank);
			log.info("La banca è stata eliminata.");
			return responseBank;
		}
	}

	public int existingBank(String nome, String sede) {
		return bankRepository.countByNomeAndSede(nome, sede);
	}
}