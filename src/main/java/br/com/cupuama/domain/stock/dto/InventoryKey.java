package br.com.cupuama.domain.stock.dto;

import java.io.Serializable;

import javax.persistence.Embeddable;

import br.com.cupuama.domain.products.dto.FruitDTO;
import br.com.cupuama.domain.products.dto.ProductDTO;

@Embeddable
public class InventoryKey implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String period;
	private ProductDTO product;
	private FruitDTO fruit;
	private DepotDTO depot;

	public InventoryKey() {
	}
	
	public InventoryKey(String period, ProductDTO product, FruitDTO fruit, DepotDTO depot) {
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

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public FruitDTO getFruit() {
		return fruit;
	}

	public void setFruit(FruitDTO fruit) {
		this.fruit = fruit;
	}

	public DepotDTO getDepot() {
		return depot;
	}

	public void setDepot(DepotDTO depot) {
		this.depot = depot;
	}

}
