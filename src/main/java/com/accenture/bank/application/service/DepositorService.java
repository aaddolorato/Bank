package com.accenture.bank.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.accenture.bank.application.entity.Depositor;
import com.accenture.bank.application.model.RequestDepositor;
import com.accenture.bank.application.model.ResponseDepositor;
import com.accenture.bank.application.repository.DepositorRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Service
public class DepositorService {

	@Autowired
	private DepositorRepository depositorRepository;

	public ResponseDepositor getById(int id) {
		Optional<Depositor> optionalDepositor = depositorRepository.findById(id);
		if(optionalDepositor.isPresent()) {
			ModelMapper mapper = new ModelMapper();
			Depositor depositor = optionalDepositor.get();
			ResponseDepositor responseDepositor = new ResponseDepositor();
			mapper.map(depositor, responseDepositor);
			log.info("Dettagli correntista. \n" + responseDepositor);
			return responseDepositor;
		}
		else {
			log.error("Non è stato trovato nessun correntista con id " + id + ".");
			return null;
		}

	}

	public List<ResponseDepositor> getAllDepositors(){
		List<Depositor> depositorsList = depositorRepository.findAll();
		ModelMapper mapper = new ModelMapper();
		List<ResponseDepositor> responseDepositorsList = new ArrayList<>();
		for(Depositor depositor : depositorsList) {
			ResponseDepositor responseDepositor = new ResponseDepositor();
			mapper.map(depositor, responseDepositor);
			responseDepositorsList.add(responseDepositor);
		}
		if(depositorsList.isEmpty()) {
			log.error("Non è stato possibile recuperare la lista dei correntisti.", new ResponseStatusException(HttpStatus.NOT_FOUND));
			return responseDepositorsList;
		}
		else {
			
			log.info("Lista correntisti recuperata.");
			return responseDepositorsList;
		}
	}

	public ResponseDepositor delete(int id) {
		Optional<Depositor> optionalDepositor = depositorRepository.findById(id);
		if(optionalDepositor.isPresent()) {
			Depositor depositor = optionalDepositor.get();
			depositorRepository.delete(depositor);
			ModelMapper mapper = new ModelMapper();
			ResponseDepositor responseDepositor = new ResponseDepositor();
			mapper.map(depositor, responseDepositor);
			log.info("Correntista cancellato con successo.");
			return responseDepositor;
		}
		else {
			log.error("Non è stato trovato un correntista con id {}", id, new ResponseStatusException(HttpStatus.NOT_FOUND));
			return null;
		}
	}

	public ResponseDepositor getByCf(String codFisc) {
		List<Depositor> depositorsList = depositorRepository.findAll();
		ModelMapper mapper = new ModelMapper();
		for(Depositor depositor : depositorsList)
			if(depositor.getCodFisc().equals(codFisc)) {
				ResponseDepositor responseDepositor = new ResponseDepositor();
				mapper.map(depositor, responseDepositor);
				log.info("Correntista con codice fiscale " + codFisc + " recuperato.");
				return responseDepositor;
			}
		log.error("Non è stato trovato un correntista con codice fiscale {}", codFisc, new ResponseStatusException(HttpStatus.NOT_FOUND));
		return null;

	}

	public ResponseDepositor addDepositor(RequestDepositor requestDepositor) {
		Depositor depositor = new Depositor();
		ModelMapper mapper = new ModelMapper();
		mapper.map(requestDepositor, depositor);
		if(existingCodFisc(depositor.getCodFisc())==0) {
			depositorRepository.save(depositor);
			ResponseDepositor responseDepositor = new ResponseDepositor();
			mapper.map(depositor, responseDepositor);
			log.info("Correntista aggiunto con successo.");
			return responseDepositor;
		}
		else {
			log.error("Il correntista è già presente nel sistema.");
			return null;
		}
	}

	public ResponseDepositor updateDepositor(int id, RequestDepositor requestDepositor) {
		if(depositorRepository.existsById(id)) {
			Depositor depositor = new Depositor();
			ModelMapper mapper = new ModelMapper();
			mapper.map(requestDepositor, depositor);
			depositor.setIdDepositor(id);
			depositorRepository.save(depositor);
			ResponseDepositor responseDepositor = new ResponseDepositor();
			mapper.map(depositor, responseDepositor);
			log.info("Correntista aggiornato con successo.");
			return responseDepositor;
		}
		else {
			log.error("Non è stato trovato un correntista con id {}", id, new ResponseStatusException(HttpStatus.NOT_FOUND));
			return null;
		}
	}


	public int existingCodFisc(String codFisc) {
		return depositorRepository.countByCodFisc(codFisc);
	}

}
