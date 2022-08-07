package com.example.bankingproject.business.abstracts;

import org.springframework.stereotype.Service;

import com.example.bankingproject.entities.Bank;

@Service
public interface BankService {

	boolean isBankExist(String name);

	Bank createBank(String name);

}
