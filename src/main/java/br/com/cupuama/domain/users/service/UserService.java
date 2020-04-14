package br.com.cupuama.domain.users.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.users.dto.UserDTO;
import br.com.cupuama.domain.users.entity.User;
import br.com.cupuama.domain.users.mapper.UserMapper;
import br.com.cupuama.domain.users.repository.UserRepository;
import br.com.cupuama.domain.users.service.passwordrules.PasswordRule;
import br.com.cupuama.domain.users.service.passwordrules.PasswordRuleFactory;
import br.com.cupuama.domain.users.service.passwordrules.RuleResult;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.exception.InvalidRequestException;
import br.com.cupuama.util.DefaultService;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some user specific things.
 * <p/>
 */
@Service
public class UserService extends DefaultService<User, String> {

	public UserService(final UserRepository userRepository) {
		super(userRepository);
	}

	@Transactional
	public UserDTO create(UserDTO dto) throws ConstraintsViolationException {
		User user = UserMapper.makeUser(dto);
		return UserMapper.makeDTO(create(user));
	}
	
	/**
	 * Update a users information.
	 *
	 * @param userLogin
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final String userLogin, final UserDTO dto) throws EntityNotFoundException {
		User user = findByIdChecked(userLogin);
		user.setName(dto.getName());
		user.getAudit().setDateUpdated(ZonedDateTime.now());
	}
	
	/**
	 * Update a users password.
	 *
	 * @param userLogin
	 * @param dto 
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void updatePassword(final String userLogin, final UserDTO dto) throws EntityNotFoundException, InvalidRequestException {
		User user = findByIdChecked(userLogin);
		
		validatePassword(dto, user);
		
		user.setPassword(dto.getNewPassword());
		user.getAudit().setDateUpdated(ZonedDateTime.now());
	}

	/**
	 * Validate given password
	 * 
	 * @param dto
	 * @param user
	 */
	private void validatePassword(final UserDTO dto, User user) {
		List<InvalidRequestException> errors = new ArrayList<>();
		
		// Password validation action
		final Predicate<PasswordRule> passwordRule = rule -> rule.processRule(user, dto) == RuleResult.FAIL;
		final Consumer<PasswordRule> resultChecker = rule -> {
			if (rule.getResult() == RuleResult.FAIL)
				errors.add(new InvalidRequestException(rule.getMessage()));
		};
		
		// Acquire all existing password validations and test them
		PasswordRuleFactory.instance()
			.getPasswordRules().stream()
				.filter(passwordRule)
				.peek(resultChecker)
				.anyMatch(rule -> true);
		
		if (!errors.isEmpty()) {
			throw errors.get(0);
		}
	}

	/**
	 * Find all users by name.
	 *
	 * @param name
	 */
	public List<User> findByName(final String name) {
		return ((UserRepository) repository).findByNameLike(name + "%");
	}

}
