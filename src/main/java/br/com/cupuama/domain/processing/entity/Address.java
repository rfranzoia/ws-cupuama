package br.com.cupuama.domain.processing.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

	@Column(nullable = false)
	private String street = "";

	@Column(nullable = false)
	private String city = "";

	@Column(nullable = false)
	private String region = "";

	@Column
	private String postalCode = "";

	@Column(nullable = false)
	private String country = "";
	
	public Address() {
	}

	public Address(String street, String city, String region, String postalCode, String country) {
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

	@Override
	public String toString() {
		return "Address [street=" + street + ", city=" + city + ", region=" + region + ", postalCode=" + postalCode
				+ ", country=" + country + "]";
	}

}
