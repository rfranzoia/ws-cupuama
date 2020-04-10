package br.com.cupuama.domain.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.stock.entity.StocktakeType;
import br.com.cupuama.domain.stock.repository.StocktakeTypeRepository;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some stocktakeType specific things.
 * <p/>
 */
@Service
public class StocktakeTypeService extends DefaultService<StocktakeType, Long> {

	public StocktakeTypeService(final StocktakeTypeRepository stocktakeTypeRepository) {
		super(stocktakeTypeRepository);
	}

	/**
	 * Update the location for a stocktakeType.
	 *
	 * @param stocktakeTypeId
	 * @param longitude
	 * @param latitude
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final Long stocktakeTypeId, final String name) throws EntityNotFoundException {
		StocktakeType stocktakeType = findByIdChecked(stocktakeTypeId);
		stocktakeType.setName(name);
	}

	/**
	 * Find all stocktakeTypes by name.
	 *
	 * @param name
	 */
	public List<StocktakeType> findByName(final String name) {
		return ((StocktakeTypeRepository) repository).findByNameLike(name + "%");
	}

}
