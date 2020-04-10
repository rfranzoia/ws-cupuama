package br.com.cupuama.domain.stock.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.stock.entity.Inventory;
import br.com.cupuama.domain.stock.entity.InventoryKey;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface InventoryRepository extends CrudRepository<Inventory, InventoryKey> {

}
