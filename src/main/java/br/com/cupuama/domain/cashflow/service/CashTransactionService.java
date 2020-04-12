package br.com.cupuama.domain.cashflow.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.cashflow.entity.CashTransaction;
import br.com.cupuama.domain.cashflow.mapper.CashTransactionMapper;
import br.com.cupuama.domain.cashflow.dto.CashTransactionDTO;
import br.com.cupuama.domain.cashflow.entity.CashFlowOperation;
import br.com.cupuama.domain.cashflow.repository.CashTransactionRepository;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.exception.InvalidDateRange;
import br.com.cupuama.util.DefaultService;
import br.com.cupuama.util.Utils;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some CashTransaction specific things.
 * <p/>
 */
@Service
public class CashTransactionService extends DefaultService<CashTransaction, Long> {

	@Autowired
	private CashFlowService cashFlowService;
	
	public CashTransactionService(final CashTransactionRepository cashTransactionRepository) {
		super(cashTransactionRepository);
	}
	
	@Transactional
	public CashTransactionDTO addCashTransaction(CashTransactionDTO cashTransactionDTO) throws ConstraintsViolationException, EntityNotFoundException {
		CashTransaction cashTransaction = CashTransactionMapper.makeCashTransaction(cashTransactionDTO);
		return CashTransactionMapper.makeCashTransactionDTO(addCashTransaction(cashTransaction));
	}
	
	/**
	 * Creates a new CashTransaction
	 * 
	 * @param cashTransactionDO 
	 * @return
	 * @throws ConstraintsViolationException if a cashTransaction already exists with the
	 *                                       given id, ... .
	 * @throws EntityNotFoundException 
	 */
	private CashTransaction addCashTransaction(CashTransaction cashTransactionDO) throws ConstraintsViolationException, EntityNotFoundException {
		CashTransaction cashTransaction;
		try {
			cashTransaction = repository.save(cashTransactionDO);
			updateCashFlow(cashTransaction, CashFlowOperation.ADD);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("ConstraintsViolationException while creating a documentType: {}", cashTransactionDO, e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return cashTransaction;
	}
	
	/**
	 * Removes a CashTransaction from the database
	 * 
	 * @param cashTransactionId
	 * @throws EntityNotFoundException if no CashTransaction with the given id was found.
	 * @throws ConstraintsViolationException 
	 * 
	 */
	@Transactional
	public void removeCashTransaction(Long cashTransactionId) throws EntityNotFoundException, ConstraintsViolationException {
		CashTransaction cashTransaction = findByIdChecked(cashTransactionId);
		updateCashFlow(cashTransaction, CashFlowOperation.REMOVE);
		repository.delete(cashTransaction);
	}

	/**
	 * Finds all CashTransactions within the Start and End date range
	 * @param start Starting Date
	 * @param end Ending Date
	 * @return List<CashTransaction>
	 * @throws InvalidDateRange if the informed dates are not valid
	 * 
	 */
	public List<CashTransaction> findByDateRange(Date start, Date end) throws InvalidDateRange {
		return ((CashTransactionRepository) repository).findByDateRange(start, end);
	}

	@Transactional
	private void updateCashFlow(CashTransaction cashTransaction, CashFlowOperation operation) throws ConstraintsViolationException, EntityNotFoundException {
		
		String period = Utils.getFormattedPeriod(cashTransaction.getItemDate());
		
		if (operation.equals(CashFlowOperation.ADD)) {
			cashFlowService.addCreditOrDebit(period, cashTransaction.getType(), cashTransaction.getValue(), cashTransaction.getValue());
			
		} else if (operation.equals(CashFlowOperation.REMOVE)) {
			cashFlowService.addCreditOrDebit(period, cashTransaction.getType(), cashTransaction.getValue() * -1, cashTransaction.getValue() * -1);
		}
		
	}
	
}
