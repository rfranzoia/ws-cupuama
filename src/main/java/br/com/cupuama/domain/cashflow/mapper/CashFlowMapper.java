package br.com.cupuama.domain.cashflow.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.cashflow.dto.CashFlowDTO;
import br.com.cupuama.domain.cashflow.entity.CashFlow;

public class CashFlowMapper {
	public static CashFlow makeCashFlow(CashFlowDTO cashFlow) {
		return new CashFlow(cashFlow.getPeriod(), cashFlow.getPreviousBalance(), cashFlow.getCredits(),cashFlow.getDebits());
	}

	public static CashFlowDTO makeDTO(CashFlow cashFlow) {
		CashFlowDTO.CashFlowDTOBuilder cashFlowDTOBuilder = CashFlowDTO.newBuilder().setPeriod(cashFlow.getPeriod())
				.setPreviousBalance(cashFlow.getPreviousBalance())
				.setCredits(cashFlow.getCredits())
				.setDebits(cashFlow.getDebits());

		return cashFlowDTOBuilder.createDTO();
	}

	public static List<CashFlowDTO> makeListDTO(Collection<CashFlow> cashFlows) {
		return cashFlows.stream().map(CashFlowMapper::makeDTO).collect(Collectors.toList());
	}
	
	public static List<CashFlow> makeList(Collection<CashFlowDTO> cashFlows) {
		return cashFlows.stream().map(CashFlowMapper::makeCashFlow).collect(Collectors.toList());
	}

}
