package br.com.cupuama.util;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class DefaultAuditEntity implements AuditableEntity {

	private static final long serialVersionUID = 1L;
	
	@Embedded
	protected Audit audit = new Audit();
	
	@Override
	public Audit getAudit() {
		return audit;
	}

	@Override
	public void setAudit(Audit audit) {
		this.audit = audit;
	}
	
	
}
