package br.com.cupuama.domain.stock.mapper;

import br.com.cupuama.domain.products.mapper.FruitMapper;
import br.com.cupuama.domain.products.mapper.ProductMapper;
import br.com.cupuama.domain.stock.dto.InventoryKey;
import br.com.cupuama.domain.stock.entity.InventoryId;


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
