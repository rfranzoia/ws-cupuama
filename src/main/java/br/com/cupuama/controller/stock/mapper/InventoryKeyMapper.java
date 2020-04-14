package br.com.cupuama.controller.stock.mapper;

import br.com.cupuama.controller.products.mapper.FruitMapper;
import br.com.cupuama.controller.products.mapper.ProductMapper;
import br.com.cupuama.controller.stock.dto.InventoryKey;
import br.com.cupuama.domain.stock.InventoryId;


public class InventoryKeyMapper {
	public static InventoryId makeId(InventoryKey key) {
		return new InventoryId(key.getPeriod(), ProductMapper.makeProduct(key.getProduct()), 
				FruitMapper.makeFruit(key.getFruit()), DepotMapper.makeDepot(key.getDepot()));
	}

	public static InventoryKey makeKey(InventoryId id) {
		return new InventoryKey(id.getPeriod(), ProductMapper.makeDTO(id.getProduct()), 
						FruitMapper.makeDTO(id.getFruit()), DepotMapper.makeDTO(id.getDepot()));
	}

}
