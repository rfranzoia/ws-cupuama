package br.com.cupuama.domain.processing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.processing.dto.CustomerDTO;
import br.com.cupuama.domain.processing.entity.Customer;
import br.com.cupuama.domain.processing.mapper.CustomerMapper;
import br.com.cupuama.domain.processing.repository.CustomerRepository;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some Customer specific things.
 * <p/>
 */
@Service
public class CustomerService extends DefaultService<Customer, Long> {

	public CustomerService(final CustomerRepository customerRepository) {
		super(customerRepository);
	}

	@Transactional
	public CustomerDTO create(CustomerDTO customerDTO) throws ConstraintsViolationException {
		Customer customer = CustomerMapper.makeCustomer(customerDTO);
		return CustomerMapper.makeCustomerDTO(create(customer));
	}
	
	/**
	 * Update Customer information
	 *
	 * @param CustomerId
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final Long customerId, final CustomerDTO dto) throws EntityNotFoundException {
		Customer customer = findByIdChecked(customerId);
		customer.setName(dto.getName());
		customer.setCompanyName(dto.getCompanyName());
		customer.setPhone(dto.getPhone());
		customer.setAddress(dto.getAddress());
	}

	/**
	 * Find all Customers by name.
	 *
	 * @param name
	 */
	public List<Customer> findByName(final String name) {
		return ((CustomerRepository) repository).findByNameLike(name + "%");
	}

}
