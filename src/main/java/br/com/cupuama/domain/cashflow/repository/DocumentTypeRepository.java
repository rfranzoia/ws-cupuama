package br.com.cupuama.domain.cashflow.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.cashflow.DocumentType;

/**
 * Repository interface for DocumentType table
 * <p/>
 */
public interface DocumentTypeRepository extends CrudRepository<DocumentType, Long> {

    List<DocumentType> findByNameLike(String name);

}
