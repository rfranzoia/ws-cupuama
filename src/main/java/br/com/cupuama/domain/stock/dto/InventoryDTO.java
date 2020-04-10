package br.com.cupuama.domain.stock.dto;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cupuama.domain.stock.entity.InventoryKey;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryDTO {

	@NotNull(message = "InventoryKey cannot be null!")
	private InventoryKey key;

	@NotNull(message = "InitialStock Balance cannot be null!")
	private Double initialStock;

	@NotNull(message = "stockIn cannot be null!")
	private Double stockIn;
	
	@NotNull(message = "stockOut cannot be null!")
	private Double stockOut;
	
	private Double finalStock;

	public InventoryDTO(InventoryKey key, Double initialStock, Double stockIn, Double stockOut) {
		this.key = key;
		
		this.initialStock = Optional.ofNullable(initialStock).orElse(0.0);
		this.stockIn = Optional.ofNullable(stockIn).orElse(0.0);;
		this.stockOut = Optional.ofNullable(stockOut).orElse(0.0);;
		
		this.finalStock = this.initialStock + this.stockIn - this.stockOut;
	}

	public static InventoryDTOBuilder newBuilder() {
		return new InventoryDTOBuilder();
	}


	@JsonProperty	
	public InventoryKey getKey() {
		return key;
	}

	@JsonProperty
	public Double getInitialStock() {
		return initialStock;
	}

	@JsonProperty
	public Double getStockIn() {
		return stockIn;
	}

	@JsonProperty
	public Double getStockOut() {
		return stockOut;
	}

	@JsonProperty
	public Double getFinalStock() {
		return finalStock;
	}

	public static class InventoryDTOBuilder {
		private InventoryKey key;
		private Double initialStock = 0.0;
		private Double stockIn = 0.0;
		private Double stockOut = 0.0;

		public InventoryDTOBuilder setKey(InventoryKey inventoryKey) {
			this.key = inventoryKey;
			return this;
		}

		public InventoryDTOBuilder setInitialStock(Double initialStock) {
			this.initialStock = initialStock;
			return this;
		}

		public InventoryDTOBuilder setStockIn(Double stockIn) {
			this.stockIn = stockIn;
			return this;
		}

		public InventoryDTOBuilder setStockOut(Double stockOut) {
			this.stockOut = stockOut;
			return this;
		}

		public InventoryDTO createInventoryDTO() {
			return new InventoryDTO(key, initialStock, stockIn, stockOut);
		}
	}
}
