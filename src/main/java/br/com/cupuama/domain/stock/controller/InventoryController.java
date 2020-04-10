package br.com.cupuama.domain.stock.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cupuama.domain.stock.dto.InventoryDTO;
import br.com.cupuama.domain.stock.entity.InventoryKey;
import br.com.cupuama.domain.stock.mapper.InventoryMapper;
import br.com.cupuama.domain.stock.service.InventoryService;
import br.com.cupuama.exception.EntityNotFoundException;

/**
 * All operations with a inventory will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/inventories")
public class InventoryController {

	private final InventoryService inventoryService;

	@Autowired
	public InventoryController(final InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@GetMapping("/inventory")
	public InventoryDTO getInventory(@RequestBody final InventoryKey key) throws EntityNotFoundException {
		return InventoryMapper.makeInventoryDTO(inventoryService.find(key));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public InventoryDTO addInventory(@Valid @RequestBody final InventoryDTO inventoryDTO) throws EntityNotFoundException {
		return inventoryService.addInventory(inventoryDTO);
	}

	@GetMapping
	public List<InventoryDTO> findAllOrderByPeriodProductFruitDepot() {
		return inventoryService.findAllOrderByPeriodProductFruitDepot();
	}
}
