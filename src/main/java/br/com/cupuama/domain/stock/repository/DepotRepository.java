package br.com.cupuama.domain.stock.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.stock.Depot;

/**
 * Repository interface for Depot table
 * <p/>
 */
public interface DepotRepository extends CrudRepository<Depot, Long> {

    List<Depot> findByNameLike(String name);

}
