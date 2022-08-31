package br.com.cupuama.domain.processing;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.cupuama.domain.stock.Depot;
import br.com.cupuama.util.audit.DefaultAuditableEntity;

@Entity
@Table(name = "process_flow_type")
public class ProcessFlowType extends DefaultAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private ProcessFlowTypeId id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "depot_in_id")
	private Depot depotIn;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "depot_out_id")
	private Depot depotOut;

	public ProcessFlowType() {
	}

	public ProcessFlowType(ProcessFlowTypeId id, Depot depotIn, Depot depotOut) {
		this.id = id;
		this.depotIn = depotIn;
		this.depotOut = depotOut;
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
