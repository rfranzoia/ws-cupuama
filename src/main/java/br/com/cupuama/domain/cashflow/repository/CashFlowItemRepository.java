package br.com.cupuama.domain.cashflow.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cupuama.domain.cashflow.entity.CashFlowItem;
import br.com.cupuama.exception.InvalidDateRange;

public interface CashFlowItemRepository extends CrudRepository<CashFlowItem, Long> {
	
	@Query(nativeQuery = true, 
			value = "select cfi.* " + 
					"from cashflow_item cfi " +
                    "where cfi.item_date between :start and :end " +
                    "order by cfi.item_date, cfi.type")
	public List<CashFlowItem> findByDateRange(@Param("start") Date start, @Param("end") Date end) throws InvalidDateRange;

}
