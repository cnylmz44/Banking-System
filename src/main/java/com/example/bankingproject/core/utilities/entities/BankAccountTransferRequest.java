package com.example.bankingproject.core.utilities.entities;

public class BankAccountTransferRequest {
	private double amount;
	private String receiverAccountId;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getReceiverAccountId() {
		return receiverAccountId;
	}

	public void setReceiverAccountId(String receiverAccountId) {
		this.receiverAccountId = receiverAccountId;
	}

}
