package br.com.cupuama.domain.processing.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	
	private String street;
	
	private String city;
	
	private String Region;
	
	private String postalCode;
	
	private String country;
	
}
