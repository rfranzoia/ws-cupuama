package br.com.cupuama.domain.products;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "product_fruit_price")
public class ProductFruitPrice implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	private ProductFruitKey key;

	@Column(nullable = false, precision = 9, scale = 3)
	private Double price;

	@Column(nullable = false, name = "price_expiration_date")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date priceExpirationDate;

	public ProductFruitKey getKey() {
		return key;
	}

	public void setKey(ProductFruitKey key) {
		this.key = key;
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
		ProductFruitPrice other = (ProductFruitPrice) obj;
		if (key == null) {
			if (other.getKey() != null)
				return false;
		} else if (!key.equals(other.getKey()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductFruitPrice [key=" + key + ", price=" + price + ", priceExpirationDate=" + priceExpirationDate
				+ "]";
	}

}
