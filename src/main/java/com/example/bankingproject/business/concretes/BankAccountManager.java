package com.example.bankingproject.business.concretes;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.bankingproject.business.abstracts.BankAccountService;
import com.example.bankingproject.core.utilities.entities.BankAccountCreateRequest;
import com.example.bankingproject.dataAccess.abstracts.BankAccountDao;
import com.example.bankingproject.entities.BankAccount;

@Component
public class BankAccountManager implements BankAccountService {

	@Autowired
	private BankAccountDao bankAccountDao;
	private BankAccount bankAccount;

	// Check Bank Account Type
	@Override
	public boolean isBankAccountValid(BankAccountCreateRequest request) {
		if (request.getType().equals("TRY") || request.getType().equals("USD") || request.getType().equals("GAU"))
			return true;
		else
			return false;
	}

	// Create Bank Account
	// Return Bank Account
	@Override
	public BankAccount createBankAccount(int bank_id, String type, String number, int user_id) {
		bankAccountDao.createBankAccount(user_id, bank_id, number, type, System.currentTimeMillis(),
				System.currentTimeMillis());
		bankAccount = bankAccountDao.getBankAccountByNumber(number);
		return bankAccount;
	}

	// Get Bank Account Details
	@Override
	public BankAccount getBankAccount(String number) {
		return bankAccountDao.getBankAccountByNumber(number);
	}

	// Generate 10 Digits Random String Number
	public String generateBankAccountNumber(int digitNumber) {
		String generatedNumber = "";
		Random random = new Random();

		// Generate every digits randomly
		for (int i = 0; i < digitNumber; i++) {
			generatedNumber = generatedNumber + Integer.toString(random.nextInt(10));
		}

		// Check if generated account number is exist
		if (getBankAccount(generatedNumber) != null)
			return generateBankAccountNumber(digitNumber);
		else
			return generatedNumber;

	}

	// Soft Delete Bank Account
	@Override
	public void deleteBankAccount(String number) {
		bankAccountDao.deleteBankAccount(number);

	}

	// Calculate Deposited Amount
	// Update Balance
	@Override
	public void deposit(String number, double amount) {
		double totalAmount = bankAccountDao.getBankAccountByNumber(number).getBalance() + amount;
		bankAccountDao.updateBalance(number, totalAmount);
	}

	// Calculate Withdrawed Balance
	// Update Balance
	@Override
	public void withdraw(String number, double amount) {
		double totalAmount = bankAccountDao.getBankAccountByNumber(number).getBalance() - amount;
		bankAccountDao.updateBalance(number, totalAmount);
	}

	// If the sending account and the receiving account belong to other banks;
	// If the sending account is in TL, 3 TL is charged,
	// and if it is in dollars, an additional EFT fee is charged.
	// If the sending account is gold, there will be no deductions.
	@Override
	public double calculateTransactionFee(String type, int senderBankId, int receiverBankId) {
		if (senderBankId == receiverBankId)
			return 0;
		else if (type.equals("TL"))
			return 3;
		else if (type.equals("USD"))
			return 1;
		else
			return 0;
	}

	// Update Last Modified Date
	@Override
	public void updateLastUpdatedDate(String number) {
		bankAccountDao.updateLastModifiedDate(number, System.currentTimeMillis());
	}
}
