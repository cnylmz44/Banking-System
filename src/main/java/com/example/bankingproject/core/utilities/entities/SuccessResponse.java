package com.example.bankingproject.core.utilities.entities;

public class SuccessResponse {
	private boolean success;
	private String message;

	public SuccessResponse(String message) {
		this.success = true;
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
