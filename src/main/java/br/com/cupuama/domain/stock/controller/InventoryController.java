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

import br.com.cupuama.domain.stock.dto.InventoryDTO;
import br.com.cupuama.domain.stock.entity.Inventory;
import br.com.cupuama.domain.stock.entity.StocktakeInOut;
import br.com.cupuama.domain.stock.mapper.InventoryMapper;
import br.com.cupuama.domain.stock.service.InventoryService;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

/**
 * All operations with a inventory will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/cashflows")
public class InventoryController {

	private final InventoryService inventoryService;

	@Autowired
	public InventoryController(final InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@GetMapping("/inventory")
	public InventoryDTO getInventory(@RequestBody final InventoryDTO inventoryDTO) throws EntityNotFoundException {
		return InventoryMapper.makeInventoryDTO(inventoryService.find(inventoryDTO.getInventoryKey()));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public InventoryDTO createInventory(@Valid @RequestBody final InventoryDTO inventoryDTO) throws ConstraintsViolationException {
		Inventory inventory = InventoryMapper.makeInventory(inventoryDTO);
		return InventoryMapper.makeInventoryDTO(inventoryService.create(inventory));
	}

	@DeleteMapping("/{period}/product/{produtcId}/fruit/{fruitId}/depot/{depotId}")
	public void deleteInventory(@PathVariable final String period, 
								@PathVariable final Long productId, 
								@PathVariable final Long fruitId, 
								@PathVariable final Long depotId) throws EntityNotFoundException {
		inventoryService.delete(period, productId, fruitId, depotId);
	}

	@PutMapping("/previousBalance")
	public void updatePreviousBalance(@RequestBody final InventoryDTO inventoryDTO) throws EntityNotFoundException, ConstraintsViolationException {
		inventoryService.updateOrCreateInitialStock(inventoryDTO.getInventoryKey(), inventoryDTO);
	}
	
	@PutMapping("/stockIn/{inventoryKey}")
	public void updateStockIn(@RequestBody final InventoryDTO inventoryDTO)
			throws EntityNotFoundException, ConstraintsViolationException {
		inventoryService.updateStockInOut(inventoryDTO.getInventoryKey(), StocktakeInOut.IN, inventoryDTO);
	}
	
	@PutMapping("/stockOut/{inventoryKey}")
	public void updateStockOut(@RequestBody final InventoryDTO inventoryDTO)
			throws EntityNotFoundException, ConstraintsViolationException {
		inventoryService.updateStockInOut(inventoryDTO.getInventoryKey(), StocktakeInOut.IN, inventoryDTO);
	}


	@GetMapping
	public List<InventoryDTO> findAllInventorys() {
		return InventoryMapper.makeInventoryDTOList(inventoryService.findAll());
	}
}
