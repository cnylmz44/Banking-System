package com.example.bankingproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankingproject.business.concretes.UserManager;
import com.example.bankingproject.core.utilities.entities.ErrorResponse;
import com.example.bankingproject.core.utilities.entities.LoginRequest;
import com.example.bankingproject.core.utilities.entities.LoginResponse;
import com.example.bankingproject.core.utilities.entities.SuccessResponse;
import com.example.bankingproject.core.utilities.entities.UserChangeEnabledRequest;
import com.example.bankingproject.core.utilities.entities.UserRegisterRequest;
import com.example.bankingproject.core.utilities.security.JWT.JWTTokenUtil;

@RestController
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTTokenUtil jwtTokenUtil;
	@Autowired
	private UserManager userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestBody UserRegisterRequest request) {
		try {
			String password = passwordEncoder.encode(request.getPassword());
			userDetailsService.createUser(request.getUsername(), request.getEmail(), password);
			return ResponseEntity.ok().body(new SuccessResponse("User is created succesfully"));
		} catch (Exception e) {
			return ResponseEntity.unprocessableEntity().body(new ErrorResponse("User is already exist"));
		}
	}

	@PostMapping("/auth")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.badRequest().body("Bad credentials");
		} catch (DisabledException e) {
			return ResponseEntity.badRequest().body("Disabled Exception");
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		LoginResponse resp = new LoginResponse();
		resp.setStatus("success");
		resp.setToken(token);
		return ResponseEntity.ok().body(resp);
	}

	@RequestMapping(path = "/users/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<?> changeEnabled(@PathVariable(name = "id") int id,
			@RequestBody UserChangeEnabledRequest request) {
		try {
			if (userDetailsService.getEnabled(id) == request.isEnabled()) {

				return ResponseEntity.unprocessableEntity()
						.body(new ErrorResponse("User enabled is already " + Boolean.toString(request.isEnabled())));
			}

			else {
				userDetailsService.changeEnabled(id, request.isEnabled());
				return ResponseEntity.ok().body(new SuccessResponse("User enabled is updated succesfully"));
			}

		} catch (Exception e) {
			return ResponseEntity.unprocessableEntity().body(new ErrorResponse("User is not exist"));
		}

	}

}
