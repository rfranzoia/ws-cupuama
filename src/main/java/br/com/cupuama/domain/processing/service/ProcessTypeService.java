package br.com.cupuama.domain.processing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.processing.entity.ProcessType;
import br.com.cupuama.domain.processing.repository.ProcessTypeRepository;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some processType specific things.
 * <p/>
 */
@Service
public class ProcessTypeService extends DefaultService<ProcessType, Long> {

	public ProcessTypeService(final ProcessTypeRepository processTypeRepository) {
		super(processTypeRepository);
	}

	/**
	 * Update the location for a processType.
	 *
	 * @param processTypeId
	 * @param longitude
	 * @param latitude
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final Long processTypeId, final String name) throws EntityNotFoundException {
		ProcessType processType = findByIdChecked(processTypeId);
		processType.setName(name);
	}

	/**
	 * Find all processTypes by name.
	 *
	 * @param name
	 */
	public List<ProcessType> findByName(final String name) {
		return ((ProcessTypeRepository) repository).findByNameLike(name + "%");
	}

}
