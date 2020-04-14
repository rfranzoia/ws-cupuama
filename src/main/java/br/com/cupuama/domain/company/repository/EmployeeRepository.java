package br.com.cupuama.domain.company.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.company.Company;
import br.com.cupuama.domain.company.Department;
import br.com.cupuama.domain.company.Employee;

/**
 * Repository interface for Company table
 * <p/>
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<Employee> findByPersonFirstName(String name);
    
    List<Employee> findByCompany(Company company);
    
    List<Employee> findByDepartment(Department department);
    
    List<Employee> findByManager(Employee manager);
}
