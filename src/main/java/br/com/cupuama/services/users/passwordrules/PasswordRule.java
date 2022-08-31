package br.com.cupuama.services.users.passwordrules;

import br.com.cupuama.controller.users.dto.UserDTO;
import br.com.cupuama.domain.users.Users;
import br.com.cupuama.enums.RuleResult;

public interface PasswordRule {

	RuleResult processRule(Users user, UserDTO dto);
	RuleResult getResult();
	String getMessage();
	
}
