package br.com.cupuama.controller.mapper.products;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.products.Fruit;
import br.com.cupuama.domain.products.Product;
import br.com.cupuama.domain.products.ProductFruit;
import br.com.cupuama.domain.products.ProductFruitKey;
import br.com.cupuama.dto.ProductFruitDTO;


public class ProductFruitMapper {
	public static ProductFruit makeProductFruit(ProductFruitDTO dto) {
		ProductFruitKey key = new ProductFruitKey();
		key.setProduct(new Product(dto.getProductId(), dto.getProductName(), dto.getProductUnit()));
		key.setFruit(new Fruit(dto.getFruitId(), dto.getFruitName(), dto.getFruitInitials(), dto.getFruitHarvest()));
		return new ProductFruit(key);
	}

	public static ProductFruitDTO makeProductFruitDTO(ProductFruit productFruit) {
		ProductFruitDTO.ProductFruitDTOBuilder 
			productFruitDTOBuilder = ProductFruitDTO.newBuilder()
				.setProductId(productFruit.getKey().getProduct().getId())
				.setProductName(productFruit.getKey().getProduct().getName())
				.setProductUnit(productFruit.getKey().getProduct().getUnit())
				
				.setFruitId(productFruit.getKey().getFruit().getId())
				.setFruitName(productFruit.getKey().getFruit().getName())
				.setFruitInitials(productFruit.getKey().getFruit().getInitials())
				.setFruitHarvest(productFruit.getKey().getFruit().getHarvest());
		
		return productFruitDTOBuilder.createProductFruitDTO();
	}

	public static List<ProductFruitDTO> makeProductFruitDTOList(Collection<ProductFruit> productFruits) {
		return productFruits.stream()
				.map(ProductFruitMapper::makeProductFruitDTO)
				.collect(Collectors.toList());
	}

}
