package br.com.cupuama.domain.company;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.com.cupuama.domain.persons.Address;
import br.com.cupuama.util.audit.DefaultAuditableEntity;

@Entity
@Table(name = "company", uniqueConstraints = @UniqueConstraint(name = "uc_company_name", columnNames = { "name" }))
public class Company extends DefaultAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	@NotNull(message = "Name cannot be null!")
	private String name;
	
	@Embedded
	private Address address;
	
}
