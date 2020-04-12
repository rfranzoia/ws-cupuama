package br.com.cupuama.domain.processing.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.processing.dto.ProcessFlowTypeDTO;
import br.com.cupuama.domain.processing.entity.ProcessFlowType;
import br.com.cupuama.domain.stock.mapper.DepotMapper;

public class ProcessFlowTypeMapper {
	public static ProcessFlowType makeProcessFlowType(ProcessFlowTypeDTO dto) {
		return new ProcessFlowType(ProcessFlowTypeKeyMapper.makeId(dto.getKey()), DepotMapper.makeDepot(dto.getDepotIn()), DepotMapper.makeDepot(dto.getDepotOut()));
	}

	public static ProcessFlowTypeDTO makeProcessFlowTypeDTO(ProcessFlowType processFlowType) {
		ProcessFlowTypeDTO.ProcessFlowTypeDTOBuilder processFlowTypeDTOBuilder = ProcessFlowTypeDTO.newBuilder()
				.setKey(ProcessFlowTypeKeyMapper.makeKey(processFlowType.getId()))
				.setDepotIn(DepotMapper.makeDepotDTO(processFlowType.getDepotIn()))
				.setDepotOut(DepotMapper.makeDepotDTO(processFlowType.getDepotOut()));

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
