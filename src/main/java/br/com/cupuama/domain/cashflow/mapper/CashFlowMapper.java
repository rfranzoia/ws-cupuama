package br.com.cupuama.domain.cashflow.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.cashflow.dto.CashFlowDTO;
import br.com.cupuama.domain.cashflow.dto.CashFlowDTO.CashFlowDTOBuilder;
import br.com.cupuama.domain.cashflow.entity.CashFlow;

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
