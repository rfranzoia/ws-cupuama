package br.com.cupuama.controller.stock.dto;

import java.io.Serializable;

import javax.persistence.Embeddable;

import br.com.cupuama.controller.products.dto.FruitsDTO;
import br.com.cupuama.controller.products.dto.ProductsDTO;

@Embeddable
public class InventoryKey implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String period;
	private ProductsDTO product;
	private FruitsDTO fruit;
	private DepotDTO depot;

	public InventoryKey() {
	}
	
	public InventoryKey(String period, ProductsDTO product, FruitsDTO fruit, DepotDTO depot) {
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

	public ProductsDTO getProduct() {
		return product;
	}

	public void setProduct(ProductsDTO product) {
		this.product = product;
	}

	public FruitsDTO getFruit() {
		return fruit;
	}

	public void setFruit(FruitsDTO fruit) {
		this.fruit = fruit;
	}

	public DepotDTO getDepot() {
		return depot;
	}

	public void setDepot(DepotDTO depot) {
		this.depot = depot;
	}

}
