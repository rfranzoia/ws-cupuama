package br.com.cupuama.domain.processing.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.processing.dto.CustomerDTO;
import br.com.cupuama.domain.processing.entity.Customer;


public class CustomerMapper {
	public static Customer makeCustomer(CustomerDTO dto) {
		return new Customer(dto.getId(), dto.getName(), dto.getCompanyName(), dto.getPhone(), dto.getAddress());
	}

	public static CustomerDTO makeCustomerDTO(Customer customer) {
		CustomerDTO.CustomerDTOBuilder CustomerDTOBuilder = CustomerDTO.newBuilder()
				.setId(customer.getId())
				.setName(customer.getName())
				.setCompanyName(customer.getCompanyName())
				.setPhone(customer.getPhone())
				.setAddress(customer.getAddress());

		return CustomerDTOBuilder.createCustomerDTO();
	}

	public static List<CustomerDTO> makeCustomerDTOList(Collection<Customer> customers) {
		return customers.stream()
				.map(CustomerMapper::makeCustomerDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Customer> makeCustomerList(Collection<CustomerDTO> dtos) {
		return dtos.stream()
				.map(CustomerMapper::makeCustomer)
				.collect(Collectors.toList());
	}

}
