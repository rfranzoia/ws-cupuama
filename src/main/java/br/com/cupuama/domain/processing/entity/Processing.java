package br.com.cupuama.domain.processing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
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

import br.com.cupuama.util.Audit;
import br.com.cupuama.util.AuditableEntity;

@Entity
@Table(name = "processing")
public class Processing implements AuditableEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "processing_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date processDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "process_type_id", nullable = false)
	private ProcessType processType;
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private ProcessStatus processStatus;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", nullable = true)
	private Customer customer;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplier_id", nullable = true)
	private Supplier supplier;
	
	@Column(name = "document_reference")
	private String documentReference;
	
	@Column
	private String remarks;
	
	@Embedded
	private Audit audit = new Audit();
	
	public Processing() {
	}

	public Processing(Long id, Date processDate, Customer customer, Supplier supplier, ProcessType processType,
			ProcessStatus processStatus, String documentReference, String remarks) {
		this.id = id;
		this.processDate = processDate;
		this.customer = customer;
		this.supplier = supplier;
		this.processType = processType;
		this.processStatus = processStatus;
		this.documentReference = documentReference;
		this.remarks = remarks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public ProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessType processType) {
		this.processType = processType;
	}

	public ProcessStatus getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(ProcessStatus processStatus) {
		this.processStatus = processStatus;
	}

	public String getDocumentReference() {
		return documentReference;
	}

	public void setDocumentReference(String documentReference) {
		this.documentReference = documentReference;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public Audit getAudit() {
		return audit;
	}

	@Override
	public void setAudit(Audit audit) {
		this.audit = audit;
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
		Processing other = (Processing) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Process [id=" + id + ", processDate=" + processDate + ", processType=" + processType
				+ ", processStatus=" + processStatus + ", customer=" + customer + ", supplier=" + supplier
				+ ", documentReference=" + documentReference + ", remarks=" + remarks + "]";
	}


	
}
