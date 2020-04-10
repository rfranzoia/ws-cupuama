package br.com.cupuama.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductFruitDTO {
	
	@NotNull(message = "ProductId cannot be null!")
	private Long productId;
	
	private String productName;
	private String productUnit;
	
	@NotNull(message = "FruitId cannot be null!")
	private Long fruitId;
	
	private String fruitName;
	private String fruitInitials;
	private String fruitHarvest;

	private ProductFruitDTO() {
	}

	private ProductFruitDTO(Long productId, String productName, String productUnit, Long fruitId, String fruitName, String fruitInitials, String fruitHarvest) {
		this.productId = productId;
		this.productName = productName;
		this.productUnit = productUnit;
		
		this.fruitId = fruitId;
		this.fruitName = fruitName;
		this.fruitInitials = fruitInitials;
		this.fruitHarvest = fruitHarvest;
	}

	public static ProductFruitDTOBuilder newBuilder() {
		return new ProductFruitDTOBuilder();
	}

	@JsonProperty
	public Long getProductId() {
		return productId;
	}
	
	@JsonProperty
	public String getProductName() {
		return productName;
	}

	@JsonProperty
	public String getProductUnit() {
		return productUnit;
	}
	
	@JsonProperty
	public Long getFruitId() {
		return fruitId;
	}
	
	@JsonProperty
	public String getFruitName() {
		return fruitName;
	}
	
	@JsonProperty
	public String getFruitInitials() {
		return fruitInitials;
	}
	
	@JsonProperty
	public String getFruitHarvest() {
		return fruitHarvest;
	}

	public static class ProductFruitDTOBuilder {
		private Long productId;
		private String productName;
		private String productUnit;
		
		private Long fruitId;
		private String fruitName;
		private String fruitInitials;
		private String fruitHarvest;

		public ProductFruitDTOBuilder setProductId(Long productId) {
			this.productId = productId;
			return this;
		}
		
		public ProductFruitDTOBuilder setFruitId(Long fruitId) {
			this.fruitId = fruitId;
			return this;
		}

		public ProductFruitDTOBuilder setProductName(String productName) {
			this.productName = productName;
			return this;			
		}

		public ProductFruitDTOBuilder setProductUnit(String productUnit) {
			this.productUnit = productUnit;
			return this;
		}

		public ProductFruitDTOBuilder setFruitName(String fruitName) {
			this.fruitName = fruitName;
			return this;
		}

		public ProductFruitDTOBuilder setFruitInitials(String fruitInitials) {
			this.fruitInitials = fruitInitials;
			return this;
		}

		public ProductFruitDTOBuilder setFruitHarvest(String fruitHarvest) {
			this.fruitHarvest = fruitHarvest;
			return this;
		}

		public ProductFruitDTO createProductFruitDTO() {
			return new ProductFruitDTO(productId, productName, productUnit, fruitId, fruitName, fruitInitials, fruitHarvest);
		}
	}
}
