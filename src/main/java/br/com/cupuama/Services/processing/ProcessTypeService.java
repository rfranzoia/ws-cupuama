package br.com.cupuama.Services.processing;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.controller.processing.dto.ProcessTypeDTO;
import br.com.cupuama.controller.processing.mapper.ProcessTypeMapper;
import br.com.cupuama.domain.processing.ProcessType;
import br.com.cupuama.domain.processing.repository.ProcessTypeRepository;
import br.com.cupuama.enums.FlowTypeModel;
import br.com.cupuama.exception.ConstraintsViolationException;
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

	public ProcessTypeDTO create(ProcessTypeDTO dto) throws ConstraintsViolationException {
		ProcessType processType = ProcessTypeMapper.makeProcessType(dto);
		return ProcessTypeMapper.makeDTO(create(processType));
	}
	/**
	 * Update the location for a processType.
	 *
	 * @param processTypeId
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final Long processTypeId, final String name, final FlowTypeModel flowTypeModel) throws EntityNotFoundException {
		ProcessType processType = findByIdChecked(processTypeId);
		processType.setName(name);
		processType.setFlowTypeModel(flowTypeModel);
		processType.getAudit().setDateUpdated(ZonedDateTime.now());
	}

	/**
	 * Find all processTypes by name.
	 *
	 * @param name
	 */
	public List<ProcessTypeDTO> findByName(final String name) {
		return ProcessTypeMapper.makeListDTO(((ProcessTypeRepository) repository).findByNameLike(name + "%"));
	}
	
	public List<ProcessTypeDTO> findByFlowTypeModel(final String flowTypeModel) {
		return ProcessTypeMapper.makeListDTO(((ProcessTypeRepository) repository).findByFlowTypeModel(flowTypeModel));
	}

}
