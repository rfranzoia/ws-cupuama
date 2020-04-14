package br.com.cupuama.domain.cashflow.service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.cashflow.dto.CashFlowDTO;
import br.com.cupuama.domain.cashflow.entity.CashFlow;
import br.com.cupuama.domain.cashflow.entity.CashFlowType;
import br.com.cupuama.domain.cashflow.mapper.CashFlowMapper;
import br.com.cupuama.domain.cashflow.repository.CashFlowRepository;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;
import br.com.cupuama.util.Utils;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some cashFlow specific things.
 * <p/>
 */
@Service
public class CashFlowService extends DefaultService<CashFlow, String> {

	public CashFlowService(final CashFlowRepository cashFlowRepository) {
		super(cashFlowRepository);
	}

	@Transactional
	public CashFlowDTO create(CashFlowDTO dto) throws ConstraintsViolationException {
		CashFlow cashFlow = CashFlowMapper.makeCashFlow(dto);
		return CashFlowMapper.makeDTO(create(cashFlow));
	}
	
	/**
	 * Update or create previous Balance for a period
	 *
	 * @param period
	 * @throws ConstraintsViolationException 
	 * @throws EntityNotFoundException 
	 */
	@Transactional
	public void updateOrCreatePreviousBalance(String period, Double previousBalance) throws ConstraintsViolationException, EntityNotFoundException {
		
		CashFlow cashFlow = findOrCreateCashFlow(period);
		
		cashFlow.getAudit().setDateUpdated(ZonedDateTime.now());
		cashFlow.setPreviousBalance(previousBalance);
		
		updateForwardBalance(cashFlow);
	}
	
	/**
	 * Update credits and debits for a period
	 *
	 * @param period
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
		String currentPeriod = Utils.getFormattedPeriod(new Date());
		String cashFlowPeriod = cashFlow.getPeriod();
		
		if (Integer.valueOf(cashFlowPeriod) >= Integer.valueOf(currentPeriod)) {
			return;
		}
		
		LOG.info(String.format("Starting forward balance update for %s", cashFlowPeriod));
		
		// save the current balance of the updated cash flow period
		Double currentBalance = cashFlow.getPreviousBalance() + cashFlow.getCredits() - cashFlow.getDebits();
		
		// instantiate month and year
		String[] start = cashFlow.getPeriod()
							.replaceAll(Utils.PERIOD_REGEX, "$1/$2")
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
								.setDebits(0.0).createDTO();

			cashFlow = create(CashFlowMapper.makeCashFlow(dto));
		}

		if (cashFlow == null) {
			LOG.error(String.format("CashFlow for period %s was not properly created!", period));
			throw new EntityNotFoundException("There was a problem while creating a new CashFlow");
		}

		return cashFlow;
	}
	
	@Transactional
	private void updateAll() {
		List<CashFlow> list = ((CashFlowRepository) repository).findOrderByPeriod();
		
		boolean first = true;
		Double periodBalance = 0.0;
		
		for (CashFlow cashFlow : list) {
			if (first) {
				periodBalance = cashFlow.getPreviousBalance() + cashFlow.getCredits() - cashFlow.getDebits();
				first = false;
			} else {
				cashFlow.setPreviousBalance(periodBalance);
				periodBalance = cashFlow.getPreviousBalance() + cashFlow.getCredits() - cashFlow.getDebits();
			}
		}
	}

}
