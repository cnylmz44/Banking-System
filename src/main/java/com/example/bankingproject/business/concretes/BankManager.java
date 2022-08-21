package com.example.bankingproject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.bankingproject.business.abstracts.BankService;
import com.example.bankingproject.dataAccess.abstracts.BankDao;
import com.example.bankingproject.entities.Bank;

@Component
public class BankManager implements BankService {
	@Autowired
	private BankDao bankDao;
	private Bank bank;

	// Check Bank is Exist
	@Override
	public boolean isBankExist(String name) {
		bank = bankDao.getBankByName(name);
		if (bank == null)
			return false;
		else
			return true;
	}

	// Create Bank
	// Return Bank Details
	@Override
	public Bank createBank(String name) {
		// TODO Auto-generated method stub
		bankDao.createBank(name);
		bank = bankDao.getBankByName(name);
		return bank;
	}

}
