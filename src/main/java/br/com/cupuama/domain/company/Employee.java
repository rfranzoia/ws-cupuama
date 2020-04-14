package br.com.cupuama.domain.company;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.cupuama.domain.persons.DefaultPersonEntity;
import br.com.cupuama.enums.CompanyRole;

@Entity
@Table(name = "company")
public class Employee extends DefaultPersonEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;
	
	@Column(name = "hired_date", nullable = false)
	@Temporal(value = TemporalType.DATE)
	private Date hiredDate;
	
	@Column(name = "fired_date", nullable = true)
	@Temporal(value = TemporalType.DATE)
	private Date firedDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "manager_employee_id", nullable = true)
	private Employee manager;
	
	@Column(name = "company_role", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private CompanyRole companyRole;
	
}
