package br.com.cupuama.domain.processing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cupuama.domain.processing.entity.ProcessFlowType;
import br.com.cupuama.domain.processing.entity.ProcessFlowTypeKey;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface ProcessFlowTypeRepository extends CrudRepository<ProcessFlowType, ProcessFlowTypeKey> {

	@Query(nativeQuery = true, value = "select pft.* " +
			"from process_flow_type pft " +
			"inner join process_type pt on pt.id = pft.process_type_id and pt.deleted = false " +
			"inner join product p on p.id = pft.product_id and p.deleted = false " +
            "inner join depot d on d.id = pft.fruit_id and d.deleted = false " +
			"where pft.process_type_id = :processTypeId and pft.deleted = false " +
			"order by pft.process_type_id, pft.product_id, pft.depot_id")
    List<ProcessFlowType> findByProcessTypeId(@Param("processTypeId") Long processTypeId);

}
