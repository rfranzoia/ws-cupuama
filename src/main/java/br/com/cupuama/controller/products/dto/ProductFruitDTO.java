package br.com.cupuama.controller.products.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductFruitDTO {
	
	@NotNull(message = "Product cannot be null!")
	private ProductDTO product;

	@NotNull(message = "Fruit cannot be null!")
	private FruitDTO fruit;
	
	private ProductFruitDTO() {
	}

	private ProductFruitDTO(ProductDTO product, FruitDTO fruit) {
		this.product = product;
		this.fruit = fruit;
	}

	public static ProductFruitDTOBuilder newBuilder() {
		return new ProductFruitDTOBuilder();
	}

	@JsonProperty
	public ProductDTO getProduct() {
		return product;
	}


	@JsonProperty
	public FruitDTO getFruit() {
		return fruit;
	}
	
	public static class ProductFruitDTOBuilder {
		private ProductDTO product;
		private FruitDTO fruit;

		public ProductFruitDTOBuilder setProduct(ProductDTO product) {
			this.product = product;
			return this;
		}

		public ProductFruitDTOBuilder setFruit(FruitDTO fruit) {
			this.fruit = fruit;
			return this;
		}

		public ProductFruitDTO createProductFruitDTO() {
			return new ProductFruitDTO(product, fruit);
		}
	}
}
