package br.com.cupuama.domain.stock.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.cupuama.util.Audit;
import br.com.cupuama.util.AuditableEntity;

@Entity
@Table(name = "inventory")
public class Inventory implements AuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private InventoryKey inventoryKey;

	@Column(nullable = false, precision = 9, scale = 3)
	private Double initialStock;

	@Column(nullable = false, precision = 9, scale = 3)
	private Double stockIn;

	@Column(nullable = false, precision = 9, scale = 3)
	private Double stockOut;

	@Embedded
	private Audit audit;

	public Inventory() {
	}

	public Inventory(InventoryKey inventoryKey, Double initialStock, Double stockIn, Double stockOut) {
		this.inventoryKey = inventoryKey;
		this.initialStock = initialStock;
		this.stockIn = stockIn;
		this.stockOut = stockOut;
		this.audit = new Audit();
		this.audit.setDeleted(false);
	}

	public InventoryKey getInventoryKey() {
		return inventoryKey;
	}

	public void setInventoryKey(InventoryKey inventoryKey) {
		this.inventoryKey = inventoryKey;
	}

	public Double getInitialStock() {
		return initialStock;
	}

	public void setInitialStock(Double initialStock) {
		this.initialStock = initialStock;
	}

	public Double getStockIn() {
		return stockIn;
	}

	public void setStockIn(Double stockIn) {
		this.stockIn = stockIn;
	}

	public Double getStockOut() {
		return stockOut;
	}

	public void setStockOut(Double stockOut) {
		this.stockOut = stockOut;
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
		result = prime * result + ((inventoryKey == null) ? 0 : inventoryKey.hashCode());
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
		Inventory other = (Inventory) obj;
		if (inventoryKey == null) {
			if (other.getInventoryKey() != null)
				return false;
		} else if (!inventoryKey.equals(other.getInventoryKey()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Inventory [inventoryKey=" + inventoryKey 
				+ ", initialStock=" + initialStock 
				+ ", stockIn=" + stockIn
				+ ", stockOut=" + stockOut + "]";
	}

}
