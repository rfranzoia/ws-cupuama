package br.com.cupuama.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.cashflow.CashFlow;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface CashFlowRepository extends CrudRepository<CashFlow, String> {

}
