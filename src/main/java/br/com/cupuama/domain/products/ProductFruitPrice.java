package br.com.cupuama.domain.products;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.cupuama.util.audit.DefaultAuditableEntity;

@Entity
@Table(name = "product_fruit_price")
public class ProductFruitPrice extends DefaultAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	private ProductFruitId productFruitId;

	@Column(nullable = false, precision = 9, scale = 3)
	private Double price;

	@Column(nullable = false, name = "price_expiration_date")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date priceExpirationDate;

	
	public ProductFruitPrice(Long id, ProductFruitId productFruitId, Double price, Date priceExpirationDate) {
		this.id = id;
		this.productFruitId = productFruitId;
		this.price = price;
		this.priceExpirationDate = priceExpirationDate;
	}

	public ProductFruitId getProductFruitId() {
		return productFruitId;
	}

	public void setKey(ProductFruitId productFruitId) {
		this.productFruitId = productFruitId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getPriceExpirationDate() {
		return priceExpirationDate;
	}

	public void setPriceExpirationDate(Date priceExpirationDate) {
		this.priceExpirationDate = priceExpirationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
		ProductFruitPrice other = (ProductFruitPrice) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductFruitPrice [id=" + id + ", productFruitId=" + productFruitId + ", price=" + price
				+ ", priceExpirationDate=" + priceExpirationDate + "]";
	}

}
