package br.com.cupuama.domain.products.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.products.entity.Fruit;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface FruitRepository extends CrudRepository<Fruit, Long> {

    List<Fruit> findByNameLike(String name);

}
