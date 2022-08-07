package com.example.bankingproject.core.utilities.entities;

import com.example.bankingproject.entities.Bank;

public class BankCreateSuccessResponse {
	private boolean success;
	private String message;
	private Bank bank;

	public BankCreateSuccessResponse(String message, Bank bank) {
		this.success = true;
		this.message = message;
		this.bank = bank;
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

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

}
