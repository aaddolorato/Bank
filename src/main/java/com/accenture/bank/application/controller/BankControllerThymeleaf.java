package com.accenture.bank.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.accenture.bank.application.entity.Bank;
import com.accenture.bank.application.model.RequestBank;
import com.accenture.bank.application.model.ResponseBank;
import com.accenture.bank.application.service.BankService;

@Controller
public class BankControllerThymeleaf {

	@Autowired
	BankService bankService;
	
	@GetMapping("/")
	public String home() {
		return "homeBank";
	}
	
	@GetMapping("/banksList")
	public String getBanks(Model model){
		List<ResponseBank> banksList = bankService.getAllBanks();
		model.addAttribute("banksList", banksList);
		if(banksList==null) {
			return null;
		}
		return "bank";
	}
	
	@GetMapping("/banks/saveNew")
	public String showSaveForm(Model model) {
		Bank bank = new Bank();
		model.addAttribute("bank", bank);
		return "saveBank";
	}
	
	@PostMapping("/banks/saveNew")
	public String saveBank(@ModelAttribute("bank") RequestBank requestBank) {
		bankService.saveBank(requestBank);
		return "homeBank";
	}
	
}
