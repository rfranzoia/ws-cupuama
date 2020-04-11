package br.com.cupuama.domain.processing.entity;

import java.util.Date;

public class Process {

	private Long id;
	
	private Date processDate;
	
	private Customer customer;
	
	private Supplier supplier;
	
	private ProcessType processType;
	
	private String documentReference;
	
	private String remarks;
	
}
