package com.example.bankingproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankingproject.business.abstracts.BankService;
import com.example.bankingproject.core.utilities.entities.BankCreateRequest;
import com.example.bankingproject.core.utilities.entities.BankCreateSuccessResponse;
import com.example.bankingproject.core.utilities.entities.ErrorResponse;
import com.example.bankingproject.entities.Bank;

@RestController
public class BankController {

	@Autowired
	private BankService bankService;
	private Bank bank;

	@RequestMapping(path = "/banks", method = RequestMethod.POST)
	public ResponseEntity<?> createBank(@RequestBody BankCreateRequest request) {
		if (bankService.isBankExist(request.getName())) {
			return ResponseEntity.unprocessableEntity()
					.body(new ErrorResponse("Given Name Already Used : " + request.getName()));
		}

		else {
			bank = bankService.createBank(request.getName());
			return ResponseEntity.ok().body(new BankCreateSuccessResponse("Created Successfully!", bank));
		}
	}

}
