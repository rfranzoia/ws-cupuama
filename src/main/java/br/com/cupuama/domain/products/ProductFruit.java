package br.com.cupuama.domain.products;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.cupuama.domain.DefaultEntity;

@Entity
@Table(name="product_fruit")
public class ProductFruit implements DefaultEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private ProductFruitKey key;
	
	public ProductFruit() {
	}
	
	public ProductFruit(ProductFruitKey key) {
		this.key = key;
	}

	public ProductFruitKey getKey() {
		return key;
	}

	public void setKey(ProductFruitKey key) {
		this.key = key;
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
		ProductFruit other = (ProductFruit) obj;
		if (key == null) {
			if (other.getKey() != null)
				return false;
		} else if (!key.equals(other.getKey()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductFruit [key=" + key.toString() + "]";
	}
	
	 
	
}
