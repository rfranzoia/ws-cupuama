package br.com.cupuama.service.products;

import java.util.List;

import br.com.cupuama.domain.products.ProductFruit;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

public interface ProductFruitService {

    ProductFruit find(Long productId, Long fruitId) throws EntityNotFoundException;

    ProductFruit create(ProductFruit productFruitDO) throws ConstraintsViolationException;

    void delete(Long productId, Long fruitId) throws EntityNotFoundException;

    List<ProductFruit> findAll();

}