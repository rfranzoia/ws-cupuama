package br.com.cupuama.domain.products.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.com.cupuama.util.Audit;
import br.com.cupuama.util.AuditableEntity;

@Entity
@Table(name = "fruit", uniqueConstraints = @UniqueConstraint(name = "uc_fruit_name", columnNames = { "name" }))
public class Fruit implements AuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotNull(message = "DocumentType Name cannot be null!")
	private String name;

	@Column(nullable = false)
	@NotNull(message = "Initials cannot be null!")
	private String initials;

	@Column(nullable = true)
	private String harvest;

	@Embedded
	private Audit audit;

	public Fruit() {
	}

	public Fruit(Long id, String name, String initials, String harvest) {
		this.id = id;
		this.name = name;
		this.initials = initials;
		this.harvest = harvest;
		this.audit = new Audit();
		this.audit.setDeleted(false);
	}

	public Fruit(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getHarvest() {
		return harvest;
	}

	public void setHarvest(String harvest) {
		this.harvest = harvest;
	}

	@Override
	public Audit getAudit() {
		return audit;
	}

	@Override
	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fruit other = (Fruit) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

}
