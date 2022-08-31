package br.com.cupuama.controller.stock.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.controller.products.mapper.ProductFruitKeyMapper;
import br.com.cupuama.controller.stock.dto.StocktakeDTO;
import br.com.cupuama.domain.stock.Stocktake;


public class StocktakeMapper {
	public static Stocktake makeStocktake(StocktakeDTO dto) {
		return new Stocktake(dto.getId(), ProductFruitKeyMapper.makeId(dto.getProductFruitKey()), DepotMapper.makeDepot(dto.getDepot()), 
						dto.getStocktakeDate(), dto.getStocktakeInOut(), dto.getAmount());
	}

	public static StocktakeDTO makeDTO(Stocktake stocktake) {
		StocktakeDTO.StocktakeDTOBuilder depotDTOBuilder = StocktakeDTO.newBuilder()
				.setId(stocktake.getId())
				.setProductFruitKey(ProductFruitKeyMapper.makeKey(stocktake.getProductFruitId()))
				.setDepot(DepotMapper.makeDTO(stocktake.getDepot()))
				.setStocktakeDate(stocktake.getStocktakeDate())
				.setStocktakeInOut(stocktake.getStocktakeInOut())
				.setAmount(stocktake.getAmount());

		return depotDTOBuilder.createDTO();
	}

	public static List<StocktakeDTO> makeListDTO(Collection<Stocktake> depots) {
		return depots.stream()
				.map(StocktakeMapper::makeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Stocktake> makeList(Collection<StocktakeDTO> dtos) {
		return dtos.stream()
				.map(StocktakeMapper::makeStocktake)
				.collect(Collectors.toList());
	}

}
