package br.com.cupuama.controller.products.mapper;

import br.com.cupuama.controller.products.dto.ProductFruitKey;
import br.com.cupuama.domain.products.ProductFruitId;


public class ProductFruitKeyMapper {
	public static ProductFruitId makeId(ProductFruitKey key) {
		return new ProductFruitId(ProductsMapper.makeProduct(key.getProduct()),
										FruitsMapper.makeFruit(key.getFruit()));
	}

	public static ProductFruitKey makeKey(ProductFruitId id) {
		return new ProductFruitKey(ProductsMapper.makeDTO(id.getProduct()),
										FruitsMapper.makeDTO(id.getFruit()));
	}

}
