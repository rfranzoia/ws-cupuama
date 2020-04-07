package br.com.cupuama.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.cashflow.DocumentType;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DocumentTypeRepository extends CrudRepository<DocumentType, Long> {

    List<DocumentType> findByNameLike(String name);

}
