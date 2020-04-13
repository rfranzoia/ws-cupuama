package br.com.cupuama.domain.users.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.users.dto.UserDTO;
import br.com.cupuama.domain.users.dto.UserDTO.UserDTOBuilder;
import br.com.cupuama.domain.users.entity.User;


public class UserMapper {
	public static User makeUser(UserDTO dto) {
		return new User(dto.getLogin(), dto.getName(), dto.getPassword());
	}

	public static UserDTO makeDTO(User depot) {
		UserDTO.UserDTOBuilder depotDTOBuilder = UserDTO.newBuilder()
				.setLogin(depot.getLogin())
				.setName(depot.getName())
				.setPassword(depot.getPassword());

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
