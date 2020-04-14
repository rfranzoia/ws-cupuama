package br.com.cupuama.services.processing;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.controller.cashflows.dto.CashTransactionDTO;
import br.com.cupuama.controller.persons.mapper.CustomerMapper;
import br.com.cupuama.controller.persons.mapper.SupplierMapper;
import br.com.cupuama.controller.processing.dto.ProcessingDTO;
import br.com.cupuama.controller.processing.dto.ProcessingDetailDTO;
import br.com.cupuama.controller.processing.mapper.ProcessTypeMapper;
import br.com.cupuama.controller.processing.mapper.ProcessingDetailMapper;
import br.com.cupuama.controller.processing.mapper.ProcessingMapper;
import br.com.cupuama.controller.products.dto.ProductFruitKey;
import br.com.cupuama.controller.stock.dto.StocktakeDTO;
import br.com.cupuama.domain.cashflow.CashFlowType;
import br.com.cupuama.domain.processing.Processing;
import br.com.cupuama.domain.processing.ProcessingDetail;
import br.com.cupuama.domain.processing.repository.ProcessingRepository;
import br.com.cupuama.enums.FlowTypeModel;
import br.com.cupuama.enums.ProcessStatus;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.exception.InvalidRequestException;
import br.com.cupuama.services.cashflow.CashTransactionService;
import br.com.cupuama.services.stock.StocktakeService;
import br.com.cupuama.util.DefaultService;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some Processing specific things.
 * <p/>
 */
@Service
public class ProcessingService extends DefaultService<Processing, Long> {

	@Autowired
	private CashTransactionService cashTransationService;
	
	@Autowired
	private StocktakeService stocktakeService;
	
	@Autowired
	private ProcessingDetailService processingDetailService;
	
	public ProcessingService(final ProcessingRepository processingRepository) {
		super(processingRepository);
	}

	@Transactional
	public ProcessingDTO create(ProcessingDTO dto) throws ConstraintsViolationException {
		Processing processing = create(ProcessingMapper.makeProcessing(dto));
				
		if (!dto.getProcessingDetail().isEmpty()) {
			processingDetailService.createAllForProcessing(processing, ProcessingDetailMapper.makeList(dto.getProcessingDetail()));
		}
		
		return ProcessingMapper.makeDTO(processing);
	}
	
	/**
	 * Update Processing information
	 *
	 * @param ProcessingId
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final Long processingId, final ProcessingDTO dto) throws EntityNotFoundException {
		
		// update some of the processing information
		Processing processing = findByIdChecked(processingId);
		processing.setCustomer(CustomerMapper.makeCustomer(dto.getCustomer()));
		processing.setSupplier(SupplierMapper.makeSupplier(dto.getSupplier()));
		processing.setDocumentReference(dto.getDocumentReference());
		processing.setRemarks(dto.getRemarks());
		processing.getAudit().setDateUpdated(ZonedDateTime.now());
		
		// update processing details (remove all, add current ones)
		processingDetailService.removeAllFromProcessing(processing);
		processingDetailService.createAllForProcessing(processing, ProcessingDetailMapper.makeList(dto.getProcessingDetail()));
		
	}
	
	@Transactional
	public void updateProcessStatus(final Long processingId, ProcessStatus processStatus) throws EntityNotFoundException, InvalidRequestException {
		Processing processing = findByIdChecked(processingId);
		
		// Processing cancellation is only allowed during Created, Approved and Rejected statuses
		switch (processStatus) {
			case Canceled:
				switch (processing.getProcessStatus()) {
					case Canceled:
					case Paid:
					case Completed:
						throw new InvalidRequestException(String.format("Process cancelation is not permitted anymore!"));
					default:
						processing.setProcessStatus(processStatus);
						break;
				}
				break;
			case Completed:
				processing.setProcessStatus(processStatus);
				updateInventory(processing);
				break;
			case Paid:
				processing.setProcessStatus(processStatus);
				updateCashFlow(processing);
				break;
			default:
				processing.setProcessStatus(processStatus);
				break;
		}
	}
	
	@Transactional
	public void delete(final Long processingId) throws EntityNotFoundException {
		Processing processing = findByIdChecked(processingId);
		
		//remove all ProcessingDetail associated to this processing
		processingDetailService.removeAllFromProcessing(processing);
		
		super.delete(processingId);
	}

	/**
	 * Finds all processings within the date range
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ProcessingDTO> findByProcessingDateRange(Date start, Date end) {
		// locate all processings 
		List<Processing> processings = ((ProcessingRepository) repository).findByProcessDateRange(start, end);
		
		// add details for each processing found
		List<ProcessingDTO> dtoList = createProcessingDTOListWithDetails(processings);
		
		return dtoList;
	}
	
	/**
	 * finds all processing that have the provided CustomerID
	 * 
	 * @param customerId
	 * @return
	 */
	public List<ProcessingDTO> findByCustomer(final Long customerId) {

		// locate all processings 
		List<Processing> processings = ((ProcessingRepository) repository).findByCustomer(customerId);
		
		// add details for each processing found
		List<ProcessingDTO> dtoList = createProcessingDTOListWithDetails(processings);
		
		return dtoList;
	
	}
	
	/**
	 * finds all processing that have the provided SupplierID
	 * 
	 * @param supplierId
	 * @return
	 */
	public List<ProcessingDTO> findBySupplier(final Long supplierId) {

		// locate all processings 
		List<Processing> processings = ((ProcessingRepository) repository).findBySupplier(supplierId);
		
		// add details for each processing found
		List<ProcessingDTO> dtoList = createProcessingDTOListWithDetails(processings);
		
		return dtoList;
	
	
	}
	
