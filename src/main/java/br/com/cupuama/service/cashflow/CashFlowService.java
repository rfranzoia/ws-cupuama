package br.com.cupuama.service.cashflow;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

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
import br.com.cupuama.service.DefaultService;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some cashFlow specific things.
 * <p/>
 */
@Service
public class CashFlowService extends DefaultService<CashFlow, String> {

	private static final Logger LOG = LoggerFactory.getLogger(CashFlowService.class);
	public static final SimpleDateFormat PERIOD_DATE_FORMAT = new SimpleDateFormat("yyyyMM");
	public static final String PERIOD_REGEX = "([0-9]{4})([0-9]{2})";

	public CashFlowService(final CashFlowRepository cashFlowRepository) {
		super(cashFlowRepository);
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
		try {
			if (repository.existsById(cashFlowDO.getPeriod())) {
				LOG.error(String.format("CashFlow with period %s already exists!", cashFlowDO.getPeriod()));
				throw new DataIntegrityViolationException(String.format("CashFlow with period %s already exists!", cashFlowDO.getPeriod()));
			}
			
			CashFlow cashFlow = repository.save(cashFlowDO);
			return cashFlow;
			
		} catch (DataIntegrityViolationException e) {
			LOG.error("ConstraintsViolationException while creating a cashFlow: {}", cashFlowDO, e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		
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
	@Transactional
	public void updateOrCreatePreviousBalance(String period, Double previousBalance) throws ConstraintsViolationException, EntityNotFoundException {
		
		CashFlow cashFlow = null;
		try {
			cashFlow = findByIdChecked(period);
			
		} catch (EntityNotFoundException e) {
			CashFlowDTO dto = CashFlowDTO.newBuilder()
								.setPeriod(period)
								.setPreviousBalance(0.0)
								.setCredits(0.0)
								.setDebits(0.0)
								.createCashFlowDTO();
			
			cashFlow = create(CashFlowMapper.makeCashFlow(dto));
		}
		
		cashFlow.getAudit().setDateUpdated(ZonedDateTime.now());
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
	@Transactional
	public void updateCreditDebit(String period, CashFlowType type, Double credits, Double debits) throws EntityNotFoundException, ConstraintsViolationException {
		CashFlow cashFlow = findByIdChecked(period);
		
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
		
		cashFlow.getAudit().setDateUpdated(ZonedDateTime.now());
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
		
		cashFlow.getAudit().setDateUpdated(ZonedDateTime.now());
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
		
		LOG.info(String.format("Starting forward balance update for %s", cashFlowPeriod));
		
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
			
			LOG.info(String.format("Updating previous balance for %s", cashFlowPeriod));
			
			nextCashFlow.setPreviousBalance(currentBalance);
			nextCashFlow.getAudit().setDateUpdated(ZonedDateTime.now());
			currentBalance = nextCashFlow.getPreviousBalance() + nextCashFlow.getCredits() - nextCashFlow.getDebits();
			
		} while (Integer.valueOf(cashFlowPeriod) < Integer.valueOf(currentPeriod));
	
		LOG.info(String.format("Balance update ended!"));
	}
	
	/**
	 * Try to locate CashFlow or create a new if not found
	 * 
	 * @param period
	 * @return
	 * @throws EntityNotFoundException
	 * @throws ConstraintsViolationException
	 */
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
			LOG.error(String.format("CashFlow for period %s was not properly created!", period));
			throw new EntityNotFoundException("There was a problem while creating a new CashFlow");
		}

		return cashFlow;
	}

}
