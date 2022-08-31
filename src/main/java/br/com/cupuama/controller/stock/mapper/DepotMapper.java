package br.com.cupuama.controller.stock.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.controller.stock.dto.DepotDTO;
import br.com.cupuama.domain.stock.Depot;


public class DepotMapper {
	public static Depot makeDepot(DepotDTO dto) {
		return new Depot(dto.getId(), dto.getName(), dto.isKeepStock());
	}

	public static DepotDTO makeDTO(Depot depot) {
		DepotDTO.DepotDTOBuilder depotDTOBuilder = DepotDTO.newBuilder()
				.setId(depot.getId())
				.setName(depot.getName())
				.setKeepStock(depot.isKeepStock());

		return depotDTOBuilder.createDepotDTO();
	}

	public static List<DepotDTO> makeListDTO(Collection<Depot> depots) {
		return depots.stream()
				.map(DepotMapper::makeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Depot> makeList(Collection<DepotDTO> dtos) {
		return dtos.stream()
				.map(DepotMapper::makeDepot)
				.collect(Collectors.toList());
	}

}
