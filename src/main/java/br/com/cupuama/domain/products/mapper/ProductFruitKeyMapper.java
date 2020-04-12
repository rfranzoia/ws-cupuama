package br.com.cupuama.domain.products.mapper;

import br.com.cupuama.domain.products.dto.ProductFruitKey;
import br.com.cupuama.domain.products.entity.ProductFruitId;


public class ProductFruitKeyMapper {
	public static ProductFruitId makeId(ProductFruitKey key) {
		return new ProductFruitId(ProductMapper.makeProduct(key.getProduct()), 
										FruitMapper.makeFruit(key.getFruit()));
	}

	public static ProductFruitKey makeKey(ProductFruitId id) {
		return new ProductFruitKey(ProductMapper.makeProductDTO(id.getProduct()), 
										FruitMapper.makeFruitDTO(id.getFruit()));
	}

}
