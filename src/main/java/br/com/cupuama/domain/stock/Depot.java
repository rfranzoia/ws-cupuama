package br.com.cupuama.domain.stock;

import javax.persistence.Embedded;

import org.springframework.data.annotation.Id;

import br.com.cupuama.domain.Audit;
import br.com.cupuama.domain.AuditableEntity;

public class Depot implements AuditableEntity{

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	private String name;
	private boolean keepStock;
	
	@Embedded
	private Audit audit;

	@Override
	public Audit getAudit() {
		return this.audit;
	}

	@Override
	public void setAudit(Audit audit) {
		this.audit = audit;
	}
}
