package br.com.cupuama.domain.processing.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.processing.dto.ProcessingDTO;
import br.com.cupuama.domain.processing.entity.Processing;


public class ProcessingMapper {
	public static Processing makeProcessing(ProcessingDTO dto) {
		return new Processing(dto.getId(), dto.getProcessDate(), ProcessTypeMapper.makeProcessType(dto.getProcessType()), 
						CustomerMapper.makeCustomer(dto.getCustomer()), SupplierMapper.makeSupplier(dto.getSupplier()), 
						dto.getDocumentReference(), dto.getRemarks());
	}

	public static ProcessingDTO makeProcessingDTO(Processing processing) {
		ProcessingDTO.ProcessingDTOBuilder builder = ProcessingDTO.newBuilder()
				.setId(processing.getId())
				.setProcessDate(processing.getProcessDate())
				.setProcessStatus(processing.getProcessStatus())
				.setProcessType(ProcessTypeMapper.makeProcessTypeDTO(processing.getProcessType()))
				.setCustomer(CustomerMapper.makeCustomerDTO(processing.getCustomer()))
				.setSupplier(SupplierMapper.makeSupplierDTO(processing.getSupplier()))
				.setDocumentReference(processing.getDocumentReference())
				.setRemarks(processing.getRemarks());

		return builder.createProcessingDTO();
	}

	public static List<ProcessingDTO> makeProcessingDTOList(Collection<Processing> processTypes) {
		return processTypes.stream()
				.map(ProcessingMapper::makeProcessingDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Processing> makeProcessingList(Collection<ProcessingDTO> dtos) {
		return dtos.stream()
				.map(ProcessingMapper::makeProcessing)
				.collect(Collectors.toList());
	}

}
