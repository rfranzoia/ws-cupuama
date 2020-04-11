package br.com.cupuama.domain.processing.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.processing.dto.ProcessFlowTypeDTO;
import br.com.cupuama.domain.processing.entity.ProcessFlowType;

public class ProcessFlowTypeMapper {
	public static ProcessFlowType makeProcessFlowType(ProcessFlowTypeDTO dto) {
		return new ProcessFlowType(dto.getKey(), dto.getDepotIn(), dto.getDepotOut());
	}

	public static ProcessFlowTypeDTO makeProcessFlowTypeDTO(ProcessFlowType processFlowType) {
		ProcessFlowTypeDTO.ProcessFlowTypeDTOBuilder processFlowTypeDTOBuilder = ProcessFlowTypeDTO.newBuilder()
				.setKey(processFlowType.getKey())
				.setDepotIn(processFlowType.getDepotIn())
				.setDepotOut(processFlowType.getDepotOut());

		return processFlowTypeDTOBuilder.createProcessFlowTypeDTO();
	}

	public static List<ProcessFlowTypeDTO> makeProcessFlowTypeDTOList(Collection<ProcessFlowType> inventories) {
		return inventories.stream()
				.map(ProcessFlowTypeMapper::makeProcessFlowTypeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<ProcessFlowType> makeProcessFlowTypeList(Collection<ProcessFlowTypeDTO> dtos) {
		return dtos.stream()
				.map(ProcessFlowTypeMapper::makeProcessFlowType)
				.collect(Collectors.toList());
	}

}
