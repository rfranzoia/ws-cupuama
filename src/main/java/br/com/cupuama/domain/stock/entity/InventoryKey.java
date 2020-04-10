package br.com.cupuama.domain.stock.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.cupuama.domain.products.entity.Fruit;
import br.com.cupuama.domain.products.entity.Product;

@Embeddable
public class InventoryKey implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String period;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fruit_id", nullable = false)
	private Fruit fruit;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "depot_id", nullable = false)
	private Depot depot;

	public InventoryKey() {
	}
	
	public InventoryKey(String period, Product product, Fruit fruit, Depot depot) {
		this.period = period;
		this.product = product;
		this.fruit = fruit;
		this.depot = depot;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Fruit getFruit() {
		return fruit;
	}

	public void setFruit(Fruit fruit) {
		this.fruit = fruit;
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
		result = prime * result + ((fruit == null) ? 0 : fruit.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		InventoryKey other = (InventoryKey) obj;
		if (depot == null) {
			if (other.getDepot() != null)
				return false;
		} else if (!depot.equals(other.getDepot()))
			return false;
		if (fruit == null) {
			if (other.getFruit() != null)
				return false;
		} else if (!fruit.equals(other.getFruit()))
			return false;
		if (period == null) {
			if (other.getPeriod() != null)
				return false;
		} else if (!period.equals(other.getPeriod()))
			return false;
		if (product == null) {
			if (other.getProduct() != null)
				return false;
		} else if (!product.equals(other.getProduct()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InventoryKey [period=" + period 
				+ ", product=" + product.toString() 
				+ ", fruit=" + fruit.toString() 
				+ ", depot=" + depot.toString()
				+ "]";
	}
	
	@Override
	public InventoryKey clone() {
		return new InventoryKey(period, product.clone(), fruit.clone(), depot.clone());
	}
	
}
