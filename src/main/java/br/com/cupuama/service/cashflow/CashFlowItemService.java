package br.com.cupuama.service.cashflow;

import java.util.Date;
import java.util.List;

import br.com.cupuama.domain.cashflow.CashFlowItem;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.exception.InvalidDateRange;

public interface CashFlowItemService {

    CashFlowItem find(Long cashFlowItemId) throws EntityNotFoundException;

    CashFlowItem addCashFlowItem(CashFlowItem cashFlowItemDO) throws ConstraintsViolationException, EntityNotFoundException;

    void removeCashFlowItem(Long cashFlowItemId) throws EntityNotFoundException, ConstraintsViolationException;

    List<CashFlowItem> findByDateRange(Date start, Date end) throws InvalidDateRange;

    List<CashFlowItem> findAll();

}