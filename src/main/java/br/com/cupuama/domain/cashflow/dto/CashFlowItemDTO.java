package br.com.cupuama.domain.cashflow.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.cupuama.domain.cashflow.entity.CashFlowType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CashFlowItemDTO {

	private Long id;

	@NotNull(message = "Item Date cannot be null!")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date itemDate;

	@NotNull(message = "Document Type cannot be null!")
	private Long documentTypeId;
	
	private String documentTypeName;
	
	private String documentNumber;
	
	private String description;

	@NotNull(message = "Type cannot be null!")
	private CashFlowType type;

	@NotNull(message = "Value cannot be null!")
	private Double value;
	
	private CashFlowItemDTO(Long id, Date itemDate, Long documentTypeId, String documentTypeName, String documentNumber,
			String description, CashFlowType type, Double value) {
		this.id = id;
		this.itemDate = itemDate;
		this.documentTypeId = documentTypeId;
		this.documentTypeName = documentTypeName;
		this.documentNumber = documentNumber;
		this.description = description;
		this.type = type;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public Date getItemDate() {
		return itemDate;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public Long getDocumentTypeId() {
		return documentTypeId;
	}
	
	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public String getDescription() {
		return description;
	}

	public CashFlowType getType() {
		return type;
	}

	public Double getValue() {
		return value;
	}

	public static CashFlowItemDTOBuilder newBuilder() {
		return new CashFlowItemDTOBuilder();
	}
	
	public static class CashFlowItemDTOBuilder {
		
		private Long id;
		private Date itemDate;
		private Long documentTypeId;
		private String documentTypeName;
		private String documentNumber;
		private String description;
		private CashFlowType type;
		private Double value; 
		
		public CashFlowItemDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}
		
		public CashFlowItemDTOBuilder setItemDate(Date itemDate) {
			this.itemDate = itemDate;
			return this;
		}
		
		public CashFlowItemDTOBuilder setDocumentNumber(String documentNumber) {
			this.documentNumber = documentNumber;
			return this;
		}
		
		public CashFlowItemDTOBuilder setDocumentTypeId(Long documentTypeId) {
			this.documentTypeId = documentTypeId;
			return this;
		}
		
		public CashFlowItemDTOBuilder setDocumentTypeName(String documentTypeName) {
			this.documentTypeName = documentTypeName;
			return this;
		}
		
		public CashFlowItemDTOBuilder setDescription(String description) {
			this.description = description;
			return this;
		}
		
		public CashFlowItemDTOBuilder setType(CashFlowType type) {
			this.type = type;
			return this;
		}
		
		public CashFlowItemDTOBuilder setValue(Double value) {
			this.value = value;
			return this;
		}
		
		public CashFlowItemDTO createCashFlowItemDTO() {
			return new CashFlowItemDTO(id, itemDate, documentTypeId, documentTypeName, documentNumber, description, type, value);
		}
	}
}
