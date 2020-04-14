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
import br.com.cupuama.domain.processing.mapper.ProcessTypeMapper;
import br.com.cupuama.domain.processing.service.ProcessTypeService;
import br.com.cupuama.enums.FlowTypeModel;
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
		return ProcessTypeMapper.makeDTO(processTypeService.find(processTypeId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProcessTypeDTO createProcessType(@Valid @RequestBody final ProcessTypeDTO processTypeDTO) throws ConstraintsViolationException {
		return processTypeService.create(processTypeDTO);
	}

	@DeleteMapping("/{processTypeId}")
	public void deleteProcessType(@PathVariable final long processTypeId) throws EntityNotFoundException {
		processTypeService.delete(processTypeId);
	}

	@PutMapping("/{processTypeId}")
	public void update(@PathVariable final long processTypeId, @RequestParam final String name, @RequestParam final FlowTypeModel model)
			throws EntityNotFoundException {
		processTypeService.update(processTypeId, name, model);
	}

	@GetMapping("/name/{name}")
	public List<ProcessTypeDTO> findProcessTypesByName(@PathVariable final String name) {
		return processTypeService.findByName(name);
	}
	
	@GetMapping("/flowTypeModel/{flowTypeModel}")
	public List<ProcessTypeDTO> findProcessTypesByFlowTypeModel(@PathVariable final String flowTypeModel) {
		return processTypeService.findByFlowTypeModel(flowTypeModel);
	}

	@GetMapping
	public List<ProcessTypeDTO> findAllProcessTypes() {
		return ProcessTypeMapper.makeListDTO(processTypeService.findAll());
	}
}
