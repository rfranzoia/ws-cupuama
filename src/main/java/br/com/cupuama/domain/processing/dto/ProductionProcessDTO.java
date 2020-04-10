package br.com.cupuama.domain.processing.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cupuama.domain.processing.entity.ProductionProcessKey;
import br.com.cupuama.domain.stock.entity.Depot;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductionProcessDTO {
	
	@NotNull(message = "ProductionProcessKey cannot be null!")
	private ProductionProcessKey key;
	
	@NotNull(message = "Depot cannot be null!")
	private Depot depot;
	
	private ProductionProcessDTO() {
	}

	private ProductionProcessDTO(ProductionProcessKey key, Depot depot) {
		this.key = key;
		this.depot = depot;
	}

	public static ProductionProcessDTOBuilder newBuilder() {
		return new ProductionProcessDTOBuilder();
	}

	@JsonProperty
	public ProductionProcessKey getKey() {
		return key;
	}

	@JsonProperty
	public Depot getDepot() {
		return depot;
	}
	
	public static class ProductionProcessDTOBuilder {
		private ProductionProcessKey key;
		private Depot depot;

		public ProductionProcessDTOBuilder setKey(ProductionProcessKey key) {
			this.key = key;
			return this;
		}

		public ProductionProcessDTOBuilder setDepot(Depot depot) {
			this.depot = depot;
			return this;
		}

		public ProductionProcessDTO createProductionProcessDTO() {
			return new ProductionProcessDTO(key, depot);
		}
	}
}
