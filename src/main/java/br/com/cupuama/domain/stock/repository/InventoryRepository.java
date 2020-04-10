package br.com.cupuama.domain.stock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cupuama.domain.stock.entity.Inventory;
import br.com.cupuama.domain.stock.entity.InventoryKey;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface InventoryRepository extends CrudRepository<Inventory, InventoryKey> {
	
	@Query(nativeQuery = true, value = "select i.* " +
			"from inventory i " +
			"inner join product p on p.id = i.product_id and p.deleted = false " +
            "inner join fruit f on f.id = i.fruit_id and f.deleted = false " +
            "inner join depot d on d.id = i.fruit_id and d.deleted = false " +
			"order by i.period, i.product_id, i.fruit_id, i.depot_id")
	public List<Inventory> findAllOrderByPeriodProductFruitDepot();
	
	@Query(nativeQuery = true, value = "select i.* " +
			"from inventory i " +
			"inner join product p on p.id = i.product_id and p.deleted = false " +
            "inner join fruit f on f.id = i.fruit_id and f.deleted = false " +
            "inner join depot d on d.id = i.fruit_id and d.deleted = false " +
            "where i.product_id = :productid and i.fruit_id = :fruitid and i.depot_id = :depotid" +
			"order by i.period, i.product_id, i.fruit_id, i.depot_id")
	public List<Inventory> findByProductFruitDepotOrderByPeriod(@Param("productid") Long productId, @Param("fruitid") Long fruitId, @Param("depotid") Long depotId);

}
