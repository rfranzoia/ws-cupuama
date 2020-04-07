package br.com.cupuama.service.products;

import java.util.List;

import br.com.cupuama.domain.products.Product;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

public interface ProductService {

    Product find(Long productId) throws EntityNotFoundException;

    Product create(Product productDO) throws ConstraintsViolationException;

    void delete(Long productId) throws EntityNotFoundException;

    void update(long productId, String name) throws EntityNotFoundException;

    List<Product> findByName(String name);

    List<Product> findAll();

}