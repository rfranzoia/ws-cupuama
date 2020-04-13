package br.com.cupuama.domain.persons.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cupuama.domain.persons.dto.CustomerDTO;
import br.com.cupuama.domain.persons.mapper.CustomerMapper;
import br.com.cupuama.domain.persons.service.CustomerService;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

/**
 * All operations with a customer will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

	private final CustomerService customerService;

	@Autowired
	public CustomerController(final CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/{customerId}")
	public CustomerDTO getCustomer(@PathVariable final long customerId) throws EntityNotFoundException {
		return CustomerMapper.makeDTO(customerService.find(customerId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerDTO createCustomer(@Valid @RequestBody final CustomerDTO customerDTO) throws ConstraintsViolationException {
		return customerService.create(customerDTO);
	}

	@DeleteMapping("/{customerId}")
	public void deleteCustomer(@PathVariable final long customerId) throws EntityNotFoundException {
		customerService.delete(customerId);
	}

	@PutMapping("/{customerId}")
	public void updateCustomer(@PathVariable final long customerId, @RequestBody final CustomerDTO customerDTO) throws EntityNotFoundException {
		customerService.update(customerId, customerDTO);
	}

	@GetMapping("/name/{name}")
	public List<CustomerDTO> findDocumentTypesByName(@PathVariable final String name) {
		return CustomerMapper.makeListDTO(customerService.findByName(name));
	}
	
	@GetMapping
	public List<CustomerDTO> findAllCustomers() {
		return CustomerMapper.makeListDTO(customerService.findAll());
	}
}
