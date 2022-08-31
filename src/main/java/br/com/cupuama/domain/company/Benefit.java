package br.com.cupuama.domain.company;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.com.cupuama.util.audit.DefaultAuditableEntity;

@Entity
@Table(name = "benefit", uniqueConstraints = @UniqueConstraint(name = "uc_benefit_name", columnNames = { "name" }))
public class Benefit extends DefaultAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	@NotNull(message = "Name cannot be null!")
	private String name;
	
	@Column(name = "valid_until", nullable = false)
	@Temporal(value = TemporalType.DATE)
	private Date validUntil;
	
}
