package br.com.cupuama.domain.processing.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.cupuama.domain.stock.entity.Depot;
import br.com.cupuama.util.Audit;
import br.com.cupuama.util.AuditableEntity;

@Entity
@Table(name = "process_flow_type")
public class ProcessFlowType implements AuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private ProcessFlowTypeId id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "depot_in_id")
	private Depot depotIn;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "depot_out_id")
	private Depot depotOut;

	@Embedded
	private Audit audit;

	public ProcessFlowType() {
	}

	public ProcessFlowType(ProcessFlowTypeId id, Depot depotIn, Depot depotOut) {
		this.id = id;
		this.depotIn = depotIn;
		this.depotOut = depotOut;
		this.audit = new Audit();
		this.audit.setDeleted(false);
	}

	public ProcessFlowTypeId getId() {
		return id;
	}

	public void setId(ProcessFlowTypeId id) {
		this.id = id;
	}

	public Depot getDepotIn() {
		return depotIn;
	}

	public void setDepotIn(Depot depotIn) {
		this.depotIn = depotIn;
	}

	public Depot getDepotOut() {
		return depotOut;
	}

	public void setDepotOut(Depot depotOut) {
		this.depotOut = depotOut;
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
		ProcessFlowType other = (ProcessFlowType) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProcessFlowType [id=" + id + ", depotIn=" + depotIn + ", depotOut=" + depotOut + "]";
	}
	
}
