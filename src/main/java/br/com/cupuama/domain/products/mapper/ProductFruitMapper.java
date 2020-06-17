package br.com.cupuama.domain.products.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.products.dto.ProductFruitDTO;
import br.com.cupuama.domain.products.entity.ProductFruit;
import br.com.cupuama.domain.products.entity.ProductFruitId;


public class ProductFruitMapper {
	public static ProductFruit makeProductFruit(ProductFruitDTO dto) {
		ProductFruitId id = new ProductFruitId(ProductMapper.makeProduct(dto.getProduct()), FruitMapper.makeFruit(dto.getFruit()));
		return new ProductFruit(id);
	}

	public static ProductFruitDTO makeDTO(ProductFruit productFruit) {
		ProductFruitDTO.ProductFruitDTOBuilder 
			productFruitDTOBuilder = ProductFruitDTO.newBuilder()
				.setProduct(ProductMapper.makeDTO(productFruit.getId().getProduct()))
				.setFruit(FruitMapper.makeDTO(productFruit.getId().getFruit()));
		
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
