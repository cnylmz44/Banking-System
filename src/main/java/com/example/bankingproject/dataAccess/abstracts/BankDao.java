package com.example.bankingproject.dataAccess.abstracts;

import org.apache.ibatis.annotations.Mapper;

import com.example.bankingproject.entities.Bank;

@Mapper
public interface BankDao {

	Bank getBankByName(String name);

	void createBank(String name);

}
