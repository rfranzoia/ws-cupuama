package br.com.cupuama.domain.products.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.products.dto.FruitDTO;
import br.com.cupuama.domain.products.entity.Fruit;


public class FruitMapper {
	public static Fruit makeFruit(FruitDTO dto) {
		return new Fruit(dto.getId(), dto.getName(), dto.getInitials(), dto.getHarvest());
	}

	public static FruitDTO makeDTO(Fruit fruit) {
		FruitDTO.FruitDTOBuilder fruitDTOBuilder = FruitDTO.newBuilder()
				.setId(fruit.getId())
				.setName(fruit.getName())
				.setInitials(fruit.getInitials())
				.setHarvest(fruit.getHarvest());

		return fruitDTOBuilder.createFruitDTO();
	}

	public static List<FruitDTO> makeListDTO(Collection<Fruit> fruits) {
		return fruits.stream()
				.map(FruitMapper::makeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Fruit> makeList(Collection<FruitDTO> fruitsDTO) {
		return fruitsDTO.stream()
				.map(FruitMapper::makeFruit)
				.collect(Collectors.toList());
	}

}
