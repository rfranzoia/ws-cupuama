package br.com.cupuama.domain.stock.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.stock.entity.StocktakeType;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface StocktakeTypeRepository extends CrudRepository<StocktakeType, Long> {

    List<StocktakeType> findByNameLike(String name);

}
