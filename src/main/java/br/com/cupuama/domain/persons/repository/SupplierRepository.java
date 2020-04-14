package br.com.cupuama.domain.persons.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.persons.entity.Supplier;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    List<Supplier> findByNameLike(String name);

}