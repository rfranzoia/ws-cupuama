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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cupuama.domain.stock.dto.DepotDTO;
import br.com.cupuama.domain.stock.entity.Depot;
import br.com.cupuama.domain.stock.mapper.DepotMapper;
import br.com.cupuama.domain.stock.service.DepotService;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

/**
 * All operations with a depot will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/depots")
public class DepotController {

	private final DepotService depotService;

	@Autowired
	public DepotController(final DepotService depotService) {
		this.depotService = depotService;
	}

	@GetMapping("/{depotId}")
	public DepotDTO getDepot(@PathVariable final long depotId) throws EntityNotFoundException {
		return DepotMapper.makeDepotDTO(depotService.find(depotId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DepotDTO createDepot(@Valid @RequestBody final DepotDTO depotDTO) throws ConstraintsViolationException {
		Depot depot = DepotMapper.makeDepot(depotDTO);
		return DepotMapper.makeDepotDTO(depotService.create(depot));
	}

	@DeleteMapping("/{depotId}")
	public void deleteDepot(@PathVariable final long depotId) throws EntityNotFoundException {
		depotService.delete(depotId);
	}

	@PutMapping("/{depotId}")
	public void updateLocation(@PathVariable final long depotId, @RequestBody final DepotDTO depotDTO)
			throws EntityNotFoundException {
		depotService.update(depotId, depotDTO);
	}

	@GetMapping("/name/{name}")
	public List<DepotDTO> findDepotsByName(@PathVariable final String name) {
		return DepotMapper.makeDepotDTOList(depotService.findByName(name));
	}

	@GetMapping
	public List<DepotDTO> findAllDepots() {
		return DepotMapper.makeDepotDTOList(depotService.findAll());
	}
}
