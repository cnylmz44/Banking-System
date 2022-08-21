package com.example.bankingproject.core.utilities.security.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.bankingproject.business.concretes.UserManager;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class JWTSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(new UserManager()).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests().antMatchers("/auth").permitAll()
				.antMatchers(HttpMethod.POST, "/register").permitAll().antMatchers("/banks").hasAuthority("CREATE_BANK")
				.antMatchers(HttpMethod.GET, "/accounts/**").permitAll().antMatchers(HttpMethod.POST, "/accounts")
				.hasAuthority("CREATE_ACCOUNT").antMatchers(HttpMethod.DELETE, "/accounts/**")
				.hasAuthority("REMOVE_ACCOUNT").antMatchers(HttpMethod.PATCH, "/accounts/**").permitAll()
				.antMatchers(HttpMethod.PUT, "/accounts/**").permitAll().antMatchers(HttpMethod.PATCH, "/users/**")
				.hasAuthority("ACTIVATE_DEACTIVATE_USER").anyRequest().authenticated().and().formLogin();

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}