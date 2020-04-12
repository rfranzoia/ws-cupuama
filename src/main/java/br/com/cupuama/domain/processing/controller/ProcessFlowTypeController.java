package br.com.cupuama.domain.processing.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cupuama.domain.processing.dto.ProcessFlowTypeDTO;
import br.com.cupuama.domain.processing.dto.ProcessFlowTypeKey;
import br.com.cupuama.domain.processing.mapper.ProcessFlowTypeIdMapper;
import br.com.cupuama.domain.processing.mapper.ProcessFlowTypeMapper;
import br.com.cupuama.domain.processing.service.ProcessFlowTypeService;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

/**
 * All operations with a processFlowType will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/processFlowTypes")
public class ProcessFlowTypeController {

	private final ProcessFlowTypeService processFlowTypeService;

	@Autowired
	public ProcessFlowTypeController(final ProcessFlowTypeService processFlowTypeService) {
		this.processFlowTypeService = processFlowTypeService;
	}

	@GetMapping("/processFlowType")
	public ProcessFlowTypeDTO getProcessFlowType(@RequestBody final ProcessFlowTypeKey key) throws EntityNotFoundException {
		return ProcessFlowTypeMapper.makeProcessFlowTypeDTO(processFlowTypeService.find(ProcessFlowTypeIdMapper.makeId(key)));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProcessFlowTypeDTO createProcessFlowType(@Valid @RequestBody final ProcessFlowTypeDTO processFlowTypeDTO) throws ConstraintsViolationException {
		return processFlowTypeService.create(processFlowTypeDTO);
	}

	@DeleteMapping("/processFlowType")
	public void deleteProcessFlowType(@RequestBody final ProcessFlowTypeKey key) throws EntityNotFoundException {
		processFlowTypeService.delete(ProcessFlowTypeIdMapper.makeId(key));
	}

	@PutMapping("/processFlowType")
	public void updateLocation(@RequestBody final ProcessFlowTypeDTO processFlowTypeDTO) throws EntityNotFoundException {
		processFlowTypeService.update(processFlowTypeDTO);
	}

	@GetMapping
	public List<ProcessFlowTypeDTO> findAllProcessFlowTypes() {
		return ProcessFlowTypeMapper.makeProcessFlowTypeDTOList(processFlowTypeService.findAll());
	}
}
