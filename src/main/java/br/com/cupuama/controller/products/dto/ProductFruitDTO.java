package br.com.cupuama.controller.products.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductFruitDTO {
	
	@NotNull(message = "Product cannot be null!")
	private ProductsDTO product;

	@NotNull(message = "Fruit cannot be null!")
	private FruitsDTO fruit;
	
	private ProductFruitDTO() {
	}

	private ProductFruitDTO(ProductsDTO product, FruitsDTO fruit) {
		this.product = product;
		this.fruit = fruit;
	}

	public static ProductFruitDTOBuilder newBuilder() {
		return new ProductFruitDTOBuilder();
	}

	@JsonProperty
	public ProductsDTO getProduct() {
		return product;
	}


	@JsonProperty
	public FruitsDTO getFruit() {
		return fruit;
	}
	
	public static class ProductFruitDTOBuilder {
		private ProductsDTO product;
		private FruitsDTO fruit;

		public ProductFruitDTOBuilder setProduct(ProductsDTO product) {
			this.product = product;
			return this;
		}

		public ProductFruitDTOBuilder setFruit(FruitsDTO fruit) {
			this.fruit = fruit;
			return this;
		}

		public ProductFruitDTO createProductFruitDTO() {
			return new ProductFruitDTO(product, fruit);
		}
	}
}
