package br.com.cupuama.controller.users;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cupuama.Services.users.UserService;
import br.com.cupuama.controller.users.dto.UserDTO;
import br.com.cupuama.controller.users.mapper.UserMapper;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.exception.InvalidRequestException;

/**
 * All operations with a user will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(final UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{userLogin}")
	public UserDTO getUser(@PathVariable final String userLogin) throws EntityNotFoundException {
		return UserMapper.makeDTO(userService.find(userLogin));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO createUser(@Valid @RequestBody final UserDTO userDTO) throws ConstraintsViolationException {
		return userService.create(userDTO);
	}

	@DeleteMapping("/{userLogin}")
	public void deleteUser(@PathVariable final String userLogin) throws EntityNotFoundException {
		userService.delete(userLogin);
	}

	@PutMapping("/{userLogin}")
	public void update(@PathVariable final String userLogin, @RequestBody final UserDTO userDTO)
			throws EntityNotFoundException {
		userService.update(userLogin, userDTO);
	}
	
	@PutMapping("/changePassword/{userLogin}")
	public void updatePassword(@PathVariable final String userLogin, @RequestBody final UserDTO userDTO)
			throws EntityNotFoundException, InvalidRequestException {
		userService.updatePassword(userLogin, userDTO);
	}

	@GetMapping("/name/{name}")
	public List<UserDTO> findUsersByName(@PathVariable final String name) {
		return UserMapper.makeListDTO(userService.findByName(name));
	}

	@GetMapping
	public List<UserDTO> findAllUsers() {
		return UserMapper.makeListDTO(userService.findAll());
	}
}
