package br.com.cupuama.domain.stock.service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.stock.dto.InventoryDTO;
import br.com.cupuama.domain.stock.entity.Inventory;
import br.com.cupuama.domain.stock.entity.InventoryKey;
import br.com.cupuama.domain.stock.entity.StocktakeInOut;
import br.com.cupuama.domain.stock.mapper.InventoryMapper;
import br.com.cupuama.domain.stock.repository.InventoryRepository;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;
import br.com.cupuama.util.Utils;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some inventory specific things.
 * <p/>
 */
@Service
public class InventoryService extends DefaultService<Inventory, InventoryKey> {

	public InventoryService(final InventoryRepository inventoryRepository) {
		super(inventoryRepository);
	}

	public InventoryDTO addInventory(final InventoryDTO inventoryDTO) throws EntityNotFoundException {
		addStockInOrStockOut(inventoryDTO.getKey(), StocktakeInOut.IN_AND_OUT, inventoryDTO.getStockIn(), inventoryDTO.getStockOut());
		return InventoryMapper.makeInventoryDTO(find(inventoryDTO.getKey()));
	}
	/**
	 * Update stockIn and stockOut for a inventoryKey
	 *
	 * @param inventoryKey
	 * @throws EntityNotFoundException
	 * @throws ConstraintsViolationException 
	 */
	@Transactional
	public void addStockInOrStockOut(final InventoryKey inventoryKey, final StocktakeInOut inOut, final Double stockIn, final Double stockOut) throws EntityNotFoundException {
		Inventory inventory = findOrCreateInventory(inventoryKey);
		
		switch (inOut) {
			case IN:
				inventory.setStockIn(inventory.getStockIn() + stockIn);
				break;
			case OUT:
				inventory.setStockOut(inventory.getStockOut() + stockOut);
				break;
			default:
				inventory.setStockIn(inventory.getStockIn() + stockIn);
				inventory.setStockOut(inventory.getStockOut() + stockOut);
				break;
		}
		
		inventory.getAudit().setDateUpdated(ZonedDateTime.now());
		updateForwardInitialStock(inventory);
	}
	
	/**
	 * Forward updates previous balances up to current date inventoryKey
	 * 
	 * @param inventory
	 * @throws ConstraintsViolationException
	 * @throws EntityNotFoundException 
	 */
	@Transactional
	private void updateForwardInitialStock(final Inventory inventory) throws EntityNotFoundException {
		String currentPeriod = Utils.getFormattedPeriod(new Date());
		InventoryKey inventoryKey = inventory.getInventoryKey();
		
		if (Integer.valueOf(inventoryKey.getPeriod()) >= Integer.valueOf(currentPeriod)) {
			return;
		}
		
		LOG.info(String.format("Starting forward Stock update for %s", inventoryKey.getPeriod()));
		
		// save the current balance of the updated cash flow inventoryKey
		Double initialStock = inventory.getInitialStock() + inventory.getStockIn() - inventory.getStockOut();
		
		// instantiate month and year
		String[] start = inventory.getInventoryKey().getPeriod()
							.replaceAll(Utils.PERIOD_REGEX, "$1/$2")
							.split("[/]");
		
		int year = Integer.valueOf(start[0]);
		int month = Integer.valueOf(start[1]);
		
		InventoryKey nextPeriodKey = null;
		
		do {
			month++;
			if (month > 12) {
				year++;
				month = 1;
			}
			
			//make a copy of the key for safety purposes
			nextPeriodKey = inventoryKey.clone();
			
			//set the new period
			nextPeriodKey.setPeriod(String.format("%04d", year) + String.format("%02d", month));
			
			Inventory nextInventory = findOrCreateInventory(nextPeriodKey);
			
			LOG.info(String.format("Updating previous stock for %s", inventoryKey));
			
			nextInventory.setInitialStock(initialStock);
			nextInventory.getAudit().setDateUpdated(ZonedDateTime.now());
			initialStock = nextInventory.getInitialStock() + nextInventory.getStockIn() - nextInventory.getStockOut();
			
		} while (Integer.valueOf(nextPeriodKey.getPeriod()) < Integer.valueOf(currentPeriod));
	
		LOG.info(String.format("Stock update ended!"));
	}
	
	/**
	 * Try to locate Inventory or create a new if not found
	 * 
	 * @param inventoryKey
	 * @return
	 * @throws EntityNotFoundException
	 * @throws ConstraintsViolationException
	 */
	@Transactional
	public Inventory findOrCreateInventory(final InventoryKey inventoryKey) throws EntityNotFoundException {
		Inventory inventory = null;

		try {
			inventory = find(inventoryKey);
		} catch (EntityNotFoundException enfex) {
			InventoryDTO dto = InventoryDTO.newBuilder()
								.setKey(inventoryKey)
								.setInitialStock(0.0)
								.setStockIn(0.0)
								.setStockOut(0.0).createInventoryDTO();

			try {
				inventory = create(InventoryMapper.makeInventory(dto));
			} catch (ConstraintsViolationException cvex) {
				inventory = null;
			}
		}

		if (inventory == null) {
			LOG.error(String.format("Inventory for inventoryKey %s was not properly created!", inventoryKey));
			throw new EntityNotFoundException("There was a problem while creating a new Inventory");
		}

		return inventory;
	}
	
	public List<InventoryDTO> findAllOrderByPeriodProductFruitDepot() {
		return InventoryMapper.makeInventoryDTOList(((InventoryRepository) repository).findAllOrderByPeriodProductFruitDepot());
	}

	@Transactional
	private void updateAll(final InventoryKey key) {
		List<Inventory> list = ((InventoryRepository) repository).findByProductFruitDepotOrderByPeriod(
				key.getProduct().getId(), 
				key.getFruit().getId(), 
				key.getDepot().getId());
		
		boolean first = true;
		Double initialStock = 0.0;
		
		for (Inventory inventory : list) {
			if (first) {
				initialStock = inventory.getInitialStock() + inventory.getStockIn() - inventory.getStockOut();
				first = false;
			} else {
				inventory.setInitialStock(initialStock);
				initialStock = inventory.getInitialStock() + inventory.getStockIn() - inventory.getStockOut();
			}
		}
	}
}
