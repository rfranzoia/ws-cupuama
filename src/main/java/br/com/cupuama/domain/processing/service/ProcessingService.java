package br.com.cupuama.domain.processing.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.cashflow.dto.CashTransactionDTO;
import br.com.cupuama.domain.cashflow.entity.CashFlowType;
import br.com.cupuama.domain.cashflow.service.CashTransactionService;
import br.com.cupuama.domain.processing.dto.ProcessingDTO;
import br.com.cupuama.domain.processing.entity.FlowTypeModel;
import br.com.cupuama.domain.processing.entity.ProcessStatus;
import br.com.cupuama.domain.processing.entity.Processing;
import br.com.cupuama.domain.processing.entity.ProcessingDetail;
import br.com.cupuama.domain.processing.mapper.CustomerMapper;
import br.com.cupuama.domain.processing.mapper.ProcessTypeMapper;
import br.com.cupuama.domain.processing.mapper.ProcessingDetailMapper;
import br.com.cupuama.domain.processing.mapper.ProcessingMapper;
import br.com.cupuama.domain.processing.mapper.SupplierMapper;
import br.com.cupuama.domain.processing.repository.ProcessingRepository;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
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
	
	public ProcessingService(final ProcessingRepository processingRepository) {
		super(processingRepository);
	}

	@Transactional
	public ProcessingDTO create(ProcessingDTO processingDTO) throws ConstraintsViolationException {
		Processing processing = create(ProcessingMapper.makeProcessing(processingDTO));
				
		if (!processingDTO.getProcessingDetail().isEmpty()) {
			
		}
		
		return ProcessingMapper.makeProcessingDTO(processing);
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
		
		// update processing details (remove all, add current ones)
		
	}
	
	@Transactional
	public void updateProcessStatus(final Long processingId, ProcessStatus processStatus) throws EntityNotFoundException {
		Processing processing = findByIdChecked(processingId);
		processing.setProcessStatus(processStatus);
		
		switch (processStatus) {
			case Canceled:
			case Completed:
				updateInventory(processing);
				break;
			case Paid:
				updateCashFlow(processing);
				break;
			default:
				break;
		}
	}
	
	private void updateInventory(final Processing processing) {
		final List<ProcessingDetail> details = new ArrayList<>(); // TODO: use processingDetailService here
		// use stream and predicate to process each detail
	}
	
	/**
	 * Update the cash flow for Sales and Acquisition processing types
	 * @param processing
	 */
	@Transactional
	private void updateCashFlow(final Processing processing) {
		final FlowTypeModel flowTypeModel = processing.getProcessType().getFlowTypeModel();
		final List<ProcessingDetail> details = new ArrayList<>(); // TODO: use processingDetailService here
		
		if (flowTypeModel == FlowTypeModel.Sales) {
			Double credits = 0.0;
			for (ProcessingDetail processDetail : details) {
				credits += (processDetail.getPrice() * processDetail.getAmount()) - (processDetail.getDiscount() == null?0.0: processDetail.getDiscount());
			}
			
			try {
				CashTransactionDTO creditDTO = CashTransactionDTO.newBuilder()
						.setItemDate(processing.getProcessDate())
						.setCashFlowType(CashFlowType.CREDIT)
						.setValue(credits)
						.setDocumentNumber(processing.getDocumentReference())
						.setDescription(flowTypeModel.toString())
						.createCashTransation();
				cashTransationService.addCashTransaction(creditDTO);
				
			} catch (ConstraintsViolationException | EntityNotFoundException ex) {
				LOG.error(String.format("There was an error to perform a addCashTransaction(credit)"), ex);
			}
		} else if (flowTypeModel == FlowTypeModel.Acqisitions) {
			Double debits = 0.0;
			for (ProcessingDetail processDetail : details) {
				debits += (processDetail.getPrice() * processDetail.getAmount()) - (processDetail.getDiscount() == null?0.0: processDetail.getDiscount());
			}
			
			try {
				CashTransactionDTO debitDTO = CashTransactionDTO.newBuilder()
						.setItemDate(processing.getProcessDate())
						.setCashFlowType(CashFlowType.DEBIT)
						.setValue(debits)
						.setDocumentNumber(processing.getDocumentReference())
						.setDescription(flowTypeModel.toString())
						.createCashTransation();
				cashTransationService.addCashTransaction(debitDTO);
				
			} catch (ConstraintsViolationException | EntityNotFoundException ex) {
				LOG.error(String.format("There was an error to perform a addCashTransaction(debit)"), ex);
			}
		}
		
	}
	
	@Transactional
	public void delete(final Long processingId) throws EntityNotFoundException {
		Processing processing = findByIdChecked(processingId);
		
		//update processing details (remove all for the processingId)
		
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
		List<ProcessingDTO> processingDTOList = createProcessingDTOListWithDetails(processings);
		
		return processingDTOList;
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
		List<ProcessingDTO> processingDTOList = createProcessingDTOListWithDetails(processings);
		
		return processingDTOList;
	
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
		List<ProcessingDTO> processingDTOList = createProcessingDTOListWithDetails(processings);
		
		return processingDTOList;
	
	
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
		List<ProcessingDTO> processingDTOList = createProcessingDTOListWithDetails(processings);
		
		return processingDTOList;

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
		List<ProcessingDTO> processingDTOList = createProcessingDTOListWithDetails(processings);

		return processingDTOList;
	}

	/**
	 * add processing details for each memeber of a given processing list
	 * 
	 * @param processings
	 * @return
	 */
	private List<ProcessingDTO> createProcessingDTOListWithDetails(List<Processing> processings) {
		final List<ProcessingDTO> processingDTOList = processings.stream()
				.map(p -> {
					List<ProcessingDetail> details = new ArrayList<>(); // TODO: use processingDetail Service here
					ProcessingDTO dto = ProcessingDTO.newBuilder()
											.setId(p.getId())
											.setProcessDate(p.getProcessDate())
											.setProcessType(ProcessTypeMapper.makeProcessTypeDTO(p.getProcessType()))
											.setProcessStatus(p.getProcessStatus())
											.setCustomer(CustomerMapper.makeCustomerDTO(p.getCustomer()))
											.setSupplier(SupplierMapper.makeSupplierDTO(p.getSupplier()))
											.setDocumentReference(p.getDocumentReference())
											.setRemarks(p.getRemarks())
											.setDetails(ProcessingDetailMapper.makeProcessingDetailDTOList(details))
											.createProcessingDTO();
					return dto;
				})
				.collect(Collectors.toList());
		return processingDTOList;
	}
}
