package br.com.cupuama.domain.stock.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.stock.dto.StocktakeProcessDTO;
import br.com.cupuama.domain.stock.entity.StocktakeProcess;


public class StocktakeProcessMapper {
	public static StocktakeProcess makeStocktakeProcess(StocktakeProcessDTO dto) {
		return new StocktakeProcess(dto.getKey(), dto.getDepot());
	}

	public static StocktakeProcessDTO makeStocktakeProcessDTO(StocktakeProcess stocktakeProcess) {
		StocktakeProcessDTO.StocktakeProcessDTOBuilder stocktakeProcessDTOBuilder = StocktakeProcessDTO.newBuilder()
				.setKey(stocktakeProcess.getKey())
				.setDepot(stocktakeProcess.getDepot());

		return stocktakeProcessDTOBuilder.createStocktakeProcessDTO();
	}

	public static List<StocktakeProcessDTO> makeStocktakeProcessDTOList(Collection<StocktakeProcess> inventories) {
		return inventories.stream()
				.map(StocktakeProcessMapper::makeStocktakeProcessDTO)
				.collect(Collectors.toList());
	}
	
	public static List<StocktakeProcess> makeStocktakeProcessList(Collection<StocktakeProcessDTO> dtos) {
		return dtos.stream()
				.map(StocktakeProcessMapper::makeStocktakeProcess)
				.collect(Collectors.toList());
	}

}
