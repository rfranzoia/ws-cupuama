package br.com.cupuama.domain.persons.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.persons.Customer;

/**
 * Repository interface for Customer table
 * <p/>
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByPersonFirstName(String firstName);

}
