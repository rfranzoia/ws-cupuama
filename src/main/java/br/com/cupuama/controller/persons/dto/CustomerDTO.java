package br.com.cupuama.controller.persons.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {
	
	private Long id;

	private PersonDTO person;
	
	private String companyName;
	
	private String phone;
	
	@NotNull(message = "Name cannot be null!")
	private AddressDTO address;
	
	private CustomerDTO() {
	}

	private CustomerDTO(Long id, PersonDTO person, String companyName, String phone, AddressDTO address) {
		this.id = id;
		this.person = person;
		this.companyName = companyName;
		this.phone = phone;
		this.address = address;
	}

	public static CustomerDTOBuilder newBuilder() {
		return new CustomerDTOBuilder();
	}

	@JsonProperty
	public Long getId() {
		return id;
	}

	@JsonProperty
	public PersonDTO getPerson() {
		return person;
	}
	
	@JsonProperty
	public String getCompanyName() {
		return companyName;
	}

	@JsonProperty
	public String getPhone() {
		return phone;
	}

	@JsonProperty
	public AddressDTO getAddress() {
		return address;
	}

	public static class CustomerDTOBuilder {
		private Long id;
		private PersonDTO person;
		private String companyName;
		private String phone;
		private AddressDTO address;

		public CustomerDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public CustomerDTOBuilder setPerson(PersonDTO person) {
			this.person = person;
			return this;
		}

		public CustomerDTOBuilder setCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}

		public CustomerDTOBuilder setPhone(String phone) {
			this.phone = phone;
			return this;
		}

		public CustomerDTOBuilder setAddress(AddressDTO address) {
			this.address = address;
			return this;
		}

		public CustomerDTO createCustomerDTO() {
			return new CustomerDTO(id, person, companyName, phone, address);
		}
	}
}
