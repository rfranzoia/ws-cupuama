package br.com.cupuama.domain.processing.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessTypeDTO {
	
	private Long id;

	@NotNull(message = "Name cannot be null!")
	private String name;

	private ProcessTypeDTO() {
	}

	private ProcessTypeDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static ProcessTypeDTOBuilder newBuilder() {
		return new ProcessTypeDTOBuilder();
	}

	@JsonProperty
	public Long getId() {
		return id;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public static class ProcessTypeDTOBuilder {
		private Long id;
		private String name;

		public ProcessTypeDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public ProcessTypeDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ProcessTypeDTO createProcessTypeDTO() {
			return new ProcessTypeDTO(id, name);
		}
	}
}
