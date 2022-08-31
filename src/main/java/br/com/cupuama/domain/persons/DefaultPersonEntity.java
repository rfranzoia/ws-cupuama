package br.com.cupuama.domain.persons;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import br.com.cupuama.util.audit.DefaultAuditableEntity;

@MappedSuperclass
public class DefaultPersonEntity extends DefaultAuditableEntity {

	private static final long serialVersionUID = 1L;
	
	@Embedded
	protected Person person;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
}
