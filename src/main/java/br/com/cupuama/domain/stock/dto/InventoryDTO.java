package br.com.cupuama.domain.stock.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cupuama.domain.stock.entity.InventoryKey;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryDTO {

	@NotNull(message = "Inventory Key cannot be null!")
	private InventoryKey inventoryKey;

	@NotNull(message = "Initial Stock Balance cannot be null!")
	private Double initialStock;

	@NotNull(message = "stockIn cannot be null!")
	private Double stockIn;
	
	@NotNull(message = "stockOut cannot be null!")
	private Double stockOut;
	
	private Double finalStock;

	public InventoryDTO(InventoryKey inventoryKey, Double initialStock, Double stockIn, Double stockOut) {
		this.inventoryKey = inventoryKey;
		this.initialStock = initialStock;
		this.stockIn = stockIn;
		this.stockOut = stockOut;
		this.finalStock = initialStock + stockIn - stockOut;
	}

	public static InventoryDTOBuilder newBuilder() {
		return new InventoryDTOBuilder();
	}


	@JsonProperty	
	public InventoryKey getInventoryKey() {
		return inventoryKey;
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
		private InventoryKey inventoryKey;
		private Double initialStock = 0.0;
		private Double stockIn = 0.0;
		private Double stockOut = 0.0;

		public InventoryDTOBuilder setInventoryKey(InventoryKey inventoryKey) {
			this.inventoryKey = inventoryKey;
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
			return new InventoryDTO(inventoryKey, initialStock, stockIn, stockOut);
		}
	}
}
