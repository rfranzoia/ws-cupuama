package br.com.cupuama.domain.stock.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.stock.dto.StocktakeDTO;
import br.com.cupuama.domain.stock.entity.Stocktake;


public class StocktakeMapper {
	public static Stocktake makeStocktake(StocktakeDTO dto) {
		return new Stocktake(dto.getId(), dto.getProductFruitKey(), dto.getDepot(), dto.getStocktakeDate(), dto.getStocktakeInOut(), dto.getAmount());
	}

	public static StocktakeDTO makeStocktakeDTO(Stocktake stocktake) {
		StocktakeDTO.StocktakeDTOBuilder depotDTOBuilder = StocktakeDTO.newBuilder()
				.setId(stocktake.getId())
				.setProductFruitKey(stocktake.getProductFruitKey())
				.setDepot(stocktake.getDepot())
				.setStocktakeDate(stocktake.getStocktakeDate())
				.setStocktakeInOut(stocktake.getStocktakeInOut())
				.setAmount(stocktake.getAmount());

		return depotDTOBuilder.createDTO();
	}

	public static List<StocktakeDTO> makeStocktakeDTOList(Collection<Stocktake> depots) {
		return depots.stream()
				.map(StocktakeMapper::makeStocktakeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Stocktake> makeStocktakeList(Collection<StocktakeDTO> dtos) {
		return dtos.stream()
				.map(StocktakeMapper::makeStocktake)
				.collect(Collectors.toList());
	}

}
