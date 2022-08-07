package com.example.bankingproject.dataAccess.abstracts;

import org.apache.ibatis.annotations.Mapper;

import com.example.bankingproject.entities.BankAccount;

@Mapper
public interface BankAccountDao {

	/*
	 * INSERT INTO bank_accounts(user_id, bank_id, number, type, creation_date,
	 * last_updated_date)
	 * VALUES(#{user_id},#{bank_id},#{number},#{type},#{creation_date},#{
	 * last_updated_date})
	 */
	void createBankAccount(int user_id, int bank_id, int number, String type, long creation_date,
			long last_updated_date);

	BankAccount getBankAccountByNumber(int number);

}