	/**
	 * finds all processing that have the provided ProcessTypeID
	 * 
	 * @param processTypeId
	 * @return
	 */
	public List<ProcessingDTO> findByProcessType(final Long processTypeId) {

		// locate all processings
		List<Processing> processings = ((ProcessingRepository) repository).findByProcessType(processTypeId);
		
		// add details for each processing found
		List<ProcessingDTO> dtoList = createProcessingDTOListWithDetails(processings);
		
		return dtoList;

	}
	
	/**
	 * finds all processing that are in the provided ProcessStatus
	 * 
	 * @param processStatus
	 * @return
	 */
	public List<ProcessingDTO> findByProcessStatus(final ProcessStatus processStatus) {
		// locate all processings 
		List<Processing> processings = ((ProcessingRepository) repository).findByProcessStatus(processStatus);

		// add details for each processing found
		List<ProcessingDTO> dtoList = createProcessingDTOListWithDetails(processings);

		return dtoList;
	}

	/**
	 * Updates the inventory with all ProcessingDetail associated to the Processing
	 * 
	 * @param processing
	 */
	private void updateInventory(final Processing processing) {
		final List<ProcessingDetailDTO> details = processingDetailService.findByProcessing(processing);
		
		for (ProcessingDetailDTO processingDetail : details) {
			Double amount = processingDetail.getAmount();
			
			try {
				final ProductFruitKey productFruitKey = new ProductFruitKey(processingDetail.getProduct(), processingDetail.getFruit());
				final StocktakeDTO stocktakeDTO = StocktakeDTO.newBuilder()
										.setProductFruitKey(productFruitKey)
										.setDepot(processingDetail.getDepot())
										.setAmount(amount)
										.setStocktakeDate(processing.getProcessDate())
										.setStocktakeInOut(processingDetail.getStocktakeInOut())
										.createDTO();
				stocktakeService.addStocktake(stocktakeDTO);
			} catch (ConstraintsViolationException ex) {
				LOG.error(String.format("There was an error to perform a addStocktake(dto)"), ex);
			}
		}
	}
	
	/**
	 * Update the cash flow for Sales and Acquisition processing types
	 * @param processing
	 */
	@Transactional
	private void updateCashFlow(final Processing processing) {
		final FlowTypeModel flowTypeModel = processing.getProcessType().getFlowTypeModel();
		final List<ProcessingDetailDTO> details = processingDetailService.findByProcessing(processing);
		
		if (flowTypeModel == FlowTypeModel.Sales) {
			Double credits = 0.0;
			for (ProcessingDetailDTO processDetail : details) {
				credits += (processDetail.getPrice() * processDetail.getAmount()) - (processDetail.getDiscount() == null?0.0: processDetail.getDiscount());
			}
			
			try {
				final CashTransactionDTO creditDTO = CashTransactionDTO.newBuilder()
						.setItemDate(processing.getProcessDate())
						.setCashFlowType(CashFlowType.CREDIT)
						.setValue(credits)
						.setDocumentNumber(processing.getDocumentReference())
						.setDescription(flowTypeModel.toString())
						.createDTO();
				cashTransationService.addCashTransaction(creditDTO);
				
			} catch (ConstraintsViolationException | EntityNotFoundException ex) {
				LOG.error(String.format("There was an error to perform a addCashTransaction(credit)"), ex);
			}
		} else if (flowTypeModel == FlowTypeModel.Acqisitions) {
			Double debits = 0.0;
			for (ProcessingDetailDTO processDetail : details) {
				debits += (processDetail.getPrice() * processDetail.getAmount()) - (processDetail.getDiscount() == null?0.0: processDetail.getDiscount());
			}
			
			try {
				final CashTransactionDTO debitDTO = CashTransactionDTO.newBuilder()
						.setItemDate(processing.getProcessDate())
						.setCashFlowType(CashFlowType.DEBIT)
						.setValue(debits)
						.setDocumentNumber(processing.getDocumentReference())
						.setDescription(flowTypeModel.toString())
						.createDTO();
				cashTransationService.addCashTransaction(debitDTO);
				
			} catch (ConstraintsViolationException | EntityNotFoundException ex) {
				LOG.error(String.format("There was an error to perform a addCashTransaction(debit)"), ex);
			}
		}
		
	}
	
	/**
	 * add processing details for each memeber of a given processing list
	 * 
	 * @param processings
	 * @return
	 */
	private List<ProcessingDTO> createProcessingDTOListWithDetails(List<Processing> processings) {
		final List<ProcessingDTO> dtoList = processings.stream()
				.map(p -> {
					List<ProcessingDetail> details = new ArrayList<>(); // TODO: use processingDetail Service here
					ProcessingDTO dto = ProcessingDTO.newBuilder()
											.setId(p.getId())
											.setProcessDate(p.getProcessDate())
											.setProcessType(ProcessTypeMapper.makeDTO(p.getProcessType()))
											.setProcessStatus(p.getProcessStatus())
											.setCustomer(CustomerMapper.makeDTO(p.getCustomer()))
											.setSupplier(SupplierMapper.makeDTO(p.getSupplier()))
											.setDocumentReference(p.getDocumentReference())
											.setRemarks(p.getRemarks())
											.setDetails(ProcessingDetailMapper.makeListDTO(details))
											.createProcessingDTO();
					return dto;
				})
				.collect(Collectors.toList());
		return dtoList;
	}
}
