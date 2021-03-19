package br.com.cupuama.controller.products.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.controller.products.dto.ProductFruitDTO;
import br.com.cupuama.domain.products.ProductFruit;
import br.com.cupuama.domain.products.ProductFruitId;


public class ProductFruitMapper {
	public static ProductFruit makeProductFruit(ProductFruitDTO dto) {
		ProductFruitId id = new ProductFruitId(ProductsMapper.makeProduct(dto.getProduct()), FruitsMapper.makeFruit(dto.getFruit()));
		return new ProductFruit(id);
	}

	public static ProductFruitDTO makeDTO(ProductFruit productFruit) {
		ProductFruitDTO.ProductFruitDTOBuilder 
			productFruitDTOBuilder = ProductFruitDTO.newBuilder()
				.setProduct(ProductsMapper.makeDTO(productFruit.getId().getProduct()))
				.setFruit(FruitsMapper.makeDTO(productFruit.getId().getFruit()));
		
		return productFruitDTOBuilder.createProductFruitDTO();
	}

	public static List<ProductFruitDTO> makeListDTO(Collection<ProductFruit> productFruits) {
		return productFruits.stream()
				.map(ProductFruitMapper::makeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<ProductFruit> makeList(Collection<ProductFruitDTO> productFruits) {
		return productFruits.stream()
				.map(ProductFruitMapper::makeProductFruit)
				.collect(Collectors.toList());
	}

}
