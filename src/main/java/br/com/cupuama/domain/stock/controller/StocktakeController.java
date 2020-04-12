package br.com.cupuama.domain.stock.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cupuama.domain.stock.dto.StocktakeDTO;
import br.com.cupuama.domain.stock.mapper.StocktakeMapper;
import br.com.cupuama.domain.stock.service.StocktakeService;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

/**
 * All operations with a stocktake will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/stocktakes")
public class StocktakeController {

	private final StocktakeService stocktakeService;

	@Autowired
	public StocktakeController(final StocktakeService stocktakeService) {
		this.stocktakeService = stocktakeService;
	}

	@GetMapping("/{stocktakeId}")
	public StocktakeDTO getStocktake(@PathVariable final long stocktakeId) throws EntityNotFoundException {
		return StocktakeMapper.makeStocktakeDTO(stocktakeService.find(stocktakeId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StocktakeDTO createStocktake(@Valid @RequestBody final StocktakeDTO stocktakeDTO) throws ConstraintsViolationException {
		return stocktakeService.create(stocktakeDTO);
	}

	@DeleteMapping("/{stocktakeId}")
	public void deleteStocktake(@PathVariable final long stocktakeId) throws EntityNotFoundException {
		stocktakeService.delete(stocktakeId);
	}

	@GetMapping
	public List<StocktakeDTO> findAllStocktakes() {
		return StocktakeMapper.makeStocktakeDTOList(stocktakeService.findAll());
	}
}
