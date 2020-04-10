package br.com.cupuama.domain.cashflow.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.cashflow.entity.CashFlow;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface CashFlowRepository extends CrudRepository<CashFlow, String> {
	
	List<CashFlow> findAllOrderByPeriod();

}
