package br.com.cupuama.domain.processing.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cupuama.domain.processing.entity.ProcessStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessingDTO {
	
	private Long id;

	@NotNull(message = "ProcessDate cannot be null!")
	private Date processDate;
	
	@NotNull(message = "ProcessType cannot be null!")
	private ProcessTypeDTO processType;
	
	private ProcessStatus processStatus;
	
	private CustomerDTO customer;
	private SupplierDTO supplier;
	private String documentReference;
	private String remarks;
	
	private List<ProcessingDetailDTO> details;
	
	public ProcessingDTO(Long id, Date processDate, ProcessTypeDTO processType, ProcessStatus processStatus, CustomerDTO customer,
			SupplierDTO supplier, String documentReference, String remarks, List<ProcessingDetailDTO> details) {
		this.id = id;
		this.processDate = processDate;
		this.processType = processType;
		this.processStatus = processStatus;
		this.customer = customer;
		this.supplier = supplier;
		this.documentReference = documentReference;
		this.remarks = remarks;
		this.details = new ArrayList<>();
		this.details.addAll(details);
	}

	@JsonProperty
	public Long getId() {
		return id;
	}
	
	@JsonProperty
	public Date getProcessDate() {
		return processDate;
	}

	@JsonProperty
	public ProcessTypeDTO getProcessType() {
		return processType;
	}

	@JsonProperty
	public ProcessStatus getProcessStatus() {
		return processStatus;
	}
	
	@JsonProperty
	public CustomerDTO getCustomer() {
		return customer;
	}

	@JsonProperty
	public SupplierDTO getSupplier() {
		return supplier;
	}

	@JsonProperty
	public String getDocumentReference() {
		return documentReference;
	}

	@JsonProperty
	public String getRemarks() {
		return remarks;
	}

	public static CustomerDTOBuilder newBuilder() {
		return new CustomerDTOBuilder();
	}
	
	public static class CustomerDTOBuilder {
		private Long id;
		private Date processDate;
		private ProcessTypeDTO processType;
		private ProcessStatus processStatus;
		private CustomerDTO customer;
		private SupplierDTO supplier;
		private String documentReference;
		private String remarks;
		private List<ProcessingDetailDTO> details;

		public CustomerDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public CustomerDTOBuilder setProcessDate(Date processDate) {
			this.processDate = processDate;
			return this;
		}

		public CustomerDTOBuilder setProcessType(ProcessTypeDTO processType) {
			this.processType = processType;
			return this;
		}
		
		public CustomerDTOBuilder setProcessStatus(ProcessStatus processStatus) {
			this.processStatus = processStatus;
			return this;
		}

		public CustomerDTOBuilder setCustomer(CustomerDTO customer) {
			this.customer = customer;
			return this;
		}

		public CustomerDTOBuilder setSupplier(SupplierDTO supplier) {
			this.supplier = supplier;
			return this;
		}

		public CustomerDTOBuilder setDocumentReference(String documentReference) {
			this.documentReference = documentReference;
			return this;
		}

		public CustomerDTOBuilder setRemarks(String remarks) {
			this.remarks = remarks;
			return this;
		}
		
		public CustomerDTOBuilder setDetails(List<ProcessingDetailDTO> details) {
			this.details = new ArrayList<>();
			this.details.addAll(details);
			return this;
		}

		public ProcessingDTO createCustomerDTO() {
			return new ProcessingDTO(id, processDate, processType, processStatus, customer, supplier, documentReference, remarks, details);
		}
	}
}
