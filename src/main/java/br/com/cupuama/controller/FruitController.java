package br.com.cupuama.controller;

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

import br.com.cupuama.controller.mapper.products.FruitMapper;
import br.com.cupuama.domain.products.Fruit;
import br.com.cupuama.dto.FruitDTO;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.service.products.FruitService;

/**
 * All operations with a fruit will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/fruits")
public class FruitController {

	private final FruitService fruitService;

	@Autowired
	public FruitController(final FruitService fruitService) {
		this.fruitService = fruitService;
	}

	@GetMapping("/{fruitId}")
	public FruitDTO getFruit(@PathVariable final long fruitId) throws EntityNotFoundException {
		return FruitMapper.makeFruitDTO(fruitService.find(fruitId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FruitDTO createFruit(@Valid @RequestBody final FruitDTO fruitDTO) throws ConstraintsViolationException {
		Fruit fruit = FruitMapper.makeFruit(fruitDTO);
		return FruitMapper.makeFruitDTO(fruitService.create(fruit));
	}

	@DeleteMapping("/{fruitId}")
	public void deleteFruit(@PathVariable final long fruitId) throws EntityNotFoundException {
		fruitService.delete(fruitId);
	}

	@PutMapping("/{fruitId}")
	public void updateLocation(@PathVariable final long fruitId, @RequestBody final FruitDTO fruitDTO)
			throws EntityNotFoundException {
		fruitService.update(fruitId, fruitDTO);
	}

	@GetMapping("/name/{name}")
	public List<FruitDTO> findFruitsByName(@PathVariable final String name) {
		return FruitMapper.makeFruitDTOList(fruitService.findByName(name));
	}

	@GetMapping
	public List<FruitDTO> findAllFruits() {
		return FruitMapper.makeFruitDTOList(fruitService.findAll());
	}
}
