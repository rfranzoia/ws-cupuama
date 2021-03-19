package br.com.cupuama.domain.products;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.com.cupuama.util.audit.DefaultAuditableEntity;

@Entity
@Table(name = "fruits", uniqueConstraints = @UniqueConstraint(name = "uc_fruit_name", columnNames = { "name" }))
public class Fruits extends DefaultAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotNull(message = "Name cannot be null!")
	private String name;

	@Column(nullable = false)
	@NotNull(message = "Initials cannot be null!")
	private String initials;

	@Column(nullable = true)
	private String harvest;

	public Fruits() {
	}

	public Fruits(Long id, String name, String initials, String harvest) {
		this.id = id;
		this.name = name;
		this.initials = initials;
		this.harvest = harvest;
	}

	public Fruits(Long id) {
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
		Fruits other = (Fruits) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}
	
	@Override
	public Fruits clone() {
		return new Fruits(id, name, initials, harvest);
	}

}
