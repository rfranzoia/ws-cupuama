package br.com.cupuama.domain.stock.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.cupuama.util.Audit;
import br.com.cupuama.util.AuditableEntity;

@Entity
@Table(name = "stocktake_process")
public class StocktakeProcess implements AuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private StocktakeProcessKey key;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "depot_id", nullable = false)
	private Depot depot;

	@Embedded
	private Audit audit;

	public StocktakeProcess() {
	}

	public StocktakeProcess(StocktakeProcessKey key, Depot depot) {
		this.key = key;
		this.depot = depot;
		this.audit = new Audit();
		this.audit.setDeleted(false);
	}

	public StocktakeProcessKey getKey() {
		return key;
	}

	public void setKey(StocktakeProcessKey key) {
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
		StocktakeProcess other = (StocktakeProcess) obj;
		if (key == null) {
			if (other.getKey() != null)
				return false;
		} else if (!key.equals(other.getKey()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StocktakeProcess [key=" + key + ", depot=" + depot + "]";
	}
	
}
