package br.com.cupuama.domain.company.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.company.Company;

/**
 * Repository interface for Company table
 * <p/>
 */
public interface CompanyRepository extends CrudRepository<Company, Long> {

    List<Company> findByName(String name);

}
