package br.com.cupuama.domain.processing.service;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.processing.dto.ProductionProcessDTO;
import br.com.cupuama.domain.processing.entity.ProductionProcess;
import br.com.cupuama.domain.processing.entity.ProductionProcessKey;
import br.com.cupuama.domain.processing.repository.ProductionProcessRepository;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some productionProcess specific things.
 * <p/>
 */
@Service
public class ProductionProcessService extends DefaultService<ProductionProcess, ProductionProcessKey> {

	public static final SimpleDateFormat PERIOD_DATE_FORMAT = new SimpleDateFormat("yyyyMM");
	public static final String PERIOD_REGEX = "([0-9]{4})([0-9]{2})";

	public ProductionProcessService(final ProductionProcessRepository productionProcessRepository) {
		super(productionProcessRepository);
	}
	
	/**
	 * Update a ProductionProcess depot.
	 *
	 * @param dto
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final ProductionProcessDTO dto) throws EntityNotFoundException {
		ProductionProcess productionProcess = findByIdChecked(dto.getKey());
		productionProcess.setDepot(dto.getDepot());
	}

}
