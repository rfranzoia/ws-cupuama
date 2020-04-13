package br.com.cupuama.domain.stock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.cupuama.util.audit.DefaultAuditableEntity;

@Entity
@Table(name = "inventory")
public class Inventory extends DefaultAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private InventoryId inventoryId;

	@Column(nullable = false, precision = 9, scale = 3)
	private Double initialStock;

	@Column(nullable = false, precision = 9, scale = 3)
	private Double stockIn;

	@Column(nullable = false, precision = 9, scale = 3)
	private Double stockOut;

	public Inventory() {
	}

	public Inventory(InventoryId inventoryId, Double initialStock, Double stockIn, Double stockOut) {
		this.inventoryId = inventoryId;
		this.initialStock = initialStock;
		this.stockIn = stockIn;
		this.stockOut = stockOut;
	}

	public InventoryId getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(InventoryId inventoryId) {
		this.inventoryId = inventoryId;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inventoryId == null) ? 0 : inventoryId.hashCode());
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
		if (inventoryId == null) {
			if (other.getInventoryId() != null)
				return false;
		} else if (!inventoryId.equals(other.getInventoryId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Inventory [inventoryId=" + inventoryId 
				+ ", initialStock=" + initialStock 
				+ ", stockIn=" + stockIn
				+ ", stockOut=" + stockOut + "]";
	}

}
