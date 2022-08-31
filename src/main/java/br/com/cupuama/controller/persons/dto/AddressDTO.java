package br.com.cupuama.controller.persons.dto;

public class AddressDTO {

	private String street = "";
	private String city = "";
	private String region = "";
	private String postalCode = "";
	private String country = "";
	
	public AddressDTO() {
	}

	public AddressDTO(String street, String city, String region, String postalCode, String country) {
		this.street = street;
		this.city = city;
		this.region = region;
		this.postalCode = postalCode;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
