/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cupuama.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cupuama.domain.products.ProductFruit;
import br.com.cupuama.domain.products.ProductFruitKey;
import br.com.cupuama.exception.EntityNotFoundException;

/**
 *
 * @author Romeu Franzoia Jr
 */
public interface ProductFruitRepository extends CrudRepository<ProductFruit, ProductFruitKey> {

	@Query(nativeQuery = true, value = "select pf.* " +
			"from product_fruit pf " +
            "inner join fruit f on f.id = pf.fruit_id and f.deleted = false " +
            "inner join product p on p.id = pf.product_id and p.deleted = false " +
			"where pf.product_id = :productId " +
			"and pf.fruit_id = :fruitId")
	public ProductFruit getByProductIdAndFruitId(@Param("productId") Integer productId, @Param("fruitId")Long fruitId) throws EntityNotFoundException;
	
	@Query(nativeQuery = true, value = "select pf.* " +
            "from product_fruit pf " +
            "inner join fruit f on f.id = pf.fruit_id and f.deleted = false " +
            "inner join product p on p.id = pf.product_id and p.deleted = false " +
            "where pf.product_id = :productId " + 
            "order by p.name asc")
	public List<ProductFruit> findByProductId(@Param("productId") Integer productId);
    
	@Query(nativeQuery = true, value = "select pf.* " +
            "from product_fruit pf " +
            "inner join fruit f on f.id = pf.fruit_id and f.deleted = false " +
            "inner join product p on p.id = pf.product_id and p.deleted = false " +
            "where pf.fruit_id = :fruitId " + 
            "order by f.name asc")
    public List<ProductFruit> findByFruitId(@Param("fruitId") Integer fruitId);
    
	@Query(nativeQuery = true, value = "select pf.* " +
			"from product_fruit pf " +
			"inner join fruit f on f.id = pf.fruit_id and f.deled = false " +
            "inner join product p on p.id = pf.product_id and p.deleted = false " +
            "order by p.name, f.name")
    public List<ProductFruit> findOrderByProductAndFruit();
}
