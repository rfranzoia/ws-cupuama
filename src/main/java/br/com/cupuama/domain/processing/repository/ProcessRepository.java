package br.com.cupuama.domain.processing.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface ProcessRepository extends CrudRepository<Process, Long> {

	@Query(nativeQuery = true, 
			value = "select p.* " + 
					"from process p " +
                    "where p.process_date between :start and :end " +
                    "order by p.process_date, p.id")
	List<Process> findByProcessDateRange(@Param("start") final Date start, @Param("end") final Date end);
	
	@Query(nativeQuery = true, 
			value = "select p.* " + 
					"from process p " +
					"inner join customer c on c.id = p.customer_id and c.deleted = false " +
                    "where p.customer_id = :customerId " +
                    "order by p.process_date")
	List<Process> findByCustomer(@Param("customerId") final Long customerId);
	
	@Query(nativeQuery = true, 
			value = "select p.* " + 
					"from process p " +
					"inner join supplier s on s.id = p.supplier_id and s.deleted = false " +
                    "where p.supplier_id = :supplierId " +
                    "order by p.process_date")
	List<Process> findBySupplier(@Param("customerId") final Long customerId);
	
	@Query(nativeQuery = true, 
			value = "select p.* " + 
					"from process p " +
					"inner join process_type pt on pt.id = p.process_type_id and pt.deleted = false " +
                    "where p.process_type_id = :processTypeId " +
                    "order by p.process_date")
	List<Process> findByProcessType(@Param("processTypeId") final Long processTypeId);
}
