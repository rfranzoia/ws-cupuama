package br.com.cupuama.domain.users.service;

import org.apache.commons.lang3.StringUtils;

import br.com.cupuama.domain.users.dto.UserDTO;
import br.com.cupuama.domain.users.entity.User;

public class NotEmptyPasswordRule implements PasswordRule {

	private RuleResult result;
	
	@Override
	public RuleResult processRule(User user, UserDTO dto) {
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
