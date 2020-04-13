package br.com.cupuama.domain.users.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.users.dto.UserDTO;
import br.com.cupuama.domain.users.entity.User;
import br.com.cupuama.domain.users.mapper.UserMapper;
import br.com.cupuama.domain.users.repository.UserRepository;
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
	public void update(final String userLogin, final UserDTO userDTO) throws EntityNotFoundException {
		User user = findByIdChecked(userLogin);
		user.setName(userDTO.getName());
	}
	
	/**
	 * Update a users password.
	 *
	 * @param userLogin
	 * @param userDTO 
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void updatePassword(final String userLogin, final UserDTO userDTO) throws EntityNotFoundException, InvalidRequestException {
		User user = findByIdChecked(userLogin);
		
		if (!StringUtils.equals(user.getPassword(), userDTO.getPassword())) {
			throw new InvalidRequestException(String.format("Old password doesn't match the one provided!"));
		}
		
		user.setPassword(userDTO.getNewPassword());
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
