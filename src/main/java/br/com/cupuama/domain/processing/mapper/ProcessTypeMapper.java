package br.com.cupuama.domain.processing.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.processing.dto.ProcessTypeDTO;
import br.com.cupuama.domain.processing.entity.ProcessType;


public class ProcessTypeMapper {
	public static ProcessType makeProcessType(ProcessTypeDTO processType) {
		return new ProcessType(processType.getName());
	}

	public static ProcessTypeDTO makeProcessTypeDTO(ProcessType processType) {
		ProcessTypeDTO.ProcessTypeDTOBuilder processTypeDTOBuilder = ProcessTypeDTO.newBuilder()
				.setId(processType.getId())
				.setName(processType.getName());

		return processTypeDTOBuilder.createProcessTypeDTO();
	}

	public static List<ProcessTypeDTO> makeProcessTypeDTOList(Collection<ProcessType> processTypes) {
		return processTypes.stream()
				.map(ProcessTypeMapper::makeProcessTypeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<ProcessType> makeProcessTypeList(Collection<ProcessTypeDTO> dtos) {
		return dtos.stream()
				.map(ProcessTypeMapper::makeProcessType)
				.collect(Collectors.toList());
	}

}
