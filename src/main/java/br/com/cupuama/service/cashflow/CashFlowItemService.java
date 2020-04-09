package br.com.cupuama.service.cashflow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.cashflow.CashFlowItem;
import br.com.cupuama.domain.cashflow.CashFlowOperation;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.exception.InvalidDateRange;
import br.com.cupuama.repository.CashFlowItemRepository;
import br.com.cupuama.service.DefaultService;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some CashFlowItem specific things.
 * <p/>
 */
@Service
public class CashFlowItemService extends DefaultService<CashFlowItem, Long> {

	public static final SimpleDateFormat PERIOD_DATE_FORMAT = new SimpleDateFormat("yyyyMM");
	
	@Autowired
	private CashFlowService cashFlowService;
	
	public CashFlowItemService(final CashFlowItemRepository cashFlowItemRepository) {
		super(cashFlowItemRepository);
	}
	
	/**
	 * Creates a new CashFlowItem
	 * 
	 * @param cashFlowItemDO 
	 * @return
	 * @throws ConstraintsViolationException if a cashFlowItem already exists with the
	 *                                       given id, ... .
	 * @throws EntityNotFoundException 
	 */
	public CashFlowItem addCashFlowItem(CashFlowItem cashFlowItemDO) throws ConstraintsViolationException, EntityNotFoundException {
		CashFlowItem cashFlowItem;
		try {
			cashFlowItem = repository.save(cashFlowItemDO);
			updateCashFlow(cashFlowItem, CashFlowOperation.ADD);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("ConstraintsViolationException while creating a documentType: {}", cashFlowItemDO, e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return cashFlowItem;
	}
	
	/**
	 * Removes a CashFlowItem from the database
	 * 
	 * @param cashFlowItemId
	 * @throws EntityNotFoundException if no CashFlowItem with the given id was found.
	 * @throws ConstraintsViolationException 
	 * 
	 */
	@Transactional
	public void removeCashFlowItem(Long cashFlowItemId) throws EntityNotFoundException, ConstraintsViolationException {
		CashFlowItem cashFlowItem = findByIdChecked(cashFlowItemId);
		updateCashFlow(cashFlowItem, CashFlowOperation.REMOVE);
		repository.delete(cashFlowItem);
	}

	/**
	 * Finds all CashFlowItems within the Start and End date range
	 * @param start Starting Date
	 * @param end Ending Date
	 * @return List<CashFlowItem>
	 * @throws InvalidDateRange if the informed dates are not valid
	 * 
	 */
	public List<CashFlowItem> findByDateRange(Date start, Date end) throws InvalidDateRange {
		return ((CashFlowItemRepository) repository).findByDateRange(start, end);
	}

	@Transactional
	private void updateCashFlow(CashFlowItem cashFlowItem, CashFlowOperation operation) throws ConstraintsViolationException, EntityNotFoundException {
		
		String period = PERIOD_DATE_FORMAT.format(cashFlowItem.getItemDate());
		
		if (operation.equals(CashFlowOperation.ADD)) {
			cashFlowService.addCreditOrDebit(period, cashFlowItem.getType(), cashFlowItem.getValue(), cashFlowItem.getValue());
			
		} else if (operation.equals(CashFlowOperation.REMOVE)) {
			cashFlowService.addCreditOrDebit(period, cashFlowItem.getType(), cashFlowItem.getValue() * -1, cashFlowItem.getValue() * -1);
		}
		
	}
	
}
