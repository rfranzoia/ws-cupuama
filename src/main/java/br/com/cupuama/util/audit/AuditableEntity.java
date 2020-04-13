package br.com.cupuama.util.audit;

import br.com.cupuama.util.DefaultEntity;

public interface AuditableEntity extends DefaultEntity {

	Audit getAudit();
	void setAudit(Audit audit);
	
}
