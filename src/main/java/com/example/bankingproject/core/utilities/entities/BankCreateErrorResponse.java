package com.example.bankingproject.core.utilities.entities;

public class BankCreateErrorResponse {
	private boolean success;
	private String message;

	public BankCreateErrorResponse(String message) {
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
