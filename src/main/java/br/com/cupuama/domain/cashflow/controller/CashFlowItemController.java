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

import br.com.cupuama.domain.cashflow.dto.CashFlowItemDTO;
import br.com.cupuama.domain.cashflow.entity.CashFlowItem;
import br.com.cupuama.domain.cashflow.mapper.CashFlowItemMapper;
import br.com.cupuama.domain.cashflow.service.CashFlowItemService;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.exception.InvalidDateRange;

/**
 * All operations with a cashFlowItem will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("/v1/cashFlowItems")
public class CashFlowItemController {

	public static final SimpleDateFormat SEARCH_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private final CashFlowItemService cashFlowItemService;

	@Autowired
	public CashFlowItemController(final CashFlowItemService cashFlowItemService) {
		this.cashFlowItemService = cashFlowItemService;
	}

	@GetMapping("/{cashFlowItemId}")
	public CashFlowItemDTO getCashFlowItem(@PathVariable final long cashFlowItemId) throws EntityNotFoundException {
		return CashFlowItemMapper.makeCashFlowItemDTO(cashFlowItemService.find(cashFlowItemId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CashFlowItemDTO createCashFlowItem(@Valid @RequestBody final CashFlowItemDTO cashFlowItemDTO)
			throws ConstraintsViolationException, EntityNotFoundException {
		CashFlowItem cashFlowItem = CashFlowItemMapper.makeCashFlowItem(cashFlowItemDTO);
		return CashFlowItemMapper.makeCashFlowItemDTO(cashFlowItemService.addCashFlowItem(cashFlowItem));
	}

	@DeleteMapping("/{cashFlowItemId}")
	public void deleteCashFlowItem(@PathVariable final long cashFlowItemId) 
			throws ConstraintsViolationException, EntityNotFoundException {
		cashFlowItemService.removeCashFlowItem(cashFlowItemId);
	}

	@GetMapping("/dates")
	public List<CashFlowItemDTO> findByDateRange(@RequestParam(required = false) final String start, 
			@RequestParam(required = false) final String end) throws InvalidDateRange {
		try {
			Date startDate = (StringUtils.isEmpty(start))? SEARCH_DATE_FORMAT.parse(start): new Date();
			Date endDate = (StringUtils.isEmpty(end))? SEARCH_DATE_FORMAT.parse(end): new Date();
			
			if (endDate.before(startDate)) {
				throw new InvalidDateRange("The end date must be greater than start date");
			}
			
			return CashFlowItemMapper.makeCashFlowItemDTOList(cashFlowItemService.findByDateRange(startDate, endDate));
			
		} catch (InvalidDateRange | ParseException ex) {
			throw new InvalidDateRange("There was a problem while processing your request", ex);
		}
		
	}
	
	@GetMapping
	public List<CashFlowItemDTO> findAllCashFlowItems() {
		return CashFlowItemMapper.makeCashFlowItemDTOList(cashFlowItemService.findAll());
	}
}
