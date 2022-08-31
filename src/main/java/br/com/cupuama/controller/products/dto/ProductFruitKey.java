package br.com.cupuama.controller.products.dto;

public class ProductFruitKey {

	private ProductsDTO product;
	private FruitsDTO fruit;

	public ProductFruitKey() {
	}
	
	public ProductFruitKey(ProductsDTO product, FruitsDTO fruit) {
		this.product = product;
		this.fruit = fruit;
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

}
