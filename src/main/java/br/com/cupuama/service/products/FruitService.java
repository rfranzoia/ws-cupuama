package br.com.cupuama.service.products;

import java.util.List;

import br.com.cupuama.domain.products.Fruit;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

public interface FruitService {

    Fruit find(Long fruitId) throws EntityNotFoundException;

    Fruit create(Fruit fruitDO) throws ConstraintsViolationException;

    void delete(Long fruitId) throws EntityNotFoundException;

    void update(long fruitId, String name) throws EntityNotFoundException;

    List<Fruit> findByName(String name);

    List<Fruit> findAll();

}