package br.com.cupuama.domain.cashflow.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.cashflow.dto.DocumentTypeDTO;
import br.com.cupuama.domain.cashflow.entity.DocumentType;
import br.com.cupuama.domain.cashflow.mapper.DocumentTypeMapper;
import br.com.cupuama.domain.cashflow.repository.DocumentTypeRepository;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some documentType specific things.
 * <p/>
 */
@Service
public class DocumentTypeService extends DefaultService<DocumentType, Long> {

	public DocumentTypeService(final DocumentTypeRepository documentTypeRepository) {
		super(documentTypeRepository);
	}

	@Transactional
	public DocumentTypeDTO create(DocumentTypeDTO dto) throws ConstraintsViolationException {
		DocumentType documentType = DocumentTypeMapper.makeDocumentType(dto);
		return DocumentTypeMapper.makeDTO(create(documentType));
	}
	
	/**
	 * Update the location for a documentType.
	 *
	 * @param documentTypeId
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final Long documentTypeId, final String name) throws EntityNotFoundException {
		DocumentType documentType = findByIdChecked(documentTypeId);
		documentType.setName(name);
		documentType.getAudit().setDateUpdated(ZonedDateTime.now());
	}

	/**
	 * Find all documentTypes by name.
	 *
	 * @param name
	 */
	public List<DocumentType> findByName(final String name) {
		return ((DocumentTypeRepository) repository).findByNameLike(name + "%");
	}

}
