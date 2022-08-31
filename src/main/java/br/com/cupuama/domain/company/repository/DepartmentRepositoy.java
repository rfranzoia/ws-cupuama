package br.com.cupuama.domain.company.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.company.Company;
import br.com.cupuama.domain.company.Department;

/**
 * Repository interface for Company table
 * <p/>
 */
public interface DepartmentRepositoy extends CrudRepository<Department, Long> {

    List<Department> findByName(String name);
    
    List<Department> findByCompany(Company company);

}
