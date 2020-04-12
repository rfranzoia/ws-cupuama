package br.com.cupuama.domain.cashflow.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cupuama.domain.cashflow.dto.CashFlowDTO;
import br.com.cupuama.domain.cashflow.entity.CashFlowType;
import br.com.cupuama.domain.cashflow.mapper.CashFlowMapper;
import br.com.cupuama.domain.cashflow.service.CashFlowService;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

/**
 * All operations with a cashFlow will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/cashflows")
public class CashFlowController {

	private final CashFlowService cashFlowService;

	@Autowired
	public CashFlowController(final CashFlowService cashFlowService) {
		this.cashFlowService = cashFlowService;
	}

	@GetMapping("/{cashFlowPeriod}")
	public CashFlowDTO getCashFlow(@PathVariable final String cashFlowPeriod) throws EntityNotFoundException {
		return CashFlowMapper.makeCashFlowDTO(cashFlowService.find(cashFlowPeriod));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CashFlowDTO createCashFlow(@Valid @RequestBody final CashFlowDTO cashFlowDTO) throws ConstraintsViolationException {
		return cashFlowService.create(cashFlowDTO);
	}

	@DeleteMapping("/{cashFlowPeriod}")
	public void deleteCashFlow(@PathVariable final String cashFlowPeriod) throws EntityNotFoundException {
		cashFlowService.delete(cashFlowPeriod);
	}

	@PutMapping("/previousBalance/{cashFlowPeriod}")
	public void updatePreviousBalance(@PathVariable final String cashFlowPeriod, @RequestParam final Double previousBalance)
			throws EntityNotFoundException, ConstraintsViolationException {
		cashFlowService.updateOrCreatePreviousBalance(cashFlowPeriod, previousBalance);
	}
	
	@PutMapping("/credit/{cashFlowPeriod}")
	public void updateCredit(@PathVariable final String cashFlowPeriod, @RequestParam final Double credit)
			throws EntityNotFoundException, ConstraintsViolationException {
		cashFlowService.updateCreditDebit(cashFlowPeriod, CashFlowType.CREDIT, credit, null);
	}
	
	@PutMapping("/debit/{cashFlowPeriod}")
	public void updateDebit(@PathVariable final String cashFlowPeriod, @RequestParam final Double debit)
			throws EntityNotFoundException, ConstraintsViolationException {
		cashFlowService.updateCreditDebit(cashFlowPeriod, CashFlowType.DEBIT, null, debit);
	}


	@GetMapping
	public List<CashFlowDTO> findAllCashFlows() {
		return CashFlowMapper.makeCashFlowDTOList(cashFlowService.findAll());
	}
}
