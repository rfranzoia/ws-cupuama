package br.com.cupuama.controller.mapper.cashflow;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.cashflow.DocumentType;
import br.com.cupuama.dto.DocumentTypeDTO;


public class DocumentTypeMapper {
	public static DocumentType makeDocumentType(DocumentTypeDTO documentType) {
		return new DocumentType(documentType.getName());
	}

	public static DocumentTypeDTO makeDocumentTypeDTO(DocumentType documentType) {
		DocumentTypeDTO.DocumentTypeDTOBuilder documentTypeDTOBuilder = DocumentTypeDTO.newBuilder()
				.setId(documentType.getId())
				.setName(documentType.getName());

		return documentTypeDTOBuilder.createDocumentTypeDTO();
	}

	public static List<DocumentTypeDTO> makeDocumentTypeDTOList(Collection<DocumentType> documentTypes) {
		return documentTypes.stream()
				.map(DocumentTypeMapper::makeDocumentTypeDTO)
				.collect(Collectors.toList());
	}

}
