package br.com.cupuama.domain.stock.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.stock.dto.StocktakeTypeDTO;
import br.com.cupuama.domain.stock.entity.StocktakeType;


public class StocktakeTypeMapper {
	public static StocktakeType makeStocktakeType(StocktakeTypeDTO stocktakeType) {
		return new StocktakeType(stocktakeType.getName());
	}

	public static StocktakeTypeDTO makeStocktakeTypeDTO(StocktakeType stocktakeType) {
		StocktakeTypeDTO.StocktakeTypeDTOBuilder stocktakeTypeDTOBuilder = StocktakeTypeDTO.newBuilder()
				.setId(stocktakeType.getId())
				.setName(stocktakeType.getName());

		return stocktakeTypeDTOBuilder.createStocktakeTypeDTO();
	}

	public static List<StocktakeTypeDTO> makeStocktakeTypeDTOList(Collection<StocktakeType> stocktakeTypes) {
		return stocktakeTypes.stream()
				.map(StocktakeTypeMapper::makeStocktakeTypeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<StocktakeType> makeStocktakeTypeList(Collection<StocktakeTypeDTO> dtos) {
		return dtos.stream()
				.map(StocktakeTypeMapper::makeStocktakeType)
				.collect(Collectors.toList());
	}

}
