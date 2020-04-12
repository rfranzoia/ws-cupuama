package br.com.cupuama.domain.products.dto;

public class ProductFruitKey {

	private ProductDTO product;
	private FruitDTO fruit;

	public ProductFruitKey() {
	}
	
	public ProductFruitKey(ProductDTO product, FruitDTO fruit) {
		this.product = product;
		this.fruit = fruit;
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

}
