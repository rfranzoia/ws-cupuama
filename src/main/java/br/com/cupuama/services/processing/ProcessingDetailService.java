package br.com.cupuama.services.processing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.controller.processing.dto.ProcessingDetailDTO;
import br.com.cupuama.controller.processing.mapper.ProcessingDetailMapper;
import br.com.cupuama.domain.processing.Processing;
import br.com.cupuama.domain.processing.ProcessingDetail;
import br.com.cupuama.domain.processing.repository.ProcessingDetailRepository;
import br.com.cupuama.domain.products.Fruits;
import br.com.cupuama.domain.products.Products;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.services.products.FruitsService;
import br.com.cupuama.services.products.ProductsService;
import br.com.cupuama.util.DefaultService;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some ProcessingDetail specific things.
 * <p/>
 */
@Service
public class ProcessingDetailService extends DefaultService<ProcessingDetail, Long> {

	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private FruitsService fruitsService;
	
	public ProcessingDetailService(final ProcessingDetailRepository processingDetailRepository) {
		super(processingDetailRepository);
	}

	/**
	 * create a ProcessingDetail
	 * 
	 * @param dto
	 * @return
	 * @throws ConstraintsViolationException
	 */
	@Transactional
	public ProcessingDetailDTO create(ProcessingDetailDTO dto) throws ConstraintsViolationException {
		ProcessingDetail processingDetail = ProcessingDetailMapper.makeProcessingDetail(dto);
		return ProcessingDetailMapper.makeDTO(create(processingDetail));
	}
	
	/**
	 * Add all processingDetail from the given list, associating them with the Processing 
	 * 
	 * @param processing
	 * @param details
	 * @return
	 */
	@Transactional
	public List<ProcessingDetailDTO> createAllForProcessing(Processing processing, List<ProcessingDetail> details) {
		List<ProcessingDetailDTO> list = new ArrayList<>();
		
		Predicate<ProcessingDetail> createProcesingDetail = processDetail -> {
			try {
				processDetail.setProcess(processing);
				list.add(ProcessingDetailMapper.makeDTO(create(processDetail)));
				return true;
			} catch (ConstraintsViolationException ex) {
				LOG.error(String.format("There was an error while creating a processingDetail %s", processDetail.toString()), ex);
				return false;
			}
		};
		
		details.stream()
			.filter(createProcesingDetail)
			.anyMatch(processingDetail -> false);
		
		return list;
	}
	
	/**
	 * Removes all ProcessingDetail associated with the given Processing
	 * 
	 * @param processing
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void removeAllFromProcessing(final Processing processing) throws EntityNotFoundException {
		List<ProcessingDetail> list = ((ProcessingDetailRepository) repository).findByProcessing(processing);
		
		Predicate<ProcessingDetail> removeProcessingDetail = processDetail -> {
			try {
				repository.delete(processDetail);
				return true;
			} catch (Exception ex) {
				LOG.error(String.format("There was an error while removing a processingDetail %s", processDetail.toString()), ex);
				return false;
			}
		};
		
		list.stream()
				.filter(removeProcessingDetail)
				.anyMatch(pd -> false);
		
	}
	
	/**
	 * Returns all ProcessingDetail for a given Processing
	 * 
	 * @param processing
	 * @return
	 */
	public List<ProcessingDetailDTO> findByProcessing(final Processing processing) {
		return ProcessingDetailMapper.makeListDTO(((ProcessingDetailRepository) repository).findByProcessing(processing));
	}
	
	/**
	 * Returns all ProcessingDetail that have Product and Fruit
	 * s
	 * @param productId
	 * @param fruitId
	 * @return
	 */
	public List<ProcessingDetailDTO> findByProductAndFruit(final Long productId, final Long fruitId) {
		try {
			Products products = productsService.find(productId);
			Fruits fruits = fruitsService.find(fruitId);
			return ProcessingDetailMapper.makeListDTO(((ProcessingDetailRepository) repository).findByProductAndFruit(products, fruits));
		} catch (EntityNotFoundException ex) {
			LOG.error(String.format("Either a Product(ID:%d) or Fruit(ID:%d) was not found!", productId, fruitId), ex);
			return new ArrayList<ProcessingDetailDTO>();
		}
	}
	
	/**
	 * List all ProcessingDetail that are in the Processing date range provided
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ProcessingDetailDTO> findByProcessDateRange(Date start, Date end) {
		return ProcessingDetailMapper.makeListDTO(((ProcessingDetailRepository) repository).findByProcessDateRange(start, end));
	}
}
