package br.com.cupuama.services.users.passwordrules;

import org.apache.commons.lang3.StringUtils;

import br.com.cupuama.controller.users.dto.UserDTO;
import br.com.cupuama.domain.users.Users;
import br.com.cupuama.enums.RuleResult;

public class NotEmptyPasswordRule implements PasswordRule {

	private RuleResult result;
	
	@Override
	public RuleResult processRule(Users user, UserDTO dto) {
		return result = (StringUtils.isEmpty(dto.getNewPassword()))? RuleResult.FAIL: RuleResult.SUCCESS;
	}
	
	@Override
	public RuleResult getResult() {
		return result;
	}
	
	@Override
	public String getMessage() {
		return String.format("NotEmptyPasswordRule: Passwords cannot be Null or Empty");
	}

}
