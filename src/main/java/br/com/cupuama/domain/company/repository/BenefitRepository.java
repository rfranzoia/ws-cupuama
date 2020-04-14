package br.com.cupuama.domain.company.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.company.Benefit;

/**
 * Repository interface for Company table
 * <p/>
 */
public interface BenefitRepository extends CrudRepository<Benefit, Long> {

    List<Benefit> findByName(String name);
    
}
