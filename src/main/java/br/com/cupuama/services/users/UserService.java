package br.com.cupuama.services.users;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.controller.persons.mapper.PersonMapper;
import br.com.cupuama.controller.users.dto.UserDTO;
import br.com.cupuama.controller.users.mapper.UsersMapper;
import br.com.cupuama.domain.users.Users;
import br.com.cupuama.domain.users.repository.UserRepository;
import br.com.cupuama.enums.RuleResult;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.exception.InvalidRequestException;
import br.com.cupuama.services.users.passwordrules.PasswordRule;
import br.com.cupuama.services.users.passwordrules.PasswordRuleFactory;
import br.com.cupuama.util.DefaultService;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some user specific things.
 * <p/>
 */
@Service
public class UserService extends DefaultService<Users, String> {

	public UserService(final UserRepository userRepository) {
		super(userRepository);
	}

	@Transactional
	public UserDTO create(UserDTO dto) throws ConstraintsViolationException {
		Users user = UsersMapper.makeUser(dto);
		return UsersMapper.makeDTO(create(user));
	}
	
	/**
	 * Update a users information.
	 *
	 * @param userLogin
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final String userLogin, final UserDTO dto) throws EntityNotFoundException {
		Users user = findByIdChecked(userLogin);
		user.setPerson(PersonMapper.makePerson(dto.getPerson()));
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
		Users user = findByIdChecked(userLogin);
		
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
	private void validatePassword(final UserDTO dto, Users user) {
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
	public List<Users> findByName(final String name) {
		return ((UserRepository) repository).findByPersonFirstNameLike(name + "%");
	}

}
