package com.example.bankingproject.business.abstracts;

import org.springframework.stereotype.Service;

import com.example.bankingproject.core.utilities.entities.BankAccountCreateRequest;
import com.example.bankingproject.entities.BankAccount;

@Service
public interface BankAccountService {

	boolean isBankAccountValid(BankAccountCreateRequest request);

	BankAccount createBankAccount(int bank_id, String type);

}
