package br.com.cupuama.domain.stock.service;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.stock.dto.StocktakeProcessDTO;
import br.com.cupuama.domain.stock.entity.StocktakeProcess;
import br.com.cupuama.domain.stock.entity.StocktakeProcessKey;
import br.com.cupuama.domain.stock.repository.StocktakeProcessRepository;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some stocktakeProcess specific things.
 * <p/>
 */
@Service
public class StocktakeProcessService extends DefaultService<StocktakeProcess, StocktakeProcessKey> {

	public static final SimpleDateFormat PERIOD_DATE_FORMAT = new SimpleDateFormat("yyyyMM");
	public static final String PERIOD_REGEX = "([0-9]{4})([0-9]{2})";

	public StocktakeProcessService(final StocktakeProcessRepository stocktakeProcessRepository) {
		super(stocktakeProcessRepository);
	}
	
	/**
	 * Update a StocktakeProcess depot.
	 *
	 * @param dto
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final StocktakeProcessDTO dto) throws EntityNotFoundException {
		StocktakeProcess stocktakeProcess = findByIdChecked(dto.getKey());
		stocktakeProcess.setDepot(dto.getDepot());
	}

}
