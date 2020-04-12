package br.com.cupuama.domain.processing.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cupuama.domain.processing.entity.ProcessingDetail;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface ProcessingDetailRepository extends CrudRepository<ProcessingDetail, Long> {

	@Query(nativeQuery = true, 
			value = "select pd.* " + 
					"from process_detail pd " +
					"inner join product p on p.id = pd.product_id and p.deleted = false " +
					"inner join fruit f on f.id = pd.fruit_id and f.deleted = false " +
					"inner join depot d on d.id = pd.depot_id and d.deleted = false " +
					"inner join processing processing on processing.id = pd.process_id and processing.deleted = false " +
					"where pd.process_id = :processId " +
			"order by pd.product_id, pd.fruit_id, pd.depot_id, pd.id")
	List<ProcessingDetail> findByProcessId(@Param("processId") final Long processId);
	
	@Query(nativeQuery = true, 
			value = "select pd.* " + 
					"from process_detail pd " +
					"inner join product p on p.id = pd.product_id and p.deleted = false " +
					"inner join fruit f on f.id = pd.fruit_id and f.deleted = false " +
					"inner join depot d on d.id = pd.depot_id and d.deleted = false " +
					"inner join processing processing on processing.id = pd.process_id and processing.deleted = false " +
                    "where pd.product_id = :productId and pd.fruit_id = :fruitId " +
                    "order by pd.fruit_id, pd.depot_id, pd.id")
	List<ProcessingDetail> findByProductIdAndFruitId(@Param("productId") final Long productId, @Param("fruitId") final Long fruitId);
	
	@Query(nativeQuery = true, 
			value = "select pd.* " + 
					"from process_detail pd " +
					"inner join product p on p.id = pd.product_id and p.deleted = false " +
					"inner join fruit f on f.id = pd.fruit_id and f.deleted = false " +
					"inner join depot d on d.id = pd.depot_id and d.deleted = false " +
					"inner join processing processing on processing.id = pd.process_id and processing.deleted = false " +
					"where processing.process_date between :start and :end " +
                    "order by pd.fruit_id, pd.depot_id, pd.id")
	List<ProcessingDetail> findByProcessDateRange(@Param("start") final Date start, @Param("end") final Date end);
}
