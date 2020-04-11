package br.com.cupuama.domain.products.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.products.dto.FruitDTO;
import br.com.cupuama.domain.products.entity.Fruit;
import br.com.cupuama.domain.products.repository.FruitRepository;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some fruit specific things.
 * <p/>
 */
@Service
public class FruitService extends DefaultService<Fruit, Long> {

	public FruitService(final FruitRepository fruitRepository) {
		super(fruitRepository);
	}

	/**
	 * Update a fruits information.
	 *
	 * @param fruitId
	 * @param longitude
	 * @param latitude
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final Long fruitId, final FruitDTO fruitDTO) throws EntityNotFoundException {
		Fruit fruit = findByIdChecked(fruitId);
		fruit.setName(fruitDTO.getName());
		fruit.setInitials(fruitDTO.getInitials());
		fruit.setHarvest(fruitDTO.getHarvest());
	}

	/**
	 * Find all fruits by name.
	 *
	 * @param name
	 */
	public List<Fruit> findByName(final String name) {
		return ((FruitRepository) repository).findByNameLike(name + "%");
	}

}