package br.com.cupuama.domain.processing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cupuama.domain.processing.entity.ProductionProcess;
import br.com.cupuama.domain.processing.entity.ProductionProcessKey;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface ProductionProcessRepository extends CrudRepository<ProductionProcess, ProductionProcessKey> {

	@Query(nativeQuery = true, value = "select stp.* " +
			"from production_process pp " +
			"inner join process_type pt on stt.id = pp.stocktake_type_id and pt.deleted = false " +
			"inner join product p on p.id = pp.product_id and p.deleted = false " +
            "inner join depot d on d.id = pp.fruit_id and d.deleted = false " +
			"where pp.process_type_id = :processTypeId " +
			"order by pp.process_type_id, pp.product_id, pp.depot_id")
    List<ProductionProcess> findByProcessTypeId(@Param("processTypeId") Long processTypeId);

}
