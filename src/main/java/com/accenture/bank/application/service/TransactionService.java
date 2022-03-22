package com.accenture.bank.application.service;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.accenture.bank.application.entity.BankAccount;
import com.accenture.bank.application.entity.Transaction;
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
	BankAccountRepository bankAccountRepository;

	public ResponseTransaction deposit(int id, int amount) {
		Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(id);
		if(optionalBankAccount.isPresent()) {
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
				BankAccount bankAccount = optionalBankAccount.get();
				bankAccount.setBalance(amount + bankAccount.getBalance());
				bankAccountRepository.save(bankAccount);
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
		List<ResponseTransaction> transactionsList = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();
		for(Transaction t : transactions) {
			ResponseTransaction responseTransaction = new ResponseTransaction();
			mapper.map(t, responseTransaction);
			transactionsList.add(responseTransaction);
		}
		if(transactions.isEmpty()) {
			log.error("Non è stato possibile recuperare la lista delle transazioni", new ResponseStatusException(HttpStatus.NOT_FOUND));
			return transactionsList;
		}
		else {

			log.info("Transazioni recuperate");
			return transactionsList;
		}
	}

	public ResponseTransaction getByIdTransaction(int id) {
		Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
		if(optionalTransaction.isPresent()) {
			Transaction transaction = optionalTransaction.get();
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
		List<Transaction> transactionList = transactionRepository.findAll();
		List<ResponseTransaction> transactionsBankAccount = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();
		for(Transaction t : transactionList)
			if(t.getIdBankAccount()==id) {
				ResponseTransaction responseTransaction = new ResponseTransaction();
				mapper.map(t, responseTransaction);
				transactionsBankAccount.add(responseTransaction);
			}
		if(transactionsBankAccount.isEmpty()) {
			log.error("Non esistono transazioni per il conto con id {}", id, new ResponseStatusException(HttpStatus.NOT_FOUND));
			return transactionsBankAccount;
		}
		log.info("Transazioni recuperate");
		return transactionsBankAccount;
	}

	public ResponseTransaction getLastByIdBankAccount(int id) {
		if(bankAccountRepository.existsById(id)) {
			List<Transaction> transactionsBankAccount = transactionRepository.findAll();
			ResponseTransaction lastTransaction = new ResponseTransaction();
			ModelMapper mapper = new ModelMapper();
			for(Transaction t : transactionsBankAccount) {
				if(t.getIdBankAccount()==id) {
					ResponseTransaction responseTransaction = new ResponseTransaction();
					mapper.map(t, responseTransaction);
					if(t.getDateTransaction().after(lastTransaction.getDateTransaction())) {
						lastTransaction = responseTransaction;
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

	public ResponseTransaction withdraw(int amount, int id) throws ParseException {
		Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(id);
		if(optionalBankAccount.isPresent()) {
			BankAccount bankAccount = optionalBankAccount.get();
			if(amount>0 && amount<=bankAccount.getBalance()) {
				Transaction t = new Transaction();
				t.setAmount(amount);
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
				Date date = new Date();
				String string = sdf.format(date);
				Date d = sdf.parse(string);
				t.setDateTransaction(d);
				t.setIdBankAccount(id);
				t.setType("withdraw");
				transactionRepository.save(t);
				ModelMapper mapper = new ModelMapper();
				ResponseTransaction responseTransaction = new ResponseTransaction();
				mapper.map(t, responseTransaction);
				bankAccount.setBalance(bankAccount.getBalance() - amount);
				bankAccountRepository.save(bankAccount);
				log.info("Operazione effettuata");
				return responseTransaction;
			}
			else {
				log.error("Input invalido", new IllegalArgumentException());
				return null;
			}
		}
		else {
			log.error("Conto non trovato");
		}
		return null;
	}

	public ResponseTransaction updateTransaction(RequestTransaction rqt, int id) {
		Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
		Optional<BankAccount> optionalBankAccount = bankAccountRepository.findById(rqt.getIdBankAccount());
		if(optionalTransaction.isPresent() && optionalBankAccount.isPresent()) {
			Transaction t = optionalTransaction.get();
			BankAccount bankAccount = optionalBankAccount.get();
			if(t.getType().equalsIgnoreCase("deposit")) {
				ResponseTransaction responseTransaction = updateTransactionDeposit(rqt, t, bankAccount);
				log.info("Modifica effettuata");
				return responseTransaction;
			}
			else if(t.getType().equals("withdraw")) {
				ResponseTransaction responseTransaction = updateTransactionWithdraw(rqt, t, bankAccount);
				log.info("Modifica effettuata");
				return responseTransaction;
			}
		}

		return null;
	}
	
	public ResponseTransaction updateTransactionDeposit(RequestTransaction rqt, Transaction t, BankAccount bankAccount) {
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
			return responseTransaction;
		}
		log.error("Impossibile depositare una somma pari a zero");
		return null;
	}

	public ResponseTransaction updateTransactionWithdraw(RequestTransaction rqt, Transaction t, BankAccount bankAccount) {
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
			return responseTransaction;
		}
		return null;
	}
}


