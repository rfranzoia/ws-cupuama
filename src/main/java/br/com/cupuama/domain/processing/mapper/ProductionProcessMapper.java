package br.com.cupuama.domain.processing.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.processing.dto.ProductionProcessDTO;
import br.com.cupuama.domain.processing.entity.ProductionProcess;

public class ProductionProcessMapper {
	public static ProductionProcess makeProductionProcess(ProductionProcessDTO dto) {
		return new ProductionProcess(dto.getKey(), dto.getDepot());
	}

	public static ProductionProcessDTO makeProductionProcessDTO(ProductionProcess stocktakeProcess) {
		ProductionProcessDTO.ProductionProcessDTOBuilder stocktakeProcessDTOBuilder = ProductionProcessDTO.newBuilder()
				.setKey(stocktakeProcess.getKey())
				.setDepot(stocktakeProcess.getDepot());

		return stocktakeProcessDTOBuilder.createProductionProcessDTO();
	}

	public static List<ProductionProcessDTO> makeProductionProcessDTOList(Collection<ProductionProcess> inventories) {
		return inventories.stream()
				.map(ProductionProcessMapper::makeProductionProcessDTO)
				.collect(Collectors.toList());
	}
	
	public static List<ProductionProcess> makeProductionProcessList(Collection<ProductionProcessDTO> dtos) {
		return dtos.stream()
				.map(ProductionProcessMapper::makeProductionProcess)
				.collect(Collectors.toList());
	}

}
