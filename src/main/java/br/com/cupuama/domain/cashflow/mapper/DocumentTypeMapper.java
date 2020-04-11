package br.com.cupuama.domain.cashflow.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.cashflow.dto.DocumentTypeDTO;
import br.com.cupuama.domain.cashflow.entity.DocumentType;


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
