package br.com.cupuama.domain.processing.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.processing.ProcessType;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface ProcessTypeRepository extends CrudRepository<ProcessType, Long> {

    List<ProcessType> findByNameLike(String name);

    List<ProcessType> findByFlowTypeModel(String flowTypeModel);
}
