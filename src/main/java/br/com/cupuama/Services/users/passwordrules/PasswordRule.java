package br.com.cupuama.Services.users.passwordrules;

import br.com.cupuama.controller.users.dto.UserDTO;
import br.com.cupuama.domain.users.User;
import br.com.cupuama.enums.RuleResult;

public interface PasswordRule {

	RuleResult processRule(User user, UserDTO dto);
	RuleResult getResult();
	String getMessage();
	
}
