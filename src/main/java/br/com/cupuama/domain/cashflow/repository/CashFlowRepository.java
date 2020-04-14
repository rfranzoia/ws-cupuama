package br.com.cupuama.domain.cashflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.cashflow.CashFlow;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface CashFlowRepository extends CrudRepository<CashFlow, String> {
	
	@Query(nativeQuery = true, 
			value = "select cf.* " + 
					"from cashflow cf " +
                    "order by cf.period")
	List<CashFlow> findOrderByPeriod();

}
