package br.com.cupuama.service.products;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.products.Fruit;
import br.com.cupuama.dto.FruitDTO;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.repository.FruitRepository;
import br.com.cupuama.service.DefaultServiceImplementation;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some fruit specific things.
 * <p/>
 */
@Service
public class FruitService extends DefaultServiceImplementation<Fruit, Long> {

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
	public void update(long fruitId, FruitDTO fruitDTO) throws EntityNotFoundException {
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
	public List<Fruit> findByName(String name) {
		return ((FruitRepository) repository).findByNameLike(name + "%");
	}

}
