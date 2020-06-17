package br.com.cupuama.domain.products.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.products.entity.Product;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByNameLike(String name);

}
