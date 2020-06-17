package br.com.cupuama.domain.products.service;

import java.util.List;

import br.com.cupuama.domain.products.dto.ProductFruitPriceDTO;
import br.com.cupuama.domain.products.entity.Product;
import br.com.cupuama.domain.products.entity.ProductFruit;
import br.com.cupuama.domain.products.entity.ProductFruitPrice;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

public interface ProductFruitPriceService {

    Product find(Long id) throws EntityNotFoundException;

    Product create(ProductFruitPriceDTO productFruitPriceDTO) throws ConstraintsViolationException;

    void delete(Long id) throws EntityNotFoundException;
    
    void deleteByProductId(Long productId) throws EntityNotFoundException;
    
    void deleteByFruitId(Long fruitId) throws EntityNotFoundException;

    void update(long id, ProductFruitPriceDTO productFruitPriceDTO) throws EntityNotFoundException;

    List<ProductFruitPrice> findAll();
    
    List<ProductFruit> findByProductId(Integer productId);
    
    List<ProductFruit> findByFruitId(Integer fruitId);

}