package com.example.bankingproject.core.utilities.entities;

import com.example.bankingproject.entities.BankAccount;

public class BankAccountDetailsSuccessResponse {
	private boolean success;
	private BankAccount bankAccount;

	public BankAccountDetailsSuccessResponse(BankAccount bankAccount) {
		this.success = true;
		this.bankAccount = bankAccount;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

}
