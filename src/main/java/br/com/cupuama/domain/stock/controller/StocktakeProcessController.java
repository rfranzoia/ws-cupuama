package br.com.cupuama.domain.stock.controller;

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

import br.com.cupuama.domain.stock.dto.StocktakeProcessDTO;
import br.com.cupuama.domain.stock.entity.StocktakeProcess;
import br.com.cupuama.domain.stock.entity.StocktakeProcessKey;
import br.com.cupuama.domain.stock.mapper.StocktakeProcessMapper;
import br.com.cupuama.domain.stock.service.StocktakeProcessService;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

/**
 * All operations with a stocktakeProcess will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/stocktakeProcesses")
public class StocktakeProcessController {

	private final StocktakeProcessService stocktakeProcessService;

	@Autowired
	public StocktakeProcessController(final StocktakeProcessService stocktakeProcessService) {
		this.stocktakeProcessService = stocktakeProcessService;
	}

	@GetMapping("/stocktakeProcess")
	public StocktakeProcessDTO getStocktakeProcess(@RequestBody final StocktakeProcessKey key) throws EntityNotFoundException {
		return StocktakeProcessMapper.makeStocktakeProcessDTO(stocktakeProcessService.find(key));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StocktakeProcessDTO createStocktakeProcess(@Valid @RequestBody final StocktakeProcessDTO stocktakeProcessDTO) throws ConstraintsViolationException {
		StocktakeProcess stocktakeProcess = StocktakeProcessMapper.makeStocktakeProcess(stocktakeProcessDTO);
		return StocktakeProcessMapper.makeStocktakeProcessDTO(stocktakeProcessService.create(stocktakeProcess));
	}

	@DeleteMapping("/stocktakeProcess")
	public void deleteStocktakeProcess(@RequestBody final StocktakeProcessKey key) throws EntityNotFoundException {
		stocktakeProcessService.delete(key);
	}

	@PutMapping("/stocktakeProcess")
	public void updateLocation(@RequestBody final StocktakeProcessDTO stocktakeProcessDTO) throws EntityNotFoundException {
		stocktakeProcessService.update(stocktakeProcessDTO);
	}

	@GetMapping
	public List<StocktakeProcessDTO> findAllStocktakeProcesss() {
		return StocktakeProcessMapper.makeStocktakeProcessDTOList(stocktakeProcessService.findAll());
	}
}
