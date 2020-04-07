package br.com.cupuama.service.cashflow;

import java.util.List;

import br.com.cupuama.domain.cashflow.CashFlow;
import br.com.cupuama.domain.cashflow.CashFlowType;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

public interface CashFlowService {

    CashFlow find(String period) throws EntityNotFoundException;

    CashFlow create(CashFlow cashFlowDO) throws ConstraintsViolationException;

    void delete(String period) throws EntityNotFoundException;

    void updateOrCreatePreviousBalance(String period, Double previousBalance) throws ConstraintsViolationException, EntityNotFoundException;
    
    void updateCreditDebit(String period, CashFlowType type, Double credits, Double debits) throws EntityNotFoundException, ConstraintsViolationException;

    void addCreditOrDebit(String period, CashFlowType type, Double credits, Double debits) throws EntityNotFoundException, ConstraintsViolationException;
    
    CashFlow findOrCreateCashFlow(String period) throws EntityNotFoundException, ConstraintsViolationException;
    
    List<CashFlow> findAll();

}