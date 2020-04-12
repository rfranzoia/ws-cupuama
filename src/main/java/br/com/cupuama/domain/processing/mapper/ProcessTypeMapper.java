package br.com.cupuama.domain.processing.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.processing.dto.ProcessTypeDTO;
import br.com.cupuama.domain.processing.entity.ProcessType;


public class ProcessTypeMapper {
	public static ProcessType makeProcessType(ProcessTypeDTO dto) {
		return new ProcessType(dto.getId(), dto.getName(), dto.getFlowTypeModel());
	}

	public static ProcessTypeDTO makeDTO(ProcessType processType) {
		ProcessTypeDTO.ProcessTypeDTOBuilder processTypeDTOBuilder = ProcessTypeDTO.newBuilder()
				.setId(processType.getId())
				.setName(processType.getName())
				.setModel(processType.getFlowTypeModel());

		return processTypeDTOBuilder.createProcessTypeDTO();
	}

	public static List<ProcessTypeDTO> makeListDTO(Collection<ProcessType> processTypes) {
		return processTypes.stream()
				.map(ProcessTypeMapper::makeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<ProcessType> makeList(Collection<ProcessTypeDTO> dtos) {
		return dtos.stream()
				.map(ProcessTypeMapper::makeProcessType)
				.collect(Collectors.toList());
	}

}
