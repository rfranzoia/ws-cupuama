package br.com.cupuama.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.products.Product;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByNameLike(String name);

}
