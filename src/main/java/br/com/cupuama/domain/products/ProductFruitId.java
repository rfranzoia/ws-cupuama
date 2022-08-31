package br.com.cupuama.domain.products;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ProductFruitId implements Serializable{

	private static final long serialVersionUID = 7163808945435615192L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false)
	private Products products;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fruit_id", nullable = false)
	private Fruits fruits;

	public ProductFruitId() {
	}
	
	public ProductFruitId(Products products, Fruits fruits) {
		this.products = products;
		this.fruits = fruits;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fruits == null) ? 0 : fruits.hashCode());
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
		ProductFruitId other = (ProductFruitId) obj;
		if (fruits == null) {
			if (other.getFruit() != null)
				return false;
		} else if (!fruits.equals(other.getFruit()))
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
		return "ProdutoFrutaKey [produto=" + products + ", fruta=" + fruits + "]";
	}
	
	

}
