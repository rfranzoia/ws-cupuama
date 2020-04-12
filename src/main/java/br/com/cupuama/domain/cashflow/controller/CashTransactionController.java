package br.com.cupuama.domain.cashflow.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cupuama.domain.cashflow.dto.CashTransactionDTO;
import br.com.cupuama.domain.cashflow.mapper.CashTransactionMapper;
import br.com.cupuama.domain.cashflow.service.CashTransactionService;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.exception.InvalidRequestException;

/**
 * All operations with a cashTransaction will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/cashTransactions")
public class CashTransactionController {

	public static final SimpleDateFormat SEARCH_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private final CashTransactionService cashTransactionService;

	@Autowired
	public CashTransactionController(final CashTransactionService cashTransactionService) {
		this.cashTransactionService = cashTransactionService;
	}

	@GetMapping("/{cashTransactionId}")
	public CashTransactionDTO getCashTransaction(@PathVariable final long cashTransactionId) throws EntityNotFoundException {
		return CashTransactionMapper.makeDTO(cashTransactionService.find(cashTransactionId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CashTransactionDTO addCashTransaction(@Valid @RequestBody final CashTransactionDTO cashTransactionDTO)
			throws ConstraintsViolationException, EntityNotFoundException {
		return cashTransactionService.addCashTransaction(cashTransactionDTO);
	}

	@DeleteMapping("/{cashTransactionId}")
	public void removeCashTransaction(@PathVariable final long cashTransactionId) 
			throws ConstraintsViolationException, EntityNotFoundException {
		cashTransactionService.removeCashTransaction(cashTransactionId);
	}

	@GetMapping("/dates")
	public List<CashTransactionDTO> findByDateRange(@RequestParam(required = false) final String start, 
			@RequestParam(required = false) final String end) throws InvalidRequestException {
		try {
			Date startDate = (StringUtils.isEmpty(start))? SEARCH_DATE_FORMAT.parse(start): new Date();
			Date endDate = (StringUtils.isEmpty(end))? SEARCH_DATE_FORMAT.parse(end): new Date();
			
			if (endDate.before(startDate)) {
				throw new InvalidRequestException("The end date must be greater than start date");
			}
			
			return CashTransactionMapper.makeListDTO(cashTransactionService.findByDateRange(startDate, endDate));
			
		} catch (InvalidRequestException | ParseException ex) {
			throw new InvalidRequestException("There was a problem while processing your request", ex);
		}
		
	}
	
	@GetMapping
	public List<CashTransactionDTO> findAllCashTransactions() {
		return CashTransactionMapper.makeListDTO(cashTransactionService.findAll());
	}
}
