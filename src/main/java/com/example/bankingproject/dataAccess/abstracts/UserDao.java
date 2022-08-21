package com.example.bankingproject.dataAccess.abstracts;

import org.apache.ibatis.annotations.Mapper;

import com.example.bankingproject.entities.BankUser;

@Mapper
public interface UserDao {
	BankUser getUserByName(String username);

	void createUser(String username, String email, String password, String authorities);

	boolean getEnabledById(int id);

	void updateEnabled(int id, boolean enabled);
}
