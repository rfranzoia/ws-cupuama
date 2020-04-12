package br.com.cupuama.domain.stock.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.stock.dto.InventoryDTO;
import br.com.cupuama.domain.stock.entity.Inventory;


public class InventoryMapper {
	public static Inventory makeInventory(InventoryDTO dto) {
		return new Inventory(dto.getId(), dto.getInitialStock(), dto.getStockIn(), dto.getStockOut());
	}

	public static InventoryDTO makeInventoryDTO(Inventory inventory) {
		InventoryDTO.InventoryDTOBuilder inventoryDTOBuilder = InventoryDTO.newBuilder()
				.setId(inventory.getInventoryId())
				.setInitialStock(inventory.getInitialStock())
				.setStockIn(inventory.getStockIn())
				.setStockOut(inventory.getStockOut());

		return inventoryDTOBuilder.createInventoryDTO();
	}

	public static List<InventoryDTO> makeInventoryDTOList(Collection<Inventory> inventories) {
		return inventories.stream()
				.map(InventoryMapper::makeInventoryDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Inventory> makeInventoryList(Collection<InventoryDTO> dtos) {
		return dtos.stream()
				.map(InventoryMapper::makeInventory)
				.collect(Collectors.toList());
	}

}
