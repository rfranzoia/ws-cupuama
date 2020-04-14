package br.com.cupuama.domain.persons;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Embeddable
public class Person {

	@Column(name = "first_name", nullable = false)
	@NotNull(message = "FirstName cannot be null!")
	private String firstName;
	
	@Column(name = "last_name", nullable = false)
	@NotNull(message = "LastName cannot be null!")
	private String lastName;
	
	@Column(name = "date_of_birth", nullable = false)
	@NotNull(message = "DateOfBirth cannot be null!")
	@Temporal(value = TemporalType.DATE)
	private Date dateOfBirth;
	
	public Person(String firstName, String lastName, Date dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + "]";
	}
}
