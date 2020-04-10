package br.com.cupuama.domain.stock.controller;

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

import br.com.cupuama.domain.stock.dto.StocktakeTypeDTO;
import br.com.cupuama.domain.stock.entity.StocktakeType;
import br.com.cupuama.domain.stock.mapper.StocktakeTypeMapper;
import br.com.cupuama.domain.stock.service.StocktakeTypeService;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

/**
 * All operations with a stocktakeType will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/stocktakeTypes")
public class StocktakeTypeController {

	private final StocktakeTypeService stocktakeTypeService;

	@Autowired
	public StocktakeTypeController(final StocktakeTypeService stocktakeTypeService) {
		this.stocktakeTypeService = stocktakeTypeService;
	}

	@GetMapping("/{stocktakeTypeId}")
	public StocktakeTypeDTO getStocktakeType(@PathVariable final long stocktakeTypeId) throws EntityNotFoundException {
		return StocktakeTypeMapper.makeStocktakeTypeDTO(stocktakeTypeService.find(stocktakeTypeId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StocktakeTypeDTO createStocktakeType(@Valid @RequestBody final StocktakeTypeDTO stocktakeTypeDTO) throws ConstraintsViolationException {
		StocktakeType stocktakeType = StocktakeTypeMapper.makeStocktakeType(stocktakeTypeDTO);
		return StocktakeTypeMapper.makeStocktakeTypeDTO(stocktakeTypeService.create(stocktakeType));
	}

	@DeleteMapping("/{stocktakeTypeId}")
	public void deleteStocktakeType(@PathVariable final long stocktakeTypeId) throws EntityNotFoundException {
		stocktakeTypeService.delete(stocktakeTypeId);
	}

	@PutMapping("/{stocktakeTypeId}")
	public void updateLocation(@PathVariable final long stocktakeTypeId, @RequestParam final String name)
			throws EntityNotFoundException {
		stocktakeTypeService.update(stocktakeTypeId, name);
	}

	@GetMapping("/name/{name}")
	public List<StocktakeTypeDTO> findStocktakeTypesByName(@PathVariable final String name) {
		return StocktakeTypeMapper.makeStocktakeTypeDTOList(stocktakeTypeService.findByName(name));
	}

	@GetMapping
	public List<StocktakeTypeDTO> findAllStocktakeTypes() {
		return StocktakeTypeMapper.makeStocktakeTypeDTOList(stocktakeTypeService.findAll());
	}
}
