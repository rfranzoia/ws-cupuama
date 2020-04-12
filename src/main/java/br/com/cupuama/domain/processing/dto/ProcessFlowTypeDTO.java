package br.com.cupuama.domain.processing.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cupuama.domain.processing.entity.ProcessFlowTypeId;
import br.com.cupuama.domain.stock.entity.Depot;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessFlowTypeDTO {
	
	@NotNull(message = "Key cannot be null!")
	private ProcessFlowTypeId key;
	
	private Depot depotIn;
	private Depot depotOut;
	
	private ProcessFlowTypeDTO() {
	}

	private ProcessFlowTypeDTO(ProcessFlowTypeId key, Depot depotIn, Depot depotOut) {
		this.key = key;
		this.depotIn = depotIn;
		this.depotOut = depotOut;
	}

	public static ProcessFlowTypeDTOBuilder newBuilder() {
		return new ProcessFlowTypeDTOBuilder();
	}

	@JsonProperty
	public ProcessFlowTypeId getKey() {
		return key;
	}

	@JsonProperty
	public Depot getDepotIn() {
		return depotIn;
	}
	
	@JsonProperty
	public Depot getDepotOut() {
		return depotOut;
	}
	
	public static class ProcessFlowTypeDTOBuilder {
		private ProcessFlowTypeId key;
		private Depot depotIn;
		private Depot depotOut;

		public ProcessFlowTypeDTOBuilder setKey(ProcessFlowTypeId key) {
			this.key = key;
			return this;
		}

		public ProcessFlowTypeDTOBuilder setDepotIn(Depot depotIn) {
			this.depotIn = depotIn;
			return this;
		}
		
		public ProcessFlowTypeDTOBuilder setDepotOut(Depot depotOut) {
			this.depotOut = depotOut;
			return this;
		}

		public ProcessFlowTypeDTO createProcessFlowTypeDTO() {
			return new ProcessFlowTypeDTO(key, depotIn, depotOut);
		}
	}
}
