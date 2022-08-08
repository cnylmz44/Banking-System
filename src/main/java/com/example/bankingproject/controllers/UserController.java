package com.example.bankingproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankingproject.business.abstracts.UserService;
import com.example.bankingproject.core.utilities.entities.UserChangeEnabledRequest;
import com.example.bankingproject.core.utilities.entities.UserLoginRequest;
import com.example.bankingproject.core.utilities.entities.UserRegisterRequest;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestBody UserRegisterRequest request) {
		return null;
	}

	@RequestMapping(path = "/auth", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
		return null;
	}

	@RequestMapping(path = "/users/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<?> changeEnabled(@PathVariable(name = "id") int id,
			@RequestBody UserChangeEnabledRequest request) {
		return null;
	}

}
