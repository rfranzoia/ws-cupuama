package br.com.cupuama.controller.processing.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.controller.persons.mapper.CustomerMapper;
import br.com.cupuama.controller.persons.mapper.SupplierMapper;
import br.com.cupuama.controller.processing.dto.ProcessingDTO;
import br.com.cupuama.domain.processing.Processing;


public class ProcessingMapper {
	public static Processing makeProcessing(ProcessingDTO dto) {
		return new Processing(dto.getId(), dto.getProcessDate(), ProcessTypeMapper.makeProcessType(dto.getProcessType()), 
						CustomerMapper.makeCustomer(dto.getCustomer()), SupplierMapper.makeSupplier(dto.getSupplier()), 
						dto.getDocumentReference(), dto.getRemarks());
	}

	public static ProcessingDTO makeDTO(Processing processing) {
		ProcessingDTO.ProcessingDTOBuilder builder = ProcessingDTO.newBuilder()
				.setId(processing.getId())
				.setProcessDate(processing.getProcessDate())
				.setProcessStatus(processing.getProcessStatus())
				.setProcessType(ProcessTypeMapper.makeDTO(processing.getProcessType()))
				.setCustomer(CustomerMapper.makeDTO(processing.getCustomer()))
				.setSupplier(SupplierMapper.makeDTO(processing.getSupplier()))
				.setDocumentReference(processing.getDocumentReference())
				.setRemarks(processing.getRemarks());

		return builder.createProcessingDTO();
	}

	public static List<ProcessingDTO> makeListDTO(Collection<Processing> processTypes) {
		return processTypes.stream()
				.map(ProcessingMapper::makeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Processing> makeList(Collection<ProcessingDTO> dtos) {
		return dtos.stream()
				.map(ProcessingMapper::makeProcessing)
				.collect(Collectors.toList());
	}

}
