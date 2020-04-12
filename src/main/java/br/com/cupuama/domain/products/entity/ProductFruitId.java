package br.com.cupuama.domain.products.entity;

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
	private Product product;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fruit_id", nullable = false)
	private Fruit fruit;

	public ProductFruitId() {
	}
	
	public ProductFruitId(Product product, Fruit fruit) {
		this.product = product;
		this.fruit = fruit;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fruit == null) ? 0 : fruit.hashCode());
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
		ProductFruitId other = (ProductFruitId) obj;
		if (fruit == null) {
			if (other.getFruit() != null)
				return false;
		} else if (!fruit.equals(other.getFruit()))
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
		return "ProdutoFrutaKey [produto=" + product + ", fruta=" + fruit + "]";
	}
	
	

}
