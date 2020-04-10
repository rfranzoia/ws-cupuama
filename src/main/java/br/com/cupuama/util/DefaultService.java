package br.com.cupuama.util;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

public abstract class DefaultService<T extends DefaultEntity, ID> implements Service<T, ID> {

	protected static final Logger LOG = LoggerFactory.getLogger(DefaultService.class);
	
	protected final CrudRepository<T, ID> repository;
	
	public DefaultService(final CrudRepository<T, ID> repository) {
		this.repository = repository;
	}

	@Override
	public T find(final ID id) throws EntityNotFoundException {
		return findByIdChecked(id);
	}

	@Override
	public T create(T t) throws ConstraintsViolationException {
		try {
			T savedT = repository.save(t);
			return savedT;
		} catch (DataIntegrityViolationException e) {
			LOG.error("ConstraintsViolationException while creating a fruit: {}", t.toString(), e);
			throw new ConstraintsViolationException(e);
		}
		
	}

	@Override
	@Transactional
	public void delete(final ID id) throws EntityNotFoundException {
		T t = findByIdChecked(id);
		
		if (t instanceof AuditableEntity) {
			AuditableEntity at = (AuditableEntity) t;
			at.getAudit().setDateUpdated(ZonedDateTime.now());
			at.getAudit().setDeleted(true);
		} else {
			repository.delete(t);
		}
	}

	@Override
	public List<T> findAll() {
		List<T> list = new ArrayList<>();
		repository.findAll()
					.forEach(t -> {
						if (t instanceof AuditableEntity) {
							if (!((AuditableEntity) t).getAudit().getDeleted()) {
								list.add(t);
							}
						} else {
							list.add(t);
						}
					});
		return list;
	}

	protected List<T> findAny() {
		List<T> list = new ArrayList<>();
		repository.findAll()
					.forEach(t -> {
						list.add(t);
					});
		return list;
	}
	
	protected T findByIdChecked(final ID id) throws EntityNotFoundException {
		T t = repository.findById(id)
						.orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + id));
		Hibernate.initialize(t);
		return t;
	}
}
