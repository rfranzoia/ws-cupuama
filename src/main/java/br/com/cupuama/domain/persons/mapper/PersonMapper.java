package br.com.cupuama.domain.persons.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.persons.dto.PersonDTO;
import br.com.cupuama.domain.persons.entity.Person;


public class PersonMapper {
	public static Person makePerson(final PersonDTO dto) {
		return new Person(dto.getFirstName(), dto.getLastName(), dto.getDateOfBirth());
	}

	public static PersonDTO makeDTO(final Person person) {
		PersonDTO.PersonDTOBuilder builder = PersonDTO.newBuilder();
	
		builder.setFirstName(person.getFirstName());
		builder.setLastName(person.getLastName());
		builder.setDateOfBirth(person.getDateOfBirth());
		
		return builder.createDTO();
	}

	public static List<PersonDTO> makeListDTO(final Collection<Person> persons) {
		return persons.stream()
				.map(PersonMapper::makeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Person> makeList(final Collection<PersonDTO> dtos) {
		return dtos.stream()
				.map(PersonMapper::makePerson)
				.collect(Collectors.toList());
	}

}
