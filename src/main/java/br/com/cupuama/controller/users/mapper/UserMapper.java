package br.com.cupuama.controller.users.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.controller.persons.mapper.PersonMapper;
import br.com.cupuama.controller.users.dto.UserDTO;
import br.com.cupuama.domain.users.User;


public class UserMapper {
	public static User makeUser(UserDTO dto) {
		return new User(dto.getLogin(), PersonMapper.makePerson(dto.getPerson()), dto.getPassword());
	}

	public static UserDTO makeDTO(User user) {
		UserDTO.UserDTOBuilder depotDTOBuilder = UserDTO.newBuilder()
				.setLogin(user.getLogin())
				.setPerson(PersonMapper.makeDTO(user.getPerson()))
				.setPassword(user.getPassword());

		return depotDTOBuilder.createUserDTO();
	}

	public static List<UserDTO> makeListDTO(Collection<User> depots) {
		return depots.stream()
				.map(UserMapper::makeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<User> makeList(Collection<UserDTO> dtos) {
		return dtos.stream()
				.map(UserMapper::makeUser)
				.collect(Collectors.toList());
	}

}
