package br.com.cupuama.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;

import br.com.cupuama.domain.users.Users;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.services.users.UserService;

public class UserFactory {

	@Autowired
	private UserService userService;
	
	private static UserFactory instance = new UserFactory();
	
	private UserFactory() {
	}
	
	public static UserFactory instance() {
		return instance;
	}
	
	public Users getUser(String login, String password) throws BadCredentialsException {
		try {
			Users user = userService.find(login);
			if (!StringUtils.equals(user.getPassword(), password)) {
				throw new BadCredentialsException("Invalid Login/Password!"); 
			}
			return user;
		} catch (EntityNotFoundException e) {
			throw new BadCredentialsException("Invalid Login/Password!");
		}
	}
}
