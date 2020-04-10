package br.com.cupuama.domain.stock.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cupuama.domain.stock.entity.Depot;
import br.com.cupuama.domain.stock.entity.StocktakeProcessKey;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StocktakeProcessDTO {
	
	@NotNull(message = "StocktakeProcessKey cannot be null!")
	private StocktakeProcessKey key;
	
	@NotNull(message = "Depot cannot be null!")
	private Depot depot;
	
	private StocktakeProcessDTO() {
	}

	private StocktakeProcessDTO(StocktakeProcessKey key, Depot depot) {
		this.key = key;
		this.depot = depot;
	}

	public static StocktakeProcessDTOBuilder newBuilder() {
		return new StocktakeProcessDTOBuilder();
	}

	@JsonProperty
	public StocktakeProcessKey getKey() {
		return key;
	}

	@JsonProperty
	public Depot getDepot() {
		return depot;
	}
	
	public static class StocktakeProcessDTOBuilder {
		private StocktakeProcessKey key;
		private Depot depot;

		public StocktakeProcessDTOBuilder setKey(StocktakeProcessKey key) {
			this.key = key;
			return this;
		}

		public StocktakeProcessDTOBuilder setDepot(Depot depot) {
			this.depot = depot;
			return this;
		}

		public StocktakeProcessDTO createStocktakeProcessDTO() {
			return new StocktakeProcessDTO(key, depot);
		}
	}
}
