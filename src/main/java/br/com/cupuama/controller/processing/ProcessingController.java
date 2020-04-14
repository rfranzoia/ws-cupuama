package br.com.cupuama.controller.processing;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cupuama.Services.processing.ProcessingDetailService;
import br.com.cupuama.Services.processing.ProcessingService;
import br.com.cupuama.controller.processing.dto.ProcessingDTO;
import br.com.cupuama.controller.processing.dto.ProcessingDetailDTO;
import br.com.cupuama.controller.processing.mapper.ProcessingMapper;
import br.com.cupuama.enums.ProcessStatus;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.exception.InvalidRequestException;
import br.com.cupuama.util.Utils;

/**
 * All operations with a processing will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/processings")
public class ProcessingController {

	@Autowired
	private ProcessingDetailService ProcessingDetailService;
	
	private final ProcessingService processingService;

	@Autowired
	public ProcessingController(final ProcessingService processingService) {
		this.processingService = processingService;
	}

	@GetMapping("/{processingId}")
	public ProcessingDTO getProcessing(@PathVariable final long processingId) throws EntityNotFoundException {
		final ProcessingDTO processingDTO = ProcessingMapper.makeDTO(processingService.find(processingId));
		final List<ProcessingDetailDTO> details = ProcessingDetailService.findByProcessing(ProcessingMapper.makeProcessing(processingDTO));
		processingDTO.getProcessingDetail().addAll(details);
		return processingDTO;
	}
	
	@GetMapping("/processingDetails/{processingId}")
	public List<ProcessingDetailDTO> findProcessingDetailByProcessing(@PathVariable final long processingId) throws EntityNotFoundException {
		return ProcessingDetailService.findByProcessing(processingService.find(processingId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProcessingDTO createProcessing(@Valid @RequestBody final ProcessingDTO processingDTO) throws ConstraintsViolationException {
		return processingService.create(processingDTO);
	}

	@PutMapping("/{processingId}")
	public void update(@PathVariable final long processingId, @RequestBody final ProcessingDTO processingDTO) throws EntityNotFoundException {
		processingService.update(processingId, processingDTO);
	}
	
	@PutMapping("/processingStatus/{processingId}")
	public void updateProcessStatus(@PathVariable final long processingId, 
			@RequestParam final ProcessStatus processStatus) throws EntityNotFoundException, InvalidRequestException {
		processingService.updateProcessStatus(processingId, processStatus);
	}

	@GetMapping("/dates")
	public List<ProcessingDTO> findByDateRange(@RequestParam(required = false, name = "startDate") final String start, 
			@RequestParam(required = false, name = "endDate") final String end) throws InvalidRequestException {
		try {
			Date startDate = (StringUtils.isEmpty(start))? Utils.SEARCH_DATE_FORMAT.parse(start): new Date();
			Date endDate = (StringUtils.isEmpty(end))? Utils.SEARCH_DATE_FORMAT.parse(end): new Date();
			
			if (endDate.before(startDate)) {
				throw new InvalidRequestException("The end date must be greater than start date");
			}
			
			return processingService.findByProcessingDateRange(startDate, endDate);
			
		} catch (InvalidRequestException | ParseException ex) {
			throw new InvalidRequestException("There was a problem while processing your request", ex);
		}
		
	}
	
	@GetMapping("/customer/{customerId}")
	public List<ProcessingDTO> findByCustomerId(@PathVariable final long customerId) {
		return processingService.findByCustomer(customerId);
	}
	
	@GetMapping("/supplier/{supplierId}")
	public List<ProcessingDTO> findBySupplierId(@PathVariable final long supplierId) {
		return processingService.findBySupplier(supplierId);
	}
	
	@GetMapping("/processType/{processTypeId}")
	public List<ProcessingDTO> findByProcessTypeId(@PathVariable final long processTypeId) {
		return processingService.findByProcessType(processTypeId);
	}
	
	@GetMapping("/status/{processStatus}")
	public List<ProcessingDTO> findByProcessStatus(@PathVariable final ProcessStatus processStatus) {
		return processingService.findByProcessStatus(processStatus);
	}
	
	@GetMapping
	public List<ProcessingDTO> findAllProcessings() {
		return ProcessingMapper.makeListDTO(processingService.findAll());
	}
}
