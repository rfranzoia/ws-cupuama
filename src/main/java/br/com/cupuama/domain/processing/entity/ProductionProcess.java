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
@Table(name = "production_process")
public class ProductionProcess implements AuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private ProductionProcessKey key;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "depot_id", nullable = false)
	private Depot depot;

	@Embedded
	private Audit audit;

	public ProductionProcess() {
	}

	public ProductionProcess(ProductionProcessKey key, Depot depot) {
		this.key = key;
		this.depot = depot;
		this.audit = new Audit();
		this.audit.setDeleted(false);
	}

	public ProductionProcessKey getKey() {
		return key;
	}

	public void setKey(ProductionProcessKey key) {
		this.key = key;
	}

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
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
		ProductionProcess other = (ProductionProcess) obj;
		if (key == null) {
			if (other.getKey() != null)
				return false;
		} else if (!key.equals(other.getKey()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductionProcess [key=" + key + ", depot=" + depot + "]";
	}
	
}
