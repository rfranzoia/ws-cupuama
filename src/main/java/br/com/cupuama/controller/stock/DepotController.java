package br.com.cupuama.controller.stock;

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

import br.com.cupuama.controller.stock.dto.DepotDTO;
import br.com.cupuama.controller.stock.mapper.DepotMapper;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.services.stock.DepotService;

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
		return DepotMapper.makeDTO(depotService.find(depotId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public DepotDTO createDepot(@Valid @RequestBody final DepotDTO depotDTO) throws ConstraintsViolationException {
		return depotService.create(depotDTO);
	}

	@DeleteMapping("/{depotId}")
	public void deleteDepot(@PathVariable final long depotId) throws EntityNotFoundException {
		depotService.delete(depotId);
	}

	@PutMapping("/{depotId}")
	public void update(@PathVariable final long depotId, @RequestBody final DepotDTO depotDTO)
			throws EntityNotFoundException {
		depotService.update(depotId, depotDTO);
	}

	@GetMapping("/name/{name}")
	public List<DepotDTO> findDepotsByName(@PathVariable final String name) {
		return DepotMapper.makeListDTO(depotService.findByName(name));
	}

	@GetMapping
	public List<DepotDTO> findAllDepots() {
		return DepotMapper.makeListDTO(depotService.findAll());
	}
}
