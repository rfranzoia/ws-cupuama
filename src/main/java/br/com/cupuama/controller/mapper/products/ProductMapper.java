package br.com.cupuama.controller.mapper.products;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.products.Product;
import br.com.cupuama.dto.ProductDTO;


public class ProductMapper {
	public static Product makeProduct(ProductDTO dto) {
		return new Product(dto.getId(), dto.getName(), dto.getUnit());
	}

	public static ProductDTO makeProductDTO(Product product) {
		ProductDTO.ProductDTOBuilder productDTOBuilder = ProductDTO.newBuilder()
				.setId(product.getId())
				.setName(product.getName())
				.setUnit(product.getUnit());

		return productDTOBuilder.createProductDTO();
	}

	public static List<ProductDTO> makeProductDTOList(Collection<Product> products) {
		return products.stream()
				.map(ProductMapper::makeProductDTO)
				.collect(Collectors.toList());
	}

}
