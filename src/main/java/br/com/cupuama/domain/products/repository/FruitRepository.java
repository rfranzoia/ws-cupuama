package br.com.cupuama.domain.products.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.products.Fruit;

/**
 * Repository interface for Fruit table
 * <p/>
 */
public interface FruitRepository extends CrudRepository<Fruit, Long> {

    List<Fruit> findByNameLike(String name);

}
