package br.com.cupuama.controller.products.mapper;

import br.com.cupuama.controller.products.dto.ProductsDTO;
import br.com.cupuama.domain.products.Products;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class ProductsMapper {
    public static Products makeProduct(ProductsDTO dto) {
        return new Products(dto.getId(), dto.getName(), dto.getUnit());
    }

    public static ProductsDTO makeDTO(Products products) {
        ProductsDTO.ProductDTOBuilder productDTOBuilder = ProductsDTO.newBuilder()
                .setId(products.getId())
                .setName(products.getName())
                .setUnit(products.getUnit());

        return productDTOBuilder.createProductDTO();
    }

    public static List<ProductsDTO> makeListDTO(Collection<Products> products) {
        return products.stream()
                .map(ProductsMapper::makeDTO)
                .collect(Collectors.toList());
    }

    public static List<Products> makeList(Collection<ProductsDTO> productsDTO) {
        return productsDTO.stream()
                .map(ProductsMapper::makeProduct)
                .collect(Collectors.toList());
    }

}
