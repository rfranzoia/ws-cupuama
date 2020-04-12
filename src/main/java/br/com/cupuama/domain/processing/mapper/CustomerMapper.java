package br.com.cupuama.domain.processing.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.processing.dto.AddressDTO;
import br.com.cupuama.domain.processing.dto.CustomerDTO;
import br.com.cupuama.domain.processing.entity.Address;
import br.com.cupuama.domain.processing.entity.Customer;


public class CustomerMapper {
	public static Customer makeCustomer(final CustomerDTO dto) {
		if (dto == null) {
			return new Customer();
		}
		
		final Address address = new Address(dto.getAddress().getStreet(),
				dto.getAddress().getCity(), dto.getAddress().getRegion(), 
				dto.getAddress().getPostalCode(), dto.getAddress().getCountry());
		
		return new Customer(dto.getId(), dto.getName(), dto.getCompanyName(), dto.getPhone(), address);
	}

	public static CustomerDTO makeDTO(final Customer customer) {
		CustomerDTO.CustomerDTOBuilder builder = CustomerDTO.newBuilder();
		
		if (customer != null) {
			final AddressDTO address = new AddressDTO(customer.getAddress().getStreet(), 
					customer.getAddress().getCity(),
					customer.getAddress().getRegion(),
					customer.getAddress().getPostalCode(),
					customer.getAddress().getCountry());
			
			builder.setId(customer.getId())
					.setName(customer.getName())
					.setCompanyName(customer.getCompanyName())
					.setPhone(customer.getPhone())
					.setAddress(address);
		}

		return builder.createCustomerDTO();
	}

	public static List<CustomerDTO> makeListDTO(final Collection<Customer> customers) {
		return customers.stream()
				.map(CustomerMapper::makeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Customer> makeList(final Collection<CustomerDTO> dtos) {
		return dtos.stream()
				.map(CustomerMapper::makeCustomer)
				.collect(Collectors.toList());
	}

}
