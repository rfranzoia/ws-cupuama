package br.com.cupuama.services.products;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.controller.products.dto.FruitsDTO;
import br.com.cupuama.controller.products.mapper.FruitsMapper;
import br.com.cupuama.domain.products.Fruits;
import br.com.cupuama.domain.products.repository.FruitsRepository;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some fruit specific things.
 * <p/>
 */
@Service
public class FruitsService extends DefaultService<Fruits, Long> {

	public FruitsService(final FruitsRepository fruitsRepository) {
		super(fruitsRepository);
	}

	@Transactional
	public FruitsDTO create(FruitsDTO dto) throws ConstraintsViolationException {
		Fruits fruits = FruitsMapper.makeFruit(dto);
		return FruitsMapper.makeDTO(create(fruits));
	}
	
	/**
	 * Update a fruits information.
	 *
	 * @param fruitId
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final Long fruitId, final FruitsDTO dto) throws EntityNotFoundException {
		Fruits fruits = findByIdChecked(fruitId);
		fruits.setName(dto.getName());
		fruits.setInitials(dto.getInitials());
		fruits.setHarvest(dto.getHarvest());
		fruits.getAudit().setDateUpdated(ZonedDateTime.now());
	}

	/**
	 * Find all fruits by name.
	 *
	 * @param name
	 */
	public List<Fruits> findByName(final String name) {
		return ((FruitsRepository) repository).findByNameLike(name + "%");
	}

}
