package br.com.cupuama.domain.persons.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.persons.Customer;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByNameLike(String name);

}
