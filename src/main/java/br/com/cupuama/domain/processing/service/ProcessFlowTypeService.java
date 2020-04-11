package br.com.cupuama.domain.processing.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.processing.dto.ProcessFlowTypeDTO;
import br.com.cupuama.domain.processing.entity.ProcessFlowType;
import br.com.cupuama.domain.processing.entity.ProcessFlowTypeKey;
import br.com.cupuama.domain.processing.repository.ProcessFlowTypeRepository;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some processFlowType specific things.
 * <p/>
 */
@Service
public class ProcessFlowTypeService extends DefaultService<ProcessFlowType, ProcessFlowTypeKey> {

	public ProcessFlowTypeService(final ProcessFlowTypeRepository processFlowTypeRepository) {
		super(processFlowTypeRepository);
	}
	
	/**
	 * Update a ProcessFlowType depot.
	 *
	 * @param dto
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final ProcessFlowTypeDTO dto) throws EntityNotFoundException {
		ProcessFlowType processFlowType = findByIdChecked(dto.getKey());
		processFlowType.setDepotIn(dto.getDepotIn());
		processFlowType.setDepotOut(dto.getDepotOut());
	}

}
