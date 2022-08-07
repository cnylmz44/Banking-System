package com.example.bankingproject.entities;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("BankAccount")
public class BankAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int user_id;
	private int bank_id;
	private int number;
	private String type;
	private double balance;
	private long creation_date;
	private long last_update_date;
	private boolean is_deleted;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getBank_id() {
		return bank_id;
	}

	public void setBank_id(int bank_id) {
		this.bank_id = bank_id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public long getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(long creation_date) {
		this.creation_date = creation_date;
	}

	public long getLast_update_date() {
		return last_update_date;
	}

	public void setLast_update_date(long last_update_date) {
		this.last_update_date = last_update_date;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
