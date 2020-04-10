package br.com.cupuama.domain.stock.service;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.products.entity.Fruit;
import br.com.cupuama.domain.products.entity.Product;
import br.com.cupuama.domain.products.service.FruitService;
import br.com.cupuama.domain.products.service.ProductService;
import br.com.cupuama.domain.stock.dto.InventoryDTO;
import br.com.cupuama.domain.stock.entity.Depot;
import br.com.cupuama.domain.stock.entity.Inventory;
import br.com.cupuama.domain.stock.entity.InventoryKey;
import br.com.cupuama.domain.stock.entity.StocktakeInOut;
import br.com.cupuama.domain.stock.mapper.InventoryMapper;
import br.com.cupuama.domain.stock.repository.InventoryRepository;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some inventory specific things.
 * <p/>
 */
@Service
public class InventoryService extends DefaultService<Inventory, InventoryKey> {

	public static final SimpleDateFormat PERIOD_DATE_FORMAT = new SimpleDateFormat("yyyyMM");
	public static final String PERIOD_REGEX = "([0-9]{4})([0-9]{2})";

	@Autowired
	private ProductService productService;
	
	@Autowired
	private FruitService fruitService;
	
	@Autowired
	private DepotService depotService;
	
	public InventoryService(final InventoryRepository inventoryRepository) {
		super(inventoryRepository);
	}

	/**
	 * Creates a new inventory.
	 *
	 * @param inventoryDO
	 * @return
	 * @throws ConstraintsViolationException if a inventory already exists with the
	 *                                       given license plate, ... .
	 */
	@Override
	public Inventory create(Inventory inventoryDO) throws ConstraintsViolationException {
		try {
			if (repository.existsById(inventoryDO.getInventoryKey())) {
				LOG.error(String.format("Inventory with inventoryKey %s already exists!", inventoryDO.getInventoryKey()));
				throw new DataIntegrityViolationException(String.format("Inventory %s already exists!", inventoryDO.getInventoryKey().toString()));
			}
			
			Inventory inventory = repository.save(inventoryDO);
			return inventory;
			
		} catch (DataIntegrityViolationException e) {
			LOG.error("ConstraintsViolationException while creating a inventory: {}", inventoryDO, e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		
	}
	
	@Transactional
	public void delete(final String period, final Long productId, final Long fruitId,final Long depotId) throws EntityNotFoundException {
		
		Product product = productService.find(productId);
		Fruit fruit = fruitService.find(fruitId);
		Depot depot = depotService.find(depotId);
		
		InventoryKey key = new InventoryKey(period, product, fruit, depot);
		delete(key);
		
	}

	/**
	 * Update or create previous Balance for a inventoryKey
	 *
	 * @param inventoryKey
	 * @param longitude
	 * @param latitude
	 * @throws ConstraintsViolationException 
	 * @throws EntityNotFoundException 
	 */
	@Transactional
	public void updateOrCreateInitialStock(InventoryKey inventoryKey, InventoryDTO inventoryDTO) throws ConstraintsViolationException, EntityNotFoundException {
		
		Inventory inventory = findOrCreateInventory(inventoryDTO.getInventoryKey());
		inventory.getAudit().setDateUpdated(ZonedDateTime.now());
		inventory.setInitialStock(inventoryDTO.getInitialStock());
		updateForwardBalance(inventory);
	}
	
	/**
	 * Update stockIn and stockOut for a inventoryKey
	 *
	 * @param inventoryKey
	 * @param longitude
	 * @param latitude
	 * @throws EntityNotFoundException
	 * @throws ConstraintsViolationException 
	 */
	@Transactional
	public void updateStockInOut(InventoryKey inventoryKey, StocktakeInOut inOut, InventoryDTO inventoryDTO) throws EntityNotFoundException, ConstraintsViolationException {
		Inventory inventory = findByIdChecked(inventoryKey);
		
		switch (inOut) {
			case IN:
				inventory.setStockIn(inventoryDTO.getStockIn());
				break;
			case OUT:
				inventory.setStockOut(inventoryDTO.getStockOut());
				break;
			default:
				inventory.setStockIn(inventory.getStockIn());
				inventory.setStockOut(inventoryDTO.getStockOut());
				break;
		}
		
		inventory.getAudit().setDateUpdated(ZonedDateTime.now());
		updateForwardBalance(inventory);
	}
	
	/**
	 * Update stockIn and stockOut for a inventoryKey
	 *
	 * @param inventoryKey
	 * @param longitude
	 * @param latitude
	 * @throws EntityNotFoundException
	 * @throws ConstraintsViolationException 
	 */
	@Transactional
	public void addCreditOrDebit(InventoryKey inventoryKey, StocktakeInOut inOut, Double stockIn, Double stockOut) throws EntityNotFoundException, ConstraintsViolationException {
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
		updateForwardBalance(inventory);
	}
	
	/**
	 * Forward updates previous balances up to current date inventoryKey
	 * 
	 * @param inventory
	 * @throws ConstraintsViolationException
	 * @throws EntityNotFoundException 
	 */
	@Transactional
	private void updateForwardBalance(final Inventory inventory) throws ConstraintsViolationException, EntityNotFoundException {
		String currentPeriod = PERIOD_DATE_FORMAT.format(new Date());
		InventoryKey inventoryKey = inventory.getInventoryKey();
		
		if (Integer.valueOf(inventoryKey.getPeriod()) >= Integer.valueOf(currentPeriod)) {
			return;
		}
		
		LOG.info(String.format("Starting forward Stock update for %s", inventoryKey.getPeriod()));
		
		// save the current balance of the updated cash flow inventoryKey
		Double initialStock = inventory.getInitialStock() + inventory.getStockIn() - inventory.getStockOut();
		
		// instantiate month and year
		String[] start = inventory.getInventoryKey().getPeriod()
							.replaceAll(PERIOD_REGEX, "$1/$2")
							.split("[/]");
		
		int year = Integer.valueOf(start[0]);
		int month = Integer.valueOf(start[1]);
		
		do {
			month++;
			if (month > 12) {
				year++;
				month = 1;
			}
			
			//make a copy of the key for safety purposes
			final InventoryKey nextPeriodKey = inventoryKey.clone();
			
			//set the new period
			nextPeriodKey.setPeriod(String.format("%04d", year) + String.format("%02d", month));
			
			Inventory nextInventory = findOrCreateInventory(nextPeriodKey);
			
			LOG.info(String.format("Updating previous stock for %s", inventoryKey));
			
			nextInventory.setInitialStock(initialStock);
			nextInventory.getAudit().setDateUpdated(ZonedDateTime.now());
			initialStock = nextInventory.getInitialStock() + nextInventory.getStockIn() - nextInventory.getStockOut();
			
		} while (Integer.valueOf(inventoryKey.getPeriod()) < Integer.valueOf(currentPeriod));
	
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
	public Inventory findOrCreateInventory(InventoryKey inventoryKey) throws EntityNotFoundException, ConstraintsViolationException {
		Inventory inventory = null;

		try {
			inventory = find(inventoryKey);
		} catch (EntityNotFoundException e) {
			InventoryDTO dto = InventoryDTO.newBuilder()
								.setInventoryKey(inventoryKey)
								.setInitialStock(0.0)
								.setStockIn(0.0)
								.setStockOut(0.0).createInventoryDTO();

			inventory = create(InventoryMapper.makeInventory(dto));
		}

		if (inventory == null) {
			LOG.error(String.format("Inventory for inventoryKey %s was not properly created!", inventoryKey));
			throw new EntityNotFoundException("There was a problem while creating a new Inventory");
		}

		return inventory;
	}

}
