package br.com.cupuama.domain.products.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
	
	private Long id;

	@NotNull(message = "Name cannot be null!")
	private String name;
	
	@NotNull(message = "Unit cannot be null!")
	private String unit;

	private ProductDTO() {
	}

	private ProductDTO(Long id, String name, String unit) {
		this.id = id;
		this.name = name;
		this.unit = unit;
	}

	public static ProductDTOBuilder newBuilder() {
		return new ProductDTOBuilder();
	}

	@JsonProperty
	public Long getId() {
		return id;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	@JsonProperty
	public String getUnit() {
		return unit;
	}

	public static class ProductDTOBuilder {
		private Long id;
		private String name;
		private String unit;

		public ProductDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public ProductDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}
		
		public ProductDTOBuilder setUnit(String unit) {
			this.unit = unit;
			return this;
		}

		public ProductDTO createProductDTO() {
			return new ProductDTO(id, name, unit);
		}
	}
}
