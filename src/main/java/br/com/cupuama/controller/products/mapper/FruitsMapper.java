package br.com.cupuama.controller.products.mapper;

import br.com.cupuama.controller.products.dto.FruitsDTO;
import br.com.cupuama.domain.products.Fruits;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class FruitsMapper {
    public static Fruits makeFruit(FruitsDTO dto) {
        return new Fruits(dto.getId(), dto.getName(), dto.getInitials(), dto.getHarvest());
    }

    public static FruitsDTO makeDTO(Fruits fruits) {
        FruitsDTO.FruitDTOBuilder fruitDTOBuilder = FruitsDTO.newBuilder()
                .setId(fruits.getId())
                .setName(fruits.getName())
                .setInitials(fruits.getInitials())
                .setHarvest(fruits.getHarvest());

        return fruitDTOBuilder.createFruitDTO();
    }

    public static List<FruitsDTO> makeListDTO(Collection<Fruits> fruits) {
        return fruits.stream()
                .map(FruitsMapper::makeDTO)
                .collect(Collectors.toList());
    }

    public static List<Fruits> makeList(Collection<FruitsDTO> fruitsDTO) {
        return fruitsDTO.stream()
                .map(FruitsMapper::makeFruit)
                .collect(Collectors.toList());
    }

}
