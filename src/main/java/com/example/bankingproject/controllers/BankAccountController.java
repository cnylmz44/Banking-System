package com.example.bankingproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankingproject.business.abstracts.BankAccountService;
import com.example.bankingproject.core.utilities.entities.BankAccountCreateErrorResponse;
import com.example.bankingproject.core.utilities.entities.BankAccountCreateRequest;
import com.example.bankingproject.core.utilities.entities.BankAccountCreateSuccessResponse;
import com.example.bankingproject.entities.BankAccount;

@RestController
public class BankAccountController {

	@Autowired
	private BankAccountService bankAccountService;
	private BankAccount bankAccount;

	@RequestMapping(path = "/accounts", method = RequestMethod.POST)
	public ResponseEntity<?> createBankAccount(@RequestBody BankAccountCreateRequest request) {
		if (bankAccountService.isBankAccountValid(request)) {
			bankAccount = bankAccountService.createBankAccount(request.getBank_id(), request.getType());
			return ResponseEntity.ok().body(new BankAccountCreateSuccessResponse("Account Created", bankAccount));
		}

		else
			return ResponseEntity.unprocessableEntity()
					.body(new BankAccountCreateErrorResponse("Invalid Account Type : " + request.getType()));
	}
}
