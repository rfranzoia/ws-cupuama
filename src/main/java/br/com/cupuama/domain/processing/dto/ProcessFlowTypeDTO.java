package br.com.cupuama.domain.processing.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cupuama.domain.stock.dto.DepotDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessFlowTypeDTO {
	
	@NotNull(message = "Key cannot be null!")
	private ProcessFlowTypeKey key;
	
	private DepotDTO depotIn;
	private DepotDTO depotOut;
	
	private ProcessFlowTypeDTO() {
	}

	private ProcessFlowTypeDTO(ProcessFlowTypeKey key, DepotDTO depotIn, DepotDTO depotOut) {
		this.key = key;
		this.depotIn = depotIn;
		this.depotOut = depotOut;
	}

	public static ProcessFlowTypeDTOBuilder newBuilder() {
		return new ProcessFlowTypeDTOBuilder();
	}

	@JsonProperty
	public ProcessFlowTypeKey getKey() {
		return key;
	}

	@JsonProperty
	public DepotDTO getDepotIn() {
		return depotIn;
	}
	
	@JsonProperty
	public DepotDTO getDepotOut() {
		return depotOut;
	}
	
	public static class ProcessFlowTypeDTOBuilder {
		private ProcessFlowTypeKey key;
		private DepotDTO depotIn;
		private DepotDTO depotOut;

		public ProcessFlowTypeDTOBuilder setKey(ProcessFlowTypeKey key) {
			this.key = key;
			return this;
		}

		public ProcessFlowTypeDTOBuilder setDepotIn(DepotDTO depotIn) {
			this.depotIn = depotIn;
			return this;
		}
		
		public ProcessFlowTypeDTOBuilder setDepotOut(DepotDTO depotOut) {
			this.depotOut = depotOut;
			return this;
		}

		public ProcessFlowTypeDTO createProcessFlowTypeDTO() {
			return new ProcessFlowTypeDTO(key, depotIn, depotOut);
		}
	}
}
