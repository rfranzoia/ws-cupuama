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

import br.com.cupuama.domain.processing.dto.ProductionProcessDTO;
import br.com.cupuama.domain.processing.entity.ProductionProcess;
import br.com.cupuama.domain.processing.entity.ProductionProcessKey;
import br.com.cupuama.domain.processing.mapper.ProductionProcessMapper;
import br.com.cupuama.domain.processing.service.ProductionProcessService;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

/**
 * All operations with a productionProcess will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/productionProcesses")
public class ProductionProcessController {

	private final ProductionProcessService productionProcessService;

	@Autowired
	public ProductionProcessController(final ProductionProcessService productionProcessService) {
		this.productionProcessService = productionProcessService;
	}

	@GetMapping("/productionProcess")
	public ProductionProcessDTO getProductionProcess(@RequestBody final ProductionProcessKey key) throws EntityNotFoundException {
		return ProductionProcessMapper.makeProductionProcessDTO(productionProcessService.find(key));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductionProcessDTO createProductionProcess(@Valid @RequestBody final ProductionProcessDTO productionProcessDTO) throws ConstraintsViolationException {
		ProductionProcess productionProcess = ProductionProcessMapper.makeProductionProcess(productionProcessDTO);
		return ProductionProcessMapper.makeProductionProcessDTO(productionProcessService.create(productionProcess));
	}

	@DeleteMapping("/productionProcess")
	public void deleteProductionProcess(@RequestBody final ProductionProcessKey key) throws EntityNotFoundException {
		productionProcessService.delete(key);
	}

	@PutMapping("/productionProcess")
	public void updateLocation(@RequestBody final ProductionProcessDTO productionProcessDTO) throws EntityNotFoundException {
		productionProcessService.update(productionProcessDTO);
	}

	@GetMapping
	public List<ProductionProcessDTO> findAllProductionProcesss() {
		return ProductionProcessMapper.makeProductionProcessDTOList(productionProcessService.findAll());
	}
}
