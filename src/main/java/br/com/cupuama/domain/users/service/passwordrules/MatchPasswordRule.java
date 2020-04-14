package br.com.cupuama.domain.users.service.passwordrules;

import org.apache.commons.lang3.StringUtils;

import br.com.cupuama.domain.users.dto.UserDTO;
import br.com.cupuama.domain.users.entity.User;

public class MatchPasswordRule implements PasswordRule {

	private RuleResult result;
	
	public MatchPasswordRule() {
	}
	
	@Override
	public RuleResult processRule(final User user, final UserDTO dto) {
		return result = (!StringUtils.equals(user.getPassword(), dto.getPassword()))? RuleResult.FAIL: RuleResult.SUCCESS;
	}
	
	@Override
	public RuleResult getResult() {
		return result;
	}
	
	@Override
	public String getMessage() {
		return String.format("MatchPasswordRule: Old password doesn't match!");
	}

}
