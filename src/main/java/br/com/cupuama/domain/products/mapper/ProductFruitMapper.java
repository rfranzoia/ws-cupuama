package br.com.cupuama.domain.products.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.products.dto.ProductFruitDTO;
import br.com.cupuama.domain.products.entity.ProductFruit;
import br.com.cupuama.domain.products.entity.ProductFruitKey;


public class ProductFruitMapper {
	public static ProductFruit makeProductFruit(ProductFruitDTO dto) {
		final ProductFruitKey key = new ProductFruitKey(dto.getProduct(), dto.getFruit());
		return new ProductFruit(key);
	}

	public static ProductFruitDTO makeProductFruitDTO(ProductFruit productFruit) {
		ProductFruitDTO.ProductFruitDTOBuilder 
			productFruitDTOBuilder = ProductFruitDTO.newBuilder()
				.setProduct(productFruit.getKey().getProduct())
				.setFruit(productFruit.getKey().getFruit());
		
		return productFruitDTOBuilder.createProductFruitDTO();
	}

	public static List<ProductFruitDTO> makeProductFruitDTOList(Collection<ProductFruit> productFruits) {
		return productFruits.stream()
				.map(ProductFruitMapper::makeProductFruitDTO)
				.collect(Collectors.toList());
	}

}
