package com.example.bankingproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.example.bankingproject.business.abstracts.BankAccountService;
import com.example.bankingproject.core.utilities.entities.BankAccountCreateRequest;
import com.example.bankingproject.core.utilities.entities.BankAccountCreateSuccessResponse;
import com.example.bankingproject.core.utilities.entities.BankAccountDepositRequest;
import com.example.bankingproject.core.utilities.entities.BankAccountDetailsSuccessResponse;
import com.example.bankingproject.core.utilities.entities.BankAccountTransferRequest;
import com.example.bankingproject.core.utilities.entities.ErrorResponse;
import com.example.bankingproject.core.utilities.entities.SuccessResponse;
import com.example.bankingproject.core.utilities.exchange.abstracts.Exchanger;
import com.example.bankingproject.entities.BankAccount;
import com.example.bankingproject.entities.BankUser;

@RestController
@EnableCaching
@CrossOrigin(origins = { "http://localhost:4200" })
public class BankAccountController {

	@Autowired
	private BankAccountService bankAccountService;
	private BankAccount bankAccount;
	private BankUser bankUser;
	@Autowired
	private Exchanger exchanger;
	private KafkaTemplate<String, String> producer;

	@RequestMapping(path = "/accounts", method = RequestMethod.POST)
	public ResponseEntity<?> createBankAccount(@RequestBody BankAccountCreateRequest request) {
		try {
			// Get Active Bank User
			bankUser = (BankUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (bankUser == null)
				return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>("User cannot be found", HttpStatus.NOT_FOUND);
		}

		if (bankAccountService.isBankAccountValid(request)) {
			String bankAccountNumber = bankAccountService.generateBankAccountNumber(10);
			bankAccount = bankAccountService.createBankAccount(request.getBank_id(), request.getType(),
					bankAccountNumber, bankUser.getId());
			return ResponseEntity.ok()
					.body(new BankAccountCreateSuccessResponse("Account is created succesfully", bankAccount));
		}

		else
			return ResponseEntity.unprocessableEntity()
					.body(new ErrorResponse("Invalid Account Type : " + request.getType()));
	}

	@RequestMapping(path = "/accounts/{number}", method = RequestMethod.GET)
	public ResponseEntity<?> getBankAccountDetails(@PathVariable(name = "number") String number,
			WebRequest webRequest) {
		// Check Bank User
		try {
			bankUser = (BankUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (bankUser == null || bankAccountService.getBankAccount(number).getUser_id() != bankUser.getId())
				return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>("User cannot be found", HttpStatus.NOT_FOUND);
		}

		bankAccount = bankAccountService.getBankAccount(number);
		if (bankAccount == null)
			return new ResponseEntity<>("Account cannot be found", HttpStatus.NOT_FOUND);
		else {
			// Cache Operation
			if (webRequest.checkNotModified(bankAccount.getLast_update_date()))
				return null;
			else
				return ResponseEntity.ok().lastModified(bankAccount.getLast_update_date())
						.body(new BankAccountDetailsSuccessResponse(bankAccount));
		}

	}

	@RequestMapping(path = "/accounts/{number}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBankAccount(@PathVariable(name = "number") String number) {
		bankAccount = bankAccountService.getBankAccount(number);
		if (bankAccount == null)
			return ResponseEntity.badRequest().body(new ErrorResponse("Account cannot be found"));
		else {
			bankAccountService.deleteBankAccount(number);
			bankAccountService.updateLastUpdatedDate(number);
			return ResponseEntity.ok().body(new SuccessResponse("Account is deleted succesfully"));
		}
	}

	@RequestMapping(path = "/accounts/{number}", method = RequestMethod.PATCH)
	public ResponseEntity<?> deposit(@PathVariable(name = "number") String number,
			@RequestBody BankAccountDepositRequest request) {
		// Check Bank User
		try {
			bankUser = (BankUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (bankUser == null || bankAccountService.getBankAccount(number).getUser_id() != bankUser.getId())
				return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>("User cannot be found", HttpStatus.NOT_FOUND);
		}

		bankAccount = bankAccountService.getBankAccount(number);
		if (bankAccount == null)
			return ResponseEntity.badRequest().body(new ErrorResponse("Account cannot be found"));
		else {
			bankAccountService.deposit(number, request.getAmount());
			bankAccountService.updateLastUpdatedDate(number);
			bankAccount = bankAccountService.getBankAccount(number);
			// Log Operation
			producer.send("logs", number + " deposit amount: " + request.getAmount() + bankAccount.getType());
			return ResponseEntity.ok().body(new BankAccountDetailsSuccessResponse(bankAccount));
		}
	}

	@RequestMapping(path = "/accounts/{number}", method = RequestMethod.PUT)
	public ResponseEntity<?> transfer(@PathVariable(name = "number") String number,
			@RequestBody BankAccountTransferRequest request) {
		// Check Bank User
		try {
			bankUser = (BankUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (bankUser == null || bankAccountService.getBankAccount(number).getUser_id() != bankUser.getId())
				return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			return new ResponseEntity<>("User cannot be found", HttpStatus.NOT_FOUND);
		}

		BankAccount senderAccount = bankAccountService.getBankAccount(number);
		BankAccount receiverAccount = bankAccountService.getBankAccount(request.getReceiverAccountId());
		// Account Accounts Exist
		if (senderAccount == null)
			return ResponseEntity.badRequest().body(new ErrorResponse("Sender Account cannot be found"));
		if (receiverAccount == null)
			return ResponseEntity.badRequest().body(new ErrorResponse("Receiver Account cannot be found"));

		// Balance Check
		double transactionFee = bankAccountService.calculateTransactionFee(senderAccount.getType(),
				senderAccount.getBank_id(), receiverAccount.getBank_id());
		if (senderAccount.getBalance() < request.getAmount() + transactionFee)
			return ResponseEntity.badRequest().body(new ErrorResponse("Insufficient Balance"));

		// Transferable Accounts
		else {
			double exchangedAmount = exchanger.exchange(senderAccount.getType(), receiverAccount.getType(),
					request.getAmount());
			bankAccountService.withdraw(number, request.getAmount() + transactionFee);
			bankAccountService.deposit(request.getReceiverAccountId(), exchangedAmount);
			bankAccountService.updateLastUpdatedDate(senderAccount.getNumber());
			bankAccountService.updateLastUpdatedDate(receiverAccount.getNumber());
			// Log Operation
			producer.send("logs", senderAccount.getNumber() + " transferred amount : " + request.getAmount()
					+ senderAccount.getType() + " transferred acount : " + receiverAccount.getType());
			return ResponseEntity.ok().body(new SuccessResponse("Transferred Succesfully"));
		}

	}

}
