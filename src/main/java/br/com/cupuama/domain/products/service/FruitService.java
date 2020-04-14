package br.com.cupuama.domain.products.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.products.dto.FruitDTO;
import br.com.cupuama.domain.products.entity.Fruit;
import br.com.cupuama.domain.products.mapper.FruitMapper;
import br.com.cupuama.domain.products.repository.FruitRepository;
import br.com.cupuama.exception.ConstraintsViolationException;
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

	@Transactional
	public FruitDTO create(FruitDTO dto) throws ConstraintsViolationException {
		Fruit fruit = FruitMapper.makeFruit(dto);
		return FruitMapper.makeDTO(create(fruit));
	}
	
	/**
	 * Update a fruits information.
	 *
	 * @param fruitId
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final Long fruitId, final FruitDTO dto) throws EntityNotFoundException {
		Fruit fruit = findByIdChecked(fruitId);
		fruit.setName(dto.getName());
		fruit.setInitials(dto.getInitials());
		fruit.setHarvest(dto.getHarvest());
		fruit.getAudit().setDateUpdated(ZonedDateTime.now());
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
