package br.com.cupuama.domain.stock;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.cupuama.domain.products.Fruits;
import br.com.cupuama.domain.products.Products;

@Embeddable
public class InventoryId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String period;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false)
	private Products products;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fruit_id", nullable = false)
	private Fruits fruits;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "depot_id", nullable = false)
	private Depot depot;

	public InventoryId() {
	}
	
	public InventoryId(String period, Products products, Fruits fruits, Depot depot) {
		this.period = period;
		this.products = products;
		this.fruits = fruits;
		this.depot = depot;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Products getProduct() {
		return products;
	}

	public void setProduct(Products products) {
		this.products = products;
	}

	public Fruits getFruit() {
		return fruits;
	}

	public void setFruit(Fruits fruits) {
		this.fruits = fruits;
	}

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((depot == null) ? 0 : depot.hashCode());
		result = prime * result + ((fruits == null) ? 0 : fruits.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		result = prime * result + ((products == null) ? 0 : products.hashCode());
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
		InventoryId other = (InventoryId) obj;
		if (depot == null) {
			if (other.getDepot() != null)
				return false;
		} else if (!depot.equals(other.getDepot()))
			return false;
		if (fruits == null) {
			if (other.getFruit() != null)
				return false;
		} else if (!fruits.equals(other.getFruit()))
			return false;
		if (period == null) {
			if (other.getPeriod() != null)
				return false;
		} else if (!period.equals(other.getPeriod()))
			return false;
		if (products == null) {
			if (other.getProduct() != null)
				return false;
		} else if (!products.equals(other.getProduct()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InventoryKey [period=" + period 
				+ ", product=" + products.toString()
				+ ", fruit=" + fruits.toString()
				+ ", depot=" + depot.toString()
				+ "]";
	}
	
	@Override
	public InventoryId clone() {
		return new InventoryId(period, products.clone(), fruits.clone(), depot.clone());
	}
	
}
