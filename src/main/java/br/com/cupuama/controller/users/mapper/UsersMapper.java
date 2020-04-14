package br.com.cupuama.controller.users.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.controller.persons.mapper.PersonMapper;
import br.com.cupuama.controller.users.dto.UserDTO;
import br.com.cupuama.domain.users.Users;


public class UsersMapper {
	public static Users makeUser(UserDTO dto) {
		return new Users(dto.getLogin(), PersonMapper.makePerson(dto.getPerson()), dto.getPassword());
	}

	public static UserDTO makeDTO(Users user) {
		UserDTO.UserDTOBuilder depotDTOBuilder = UserDTO.newBuilder()
				.setLogin(user.getLogin())
				.setPerson(PersonMapper.makeDTO(user.getPerson()))
				.setPassword(user.getPassword());

		return depotDTOBuilder.createUserDTO();
	}

	public static List<UserDTO> makeListDTO(Collection<Users> depots) {
		return depots.stream()
				.map(UsersMapper::makeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Users> makeList(Collection<UserDTO> dtos) {
		return dtos.stream()
				.map(UsersMapper::makeUser)
				.collect(Collectors.toList());
	}

}
