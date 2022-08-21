package com.example.bankingproject.business.concretes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.bankingproject.dataAccess.abstracts.UserDao;
import com.example.bankingproject.entities.BankUser;

@Component
public class UserManager implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	private BankUser bankUser;

	// Connect to Database
	// Select User
	// Set User
	// Create Bank User
	// Set User to Bank User
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingdatabase?useSSL=false",
					"root", "1234");
			String query = "SELECT * FROM bank_users WHERE username = \"" + username + "\"";
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("username");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String authoritiesString = rs.getString("authorities");
				String[] parts = authoritiesString.split(",");
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				for (String authority : parts) {
					SimpleGrantedAuthority sga = new SimpleGrantedAuthority(authority);
					authorities.add(sga);
				}

				bankUser = new BankUser(username, password, true, true, true, true, authorities, id, email);

				return bankUser;
			} else {
				throw new UsernameNotFoundException(username + " Not Found");
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// Create User( Default "CREATE_ACCOUNT" Authority )
	public void createUser(String username, String email, String password) {
		userDao.createUser(username, email, password, "CREATE_ACCOUNT");
	}

	// Change Enability
	public void changeEnabled(int id, boolean enabled) {
		userDao.updateEnabled(id, enabled);
	}

	// Get Enabled Value
	public boolean getEnabled(int id) {
		return userDao.getEnabledById(id);
	}

}
