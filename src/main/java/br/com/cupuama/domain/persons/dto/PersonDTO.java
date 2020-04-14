package br.com.cupuama.domain.persons.dto;

import java.util.Date;

public class PersonDTO {

	private String firstName;
	private String lastName;
	private Date dateOfBirth;

	private PersonDTO() {
	}

	private PersonDTO(String firstName, String lastName, Date dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public static PersonDTOBuilder newBuilder() {
		return new PersonDTOBuilder();
	}

	public static class PersonDTOBuilder {
		private String firstName;
		private String lastName;
		private Date dateOfBirth;

		public PersonDTOBuilder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public PersonDTOBuilder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public PersonDTOBuilder setDateOfBirth(Date dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
			return this;
		}

		public PersonDTO createDTO() {
			return new PersonDTO(firstName, lastName, dateOfBirth);
		}
	}
}
