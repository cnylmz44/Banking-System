package com.example.bankingproject.dataAccess.abstracts;

import org.apache.ibatis.annotations.Mapper;

import com.example.bankingproject.entities.BankAccount;

@Mapper
public interface BankAccountDao {

	void createBankAccount(int user_id, int bank_id, String number, String type, long creation_date,
			long last_updated_date);

	BankAccount getBankAccountByNumber(String number);

	void deleteBankAccount(String number);

	void updateBalance(String number, double balance);

	void updateLastModifiedDate(String number, long currentTimeMillis);

}
