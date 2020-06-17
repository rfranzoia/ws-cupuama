package br.com.cupuama.domain.cashflow.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.cashflow.dto.CashTransactionDTO;
import br.com.cupuama.domain.cashflow.entity.CashTransaction;

public class CashTransactionMapper {
	public static CashTransaction makeCashTransaction(CashTransactionDTO dto) {
		return new CashTransaction(dto.getId(),
								dto.getItemDate(), 
								dto.getDocumentNumber(), 
								DocumentTypeMapper.makeDocumentType(dto.getDocumentType()),
								dto.getDescription(), 
								dto.getCashFlowType(), 
								dto.getValue());
	}

	public static CashTransactionDTO makeDTO(CashTransaction cashFlowItem) {
		CashTransactionDTO.CashTransactionDTOBuilder cashFlowItemDTOBuilder = CashTransactionDTO.newBuilder()
				.setId(cashFlowItem.getId())
				.setItemDate(cashFlowItem.getItemDate())
				.setDocumentNumber(cashFlowItem.getDocumentNumber())
				.setDocumentType(DocumentTypeMapper.makeDTO(cashFlowItem.getDocumentType()))
				.setDescription(cashFlowItem.getDescription())
				.setCashFlowType(cashFlowItem.getType())
				.setValue(cashFlowItem.getValue());

		return cashFlowItemDTOBuilder.createDTO();
	}

	public static List<CashTransactionDTO> makeListDTO(Collection<CashTransaction> cashFlowItems) {
		return cashFlowItems.stream().map(CashTransactionMapper::makeDTO).collect(Collectors.toList());
	}
	
	public static List<CashTransaction> makeList(Collection<CashTransactionDTO> dtos) {
		return dtos.stream().map(CashTransactionMapper::makeCashTransaction).collect(Collectors.toList());
	}

}
