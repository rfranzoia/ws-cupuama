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
	private ProcessFlowTypeKey key;

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

	public ProcessFlowType(ProcessFlowTypeKey key, Depot depotIn, Depot depotOut) {
		this.key = key;
		this.depotIn = depotIn;
		this.depotOut = depotOut;
		this.audit = new Audit();
		this.audit.setDeleted(false);
	}

	public ProcessFlowTypeKey getKey() {
		return key;
	}

	public void setKey(ProcessFlowTypeKey key) {
		this.key = key;
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
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		if (key == null) {
			if (other.getKey() != null)
				return false;
		} else if (!key.equals(other.getKey()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProcessFlowType [key=" + key + ", depotIn=" + depotIn + ", depotOut=" + depotOut + "]";
	}
	
}
