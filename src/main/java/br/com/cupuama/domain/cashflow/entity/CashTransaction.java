package br.com.cupuama.domain.cashflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.cupuama.util.audit.DefaultAuditableEntity;

@Entity
@Table(name = "cash_transaction")
public class CashTransaction extends DefaultAuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "item_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date itemDate;

	@Column(nullable = false, name = "document_number")
	private String documentNumber;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "document_type_id")
	private DocumentType documentType;
	
	@Column
	private String description;

	@Column
	@Enumerated(EnumType.STRING)
	private CashFlowType type;

	@Column(name = "value", precision = 12, scale = 2)
	private Double value;
	
	public CashTransaction() {
	}
	
	public CashTransaction(Long id, Date itemDate, String documentNumber, DocumentType documentType, String description,
			CashFlowType type, Double value) {
		
		this.id = id;
		this.itemDate = itemDate;
		this.documentNumber = documentNumber;
		this.documentType = documentType;
		this.description = description;
		this.type = type;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getItemDate() {
		return itemDate;
	}

	public void setItemDate(Date itemDate) {
		this.itemDate = itemDate;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CashFlowType getType() {
		return type;
	}

	public void setTipo(CashFlowType type) {
		this.type = type;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public void setType(CashFlowType type) {
		this.type = type;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CashTransaction other = (CashTransaction) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CashTransaction [itemDate=" + itemDate 
				+ ", documentNumber=" + documentNumber 
				+ ", documentType=" + documentType 
				+ ", description=" + description 
				+ ", type=" + type 
				+ ", value=" + value + "]";
	}


}
