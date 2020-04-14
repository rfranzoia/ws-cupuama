package br.com.cupuama.domain.products.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.products.Product;

/**
 * Repository interface for Product table
 * <p/>
 */
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByNameLike(String name);

}
