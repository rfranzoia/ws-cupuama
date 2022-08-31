package br.com.cupuama.controller.stock.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.controller.stock.dto.InventoryDTO;
import br.com.cupuama.domain.stock.Inventory;


public class InventoryMapper {
	public static Inventory makeInventory(InventoryDTO dto) {
		return new Inventory(InventoryKeyMapper.makeId(dto.getKey()), dto.getInitialStock(), dto.getStockIn(), dto.getStockOut());
	}

	public static InventoryDTO makeDTO(Inventory inventory) {
		InventoryDTO.InventoryDTOBuilder inventoryDTOBuilder = InventoryDTO.newBuilder()
				.setKey(InventoryKeyMapper.makeKey(inventory.getInventoryId()))
				.setInitialStock(inventory.getInitialStock())
				.setStockIn(inventory.getStockIn())
				.setStockOut(inventory.getStockOut());

		return inventoryDTOBuilder.createInventoryDTO();
	}

	public static List<InventoryDTO> makeListDTO(Collection<Inventory> inventories) {
		return inventories.stream()
				.map(InventoryMapper::makeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Inventory> makeList(Collection<InventoryDTO> dtos) {
		return dtos.stream()
				.map(InventoryMapper::makeInventory)
				.collect(Collectors.toList());
	}

}
