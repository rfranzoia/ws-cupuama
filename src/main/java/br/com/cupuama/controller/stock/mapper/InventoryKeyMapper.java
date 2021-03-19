package br.com.cupuama.controller.stock.mapper;

import br.com.cupuama.controller.products.mapper.FruitsMapper;
import br.com.cupuama.controller.products.mapper.ProductsMapper;
import br.com.cupuama.controller.stock.dto.InventoryKey;
import br.com.cupuama.domain.stock.InventoryId;


public class InventoryKeyMapper {
	public static InventoryId makeId(InventoryKey key) {
		return new InventoryId(key.getPeriod(), ProductsMapper.makeProduct(key.getProduct()),
				FruitsMapper.makeFruit(key.getFruit()), DepotMapper.makeDepot(key.getDepot()));
	}

	public static InventoryKey makeKey(InventoryId id) {
		return new InventoryKey(id.getPeriod(), ProductsMapper.makeDTO(id.getProduct()),
						FruitsMapper.makeDTO(id.getFruit()), DepotMapper.makeDTO(id.getDepot()));
	}

}
