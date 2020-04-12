package br.com.cupuama.domain.processing.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cupuama.domain.processing.entity.Processing;
import br.com.cupuama.domain.processing.entity.ProcessingDetail;
import br.com.cupuama.domain.products.entity.Fruit;
import br.com.cupuama.domain.products.entity.Product;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface ProcessingDetailRepository extends CrudRepository<ProcessingDetail, Long> {

	List<ProcessingDetail> findByProcessing(final Processing processing);
	
	List<ProcessingDetail> findByProductAndFruit(final Product product, final Fruit fruit);
	
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
