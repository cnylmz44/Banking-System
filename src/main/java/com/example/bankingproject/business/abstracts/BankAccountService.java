package com.example.bankingproject.business.abstracts;

import org.springframework.stereotype.Service;

import com.example.bankingproject.core.utilities.entities.BankAccountCreateRequest;
import com.example.bankingproject.entities.BankAccount;

@Service
public interface BankAccountService {

	boolean isBankAccountValid(BankAccountCreateRequest request);

	BankAccount createBankAccount(int bank_id, String type, String number, int user_id);

	BankAccount getBankAccount(String number);

	String generateBankAccountNumber(int number);

	void deleteBankAccount(String number);

	void deposit(String number, double amount);

	void withdraw(String number, double amount);

	double calculateTransactionFee(String type, int senderBankId, int receiverBankId);

	void updateLastUpdatedDate(String number);

}
