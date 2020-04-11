package br.com.cupuama.domain.processing.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cupuama.domain.processing.entity.FlowTypeModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessTypeDTO {
	
	private Long id;

	@NotNull(message = "Name cannot be null!")
	private String name;
	
	@NotNull(message = "FlowTypeModel cannot be null!")
	private FlowTypeModel model;

	private ProcessTypeDTO() {
	}

	private ProcessTypeDTO(Long id, String name, FlowTypeModel model) {
		this.id = id;
		this.name = name;
		this.model = model;
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
	
	@JsonProperty
	public FlowTypeModel getFlowTypeModel() {
		return model;
	}

	public static class ProcessTypeDTOBuilder {
		private Long id;
		private String name;
		private FlowTypeModel model;

		public ProcessTypeDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public ProcessTypeDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ProcessTypeDTOBuilder setModel(FlowTypeModel model) {
			this.model = model;
			return this;
		}
		
		public ProcessTypeDTO createProcessTypeDTO() {
			return new ProcessTypeDTO(id, name, model);
		}
	}
}
