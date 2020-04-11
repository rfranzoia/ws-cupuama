package br.com.cupuama.domain.processing.entity;

import javax.persistence.Embedded;

public class Supplier {

	private Long id;
	
	private String name;
	
	@Embedded
	private Address address;
	
}
