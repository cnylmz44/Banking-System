package com.example.bankingproject.core.utilities.entities;

import com.example.bankingproject.entities.BankAccount;

public class BankAccountCreateSuccessResponse {
	private boolean success;
	private String message;
	private BankAccount bankAccount;

	public BankAccountCreateSuccessResponse(String message, BankAccount bankAccount) {
		this.success = true;
		this.message = message;
		this.bankAccount = bankAccount;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

}
