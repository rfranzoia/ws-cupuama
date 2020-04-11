package br.com.cupuama.domain.processing.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.processing.dto.SupplierDTO;
import br.com.cupuama.domain.processing.entity.Supplier;


public class SupplierMapper {
	public static Supplier makeSupplier(SupplierDTO dto) {
		return new Supplier(dto.getId(), dto.getName(), dto.getCompanyName(), dto.getPhone(), dto.getAddress());
	}

	public static SupplierDTO makeSupplierDTO(Supplier supplier) {
		SupplierDTO.SupplierDTOBuilder SupplierDTOBuilder = SupplierDTO.newBuilder()
				.setId(supplier.getId())
				.setName(supplier.getName())
				.setCompanyName(supplier.getCompanyName())
				.setPhone(supplier.getPhone())
				.setAddress(supplier.getAddress());

		return SupplierDTOBuilder.createSupplierDTO();
	}

	public static List<SupplierDTO> makeSupplierDTOList(Collection<Supplier> suppliers) {
		return suppliers.stream()
				.map(SupplierMapper::makeSupplierDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Supplier> makeSupplierList(Collection<SupplierDTO> dtos) {
		return dtos.stream()
				.map(SupplierMapper::makeSupplier)
				.collect(Collectors.toList());
	}

}
