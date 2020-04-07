package br.com.cupuama.dto;

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

	private FruitDTO() {
	}

	private FruitDTO(Long id, String name, String initials) {
		this.id = id;
		this.name = name;
		this.initials = initials;
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

	public static class FruitDTOBuilder {
		private Long id;
		private String name;
		private String initials;

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

		public FruitDTO createFruitDTO() {
			return new FruitDTO(id, name, initials);
		}
	}
}
