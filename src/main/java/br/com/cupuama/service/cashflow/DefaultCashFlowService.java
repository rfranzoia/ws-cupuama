package br.com.cupuama.service.cashflow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.controller.mapper.CashFlowMapper;
import br.com.cupuama.domain.cashflow.CashFlow;
import br.com.cupuama.domain.cashflow.CashFlowType;
import br.com.cupuama.dto.CashFlowDTO;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.repository.CashFlowRepository;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some cashFlow specific things.
 * <p/>
 */
@Service
public class DefaultCashFlowService implements CashFlowService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultCashFlowService.class);
	public static final SimpleDateFormat PERIOD_DATE_FORMAT = new SimpleDateFormat("yyyyMM");
	public static final String PERIOD_REGEX = "([0-9]{4})([0-9]{2})";

	private final CashFlowRepository cashFlowRepository;

	public DefaultCashFlowService(final CashFlowRepository cashFlowRepository) {
		this.cashFlowRepository = cashFlowRepository;
	}

	/**
	 * Find all cashFlows.
	 *
	 */
	@Override
	public List<CashFlow> findAll() {
		List<CashFlow> list = new ArrayList<>();
		cashFlowRepository.findAll().forEach(c -> {
			list.add(c);
		});
		return list;
	}
	
	/**
	 * Selects a cashFlow by id.
	 *
	 * @param period
	 * @return found cashFlow
	 * @throws EntityNotFoundException if no cashFlow with the given id was found.
	 */
	@Override
	public CashFlow find(String period) throws EntityNotFoundException {
		return findCashFlowChecked(period);
	}

	/**
	 * Creates a new cashFlow.
	 *
	 * @param cashFlowDO
	 * @return
	 * @throws ConstraintsViolationException if a cashFlow already exists with the
	 *                                       given license plate, ... .
	 */
	@Override
	public CashFlow create(CashFlow cashFlowDO) throws ConstraintsViolationException {
		CashFlow cashFlow;
		try {
			if (cashFlowRepository.existsById(cashFlowDO.getPeriod())) {
				throw new DataIntegrityViolationException(String.format("CashFlow with period %s already exists!", cashFlowDO.getPeriod()));
			}
			cashFlow = cashFlowRepository.save(cashFlowDO);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("ConstraintsViolationException while creating a cashFlow: {}", cashFlowDO, e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return cashFlow;
	}

	/**
	 * Deletes an existing cashFlow by id.
	 *
	 * @param period
	 * @throws EntityNotFoundException if no cashFlow with the given id was found.
	 */
	@Override
	@Transactional
	public void delete(String period) throws EntityNotFoundException {
		CashFlow cashFlowDO = findCashFlowChecked(period);
		cashFlowDO.setDeleted(true);
	}

	/**
	 * Update or create previous Balance for a period
	 *
	 * @param period
	 * @param longitude
	 * @param latitude
	 * @throws ConstraintsViolationException 
	 * @throws EntityNotFoundException 
	 */
	@Override
	@Transactional
	public void updateOrCreatePreviousBalance(String period, Double previousBalance) throws ConstraintsViolationException, EntityNotFoundException {
		
		CashFlow cashFlow = null;
		try {
			cashFlow = findCashFlowChecked(period);
			
		} catch (EntityNotFoundException e) {
			CashFlowDTO dto = CashFlowDTO.newBuilder()
								.setPeriod(period)
								.setPreviousBalance(0.0)
								.setCredits(0.0)
								.setDebits(0.0)
								.createCashFlowDTO();
			
			cashFlow = create(CashFlowMapper.makeCashFlow(dto));
		}
		cashFlow.setPreviousBalance(previousBalance);
		updateForwardBalance(cashFlow);
	}
	
	/**
	 * Update credits and debits for a period
	 *
	 * @param period
	 * @param longitude
	 * @param latitude
	 * @throws EntityNotFoundException
	 * @throws ConstraintsViolationException 
	 */
	@Override
	@Transactional
	public void updateCreditDebit(String period, CashFlowType type, Double credits, Double debits) throws EntityNotFoundException, ConstraintsViolationException {
		CashFlow cashFlow = findCashFlowChecked(period);
		
		switch (type) {
			case CREDIT:
				cashFlow.setCredits(credits);
				break;
			case DEBIT:
				cashFlow.setDebits(debits);
				break;
			default:
				cashFlow.setCredits(credits);
				cashFlow.setDebits(debits);
				break;
		}
		updateForwardBalance(cashFlow);
	}
	
	/**
	 * Update credits and debits for a period
	 *
	 * @param period
	 * @param longitude
	 * @param latitude
	 * @throws EntityNotFoundException
	 * @throws ConstraintsViolationException 
	 */
	@Override
	@Transactional
	public void addCreditOrDebit(String period, CashFlowType type, Double credits, Double debits) throws EntityNotFoundException, ConstraintsViolationException {
		CashFlow cashFlow = findOrCreateCashFlow(period);
		
		switch (type) {
			case CREDIT:
				cashFlow.setCredits(cashFlow.getCredits() + credits);
				break;
			case DEBIT:
				cashFlow.setDebits(cashFlow.getDebits() + debits);
				break;
			default:
				cashFlow.setCredits(cashFlow.getCredits() + credits);
				cashFlow.setDebits(cashFlow.getDebits() + debits);
				break;
		}
		updateForwardBalance(cashFlow);
	}
	
	/**
	 * Forward updates previous balances up to current date period
	 * 
	 * @param cashFlow
	 * @throws ConstraintsViolationException
	 * @throws EntityNotFoundException 
	 */
	@Transactional
	private void updateForwardBalance(final CashFlow cashFlow) throws ConstraintsViolationException, EntityNotFoundException {
		String currentPeriod = PERIOD_DATE_FORMAT.format(new Date());
		String cashFlowPeriod = cashFlow.getPeriod();
		
		if (Integer.valueOf(cashFlowPeriod) >= Integer.valueOf(currentPeriod)) {
			return;
		}
		
		// save the current balance of the updated cash flow period
		Double currentBalance = cashFlow.getPreviousBalance() + cashFlow.getCredits() - cashFlow.getDebits();
		
		// instantiate month and year
		String[] start = cashFlow.getPeriod()
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
			cashFlowPeriod = String.format("%04d", year) + String.format("%02d", month);
			
			CashFlow nextCashFlow = findOrCreateCashFlow(cashFlowPeriod);
			
			nextCashFlow.setPreviousBalance(currentBalance);
			currentBalance = nextCashFlow.getPreviousBalance() + nextCashFlow.getCredits() - nextCashFlow.getDebits();
			
		} while (Integer.valueOf(cashFlowPeriod) < Integer.valueOf(currentPeriod));
		
	}
	
	/**
	 * Try to locate CashFlow or create a new if not found
	 * 
	 * @param period
	 * @return
	 * @throws EntityNotFoundException
	 * @throws ConstraintsViolationException
	 */
	@Override
	@Transactional
	public CashFlow findOrCreateCashFlow(String period) throws EntityNotFoundException, ConstraintsViolationException {
		CashFlow cashFlow = null;

		try {
			cashFlow = find(period);
		} catch (EntityNotFoundException e) {
			CashFlowDTO dto = CashFlowDTO.newBuilder()
								.setPeriod(period)
								.setPreviousBalance(0.0)
								.setCredits(0.0)
								.setDebits(0.0).createCashFlowDTO();

			cashFlow = create(CashFlowMapper.makeCashFlow(dto));
		}

		if (cashFlow == null) {
			throw new EntityNotFoundException("There was a problem while creating a new CashFlow");
		}

		return cashFlow;
	}

	private CashFlow findCashFlowChecked(final String period) throws EntityNotFoundException {
		return cashFlowRepository.findById(period)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with period: " + period));
	}

}
