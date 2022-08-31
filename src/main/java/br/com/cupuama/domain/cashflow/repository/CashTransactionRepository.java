package br.com.cupuama.domain.cashflow.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.cupuama.domain.cashflow.CashTransaction;
import br.com.cupuama.exception.InvalidRequestException;

public interface CashTransactionRepository extends CrudRepository<CashTransaction, Long> {
	
	@Query(nativeQuery = true, 
			value = "select ct.* " + 
					"from cash_transaction ct " +
                    "where ct.item_date between :start and :end " +
                    "order by ct.item_date, ct.type")
	public List<CashTransaction> findByDateRange(@Param("start") Date start, @Param("end") Date end) throws InvalidRequestException;

}
