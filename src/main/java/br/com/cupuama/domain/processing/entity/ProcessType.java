package br.com.cupuama.domain.processing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.com.cupuama.enums.FlowTypeModel;
import br.com.cupuama.util.audit.DefaultAuditableEntity;

@Entity
@Table(name = "process_type", uniqueConstraints = @UniqueConstraint(name = "uc_process_type_name", columnNames = { "name" }))
public class ProcessType extends DefaultAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotNull(message = "Name cannot be null!")
	private String name;
	
	@Column(nullable = false)
	@NotNull(message = "FlowTypeModel cannot be null!")
	@Enumerated(EnumType.STRING)
	private FlowTypeModel flowTypeModel;

	public ProcessType() {
	}

	public ProcessType(Long id) {
		this.id = id;
	}
	
	public ProcessType(Long id, String name, FlowTypeModel flowTypeModel) {
		this.name = name;
		this.flowTypeModel = flowTypeModel;
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

	public FlowTypeModel getFlowTypeModel() {
		return flowTypeModel;
	}

	public void setFlowTypeModel(FlowTypeModel flowTypeModel) {
		this.flowTypeModel = flowTypeModel;
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
		ProcessType other = (ProcessType) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProcessType [id=" + id + ", name=" + name + ", FlowTypeModel=" + flowTypeModel + "]";
	}

}
