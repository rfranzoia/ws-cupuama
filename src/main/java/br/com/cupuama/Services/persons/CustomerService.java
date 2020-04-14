package br.com.cupuama.Services.persons;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.controller.persons.dto.CustomerDTO;
import br.com.cupuama.controller.persons.mapper.CustomerMapper;
import br.com.cupuama.controller.persons.mapper.PersonMapper;
import br.com.cupuama.domain.persons.Address;
import br.com.cupuama.domain.persons.Customer;
import br.com.cupuama.domain.persons.repository.CustomerRepository;
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
	public CustomerDTO create(CustomerDTO dto) throws ConstraintsViolationException {
		Customer customer = CustomerMapper.makeCustomer(dto);
		return CustomerMapper.makeDTO(create(customer));
	}
	
	/**
	 * Update Customer information
	 *
	 * @param CustomerId
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final Long customerId, final CustomerDTO dto) throws EntityNotFoundException {
		final Address address = new Address(dto.getAddress().getStreet(),
				dto.getAddress().getCity(), dto.getAddress().getRegion(), dto.getAddress().getPostalCode(), dto.getAddress().getCountry());
		
		Customer customer = findByIdChecked(customerId);
		customer.setPerson(PersonMapper.makePerson(dto.getPerson()));
		customer.setCompanyName(dto.getCompanyName());
		customer.setPhone(dto.getPhone());
		customer.setAddress(address);
		customer.getAudit().setDateUpdated(ZonedDateTime.now());
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
