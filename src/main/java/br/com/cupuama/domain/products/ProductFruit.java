package br.com.cupuama.domain.products;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.cupuama.util.audit.DefaultAuditableEntity;

@Entity
@Table(name="product_fruit")
public class ProductFruit extends DefaultAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	private ProductFruitId id;
	
	public ProductFruit() {
	}
	
	public ProductFruit(ProductFruitId id) {
		this.id = id;
	}

	public ProductFruitId getId() {
		return id;
	}

	public void setId(ProductFruitId id) {
		this.id = id;
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
		ProductFruit other = (ProductFruit) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductFruit [id=" + id.toString() + "]";
	}
	
	 
	
}
