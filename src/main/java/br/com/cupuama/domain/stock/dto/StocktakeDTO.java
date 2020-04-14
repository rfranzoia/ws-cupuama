package br.com.cupuama.domain.stock.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cupuama.domain.products.dto.ProductFruitKey;
import br.com.cupuama.enums.StocktakeInOut;

public class StocktakeDTO {

	private Long id;
	
	@NotNull(message = "ProductFruitKey cannot be null!")
	private ProductFruitKey productFruitKey;
	
	@NotNull(message = "ProductFruitKey cannot be null!")
	private DepotDTO depot;
	
	@NotNull(message = "stocktakeDate cannot be null!")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date stocktakeDate;
	
	@NotNull(message = "StocktakeInOut cannot be null!")
	private StocktakeInOut stocktakeInOut;
	
	@NotNull(message = "amount cannot be null!")
	private Double amount;

	public StocktakeDTO() {
	}

	public StocktakeDTO(Long id, ProductFruitKey productFruitKey, DepotDTO depot, 
			Date stocktakeDate, StocktakeInOut stocktakeInOut, Double amount) {
		this.id = id;
		this.productFruitKey = productFruitKey;
		this.depot = depot;
		this.stocktakeDate = stocktakeDate;
		this.stocktakeInOut = stocktakeInOut;
		this.amount = amount;
	}

	@JsonProperty
	public Long getId() {
		return id;
	}

	@JsonProperty
	public ProductFruitKey getProductFruitKey() {
		return productFruitKey;
	}

	@JsonProperty
	public DepotDTO getDepot() {
		return depot;
	}

	@JsonProperty
	public Date getStocktakeDate() {
		return stocktakeDate;
	}

	@JsonProperty
	public StocktakeInOut getStocktakeInOut() {
		return stocktakeInOut;
	}

	@JsonProperty
	public Double getAmount() {
		return amount;
	}

	public static StocktakeDTOBuilder newBuilder() {
		return new StocktakeDTOBuilder();
	}
	
	public static class StocktakeDTOBuilder {
		
		private Long id;
		private ProductFruitKey productFruitKey;
		private DepotDTO depot;
		private Date stocktakeDate;
		private StocktakeInOut stocktakeInOut;
		private Double amount;
		
		public StocktakeDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}		
		
		public StocktakeDTOBuilder setProductFruitKey(ProductFruitKey productFruitKey) {
			this.productFruitKey = productFruitKey;
			return this;
		}
		
		public StocktakeDTOBuilder setDepot(DepotDTO depot) {
			this.depot = depot;
			return this;
		}
		
		public StocktakeDTOBuilder setStocktakeDate(Date stocktakeDate) {
			this.stocktakeDate = stocktakeDate;
			return this;
		}
		
		public StocktakeDTOBuilder setStocktakeInOut(StocktakeInOut stocktakeInOut) {
			this.stocktakeInOut = stocktakeInOut;
			return this;
		}
		
		public StocktakeDTOBuilder setAmount(Double amount) {
			this.amount = amount;
			return this;
		}
		
		public StocktakeDTO createDTO() {
			return new StocktakeDTO(id, productFruitKey, depot, stocktakeDate, stocktakeInOut, amount);
		}
	}
}
