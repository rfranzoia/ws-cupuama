package br.com.cupuama.Services.stock;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.controller.stock.dto.DepotDTO;
import br.com.cupuama.controller.stock.mapper.DepotMapper;
import br.com.cupuama.domain.stock.Depot;
import br.com.cupuama.domain.stock.repository.DepotRepository;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some depot specific things.
 * <p/>
 */
@Service
public class DepotService extends DefaultService<Depot, Long> {

	public DepotService(final DepotRepository depotRepository) {
		super(depotRepository);
	}

	@Transactional
	public DepotDTO create(DepotDTO dto) throws ConstraintsViolationException {
		Depot depot = DepotMapper.makeDepot(dto);
		return DepotMapper.makeDTO(create(depot));
	}
	
	/**
	 * Update a depots information.
	 *
	 * @param depotId
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final Long depotId, final DepotDTO dto) throws EntityNotFoundException {
		Depot depot = findByIdChecked(depotId);
		depot.setName(dto.getName());
		depot.setKeepStock(dto.isKeepStock());
		depot.getAudit().setDateUpdated(ZonedDateTime.now());
	}

	/**
	 * Find all depots by name.
	 *
	 * @param name
	 */
	public List<Depot> findByName(final String name) {
		return ((DepotRepository) repository).findByNameLike(name + "%");
	}

}
