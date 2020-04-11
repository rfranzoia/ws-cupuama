package br.com.cupuama.domain.processing.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cupuama.domain.processing.dto.ProcessTypeDTO;
import br.com.cupuama.domain.processing.entity.FlowTypeModel;
import br.com.cupuama.domain.processing.entity.ProcessType;
import br.com.cupuama.domain.processing.mapper.ProcessTypeMapper;
import br.com.cupuama.domain.processing.service.ProcessTypeService;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

/**
 * All operations with a processType will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/processTypes")
public class ProcessTypeController {

	private final ProcessTypeService processTypeService;

	@Autowired
	public ProcessTypeController(final ProcessTypeService processTypeService) {
		this.processTypeService = processTypeService;
	}

	@GetMapping("/{processTypeId}")
	public ProcessTypeDTO getProcessType(@PathVariable final long processTypeId) throws EntityNotFoundException {
		return ProcessTypeMapper.makeProcessTypeDTO(processTypeService.find(processTypeId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProcessTypeDTO createProcessType(@Valid @RequestBody final ProcessTypeDTO processTypeDTO) throws ConstraintsViolationException {
		ProcessType processType = ProcessTypeMapper.makeProcessType(processTypeDTO);
		return ProcessTypeMapper.makeProcessTypeDTO(processTypeService.create(processType));
	}

	@DeleteMapping("/{processTypeId}")
	public void deleteProcessType(@PathVariable final long processTypeId) throws EntityNotFoundException {
		processTypeService.delete(processTypeId);
	}

	@PutMapping("/{processTypeId}")
	public void updateLocation(@PathVariable final long processTypeId, @RequestParam final String name, @RequestParam final FlowTypeModel model)
			throws EntityNotFoundException {
		processTypeService.update(processTypeId, name, model);
	}

	@GetMapping("/name/{name}")
	public List<ProcessTypeDTO> findProcessTypesByName(@PathVariable final String name) {
		return ProcessTypeMapper.makeProcessTypeDTOList(processTypeService.findByName(name));
	}

	@GetMapping
	public List<ProcessTypeDTO> findAllProcessTypes() {
		return ProcessTypeMapper.makeProcessTypeDTOList(processTypeService.findAll());
	}
}
