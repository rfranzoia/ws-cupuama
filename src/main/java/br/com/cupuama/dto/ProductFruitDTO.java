package br.com.cupuama.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cupuama.domain.products.Fruit;
import br.com.cupuama.domain.products.Product;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductFruitDTO {
	
	@NotNull(message = "Product cannot be null!")
	private Product product;

	@NotNull(message = "Fruit cannot be null!")
	private Fruit fruit;
	
	private ProductFruitDTO() {
	}

	private ProductFruitDTO(Product product, Fruit fruit) {
		this.product = product;
		this.fruit = fruit;
	}

	public static ProductFruitDTOBuilder newBuilder() {
		return new ProductFruitDTOBuilder();
	}

	@JsonProperty
	public Product getProduct() {
		return product;
	}


	@JsonProperty
	public Fruit getFruit() {
		return fruit;
	}
	
	public static class ProductFruitDTOBuilder {
		private Product product;
		private Fruit fruit;

		public ProductFruitDTOBuilder setProduct(Product product) {
			this.product = product;
			return this;
		}

		public ProductFruitDTOBuilder setFruit(Fruit fruit) {
			this.fruit = fruit;
			return this;
		}

		public ProductFruitDTO createProductFruitDTO() {
			return new ProductFruitDTO(product, fruit);
		}
	}
}
