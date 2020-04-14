package br.com.cupuama.services.users.passwordrules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PasswordRuleFactory {

	private List<PasswordRule> rules = new ArrayList<>();
	
	private static PasswordRuleFactory instance = new PasswordRuleFactory();
	
	private PasswordRuleFactory() {
		rules.addAll(Arrays.asList(new MatchPasswordRule(),
									new NotEmptyPasswordRule()));
	}
	
	public static PasswordRuleFactory instance() {
		return instance;
	}
	
	public List<PasswordRule> getPasswordRules() {
		return rules;
	}
}
