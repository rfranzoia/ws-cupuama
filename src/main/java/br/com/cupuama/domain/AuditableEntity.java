package br.com.cupuama.domain;

public interface AuditableEntity extends DefaultEntity {

	Audit getAudit();
	void setAudit(Audit audit);
	
}
