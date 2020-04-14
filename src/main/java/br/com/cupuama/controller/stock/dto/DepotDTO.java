package br.com.cupuama.controller.stock.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepotDTO {
	
	private Long id;

	@NotNull(message = "Name cannot be null!")
	private String name;
	
	@NotNull(message = "KeepStock cannot be null!")
	private boolean keepStock;

	private DepotDTO() {
	}

	private DepotDTO(Long id, String name, boolean keepStock) {
		this.id = id;
		this.name = name;
		this.keepStock = keepStock;
	}

	public static DepotDTOBuilder newBuilder() {
		return new DepotDTOBuilder();
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
	public boolean isKeepStock() {
		return keepStock;
	}

	public static class DepotDTOBuilder {
		private Long id;
		private String name;
		private boolean keepStock;

		public DepotDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public DepotDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}
		
		public DepotDTOBuilder setKeepStock(boolean keepStock) {
			this.keepStock = keepStock;
			return this;
		}

		public DepotDTO createDepotDTO() {
			return new DepotDTO(id, name, keepStock);
		}
	}
}
