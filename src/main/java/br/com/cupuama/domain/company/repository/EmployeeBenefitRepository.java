package br.com.cupuama.domain.company.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.company.Benefit;
import br.com.cupuama.domain.company.Employee;
import br.com.cupuama.domain.company.EmployeeBenefit;

/**
 * Repository interface for Company table
 * <p/>
 */
public interface EmployeeBenefitRepository extends CrudRepository<EmployeeBenefit, Long> {

    List<EmployeeBenefit> findByEmployee(Employee employee);
    
    List<EmployeeBenefit> findByBenefit(Benefit benefit);
    
    EmployeeBenefit findByEmployeeAndBenefit(Employee employee, Benefit benefit);
    
}
