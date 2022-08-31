package br.com.cupuama.controller.persons.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.controller.persons.dto.AddressDTO;
import br.com.cupuama.controller.persons.dto.SupplierDTO;
import br.com.cupuama.domain.persons.Address;
import br.com.cupuama.domain.persons.Supplier;


public class SupplierMapper {
	public static Supplier makeSupplier(final SupplierDTO dto) {
		if (dto == null) {
			return new Supplier();
		}
		
		final Address address = new Address(dto.getAddress().getStreet(),
				dto.getAddress().getCity(), dto.getAddress().getRegion(), 
				dto.getAddress().getPostalCode(), dto.getAddress().getCountry());
		return new Supplier(dto.getId(), PersonMapper.makePerson(dto.getPerson()), dto.getCompanyName(), dto.getPhone(), address);
	}

	public static SupplierDTO makeDTO(final Supplier supplier) {
		SupplierDTO.SupplierDTOBuilder builder = SupplierDTO.newBuilder();
		
		if (supplier != null) {
			final AddressDTO address = new AddressDTO(supplier.getAddress().getStreet(), 
					supplier.getAddress().getCity(), supplier.getAddress().getRegion(), 
					supplier.getAddress().getPostalCode(), supplier.getAddress().getCountry());
			
			builder.setId(supplier.getId())
					.setPerson(PersonMapper.makeDTO(supplier.getPerson()))
					.setCompanyName(supplier.getCompanyName())
					.setPhone(supplier.getPhone())
					.setAddress(address);
		}

		return builder.createSupplierDTO();
	}

	public static List<SupplierDTO> makeListDTO(final Collection<Supplier> suppliers) {
		return suppliers.stream()
				.map(SupplierMapper::makeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Supplier> makeList(final Collection<SupplierDTO> dtos) {
		return dtos.stream()
				.map(SupplierMapper::makeSupplier)
				.collect(Collectors.toList());
	}

}
