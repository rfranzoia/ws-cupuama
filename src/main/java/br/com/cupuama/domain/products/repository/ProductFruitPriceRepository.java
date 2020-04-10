/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cupuama.domain.products.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cupuama.domain.products.entity.ProductFruitPrice;

/**
 *
 * @author Romeu Franzoia Jr
 */
public interface ProductFruitPriceRepository extends CrudRepository<ProductFruitPrice, Long> {

	@Query(nativeQuery = true, value = "select pfp.* " +
			"from product_fruit_price pfp " +
            "inner join fruit f on f.id = pfp.fruit_id and f.deleted = false " +
            "inner join product p on p.id = pfp.product_id and p.deleted = false " +
			"where pfp.product_id = :productId " +
			"and pfp.fruit_id = :fruitId")
	public List<ProductFruitPrice> findByProductIdAndFruitId(@Param("productId") Integer productId, @Param("fruitId")Long fruitId);
	
	@Query(nativeQuery = true, value = "select pfp.* " +
            "from product_fruit_price pfp " +
            "inner join fruit f on f.id = pfp.fruit_id and f.deleted = false " +
            "inner join product p on p.id = pfp.product_id and p.deleted = false " +
            "where pfp.product_id = :productId " + 
            "order by p.name asc")
	public List<ProductFruitPrice> findByProductId(@Param("productId") Integer productId);
    
	@Query(nativeQuery = true, value = "select pfp.* " +
            "from product_fruit_price pfp " +
            "inner join fruit f on f.id = pfp.fruit_id and f.deleted = false " +
            "inner join product p on p.id = pfp.product_id and p.deleted = false " +
            "where pfp.fruit_id = :fruitId " + 
            "order by f.name asc")
    public List<ProductFruitPrice> findByFruitId(@Param("fruitId") Integer fruitId);
    
	@Query(nativeQuery = true, value = "select pfp.* " +
			"from product_fruit_price pfp " +
			"inner join fruit f on f.id = pfp.fruit_id and f.deled = false " +
            "inner join product p on p.id = pfp.product_id and p.deleted = false " +
            "order by p.name, f.name, pfp.id")
    public List<ProductFruitPrice> findOrderByProductAndFruitAndId();
}
