package br.com.cupuama.controller.persons.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplierDTO {
	
	private Long id;

	private PersonDTO person;
	
	private String companyName;
	
	private String phone;
	
	@NotNull(message = "Name cannot be null!")
	private AddressDTO address;
	
	private SupplierDTO() {
	}

	private SupplierDTO(Long id, PersonDTO person, String companyName, String phone, AddressDTO address) {
		this.id = id;
		this.person = person;
		this.companyName = companyName;
		this.phone = phone;
		this.address = address;
	}

	public static SupplierDTOBuilder newBuilder() {
		return new SupplierDTOBuilder();
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

	public static class SupplierDTOBuilder {
		private Long id;
		private PersonDTO person;
		private String companyName;
		private String phone;
		private AddressDTO address;

		public SupplierDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public SupplierDTOBuilder setPerson(PersonDTO person) {
			this.person = person;
			return this;
		}

		public SupplierDTOBuilder setCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}

		public SupplierDTOBuilder setPhone(String phone) {
			this.phone = phone;
			return this;
		}

		public SupplierDTOBuilder setAddress(AddressDTO address) {
			this.address = address;
			return this;
		}

		public SupplierDTO createSupplierDTO() {
			return new SupplierDTO(id, person, companyName, phone, address);
		}
	}
}
