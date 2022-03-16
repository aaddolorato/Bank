package com.accenture.bank.application.service;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.accenture.bank.application.entity.BankAccount;
import com.accenture.bank.application.entity.Transaction;
import com.accenture.bank.application.model.RequestBankAccount;
import com.accenture.bank.application.model.RequestTransaction;
import com.accenture.bank.application.model.ResponseTransaction;
import com.accenture.bank.application.repository.BankAccountRepository;
import com.accenture.bank.application.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionService {

	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	BankAccountService baService;
	@Autowired
	BankAccountRepository bankAccountRepository;

	public ResponseTransaction deposit(int id, int amount) {
		if(bankAccountRepository.existsById(id)) {
			if(amount>0) {
				Transaction t = new Transaction();
				t.setAmount(amount);
				t.setIdBankAccount(id);
				t.setType("deposit");
				t.setDateTransaction(new Date(System.currentTimeMillis()));
				transactionRepository.save(t);
				ModelMapper mapper = new ModelMapper();
				ResponseTransaction responseTransaction = new ResponseTransaction();
				mapper.map(t, responseTransaction);
				BankAccount bankAccount = bankAccountRepository.findById(id).get();
				RequestBankAccount requestBankAccount = new RequestBankAccount();
				mapper.map(bankAccount, requestBankAccount);
				requestBankAccount.setBalance(amount + bankAccount.getBalance());
				baService.update(id, requestBankAccount);
				log.info("Deposito effettuato");
				return responseTransaction;
			}
			else {
				log.error("Non è possibile depositare una somma inferiore a zero", new IllegalArgumentException());
				return null;
			}
		}
		else {
			log.error("Non è stato trovato un conto con id {}", id, new ResponseStatusException(HttpStatus.NOT_FOUND));
			return null;
		}

	}

	public List<ResponseTransaction> getAllTransactions(){
		List<Transaction> transactions = transactionRepository.findAll();
		if(transactions.isEmpty()) {
			log.error("Non è stato possibile recuperare la lista delle transazioni", new ResponseStatusException(HttpStatus.NOT_FOUND));
			return null;
		}
		else {
			List<ResponseTransaction> transactionsList = new ArrayList<ResponseTransaction>();
			ModelMapper mapper = new ModelMapper();
			for(Transaction t : transactions) {
				ResponseTransaction responseTransaction = new ResponseTransaction();
				mapper.map(t, responseTransaction);
				transactionsList.add(responseTransaction);
			}
			log.info("Transazioni recuperate");
			return transactionsList;
		}
	}

	public ResponseTransaction getByIdTransaction(int id) {
		if(transactionRepository.existsById(id)) {
			Transaction transaction = transactionRepository.findById(id).get();
			ResponseTransaction responseTransaction = new ResponseTransaction();
			ModelMapper mapper = new ModelMapper();
			mapper.map(transaction, responseTransaction);
			log.info("Transazione recuperata \n{}" + responseTransaction);
			return responseTransaction;
		}
		else {
			log.error("Non sono presenti nel sistema transazioni con id {}" + id, new ResponseStatusException(HttpStatus.NOT_FOUND));
			return null;
		}
	}

	public List<ResponseTransaction> getByIdBankAccount(int id){
		List<ResponseTransaction> transactionList = getAllTransactions();
		List<ResponseTransaction> transactionsBankAccount = new ArrayList<ResponseTransaction>();
		for(ResponseTransaction t : transactionList)
			if(t.getIdBankAccount()==id) {
				transactionsBankAccount.add(t);
			}
		if(transactionsBankAccount.isEmpty()) {
			log.error("Non esistono transazioni per il conto con id {}", id, new ResponseStatusException(HttpStatus.NOT_FOUND));
			return null;
		}
		log.info("Transazioni recuperate");
		return transactionsBankAccount;
	}

	public ResponseTransaction getLastByIdBankAccount(int id) {
		if(bankAccountRepository.existsById(id)) {
			List<ResponseTransaction> transactionsBankAccount = getAllTransactions();
			ResponseTransaction lastTransaction = new ResponseTransaction();
			for(ResponseTransaction t : transactionsBankAccount) {
				if(t.getIdBankAccount()==id) {
					lastTransaction = t;
					if(t.getDateTransaction().after(lastTransaction.getDateTransaction())) {
						lastTransaction = t;
					}
				}
			}
			log.info("Ultima transazione recuperata");
			return lastTransaction;
		}
		else {
			log.error("Non esistono transazioni per il conto con id {}", id, new ResponseStatusException(HttpStatus.NOT_FOUND));
			return null;
		}
	}

	public ResponseTransaction withdraw(int amount, int id) {
		if(bankAccountRepository.existsById(id)) {
			BankAccount bankAccount = bankAccountRepository.findById(id).get();
			if(amount>0 && amount<=bankAccount.getBalance()) {
				Transaction t = new Transaction();
				t.setAmount(amount);
				t.setDateTransaction(new Date(System.currentTimeMillis()));
				t.setIdBankAccount(id);
				t.setType("withdraw");
				transactionRepository.save(t);
				ModelMapper mapper = new ModelMapper();
				ResponseTransaction responseTransaction = new ResponseTransaction();
				mapper.map(t, responseTransaction);
				RequestBankAccount rqba = new RequestBankAccount();
				rqba.setBalance(bankAccount.getBalance() - amount);
				baService.update(id, rqba);
				log.info("Operazione effettuata");
				return responseTransaction;
			}
		}
		log.error("Input invalido", new IllegalArgumentException());
		return null;
	}

	public ResponseTransaction updateTransaction(RequestTransaction rqt, int id) {
		if(transactionRepository.existsById(id)) {
			Transaction t = transactionRepository.findById(id).get();
			if(bankAccountRepository.existsById(t.getIdBankAccount())) {
				BankAccount bankAccount = bankAccountRepository.findById(t.getIdBankAccount()).get();
				if(t.getType().equalsIgnoreCase("deposit")) {
					if(rqt.getAmount()>0) {
						if(rqt.getAmount()<t.getAmount())
							bankAccount.setBalance(bankAccount.getBalance() - (t.getAmount() - rqt.getAmount()));
						else
							bankAccount.setBalance(bankAccount.getBalance() + (rqt.getAmount() - t.getAmount()));
						bankAccountRepository.save(bankAccount);
						t.setDateTransaction(new Date(System.currentTimeMillis()));
						t.setIdBankAccount(bankAccount.getIdAccount());
						t.setAmount(rqt.getAmount());
						transactionRepository.save(t);
						ModelMapper mapper = new ModelMapper();
						ResponseTransaction responseTransaction = new ResponseTransaction();
						mapper.map(t, responseTransaction);
						log.info("Modifica effettuata");
						return responseTransaction;
					}
					else {
						log.error("Input invalido", new IllegalArgumentException());	
						return null;
					}
				}
				else if(t.getType().equals("withdraw")) {
					if(rqt.getAmount()<=bankAccount.getBalance()) {
						if(rqt.getAmount()<t.getAmount())
							bankAccount.setBalance(bankAccount.getBalance() - (t.getAmount() - rqt.getAmount()));
						else
							bankAccount.setBalance(bankAccount.getBalance() + (rqt.getAmount() - t.getAmount()));
						bankAccountRepository.save(bankAccount);
						t.setAmount(rqt.getAmount());
						t.setIdBankAccount(bankAccount.getIdAccount());
						t.setDateTransaction(new Date(System.currentTimeMillis()));
						transactionRepository.save(t);
						ModelMapper mapper = new ModelMapper();
						ResponseTransaction responseTransaction = new ResponseTransaction();
						mapper.map(t, responseTransaction);
						log.info("Modifica effettuata");
						return responseTransaction;
					}
					else {
						log.error("Non è possibile prelevare una somma superiore al saldo", new ResponseStatusException(HttpStatus.BAD_REQUEST));
						return null;
					}
				}
			}
		}
		return null;
	}
}


