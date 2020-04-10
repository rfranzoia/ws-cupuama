package br.com.cupuama.domain.stock.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StocktakeTypeDTO {
	
	private Long id;

	@NotNull(message = "Name cannot be null!")
	private String name;

	private StocktakeTypeDTO() {
	}

	private StocktakeTypeDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static StocktakeTypeDTOBuilder newBuilder() {
		return new StocktakeTypeDTOBuilder();
	}

	@JsonProperty
	public Long getId() {
		return id;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public static class StocktakeTypeDTOBuilder {
		private Long id;
		private String name;

		public StocktakeTypeDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public StocktakeTypeDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public StocktakeTypeDTO createStocktakeTypeDTO() {
			return new StocktakeTypeDTO(id, name);
		}
	}
}
