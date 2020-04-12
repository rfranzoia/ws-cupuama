package br.com.cupuama.domain.processing.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.processing.dto.ProcessFlowTypeDTO;
import br.com.cupuama.domain.processing.entity.ProcessFlowType;
import br.com.cupuama.domain.processing.entity.ProcessFlowTypeId;
import br.com.cupuama.domain.processing.mapper.ProcessFlowTypeKeyMapper;
import br.com.cupuama.domain.processing.mapper.ProcessFlowTypeMapper;
import br.com.cupuama.domain.processing.repository.ProcessFlowTypeRepository;
import br.com.cupuama.domain.stock.mapper.DepotMapper;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some processFlowType specific things.
 * <p/>
 */
@Service
public class ProcessFlowTypeService extends DefaultService<ProcessFlowType, ProcessFlowTypeId> {

	public ProcessFlowTypeService(final ProcessFlowTypeRepository processFlowTypeRepository) {
		super(processFlowTypeRepository);
	}
	
	@Transactional
	public ProcessFlowTypeDTO create(ProcessFlowTypeDTO processFlowTypeDTO) throws ConstraintsViolationException {
		ProcessFlowType processFlowType = ProcessFlowTypeMapper.makeProcessFlowType(processFlowTypeDTO);
		return ProcessFlowTypeMapper.makeDTO(create(processFlowType));
	}
	
	/**
	 * Update a ProcessFlowType depot.
	 *
	 * @param dto
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final ProcessFlowTypeDTO dto) throws EntityNotFoundException {
		ProcessFlowType processFlowType = findByIdChecked(ProcessFlowTypeKeyMapper.makeId(dto.getKey()));
		processFlowType.setDepotIn(DepotMapper.makeDepot(dto.getDepotIn()));
		processFlowType.setDepotOut(DepotMapper.makeDepot(dto.getDepotOut()));
	}

}
