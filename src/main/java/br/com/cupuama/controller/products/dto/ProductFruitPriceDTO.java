package br.com.cupuama.controller.products.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductFruitPriceDTO {
	
	private Long id;
	
	@NotNull(message = "ProductId cannot be null!")
	private Long productId;
	
	@NotNull(message = "FruitId cannot be null!")
	private Long fruitId;
	
	private Double price;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date priceExpirationDate;

	private ProductFruitPriceDTO() {
	}

	private ProductFruitPriceDTO(Long id, Long productId, Long fruitId, Double price, Date priceExpirationDate) {
		this.id = id;
		this.productId = productId;
		this.fruitId = fruitId;
		this.price = price;
		this.priceExpirationDate = priceExpirationDate;
	}

	public static ProducFruitPriceDTOBuilder newBuilder() {
		return new ProducFruitPriceDTOBuilder();
	}

	@JsonProperty
	public Long getId() {
		return id;
	}

	@JsonProperty
	public Double getPrice() {
		return price;
	}

	@JsonProperty
	public Date getPriceExpirationDate() {
		return priceExpirationDate;
	}

	@JsonProperty
	public Long getProductId() {
		return productId;
	}
	
	@JsonProperty
	public Long getFruitId() {
		return fruitId;
	}

	public static class ProducFruitPriceDTOBuilder {
		private Long id;
		private Long productId;
		private Long fruitId;
		private Double price;
		private Date priceExpirationDate;

		public ProducFruitPriceDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public ProducFruitPriceDTOBuilder setPrice(Double price) {
			this.price = price;
			return this;
		}

		public ProducFruitPriceDTOBuilder setPriceExpirationDate(Date priceExpirationDate) {
			this.priceExpirationDate = priceExpirationDate;
			return this;
		}
		
		public ProducFruitPriceDTOBuilder setProductId(Long productId) {
			this.productId = productId;
			return this;
		}
		
		public ProducFruitPriceDTOBuilder setFruitId(Long fruitId) {
			this.fruitId = fruitId;
			return this;
		}

		public ProductFruitPriceDTO createProductFruitDTO() {
			return new ProductFruitPriceDTO(id, productId, fruitId, price, priceExpirationDate);
		}

	}
}
