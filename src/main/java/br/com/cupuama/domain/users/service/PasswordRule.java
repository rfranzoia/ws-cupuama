package br.com.cupuama.domain.users.service;

import br.com.cupuama.domain.users.dto.UserDTO;
import br.com.cupuama.domain.users.entity.User;

public interface PasswordRule {

	RuleResult processRule(User user, UserDTO dto);
	RuleResult getResult();
	String getMessage();
	
}
