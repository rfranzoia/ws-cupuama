package br.com.cupuama.util;

public interface AuditableEntity extends DefaultEntity {

	Audit getAudit();
	void setAudit(Audit audit);
	
}
