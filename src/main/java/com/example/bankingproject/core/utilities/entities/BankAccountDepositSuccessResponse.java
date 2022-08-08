package com.example.bankingproject.core.utilities.entities;

public class BankAccountDepositSuccessResponse {
	private boolean success;
	private String message;
	private double balance;

	private BankAccountDepositSuccessResponse(String message, double balance) {
		this.success = true;
		this.message = message;
		this.balance = balance;
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

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
