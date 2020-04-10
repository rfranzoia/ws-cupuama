package br.com.cupuama.domain.stock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cupuama.domain.stock.entity.StocktakeProcess;
import br.com.cupuama.domain.stock.entity.StocktakeProcessKey;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface StocktakeProcessRepository extends CrudRepository<StocktakeProcess, StocktakeProcessKey> {

	@Query(nativeQuery = true, value = "select stp.* " +
			"from stocktake_process stp " +
			"inner join stocktake_type stt on stt.id = stp.stocktake_type_id and stt.deleted = false " +
			"inner join product p on p.id = stp.product_id and p.deleted = false " +
            "inner join depot d on d.id = stp.fruit_id and d.deleted = false " +
			"where stp.stocktake_type_id = :stocktaketypeid " +
			"order by stp.stocktake_type_id, stp.product_id, stp.depot_id")
    List<StocktakeProcess> findByStocktakeTypeId(@Param("stocktaketypeid") Long stocktakeTypeId);

}
