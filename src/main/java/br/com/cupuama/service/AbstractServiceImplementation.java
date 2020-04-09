package br.com.cupuama.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.service.Service;

public abstract class AbstractServiceImplementation<T, ID> implements Service<T, ID> {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractServiceImplementation.class);
	
	protected final CrudRepository<T, ID> repository;
	
	public AbstractServiceImplementation(final CrudRepository<T, ID> repository) {
		this.repository = repository;
	}

	@Override
	public T find(ID id) throws EntityNotFoundException {
		return findByIdChecked(id);
	}

	@Override
	public T create(T t) throws ConstraintsViolationException {
		try {
			T savedT = repository.save(t);
			return savedT;
		} catch (DataIntegrityViolationException e) {
			LOG.warn("ConstraintsViolationException while creating a fruit: {}", t, e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		
	}

	@Override
	public void delete(ID id) throws EntityNotFoundException {
		T t = findByIdChecked(id);
		repository.delete(t);
	}

	@Override
	public List<T> findAll() {
		List<T> list = new ArrayList<>();
		repository.findAll().forEach(c -> {
			list.add(c);
		});
		return list;
	}

	protected T findByIdChecked(ID id) throws EntityNotFoundException {
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + id));
	}
}
