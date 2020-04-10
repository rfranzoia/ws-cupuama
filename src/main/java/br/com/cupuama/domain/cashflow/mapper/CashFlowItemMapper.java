package br.com.cupuama.domain.cashflow.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.cashflow.dto.CashFlowItemDTO;
import br.com.cupuama.domain.cashflow.dto.CashFlowItemDTO.CashFlowItemDTOBuilder;
import br.com.cupuama.domain.cashflow.entity.CashFlowItem;
import br.com.cupuama.domain.cashflow.entity.DocumentType;

public class CashFlowItemMapper {
	public static CashFlowItem makeCashFlowItem(CashFlowItemDTO cashFlowItem) {
		return new CashFlowItem(cashFlowItem.getId(),
								cashFlowItem.getItemDate(), 
								cashFlowItem.getDocumentNumber(), 
								new DocumentType(cashFlowItem.getDocumentTypeId()), 
								cashFlowItem.getDescription(), 
								cashFlowItem.getType(), 
								cashFlowItem.getValue());
	}

	public static CashFlowItemDTO makeCashFlowItemDTO(CashFlowItem cashFlowItem) {
		CashFlowItemDTO.CashFlowItemDTOBuilder cashFlowItemDTOBuilder = CashFlowItemDTO.newBuilder()
				.setId(cashFlowItem.getId())
				.setItemDate(cashFlowItem.getItemDate())
				.setDocumentNumber(cashFlowItem.getDocumentNumber())
				.setDocumentTypeId(cashFlowItem.getDocumentType().getId())
				.setDocumentTypeName(cashFlowItem.getDocumentType().getName())
				.setDescription(cashFlowItem.getDescription())
				.setType(cashFlowItem.getType())
				.setValue(cashFlowItem.getValue());

		return cashFlowItemDTOBuilder.createCashFlowItemDTO();
	}

	public static List<CashFlowItemDTO> makeCashFlowItemDTOList(Collection<CashFlowItem> cashFlowItems) {
		return cashFlowItems.stream().map(CashFlowItemMapper::makeCashFlowItemDTO).collect(Collectors.toList());
	}

}
