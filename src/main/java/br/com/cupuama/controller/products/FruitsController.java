package br.com.cupuama.controller.products;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cupuama.controller.products.dto.FruitsDTO;
import br.com.cupuama.controller.products.mapper.FruitsMapper;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.services.products.FruitsService;

/**
 * All operations with a fruit will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/fruits")
public class FruitsController {

	private final FruitsService fruitsService;

	@Autowired
	public FruitsController(final FruitsService fruitsService) {
		this.fruitsService = fruitsService;
	}

	@GetMapping("/{fruitId}")
	public FruitsDTO getFruit(@PathVariable final long fruitId) throws EntityNotFoundException {
		return FruitsMapper.makeDTO(fruitsService.find(fruitId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FruitsDTO createFruit(@Valid @RequestBody final FruitsDTO fruitsDTO) throws ConstraintsViolationException {
		return fruitsService.create(fruitsDTO);
	}

	@DeleteMapping("/{fruitId}")
	public void deleteFruit(@PathVariable final long fruitId) throws EntityNotFoundException {
		fruitsService.delete(fruitId);
	}

	@PutMapping("/{fruitId}")
	public void update(@PathVariable final long fruitId, @RequestBody final FruitsDTO fruitsDTO)
			throws EntityNotFoundException {
		fruitsService.update(fruitId, fruitsDTO);
	}

	@GetMapping("/name/{name}")
	public List<FruitsDTO> findFruitsByName(@PathVariable final String name) {
		return FruitsMapper.makeListDTO(fruitsService.findByName(name));
	}

	@GetMapping
	public List<FruitsDTO> findAllFruits() {
		return FruitsMapper.makeListDTO(fruitsService.findAll());
	}
}
