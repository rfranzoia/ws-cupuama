package br.com.cupuama.controller.mapper.products;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.products.Fruit;
import br.com.cupuama.dto.FruitDTO;


public class FruitMapper {
	public static Fruit makeFruit(FruitDTO dto) {
		return new Fruit(dto.getId(), dto.getName(), dto.getInitials(), dto.getHarvest());
	}

	public static FruitDTO makeFruitDTO(Fruit fruit) {
		FruitDTO.FruitDTOBuilder fruitDTOBuilder = FruitDTO.newBuilder()
				.setId(fruit.getId())
				.setName(fruit.getName())
				.setInitials(fruit.getInitials())
				.setHarvest(fruit.getHarvest());

		return fruitDTOBuilder.createFruitDTO();
	}

	public static List<FruitDTO> makeFruitDTOList(Collection<Fruit> fruits) {
		return fruits.stream()
				.map(FruitMapper::makeFruitDTO)
				.collect(Collectors.toList());
	}
	
	public static List<Fruit> makeFruitList(Collection<FruitDTO> fruitsDTO) {
		return fruitsDTO.stream()
				.map(FruitMapper::makeFruit)
				.collect(Collectors.toList());
	}

}
