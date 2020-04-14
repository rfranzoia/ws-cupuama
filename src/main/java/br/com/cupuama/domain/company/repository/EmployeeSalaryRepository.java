package br.com.cupuama.domain.company.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.company.Employee;
import br.com.cupuama.domain.company.EmployeeSalary;

/**
 * Repository interface for Company table
 * <p/>
 */
public interface EmployeeSalaryRepository extends CrudRepository<EmployeeSalary, Long> {

    List<EmployeeSalary> findByEmployee(Employee employee);
    
}
