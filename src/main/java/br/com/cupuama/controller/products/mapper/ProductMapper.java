package br.com.cupuama.controller.products.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.controller.products.dto.ProductDTO;
import br.com.cupuama.domain.products.Product;


public class ProductMapper {
	public static Product makeProduct(ProductDTO dto) {
		return new Product(dto.getId(), dto.getName(), dto.getUnit());
	}

	public static ProductDTO makeDTO(Product product) {
		ProductDTO.ProductDTOBuilder productDTOBuilder = ProductDTO.newBuilder()
				.setId(product.getId())
				.setName(product.getName())
				.setUnit(product.getUnit());

		return productDTOBuilder.createProductDTO();
	}

	public static List<ProductDTO> makeListDTO(Collection<Product> products) {
		return products.stream()
				.map(ProductMapper::makeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Product> makeList(Collection<ProductDTO> productsDTO) {
		return productsDTO.stream()
				.map(ProductMapper::makeProduct)
				.collect(Collectors.toList());
	}

}
