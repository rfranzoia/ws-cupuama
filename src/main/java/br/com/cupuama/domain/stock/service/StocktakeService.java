package br.com.cupuama.domain.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.stock.entity.InventoryKey;
import br.com.cupuama.domain.stock.entity.Stocktake;
import br.com.cupuama.domain.stock.repository.StocktakeRepository;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;
import br.com.cupuama.util.Utils;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some stocktake specific things.
 * <p/>
 */
@Service
public class StocktakeService extends DefaultService<Stocktake, Long> {

	@Autowired
	private InventoryService inventoryService;
	
	public StocktakeService(final StocktakeRepository stocktakeRepository) {
		super(stocktakeRepository);
	}

	@Override
	@Transactional
	public Stocktake create(Stocktake stockTakeDO) throws ConstraintsViolationException {
		Stocktake stocktake = super.create(stockTakeDO);
		updateInventory(stocktake, StocktakeAction.ADD);
		return stocktake;
	}
	
	@Override
	public void delete(Long id) throws EntityNotFoundException {
		Stocktake stocktake = findByIdChecked(id);
		updateInventory(stocktake, StocktakeAction.REMOVE);
		super.delete(id);
	}
	
	@Transactional
	private void updateInventory(Stocktake stocktake, StocktakeAction action) {
		
		String period = Utils.getFormattedPeriod(stocktake.getStocktakeDate());
		
		InventoryKey inventoryKey = new InventoryKey();
		inventoryKey.setPeriod(period);
		inventoryKey.setProduct(stocktake.getProductFruitKey().getProduct());
		inventoryKey.setFruit(stocktake.getProductFruitKey().getFruit());
		inventoryKey.setDepot(stocktake.getDepot());
		
		Double stockIn = stocktake.getAmount() * (action.equals(StocktakeAction.ADD)? 1: -1);
		Double stockOut = stocktake.getAmount() * (action.equals(StocktakeAction.ADD)? 1: -1);
		
		// assuming that the method bellow will "always" create a proper entry for the inventory, we are just ignoring if there's a problem there
		try {
			inventoryService.addStockInOrStockOut(inventoryKey, stocktake.getStocktakeInOut(), stockIn, stockOut);
		} catch (EntityNotFoundException ex) {
			LOG.error(String.format("Failed to update inventory for %s", stocktake.toString()), ex);
		}
	}

}
