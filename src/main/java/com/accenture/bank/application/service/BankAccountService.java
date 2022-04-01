package com.accenture.bank.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.accenture.bank.application.entity.BankAccount;
import com.accenture.bank.application.model.RequestBankAccount;
import com.accenture.bank.application.model.ResponseBankAccount;
import com.accenture.bank.application.repository.BankAccountRepository;
import com.accenture.bank.application.repository.DepositorRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BankAccountService {

	@Autowired
	private BankAccountRepository baRepository;

	@Autowired
	private DepositorRepository depositorRepository;
	
	private static final Random random = new Random();
	

	public List<ResponseBankAccount> getAllBankAccounts(){
		List<BankAccount> bankAccounts = baRepository.findAll();
		ModelMapper mapper = new ModelMapper();
		ResponseBankAccount responseBankAccount = new ResponseBankAccount();
		List<ResponseBankAccount> responseBankAccounts = new ArrayList<>();
		for(BankAccount bankAccount : bankAccounts) {
			mapper.map(bankAccount, responseBankAccount);
			responseBankAccounts.add(responseBankAccount);
		}
		if(bankAccounts.isEmpty()) {
			log.error("Non è stato possibile recuperare la lista di conti bancari", new ResponseStatusException(HttpStatus.NOT_FOUND));
			return responseBankAccounts;
		}
		else {
			
			log.info("Lista delle banche recuperata.");
			return responseBankAccounts;
		}

	}

	public ResponseBankAccount getAccountById(int id) {
		Optional<BankAccount> optionalBankAccount = baRepository.findById(id);
		if(optionalBankAccount.isEmpty()) {
			log.error("Il conto con id {} non è presente nel sistema.", id, new ResponseStatusException(HttpStatus.NOT_FOUND));
			return null;
		}
		else {
			BankAccount bankAccount = optionalBankAccount.get();
			ResponseBankAccount responseBankAccount = new ResponseBankAccount();
			ModelMapper mapper = new ModelMapper();
			mapper.map(bankAccount, responseBankAccount);
			log.info("Banca recuperata.");
			return responseBankAccount;
		}
	}

	public List<ResponseBankAccount> getByIdDepositor(int id){
		List<BankAccount> bankAccounts = baRepository.findAll();		
		List<ResponseBankAccount> depositorBankAccounts = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();
		for(BankAccount bankAccount : bankAccounts) {
			if(bankAccount.getIdDepositor()==id) {
				ResponseBankAccount responseBankAccount = new ResponseBankAccount();
				mapper.map(bankAccount, responseBankAccount);
				depositorBankAccounts.add(responseBankAccount);
			}
		}
		if(depositorBankAccounts.isEmpty()) {
			log.error("Non ci sono conti associati al correntista con id {}.", id, new ResponseStatusException(HttpStatus.NOT_FOUND));
			return depositorBankAccounts;
		}
		else {
			log.info("Lista dei conti associati al correntista con id {}.", id);
			return depositorBankAccounts;
		}
	}

	
	public ResponseBankAccount addAccount(RequestBankAccount requestBankAccount) {
		if(depositorRepository.existsById(requestBankAccount.getIdDepositor())) {

			int iban = random.nextInt()*10000;
			while(existingIban(iban)!=null) {
				iban = random.nextInt()*10000;
			}
			
			BankAccount bankAccount = new BankAccount();
			ModelMapper mapper = new ModelMapper();
			mapper.map(requestBankAccount, bankAccount);
			bankAccount.setIban(iban);
			baRepository.save(bankAccount);
			ResponseBankAccount responseBankAccount = new ResponseBankAccount();
			mapper.map(bankAccount, responseBankAccount);
			log.info("Il conto è stato registrato correttamente.");
			return responseBankAccount;
		}
		
		log.error("Impossibile registrare il conto. Non ci sono correntisti con id {}.", requestBankAccount.getIdDepositor(), new ResponseStatusException(HttpStatus.NOT_FOUND));
		return null;
	}

	public ResponseBankAccount delete(int id) {
		Optional<BankAccount> optionalBank = baRepository.findById(id);
		if(optionalBank.isPresent()) {
			BankAccount bankAccount = optionalBank.get();
			baRepository.delete(bankAccount);
			ResponseBankAccount responseBankAccount = new ResponseBankAccount();
			ModelMapper mapper = new ModelMapper();
			mapper.map(bankAccount, responseBankAccount);
			log.info("Conto cancellato con successo.");
			return responseBankAccount;
		}
		else {
			log.error("Non è stato possibile eliminare il conto. L'id {} non è presente nel sistema.", id, new ResponseStatusException(HttpStatus.NOT_FOUND));
			return null;
		}
	}

	public ResponseBankAccount getAccountByIban(int iban) {
		List<BankAccount> bankAccounts = baRepository.findAll();
		ModelMapper mapper = new ModelMapper();
		for(BankAccount bankAccount : bankAccounts) {
			if(bankAccount.getIban()==iban) {
				ResponseBankAccount responseBankAccount = new ResponseBankAccount();
				mapper.map(bankAccount, responseBankAccount);
				log.info("Conto recuperato. Dettagli \n{}", responseBankAccount);
				return responseBankAccount;
			}
		}
		log.error("Il conto con iban {} non è presente nel sistema.", new ResponseStatusException(HttpStatus.NOT_FOUND));
		return null;
	}

	public int getSaldoById(int id) {
		Optional<BankAccount> optionalBankAccount = baRepository.findById(id);
		if(optionalBankAccount.isPresent()) {
			BankAccount bankAccount = optionalBankAccount.get();
			ModelMapper mapper = new ModelMapper();
			ResponseBankAccount responseBankAccount = new ResponseBankAccount();
			mapper.map(bankAccount, responseBankAccount);
			log.info("Saldo recuperato: " + responseBankAccount.getBalance());
			return responseBankAccount.getBalance();
		}
		else {
			log.error("Non esistono conti con id {}.", id, new ResponseStatusException(HttpStatus.NOT_FOUND));
			return 0;
		}
	}

	public ResponseBankAccount update(int id, RequestBankAccount requestBankAccount) {
		Optional<BankAccount> optionalBankAccount = baRepository.findById(id);
		if(optionalBankAccount.isPresent()) {
			BankAccount bankAccount = optionalBankAccount.get();
			ModelMapper mapper = new ModelMapper();
			mapper.map(requestBankAccount, bankAccount);
			bankAccount.setIdAccount(id);
			baRepository.save(bankAccount);
			ResponseBankAccount responseBankAccount = new ResponseBankAccount();
			mapper.map(bankAccount, responseBankAccount);
			log.info("Conto aggiornato con successo. Dettagli \n" + responseBankAccount);
			return responseBankAccount;
		}
		else {
			log.error("Non esistono conti con id {}.", id, new ResponseStatusException(HttpStatus.NOT_FOUND));
			return null;
		}
	}

	public Integer existingIban(int iban) {
		return baRepository.findByIban(iban);
	}

	public float interestCalculation(int id, float p, float d) {
		Optional<BankAccount> optionalBankAccount = baRepository.findById(id);
		if(optionalBankAccount.isPresent()) {
			BankAccount bankAccount = optionalBankAccount.get();
			float interests = bankAccount.getBalance()*((p/100)*d);
			log.info("Gli interessi maturati sono :{}", interests);
			return interests;
		}
		else {
			log.error("Non è stato trovato un conto con questo id", new ResponseStatusException(HttpStatus.NOT_FOUND));
			return 0;
		}
	}
}
