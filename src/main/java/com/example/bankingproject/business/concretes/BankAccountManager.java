package com.example.bankingproject.business.concretes;

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

	@Override
	public boolean isBankAccountValid(BankAccountCreateRequest request) {
		if (request.getType().equals("TRY") || request.getType().equals("USD") || request.getType().equals("GAU"))
			return true;
		else
			return false;
	}

	@Override
	public BankAccount createBankAccount(int bank_id, String type) {
		bankAccountDao.createBankAccount(1, bank_id, 1334567890, type, System.currentTimeMillis(),
				System.currentTimeMillis());
		bankAccount = bankAccountDao.getBankAccountByNumber(1334567890);
		return bankAccount;
	}

}
