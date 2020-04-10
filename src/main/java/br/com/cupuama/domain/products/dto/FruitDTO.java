package br.com.cupuama.domain.products.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FruitDTO {
	
	private Long id;

	@NotNull(message = "Name cannot be null!")
	private String name;
	
	@NotNull(message = "Initials cannot be null!")
	private String initials;
	
	private String harvest;

	private FruitDTO() {
	}

	private FruitDTO(Long id, String name, String initials, String harvest) {
		this.id = id;
		this.name = name;
		this.initials = initials;
		this.harvest = harvest;
	}

	public static FruitDTOBuilder newBuilder() {
		return new FruitDTOBuilder();
	}

	@JsonProperty
	public Long getId() {
		return id;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public String getInitials() {
		return initials;
	}
	
	public String getHarvest() {
		return harvest;
	}



	public static class FruitDTOBuilder {
		private Long id;
		private String name;
		private String initials;
		private String harvest;

		public FruitDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public FruitDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}
		
		public FruitDTOBuilder setInitials(String initials) {
			this.initials = initials;
			return this;
		}
		
		public FruitDTOBuilder setHarvest(String harvest) {
			this.harvest = harvest;
			return this;
		}

		public FruitDTO createFruitDTO() {
			return new FruitDTO(id, name, initials, harvest);
		}
	}
}
