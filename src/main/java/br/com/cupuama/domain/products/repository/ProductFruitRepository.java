/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cupuama.domain.products.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cupuama.domain.products.entity.ProductFruit;
import br.com.cupuama.domain.products.entity.ProductFruitId;
import br.com.cupuama.exception.EntityNotFoundException;

/**
 *
 * @author Romeu Franzoia Jr
 */
public interface ProductFruitRepository extends CrudRepository<ProductFruit, ProductFruitId> {

	@Query(nativeQuery = true, value = "select pf.* " +
			"from product_fruit pf " +
            "inner join fruit f on f.id = pf.fruit_id and f.deleted = false " +
            "inner join product p on p.id = pf.product_id and p.deleted = false " +
			"where pf.product_id = :productId " +
			"and pf.fruit_id = :fruitId")
	public ProductFruit getByProductIdAndFruitId(@Param("productId") Long productId, @Param("fruitId")Long fruitId) throws EntityNotFoundException;
	
	@Query(nativeQuery = true, value = "select pf.* " +
            "from product_fruit pf " +
            "inner join fruit f on f.id = pf.fruit_id and f.deleted = false " +
            "inner join product p on p.id = pf.product_id and p.deleted = false " +
            "where pf.product_id = :productId " + 
            "order by p.name asc")
	public List<ProductFruit> findByProductId(@Param("productId") Long productId);
    
	@Query(nativeQuery = true, value = "select pf.* " +
            "from product_fruit pf " +
            "inner join fruit f on f.id = pf.fruit_id and f.deleted = false " +
            "inner join product p on p.id = pf.product_id and p.deleted = false " +
            "where pf.fruit_id = :fruitId " + 
            "order by f.name asc")
    public List<ProductFruit> findByFruitId(@Param("fruitId") Long fruitId);
    
	@Query(nativeQuery = true, value = "select pf.* " +
			"from product_fruit pf " +
			"inner join fruit f on f.id = pf.fruit_id and f.deled = false " +
            "inner join product p on p.id = pf.product_id and p.deleted = false " +
            "order by p.name, f.name")
    public List<ProductFruit> findOrderByProductAndFruit();
}
