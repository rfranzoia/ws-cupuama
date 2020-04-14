package br.com.cupuama.domain.persons.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.persons.Supplier;

/**
 * Repository interface for Supplier table
 * <p/>
 */
public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    List<Supplier> findByPersonFirstName(String name);

}
