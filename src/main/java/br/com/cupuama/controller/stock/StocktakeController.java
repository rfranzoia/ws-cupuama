package br.com.cupuama.controller.stock;

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

import br.com.cupuama.controller.stock.dto.StocktakeDTO;
import br.com.cupuama.controller.stock.mapper.StocktakeMapper;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.services.stock.StocktakeService;

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
		return StocktakeMapper.makeDTO(stocktakeService.find(stocktakeId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StocktakeDTO addStocktake(@Valid @RequestBody final StocktakeDTO stocktakeDTO) throws ConstraintsViolationException {
		return stocktakeService.addStocktake(stocktakeDTO);
	}

	@DeleteMapping("/{stocktakeId}")
	public void deleteStocktake(@PathVariable final long stocktakeId) throws EntityNotFoundException {
		stocktakeService.delete(stocktakeId);
	}

	@GetMapping
	public List<StocktakeDTO> findAllStocktakes() {
		return StocktakeMapper.makeListDTO(stocktakeService.findAll());
	}
}
