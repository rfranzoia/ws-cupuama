package br.com.cupuama.domain.company;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.cupuama.util.audit.DefaultAuditableEntity;

@Entity
@Table(name = "employee_salary")
public class EmployeeSalary extends DefaultAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	
	@Column(name = "from_date", nullable = true)
	@Temporal(value = TemporalType.DATE)
	private Date fromDate;
	
	@Column(name = "to_date", nullable = true)
	@Temporal(value = TemporalType.DATE)
	private Date toDate;
	
}
