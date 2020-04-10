package br.com.cupuama.domain.stock;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.cupuama.domain.products.Product;

@Embeddable
public class StocktakeProcessKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stocktake_type_id", nullable = false)
	private StocktakeType stocktakeType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Enumerated(EnumType.STRING)
	private StocktakeInOut stocktakeInOut;

	public StocktakeProcessKey() {
	}

	public StocktakeProcessKey(StocktakeType stocktakeType, Product product, StocktakeInOut stocktakeInOut) {
		this.stocktakeType = stocktakeType;
		this.product = product;
		this.stocktakeInOut = stocktakeInOut;
	}

	public StocktakeType getStocktakeType() {
		return stocktakeType;
	}

	public void setStocktakeType(StocktakeType stocktakeType) {
		this.stocktakeType = stocktakeType;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public StocktakeInOut getStocktakeInOut() {
		return stocktakeInOut;
	}

	public void setStocktakeInOut(StocktakeInOut stocktakeInOut) {
		this.stocktakeInOut = stocktakeInOut;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((stocktakeInOut == null) ? 0 : stocktakeInOut.hashCode());
		result = prime * result + ((stocktakeType == null) ? 0 : stocktakeType.hashCode());
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
		StocktakeProcessKey other = (StocktakeProcessKey) obj;
		if (product == null) {
			if (other.getProduct() != null)
				return false;
		} else if (!product.equals(other.getProduct()))
			return false;
		if (stocktakeInOut != other.getStocktakeInOut())
			return false;
		if (stocktakeType == null) {
			if (other.getStocktakeType() != null)
				return false;
		} else if (!stocktakeType.equals(other.getStocktakeType()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StocktakeProcessKey [stocktakeType=" + stocktakeType 
				+ ", product=" + product 
				+ ", stocktakeInOut=" + stocktakeInOut + "]";
	}

}
