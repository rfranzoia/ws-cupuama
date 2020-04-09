package br.com.cupuama.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductFruitDTO {
	
	@NotNull(message = "ProductId cannot be null!")
	private Long productId;
	
	@NotNull(message = "FruitId cannot be null!")
	private Long fruitId;

	private ProductFruitDTO() {
	}

	private ProductFruitDTO(Long productId, Long fruitId) {
		this.productId = productId;
		this.fruitId = fruitId;
	}

	public static ProductFruitDTOBuilder newBuilder() {
		return new ProductFruitDTOBuilder();
	}

	@JsonProperty
	public Long getProductId() {
		return productId;
	}
	
	@JsonProperty
	public Long getFruitId() {
		return fruitId;
	}

	public static class ProductFruitDTOBuilder {
		private Long productId;
		private Long fruitId;

		public ProductFruitDTOBuilder setProductId(Long productId) {
			this.productId = productId;
			return this;
		}
		
		public ProductFruitDTOBuilder setFruitId(Long fruitId) {
			this.fruitId = fruitId;
			return this;
		}

		public ProductFruitDTO createProductFruitDTO() {
			return new ProductFruitDTO(productId, fruitId);
		}
	}
}
