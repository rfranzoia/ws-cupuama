package br.com.cupuama.controller.mapper.cashflow;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.cashflow.CashFlow;
import br.com.cupuama.dto.CashFlowDTO;

public class CashFlowMapper {
	public static CashFlow makeCashFlow(CashFlowDTO cashFlow) {
		return new CashFlow(cashFlow.getPeriod(), cashFlow.getPreviousBalance(), cashFlow.getCredits(),cashFlow.getDebits());
	}

	public static CashFlowDTO makeCashFlowDTO(CashFlow cashFlow) {
		CashFlowDTO.CashFlowDTOBuilder cashFlowDTOBuilder = CashFlowDTO.newBuilder().setPeriod(cashFlow.getPeriod())
				.setPreviousBalance(cashFlow.getPreviousBalance())
				.setCredits(cashFlow.getCredits())
				.setDebits(cashFlow.getDebits());

		return cashFlowDTOBuilder.createCashFlowDTO();
	}

	public static List<CashFlowDTO> makeCashFlowDTOList(Collection<CashFlow> cashFlows) {
		return cashFlows.stream().map(CashFlowMapper::makeCashFlowDTO).collect(Collectors.toList());
	}

}
