package com.example.bankingproject.core.utilities.entities;

public class BankAccountCreateErrorResponse {
	private boolean success;
	private String message;

	public BankAccountCreateErrorResponse(String message) {
		this.success = false;
		this.message = message;
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
}
