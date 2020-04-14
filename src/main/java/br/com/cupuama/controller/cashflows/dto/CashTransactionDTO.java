package br.com.cupuama.controller.cashflows.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cupuama.domain.cashflow.CashFlowType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CashTransactionDTO {

	private Long id;

	@NotNull(message = "Item Date cannot be null!")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date itemDate;

	@NotNull(message = "Document Type cannot be null!")
	private DocumentTypeDTO documentType;
	
	private String documentNumber;
	
	private String description;

	@NotNull(message = "Type cannot be null!")
	private CashFlowType cashFlowType;

	@NotNull(message = "Value cannot be null!")
	private Double value;
	
	private CashTransactionDTO(Long id, Date itemDate, DocumentTypeDTO documentType, String documentNumber,
			String description, CashFlowType type, Double value) {
		this.id = id;
		this.itemDate = itemDate;
		this.documentType = documentType;
		this.documentNumber = documentNumber;
		this.description = description;
		this.cashFlowType = type;
		this.value = value;
	}

	@JsonProperty
	public Long getId() {
		return id;
	}

	@JsonProperty
	public Date getItemDate() {
		return itemDate;
	}

	@JsonProperty
	public String getDocumentNumber() {
		return documentNumber;
	}

	@JsonProperty
	public DocumentTypeDTO getDocumentType() {
		return documentType;
	}
	
	@JsonProperty
	public String getDescription() {
		return description;
	}

	@JsonProperty
	public CashFlowType getCashFlowType() {
		return cashFlowType;
	}

	@JsonProperty
	public Double getValue() {
		return value;
	}

	public static CashTransactionDTOBuilder newBuilder() {
		return new CashTransactionDTOBuilder();
	}
	
	public static class CashTransactionDTOBuilder {
		
		private Long id;
		private Date itemDate;
		private DocumentTypeDTO documentType;
		private String documentNumber;
		private String description;
		private CashFlowType cashFlowType;
		private Double value; 
		
		public CashTransactionDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}
		
		public CashTransactionDTOBuilder setItemDate(Date itemDate) {
			this.itemDate = itemDate;
			return this;
		}
		
		public CashTransactionDTOBuilder setDocumentNumber(String documentNumber) {
			this.documentNumber = documentNumber;
			return this;
		}
		
		public CashTransactionDTOBuilder setDocumentType(DocumentTypeDTO documentType) {
			this.documentType = documentType;
			return this;
		}
		
		public CashTransactionDTOBuilder setDescription(String description) {
			this.description = description;
			return this;
		}
		
		public CashTransactionDTOBuilder setCashFlowType(CashFlowType type) {
			this.cashFlowType = type;
			return this;
		}
		
		public CashTransactionDTOBuilder setValue(Double value) {
			this.value = value;
			return this;
		}
		
		public CashTransactionDTO createDTO() {
			return new CashTransactionDTO(id, itemDate, documentType, documentNumber, description, cashFlowType, value);
		}
	}
}
