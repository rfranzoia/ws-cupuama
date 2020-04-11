package br.com.cupuama.domain.cashflow.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.cashflow.dto.CashTransactionDTO;
import br.com.cupuama.domain.cashflow.entity.CashTransaction;
import br.com.cupuama.domain.cashflow.entity.DocumentType;

public class CashTransactionMapper {
	public static CashTransaction makeCashTransaction(CashTransactionDTO cashFlowItem) {
		return new CashTransaction(cashFlowItem.getId(),
								cashFlowItem.getItemDate(), 
								cashFlowItem.getDocumentNumber(), 
								new DocumentType(cashFlowItem.getDocumentTypeId()), 
								cashFlowItem.getDescription(), 
								cashFlowItem.getType(), 
								cashFlowItem.getValue());
	}

	public static CashTransactionDTO makeCashTransactionDTO(CashTransaction cashFlowItem) {
		CashTransactionDTO.CashTransactionDTOBuilder cashFlowItemDTOBuilder = CashTransactionDTO.newBuilder()
				.setId(cashFlowItem.getId())
				.setItemDate(cashFlowItem.getItemDate())
				.setDocumentNumber(cashFlowItem.getDocumentNumber())
				.setDocumentTypeId(cashFlowItem.getDocumentType().getId())
				.setDocumentTypeName(cashFlowItem.getDocumentType().getName())
				.setDescription(cashFlowItem.getDescription())
				.setType(cashFlowItem.getType())
				.setValue(cashFlowItem.getValue());

		return cashFlowItemDTOBuilder.createCashTransation();
	}

	public static List<CashTransactionDTO> makeCashTransactionDTOList(Collection<CashTransaction> cashFlowItems) {
		return cashFlowItems.stream().map(CashTransactionMapper::makeCashTransactionDTO).collect(Collectors.toList());
	}
	
	public static List<CashTransaction> makeCashTransationList(Collection<CashTransactionDTO> dtos) {
		return dtos.stream().map(CashTransactionMapper::makeCashTransaction).collect(Collectors.toList());
	}

}
