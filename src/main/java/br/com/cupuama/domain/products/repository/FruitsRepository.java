package br.com.cupuama.domain.products.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.products.Fruits;

/**
 * Repository interface for Fruit table
 * <p/>
 */
public interface FruitsRepository extends CrudRepository<Fruits, Long> {

    List<Fruits> findByNameLike(String name);

}
